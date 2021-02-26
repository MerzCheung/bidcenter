package com.dyg.bidcenter.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.dyg.bidcenter.common.ServiceException;
import com.dyg.bidcenter.cons.RoleCons;
import com.dyg.bidcenter.entity.*;
import com.dyg.bidcenter.mapper.SupplierLabelMapper;
import com.dyg.bidcenter.mapper.SupplierMapper;
import com.dyg.bidcenter.mapper.UserMapper;
import com.dyg.bidcenter.model.SysUserModel;
import com.dyg.bidcenter.repository.SupplierRepository;
import com.dyg.bidcenter.service.RoleService;
import com.dyg.bidcenter.service.SupplierService;
import com.dyg.bidcenter.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author merz
 * @Description:
 */
@Service("supplierService")
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private SupplierMapper supplierMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SupplierLabelMapper supplierLabelMapper;

    @Transactional
    @Override
    public SysSupplierEntity add(SysSupplierEntity sysSupplierEntity) {
        SysSupplierEntity  s1 = supplierMapper.getSupplierByPhone(sysSupplierEntity.getPhoneNumber());
        SysSupplierEntity  s2 = supplierMapper.getSupplierByCompanyName(sysSupplierEntity.getCompanyName());
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if(s1!=null){
            request.getSession().removeAttribute("code");
            throw new ServiceException("该手机号已注册供应商!");
        }
        if(s2!=null){
            throw new ServiceException("该公司名已注册!");
        }
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setAccount(sysSupplierEntity.getAccount());
        sysUserEntity.setPassword(sysSupplierEntity.getPassword());
        // 创建帐号
        SysUserEntity user = userService.createUser(sysUserEntity);
        if (user != null) {
            // 创建角色
            SysRolesEntity rolesEntity = new SysRolesEntity();
            rolesEntity.setRole(RoleCons.SUPPLIER);
            SysRolesEntity role = roleService.getRole(rolesEntity);
            SysUsersRolesEntity usersRolesEntity = new SysUsersRolesEntity();
            usersRolesEntity.setUserId(user.getId());
            usersRolesEntity.setRoleId(role.getId());
            SysUsersRolesEntity usersRolesEntity1 = roleService.saveUserRole(usersRolesEntity);
            if (usersRolesEntity1.getId() != null) {
                // 创建供应商信息
                sysSupplierEntity.setUserId(user.getId());
                return supplierRepository.save(sysSupplierEntity);
            } else {
                throw new ServiceException("角色创建失败");
            }
        }
        return null;
    }

    @Transactional
    @Override
    public SysSupplierEntity update(SysSupplierEntity sysSupplierEntity) {
        SysSupplierEntity sysSupplierEntity1 = supplierRepository.findById(sysSupplierEntity.getId()).orElse(null);
        if (sysSupplierEntity1 != null) {
            if (!StringUtils.isEmpty(sysSupplierEntity.getPassword())) {
                SysUserModel sysUserModel = new SysUserModel();
                sysUserModel.setId(sysSupplierEntity.getUserId());
                sysUserModel.setPassword(sysSupplierEntity.getPassword());
                userService.updateUser(sysUserModel);
            }
            BeanUtil.copyProperties(sysSupplierEntity, sysSupplierEntity1, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
            // 修改供应商信息
            SysSupplierEntity save = supplierRepository.save(sysSupplierEntity1);
            // 修改标签
            if (CollectionUtils.isNotEmpty(sysSupplierEntity.getLabelList())) {
                List<SysSupplierLabelEntity> collect2 = sysSupplierEntity.getLabelList().stream().filter(item -> item.getId() != null).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(collect2)) {
                    List<SysSupplierLabelEntity> supplierLabel = supplierLabelMapper.getSupplierLabel(sysSupplierEntity.getId());
                    List<Integer> supplierLabelIds = supplierLabel.stream().map(SysSupplierLabelEntity::getId).collect(Collectors.toList());
                    List<Integer> collect2Ids = collect2.stream().map(SysSupplierLabelEntity::getId).collect(Collectors.toList());
                    List<Integer> deleteIds = supplierLabelIds.stream().filter(id -> !collect2Ids.contains(id)).collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(deleteIds)) {
                        supplierLabelMapper.deleteBatch(deleteIds);
                    }
                }
                List<SysSupplierLabelEntity> collect1 = sysSupplierEntity.getLabelList().stream().filter(item -> item.getId() == null).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(collect1)) {
                    supplierLabelMapper.save(sysSupplierEntity.getId(), collect1);
                }
            }
            return save;
        }
       return null;
    }

    @Override
    public Page<SysSupplierEntity> pageQuery(String kayword, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Specification<SysSupplierEntity> specification = new Specification<SysSupplierEntity>() {
            @Override
            public Predicate toPredicate(Root<SysSupplierEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> listAnd = new ArrayList<>();
                listAnd.add(criteriaBuilder.equal(root.get("isValid"), "1"));
                Predicate predicateAnd = criteriaBuilder.and(listAnd.toArray(new Predicate[0])); //AND查询加入查询条件

                if (!StringUtils.isEmpty(kayword)) {
                    List<Predicate> listOr = new ArrayList<>();
                    listOr.add(criteriaBuilder.like(root.get("companyName"), "%" + kayword + "%"));
                    listOr.add(criteriaBuilder.like(root.get("principal"), "%" + kayword + "%"));
                    listOr.add(criteriaBuilder.like(root.get("phoneNumber"), "%" + kayword + "%"));
                    listOr.add(criteriaBuilder.like(root.get("address"), "%" + kayword + "%"));
                    Predicate predicateOR = criteriaBuilder.or(listOr.toArray(new Predicate[0])); //OR查询加入查询条件
                    return criteriaQuery.where(predicateAnd, predicateOR).getRestriction();
                }
                return predicateAnd;
            }
        };
        Page<SysSupplierEntity> all = supplierRepository.findAll(specification, pageable);
        all.forEach(x->{
            SysUserEntity user = userMapper.getUserById(x.getUserId());
            x.setAccount(user.getAccount());
            List<SysSupplierLabelEntity> supplierLabel = supplierLabelMapper.getSupplierLabel(x.getId());
            x.setLabelList(supplierLabel);
        });
        return all;
    }

    @Override
    public void delete(List<Integer> ids) {
        ids.forEach(id -> {
            SysSupplierEntity sysSupplierEntity1 = supplierRepository.findById(id).orElse(null);
            Integer userId  =  sysSupplierEntity1.getUserId();
            if (sysSupplierEntity1 != null) {
                SysUserModel user =  new SysUserModel();
                user.setId(userId);
                user.setIsValid(0);
                userMapper.updateUser(user);
                sysSupplierEntity1.setIsValid(0);
                supplierRepository.save(sysSupplierEntity1);
            }
        });
    }

    @Override
    public List<SysSupplierEntity> queryAll() {
        SysSupplierEntity sysSupplierEntity = new SysSupplierEntity();
        sysSupplierEntity.setIsValid(1);
        Example<SysSupplierEntity> entityExample = Example.of(sysSupplierEntity);
        return supplierRepository.findAll(entityExample);
    }

    @Override
    public SysSupplierEntity findByAccount(String account) {
        if (StringUtils.isEmpty(account)) {
            return null;
        }
        return supplierMapper.findByAccount(account);
    }

    @Override
    public SysSupplierEntity find(SysSupplierEntity sysSupplierEntity) {
        Example<SysSupplierEntity> of = Example.of(sysSupplierEntity);
        return supplierRepository.findOne(of).orElse(null);
    }

    @Override
    public Map<Integer, String> getNameMap() {
        List<SysSupplierEntity> all = supplierRepository.findAll();
        return all.stream().collect(Collectors.toMap(SysSupplierEntity::getId, SysSupplierEntity::getCompanyName));
    }

    @Override
    public SysSupplierEntity getPhoneNumber(String id) {
        return supplierMapper.getPhoneNumber(id);
    }

    @Override
    public SysSupplierEntity getPhoneNumberById(Integer id) {
        return  supplierMapper.getPhoneNumberById(id);
    }
}

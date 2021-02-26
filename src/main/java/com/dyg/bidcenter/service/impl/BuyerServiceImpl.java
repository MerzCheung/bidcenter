package com.dyg.bidcenter.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.dyg.bidcenter.annotation.ConverterMethod;
import com.dyg.bidcenter.common.ServiceException;
import com.dyg.bidcenter.cons.RoleCons;
import com.dyg.bidcenter.entity.*;
import com.dyg.bidcenter.mapper.BuyerMapper;
import com.dyg.bidcenter.mapper.UserMapper;
import com.dyg.bidcenter.model.SysUserModel;
import com.dyg.bidcenter.repository.BuyerRepository;
import com.dyg.bidcenter.repository.UserRoleRepository;
import com.dyg.bidcenter.service.BuyerService;
import com.dyg.bidcenter.service.RoleService;
import com.dyg.bidcenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author merz
 * @Description:
 */
@Service("buyerService")
public class BuyerServiceImpl implements BuyerService{

    @Autowired
    private BuyerRepository buyerRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BuyerMapper buyerMapper;

    @Transactional
    @Override
    public SysBuyerEntity add(SysBuyerEntity sysBuyerEntity) {
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setAccount(sysBuyerEntity.getAccount());
        sysUserEntity.setPassword(sysBuyerEntity.getPassword());
        // 创建帐号
        SysUserEntity user = userService.createUser(sysUserEntity);
        if (user != null) {
            // 创建角色
            SysRolesEntity rolesEntity = new SysRolesEntity();
            rolesEntity.setRole(sysBuyerEntity.getPosition());
            SysRolesEntity role = roleService.getRole(rolesEntity);
            SysUsersRolesEntity usersRolesEntity = new SysUsersRolesEntity();
            usersRolesEntity.setUserId(user.getId());
            usersRolesEntity.setRoleId(role.getId());
            SysUsersRolesEntity usersRolesEntity1 = roleService.saveUserRole(usersRolesEntity);
            if (usersRolesEntity1.getId() != null) {
                // 创建采购员信息
                sysBuyerEntity.setUserId(user.getId());
                return buyerRepository.save(sysBuyerEntity);
            } else {
                throw new ServiceException("角色创建失败");
            }
        }
        return null;
    }

    @Transactional
    @Override
    public SysBuyerEntity update(SysBuyerEntity sysBuyerEntity) {
        String newPosition = sysBuyerEntity.getPosition();
        SysBuyerEntity sysBuyerEntity1 = buyerRepository.findById(sysBuyerEntity.getId()).orElse(null);
        if (sysBuyerEntity1 != null) {
            if (!StringUtils.isEmpty(sysBuyerEntity.getPassword())) {
                SysUserModel sysUserModel = new SysUserModel();
                sysUserModel.setId(sysBuyerEntity.getUserId());
                sysUserModel.setPassword(sysBuyerEntity.getPassword());
                userService.updateUser(sysUserModel);
            }

            String oldPosition = sysBuyerEntity1.getPosition();
            if (!newPosition.equals(oldPosition)) {
                SysUsersRolesEntity sysUsersRolesEntity = new SysUsersRolesEntity();
                sysUsersRolesEntity.setUserId(sysBuyerEntity.getUserId());
                Example<SysUsersRolesEntity> of = Example.of(sysUsersRolesEntity);
                SysUsersRolesEntity sysUsersRolesEntity1 = userRoleRepository.findOne(of).orElse(null);
                if (sysUsersRolesEntity1 != null) {
                    SysRolesEntity rolesEntity = new SysRolesEntity();
                    rolesEntity.setRole(sysBuyerEntity.getPosition());
                    SysRolesEntity role = roleService.getRole(rolesEntity);
                    sysUsersRolesEntity1.setRoleId(role.getId());
                    userRoleRepository.save(sysUsersRolesEntity1);
                }
            }
            BeanUtil.copyProperties(sysBuyerEntity, sysBuyerEntity1, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
            return buyerRepository.save(sysBuyerEntity1);
        }
       return null;
    }

    @Override
    @ConverterMethod
    public Page<SysBuyerEntity> pageQuery(String kayword, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Specification<SysBuyerEntity> specification = new Specification<SysBuyerEntity>() {
            @Override
            public Predicate toPredicate(Root<SysBuyerEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> listAnd = new ArrayList<>();
                listAnd.add(criteriaBuilder.equal(root.get("isValid"), "1"));
                Predicate predicateAnd = criteriaBuilder.and(listAnd.toArray(new Predicate[0])); //AND查询加入查询条件

                if (!StringUtils.isEmpty(kayword)) {
                    List<Predicate> listOr = new ArrayList<>();
                    listOr.add(criteriaBuilder.like(root.get("username"), "%" + kayword + "%"));
                    listOr.add(criteriaBuilder.like(root.get("position"), "%" + kayword + "%"));
                    listOr.add(criteriaBuilder.like(root.get("phoneNumber"), "%" + kayword + "%"));
                    listOr.add(criteriaBuilder.like(root.get("department"), "%" + kayword + "%"));
                    Predicate predicateOR = criteriaBuilder.or(listOr.toArray(new Predicate[0])); //OR查询加入查询条件
                    return criteriaQuery.where(predicateAnd, predicateOR).getRestriction();
                }
                return predicateAnd;
            }
        };
        Page<SysBuyerEntity> all = buyerRepository.findAll(specification, pageable);
        all.forEach(x->{
            SysUserEntity user = userMapper.getUserById(x.getUserId());
            x.setAccount(user.getAccount());
            x.setDepartmentStr(x.getDepartment());
            x.setPositionStr(x.getPosition());
        });
        return all;
    }

    @Override
    public void delete(List<Integer> ids) {
        ids.forEach(id -> {
            SysBuyerEntity sysBuyerEntity = buyerRepository.findById(id).orElse(null);
            Integer userId  =  sysBuyerEntity.getUserId();
            if (sysBuyerEntity != null) {
                SysUserModel user =  new SysUserModel();
                user.setId(userId);
                user.setIsValid(0);
                userMapper.updateUser(user);
                sysBuyerEntity.setIsValid(0);
                buyerRepository.save(sysBuyerEntity);
            }
        });
    }

    @Override
    @ConverterMethod
    public SysBuyerEntity findByAccount(String account) {
        if (StringUtils.isEmpty(account)) {
            return null;
        }
        SysBuyerEntity byAccount = buyerMapper.findByAccount(account);
        byAccount.setDepartmentStr(byAccount.getDepartment());
        byAccount.setPositionStr(byAccount.getPosition());
        return byAccount;
    }

    @Override
    public Map<String, String> getBuyerNameMap() {
        List<SysBuyerEntity> all = userMapper.getBuyer();
        return all.stream().collect(Collectors.toMap(SysBuyerEntity::getAccount, SysBuyerEntity::getUsername));
    }
}

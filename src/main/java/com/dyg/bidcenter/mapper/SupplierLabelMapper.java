package com.dyg.bidcenter.mapper;

import com.dyg.bidcenter.entity.SysSupplierLabelEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author merz
 * @Description:
 */
@Mapper
public interface SupplierLabelMapper {

    List<SysSupplierLabelEntity> getSupplierLabel(Integer supplierId);

    Integer save(@Param("supplierId") Integer supplierId, @Param("collect") List<SysSupplierLabelEntity> collect);

    Integer deleteBatch(@Param("collect") List<Integer> deleteIds);

    List<SysSupplierLabelEntity> findAll(Integer labelId);
}

package com.dyg.bidcenter.mapper;

import com.dyg.bidcenter.entity.SysBidDto;
import com.dyg.bidcenter.entity.SysBidEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author merz
 * @Description:
 */
@Mapper
public interface BidMapper {
    List<SysBidDto> pageQuery(@Param("kayword") String kayword, @Param("status") String status,
                              @Param("supplierId") Integer supplierId, @Param("department") String department,
                              @Param("pageNo") Integer pageNo, @Param("size") Integer size,
                              @Param("account") String account);

    Integer getTotal(@Param("kayword") String kayword, @Param("status") String status,
                     @Param("supplierId") Integer supplierId, @Param("department") String department,@Param("account") String account);



    //根据开标状态查询数据
    List<SysBidEntity> pageBidQuery(@Param("kayword") String kayword,  @Param("pageNo")Integer pageNo, @Param("size")Integer size,@Param("department") String department,@Param("account") String account);

    //根据开标状查询总条数
    Integer getStatusTotal(@Param("kayword")String kayword, @Param("department")String department,@Param("account") String account);
}

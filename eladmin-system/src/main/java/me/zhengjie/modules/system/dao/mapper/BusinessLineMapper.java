package me.zhengjie.modules.system.dao.mapper;

import me.zhengjie.modules.system.domain.entity.BusinessLineDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BusinessLineMapper {

    BusinessLineDO findByBusinessLineName(String name);

    Integer insertBusinessLine(BusinessLineDO businessLineDO);

    BusinessLineDO findByBusinessLineId(Long id);

    Integer deleteBusinessLine(Long id);

//    BusinessLineDO findBusinessLineById(@Param("id") int id);
//
//    List<BusinessLineDO> findAllBusinessLines();
}

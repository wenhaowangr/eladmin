package me.zhengjie.modules.system.dao;

import me.zhengjie.modules.system.entity.BusinessLineDO;
import me.zhengjie.modules.system.service.vo.BusinessLineManageFilter;

import java.util.List;

public interface BusinessLineMapper {

    BusinessLineDO findByBusinessLineName(String name);

    Integer insertBusinessLine(BusinessLineDO businessLineDO);

    BusinessLineDO findByBusinessLineId(int id);

    Integer deleteBusinessLine(int id);

    List<BusinessLineDO> queryBusinessLineByPage(BusinessLineManageFilter businessLineManageFilter);

    Integer queryBusinessLineTotalCount(BusinessLineManageFilter businessLineManageFilter);

    List<BusinessLineDO> findAllBusinessLines();

    Integer updateBusinessLine(BusinessLineDO businessLineDO);

    BusinessLineDO getBusinessLineByName(String name);

}

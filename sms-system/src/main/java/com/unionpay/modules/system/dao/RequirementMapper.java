package com.unionpay.modules.system.dao;

import com.unionpay.modules.system.entity.RequirementDO;
import com.unionpay.modules.system.service.vo.RequirementManageFilter;

import java.util.List;

public interface RequirementMapper {
    Integer insertRequirement(RequirementDO requirementDO);
    Integer updateRequirement(RequirementDO requirementDO);
    Integer deleteRequirement(int id);
    RequirementDO findByRequirementName(String name);

    RequirementDO findByRequirementId(int id);

    List<RequirementDO> queryRequirementByPage(RequirementManageFilter filter);

    Integer queryRequirementTotalCount(RequirementManageFilter filter);

    List<RequirementDO> queryReqByBusinessLineId(int businessLineId);

    RequirementDO getRequirementByName(String name);
}

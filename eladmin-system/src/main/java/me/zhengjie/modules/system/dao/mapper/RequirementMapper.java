package me.zhengjie.modules.system.dao.mapper;

import me.zhengjie.modules.system.domain.entity.RequirementDO;
import me.zhengjie.modules.system.domain.entity.RequirementManageFilter;
import me.zhengjie.modules.system.domain.vo.PageVO;

import java.util.List;

public interface RequirementMapper {
    Integer insertRequirement(RequirementDO requirementDO);
    Integer updateRequirement(RequirementDO requirementDO);
    Integer deleteRequirement(int id);
    RequirementDO findByRequirementName(String name);

    RequirementDO findByRequirementId(int id);

    List<RequirementDO> findByBusinessLineIdAndPage(RequirementManageFilter filter);

    Integer queryTotalCountByBusinessLineId(int businessLineId);
}

package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.entity.RequirementDO;
import me.zhengjie.modules.system.service.vo.RequirementManageFilter;
import me.zhengjie.modules.system.service.vo.PageVO;
import me.zhengjie.modules.system.service.dto.RequirementDTO;

public interface RequirementManageService {

    int add(RequirementDO requirementDO);

    int update(RequirementDO requirementDO);

    int delete(int id);

    PageVO<RequirementDTO> queryRequirement(RequirementManageFilter requirementManageFilter);

    RequirementDO getRequirementByName(String name);
}


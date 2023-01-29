package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.domain.entity.RequirementDO;
import me.zhengjie.modules.system.domain.entity.RequirementManageFilter;
import me.zhengjie.modules.system.domain.vo.PageVO;
import me.zhengjie.modules.system.domain.vo.RequirementVO;

public interface RequirementManageService {

    int add(RequirementDO requirementDO);

    int update(RequirementDO requirementDO);

    int delete(int id);

    PageVO<RequirementVO> queryRequirementAndTaskByPage(RequirementManageFilter requirementManageFilter);

    RequirementDO getRequirementByName(String name);
}


package com.unionpay.modules.system.service;

import com.unionpay.modules.system.entity.RequirementDO;
import com.unionpay.modules.system.service.vo.RequirementManageFilter;
import com.unionpay.modules.system.service.vo.PageVO;
import com.unionpay.modules.system.service.dto.RequirementDTO;

public interface RequirementManageService {

    int add(RequirementDO requirementDO);

    int update(RequirementDO requirementDO);

    int delete(int id);

    PageVO<RequirementDTO> queryRequirement(RequirementManageFilter requirementManageFilter);

    RequirementDO getRequirementByName(String name);
}


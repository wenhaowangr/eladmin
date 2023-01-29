package me.zhengjie.modules.system.domain.vo;

import lombok.Data;
import me.zhengjie.modules.system.domain.entity.RequirementDO;
import java.util.List;

@Data
public class RequirementVO extends RequirementDO {

    List<TaskVO> taskVOs;

    public RequirementVO() {

    }

    public RequirementVO(RequirementDO requirementDO) {

        this.setName(requirementDO.getName());
        this.setId(requirementDO.getId());
        this.setDueDate(requirementDO.getDueDate());
        this.setStatus(requirementDO.getStatus());
        this.setBusinessLineId(requirementDO.getBusinessLineId());
    }
}

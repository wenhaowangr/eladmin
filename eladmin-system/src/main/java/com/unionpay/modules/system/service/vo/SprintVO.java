package com.unionpay.modules.system.service.vo;

import lombok.Data;
import com.unionpay.modules.system.entity.SprintDO;

import java.util.List;

@Data
public class SprintVO {
    private String sprintName;
    private String quarter;
    private List<String> requirementNameList;

    public SprintVO(){

    }

    public SprintVO(SprintDO sprintDO){
        this.sprintName = sprintDO.getName();
        this.quarter = sprintDO.getQuarter();
    }

}

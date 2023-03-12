package me.zhengjie.modules.system.service.vo;

import lombok.Data;
import me.zhengjie.modules.system.domain.entity.SprintDO;

import java.util.HashSet;
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

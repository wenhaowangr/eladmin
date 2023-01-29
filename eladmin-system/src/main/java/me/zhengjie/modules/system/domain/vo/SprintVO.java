package me.zhengjie.modules.system.domain.vo;

import lombok.Data;
import me.zhengjie.modules.system.domain.entity.SprintDO;

import java.util.HashSet;

@Data
public class SprintVO {
    public String sprintName;
    HashSet<String> requirementNameList;

    public SprintVO(){

    }

    public SprintVO(SprintDO sprintDO){
        this.sprintName = sprintDO.getName();
    }

}

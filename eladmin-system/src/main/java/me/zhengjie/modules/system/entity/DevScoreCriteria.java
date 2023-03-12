package me.zhengjie.modules.system.entity;

import lombok.Data;

import java.util.List;

@Data
public class DevScoreCriteria {

    private List<Integer> sprintIds;
    private Integer businessLineId;

}

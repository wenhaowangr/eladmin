package me.zhengjie.modules.system.domain.vo;

import lombok.Data;

@Data
public class MyCurTaskVO {
    private String businessLineName;
    private String taskName;
    private double workload;
    public MyCurTaskVO(){

    }
    public MyCurTaskVO(TaskVO taskVO){
        this.businessLineName = taskVO.getBusinessLineName();
        this.taskName = taskVO.getName();
        this.workload = taskVO.getWorkload();
    }

}

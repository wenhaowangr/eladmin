package com.unionpay.modules.system.service.vo;

import lombok.Data;

@Data
public class MyCurTaskVO {
    private int taskId;
    private String businessLineName;
    private String taskName;
    private double workload;
    public MyCurTaskVO(){

    }
    public MyCurTaskVO(TaskVO taskVO){
        this.taskId = taskVO.getId();
        this.businessLineName = taskVO.getBusinessLineName();
        this.taskName = taskVO.getName();
        this.workload = taskVO.getWorkload();
    }

}

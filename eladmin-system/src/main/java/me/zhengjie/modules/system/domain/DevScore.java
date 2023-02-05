package me.zhengjie.modules.system.domain;

import lombok.Data;

@Data
public class DevScore {

    private Integer employeeId;
    private String employeeName;
    private Double workload;
    private Double workloadPercent;
}

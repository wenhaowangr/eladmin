package me.zhengjie.modules.system.service.dto;

import lombok.Data;

@Data
public class DevScoreDTO {

    private Integer employeeId;
    private String employeeName;
    private Double workload;
    private Double workloadPercent;
}

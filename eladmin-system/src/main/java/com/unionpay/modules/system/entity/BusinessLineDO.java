package com.unionpay.modules.system.entity;

import lombok.Data;


@Data
public class BusinessLineDO {

    private Integer id;
    private String name;
    private Integer managerId;
    private String memberIds;
    private String context;
    private String milestone;
    private Integer state;

}

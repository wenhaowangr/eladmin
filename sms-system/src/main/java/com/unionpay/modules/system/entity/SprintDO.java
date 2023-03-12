package com.unionpay.modules.system.entity;

import lombok.Data;

import java.util.Date;


@Data
public class SprintDO {

    private Integer id;
    private String name;
    private Date beginDate;
    private Date endDate;
    private String quarter; //所属季度,如2022Q4

}

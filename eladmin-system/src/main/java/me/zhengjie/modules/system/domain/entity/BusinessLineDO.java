package me.zhengjie.modules.system.domain.entity;

import lombok.Data;


@Data
public class BusinessLineDO {

    private Integer id;
    private String name;
    private String managerId;
    private String memberIds;
    private String context;
    private String milestone;
    private Integer state;

}

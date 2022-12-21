package me.zhengjie.modules.system.domain.entity;

import lombok.Data;


@Data
public class BusinessLineDO {

    private Integer id;
    private String name;
    private Integer manager_id;
    private String member_ids;
    private String context;
    private String milestone;
    private Integer state;

}

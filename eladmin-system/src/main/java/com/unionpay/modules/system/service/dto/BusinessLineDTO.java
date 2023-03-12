package com.unionpay.modules.system.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BusinessLineDTO implements Serializable {

    private Integer id;
    /**
     * 条线名称
     */
    private String name;

    /**
     * 保存时 前端必传
     */
    private Integer managerId;
    /**
     * 保存时 前端必传
     */
    private List<Integer> memberIds;

    /**
     * 查询时 后端返回
     */
    private String managerName;
    /**
     * 查询时 后端返回
     */
    private List<String> memberNames;

    private String context;
    private String milestone;

}

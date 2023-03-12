package me.zhengjie.modules.system.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用于枚举出所有条线以及该条线下的所有需求
 */
@Data
public class BusinessLineVO implements Serializable {

    private int businessLineId;
    private String businessLineName;

    private List<Integer> requirementIds;
    private List<String> requirementNames;

}

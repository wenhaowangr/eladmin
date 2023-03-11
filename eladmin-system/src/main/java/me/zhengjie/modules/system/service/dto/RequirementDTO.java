package me.zhengjie.modules.system.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class RequirementDTO implements Serializable {

    private Integer id;
    private String name;
    private Integer businessLineId;
    private String businessLineName;
    private Date dueDate;
    private Integer status;

}

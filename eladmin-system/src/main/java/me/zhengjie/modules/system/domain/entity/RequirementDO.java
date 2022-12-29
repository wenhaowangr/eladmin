package me.zhengjie.modules.system.domain.entity;

import lombok.Data;
import java.util.Date;

@Data
public class RequirementDO {
    private Integer id;
    private String name;
    private Integer businessLineId;
    private Date dueDate;
    private Integer status;

}

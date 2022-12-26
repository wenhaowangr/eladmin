package me.zhengjie.modules.system.domain.entity;

import lombok.Data;
import me.zhengjie.modules.system.domain.vo.TaskVO;

import java.util.Date;

@Data
public class WorkLoadDO {

    private Integer id;
    private Integer taskId;
    private Integer businessLineId;
    private Integer requirementId;
    private Integer sprintId;
    private Integer devEmployeeId;
    private Double workLoad;
    private String type;
    private String quarter;

    public WorkLoadDO() {
    }


    public WorkLoadDO(TaskVO taskVO) {
        this.taskId = taskVO.getId();
        this.businessLineId = taskVO.getBusinessLineId();
        this.requirementId = taskVO.getRequirementId();
        this.sprintId = taskVO.getSprintId();
        this.devEmployeeId = taskVO.getDevEmployeeId();
        this.workLoad = taskVO.getWorkload();
    }

}

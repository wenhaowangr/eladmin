package me.zhengjie.modules.system.domain.entity;

import lombok.Data;
import me.zhengjie.modules.system.domain.vo.TaskDTO;

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


    public WorkLoadDO(TaskDTO taskDTO) {
        this.taskId = taskDTO.getId();
        this.businessLineId = taskDTO.getBusinessLineId();
        this.requirementId = taskDTO.getRequirementId();
        this.sprintId = taskDTO.getSprintId();
        this.devEmployeeId = taskDTO.getDevEmployeeId();
        this.workLoad = taskDTO.getWorkload();
    }

}

package me.zhengjie.modules.system.dao.mapper;

import io.swagger.models.auth.In;
import me.zhengjie.modules.system.domain.entity.TaskDO;
import me.zhengjie.modules.system.domain.entity.TaskFilter;
import me.zhengjie.modules.system.domain.vo.TaskVO;

import java.util.List;

public interface TaskMapper {

    Integer insertTask(TaskDO taskDO);

    Integer deleteTask(int id);

    Integer updateTask(TaskDO taskDO);

    List<TaskDO> findAllTask();

    List<TaskVO> queryTaskByPage(TaskFilter taskFilter);

    Integer queryTaskTotalCount(TaskFilter taskFilter);

    List<TaskDO> queryTaskByRequirementId(int requirementId);

    void updateTaskStatus(int id, int status);
}

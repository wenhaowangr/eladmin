package me.zhengjie.modules.system.dao.mapper;

import io.swagger.models.auth.In;
import me.zhengjie.modules.system.domain.entity.TaskDO;
import me.zhengjie.modules.system.domain.entity.TaskFilter;

import java.util.List;

public interface TaskMapper {

    Integer insertTask(TaskDO taskDO);

    Integer deleteTask(int id);

    Integer updateTask(TaskDO taskDO);

    List<TaskDO> findAllTask();

    List<TaskDO> queryTaskByPage(TaskFilter taskFilter);

    Integer queryTaskTotalCount(TaskFilter taskFilter);

}

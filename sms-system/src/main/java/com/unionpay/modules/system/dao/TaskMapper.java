package com.unionpay.modules.system.dao;

import com.unionpay.modules.system.entity.TaskDO;
import com.unionpay.modules.system.service.vo.TaskFilter;
import com.unionpay.modules.system.entity.TaskInfoDO;
import com.unionpay.modules.system.service.vo.TaskVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskMapper {

    Integer insertTask(TaskDO taskDO);

    Integer deleteTask(int id);

    Integer updateTask(TaskDO taskDO);

    List<TaskDO> findAllTask();

    List<TaskVO> queryTaskByPage(TaskFilter taskFilter);

    Integer queryTaskTotalCount(TaskFilter taskFilter);

    List<TaskDO> queryTaskByRequirementId(int requirementId);

    List<TaskDO> queryTaskBySprintId(int sprintId);

    List<TaskDO> queryTaskBySprintIds(@Param("sprintIds") List<Integer> sprintIds);

    List<TaskInfoDO> queryWorkloadOfTaskBySprintIds(@Param("sprintIds") List<Integer> sprintIds);

    void updateTaskStatus(int id, int status);

    TaskDO queryTaskById(int id);
}

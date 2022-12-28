package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.domain.entity.TaskDO;
import me.zhengjie.modules.system.domain.entity.TaskFilter;
import me.zhengjie.modules.system.domain.vo.PageVO;
import me.zhengjie.modules.system.domain.vo.TaskVO;

import java.util.List;

public interface TaskManageService {

    PageVO<TaskVO> queryTaskByPage(TaskFilter filter);

    int add(TaskDO taskDO);

    int delete(int id);

    List<TaskDO> findAllTask();

    int update(TaskDO taskDO);

}

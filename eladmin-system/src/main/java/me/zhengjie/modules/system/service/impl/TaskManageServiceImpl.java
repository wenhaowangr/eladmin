package me.zhengjie.modules.system.service.impl;

import me.zhengjie.modules.system.dao.mapper.TaskMapper;
import me.zhengjie.modules.system.domain.entity.TaskDO;
import me.zhengjie.modules.system.domain.entity.TaskFilter;
import me.zhengjie.modules.system.domain.vo.PageVO;
import me.zhengjie.modules.system.service.TaskManageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TaskManageServiceImpl implements TaskManageService {

    @Resource
    TaskMapper taskMapper;

    @Override
    public PageVO<TaskDO> queryTaskByPage(TaskFilter filter) {

        List<TaskDO> taskDOList = taskMapper.queryTaskByPage(filter);

        int pageNum = filter.getPageNum();
        int pageSize = filter.getPageSize();
        int taskTotalCount = taskMapper.queryTaskTotalCount(filter);

        return new PageVO<>(pageSize, pageNum, taskTotalCount, taskDOList);
    }

    @Override
    public int add(TaskDO taskDO) {

        return taskMapper.insertTask(taskDO);
    }

    @Override
    public int delete(int id) {

        return taskMapper.deleteTask(id);
    }

    @Override
    public List<TaskDO> findAllTask() {

        return taskMapper.findAllTask();
    }

    @Override
    public int update(TaskDO taskDO) {

        return taskMapper.updateTask(taskDO);
    }
}

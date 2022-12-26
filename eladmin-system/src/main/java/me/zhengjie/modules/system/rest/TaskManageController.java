package me.zhengjie.modules.system.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.zhengjie.modules.system.dao.mapper.WorkloadMapper;
import me.zhengjie.modules.system.domain.entity.TaskDO;
import me.zhengjie.modules.system.domain.entity.TaskFilter;
import me.zhengjie.modules.system.domain.entity.WorkLoadDO;
import me.zhengjie.modules.system.domain.vo.PageVO;
import me.zhengjie.modules.system.domain.vo.TaskVO;
import me.zhengjie.modules.system.service.TaskManageService;
import me.zhengjie.modules.system.service.WorkloadService;
import me.zhengjie.utils.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "任务管理")
@RestController
@RequestMapping("/task")
public class TaskManageController {


    @Resource
    TaskManageService taskManageService;

    @Resource
    WorkloadService workloadService;

    @Resource
    WorkloadMapper workloadMapper;


    @ApiOperation("查询任务")
    @PostMapping(value = "/query")
    public ResponseEntity<Object> queryTask(@RequestBody TaskFilter filter) {
        PageVO<TaskDO> taskDOPageVO = taskManageService.queryTaskByPage(filter);
        return new ResponseEntity<>(taskDOPageVO, HttpStatus.OK);
    }

    @ApiOperation("编辑任务")
    @PostMapping(value = "/update")
    public ResponseEntity<Object> updateTask(@RequestBody TaskVO taskVO) {
        TaskDO taskDO = new TaskDO(taskVO);
        int rowAffected = taskManageService.update(taskDO);
        if (taskVO.getWorkload()!=null && taskVO.getWorkload()!=0
                && taskVO.getDevEmployeeId()!=null && taskVO.getDevEmployeeId()!=0
                && taskVO.getSprintId()!=null && taskVO.getSprintId()!=0){
            int workloadRowAffected = workloadService.addRWWorkLoad(taskVO);
        } else if (workloadMapper.findRWWorkLoadByTaskId(taskVO.getId()) != null){
            workloadService.deleteRWWorkLoad(taskVO.getId());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("新增任务")
    @PostMapping(value = "/add")
    public ResponseEntity<Object> addTask(@RequestBody TaskVO taskVO) {
        TaskDO taskDO = new TaskDO(taskVO);
        int taskAffected = taskManageService.add(taskDO);
        if ( taskAffected > 0){
            int taskId = taskDO.getId();
            taskVO.setId(taskId);
            if (taskVO.getWorkload()!=null && taskVO.getWorkload()!=0
                    && taskVO.getDevEmployeeId()!=null && taskVO.getDevEmployeeId()!=0
                    && taskVO.getSprintId()!=null && taskVO.getSprintId()!=0){
                int workloadRowAffected = workloadService.addRWWorkLoad(taskVO);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation("删除任务")
    @PostMapping(value = "/delete")
    public ResponseEntity<Object> deleteTask(@RequestBody int id) {
        int rowAffected = taskManageService.delete(id);
        if (workloadMapper.findRWWorkLoadByTaskId(id) != null){
            workloadService.deleteRWWorkLoad(id);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


}

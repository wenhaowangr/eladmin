package me.zhengjie.modules.system.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.zhengjie.modules.system.dao.mapper.WorkloadMapper;
import me.zhengjie.modules.system.domain.entity.TaskDO;
import me.zhengjie.modules.system.domain.entity.TaskFilter;
import me.zhengjie.modules.system.domain.vo.PageVO;
import me.zhengjie.modules.system.domain.vo.TaskDTO;
import me.zhengjie.modules.system.domain.vo.TaskVO;
import me.zhengjie.modules.system.service.TaskManageService;
import me.zhengjie.modules.system.service.WorkloadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping(value = "/query")
    public ResponseEntity<Object> queryTask(@ModelAttribute TaskFilter filter) {
        PageVO<TaskVO> taskDOPageVO = taskManageService.queryTaskByPage(filter);
        return new ResponseEntity<>(taskDOPageVO, HttpStatus.OK);
    }

    @ApiOperation("编辑任务")
    @PostMapping(value = "/update")
    public ResponseEntity<Object> updateTask(@RequestBody TaskDTO taskDTO) {
        TaskDO taskDO = new TaskDO(taskDTO);
        int rowAffected = taskManageService.update(taskDO);
        if (taskDTO.getWorkload()!=null && taskDTO.getWorkload()!=0
                && taskDTO.getDevEmployeeId()!=null && taskDTO.getDevEmployeeId()!=0
                && taskDTO.getSprintId()!=null && taskDTO.getSprintId()!=0){
            int workloadRowAffected = workloadService.addRWWorkLoad(taskDTO);
        } else if (workloadMapper.findRWWorkLoadByTaskId(taskDTO.getId()) != null){
            workloadService.deleteRWWorkLoad(taskDTO.getId());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("新增任务")
    @PostMapping(value = "/add")
    public ResponseEntity<Object> addTask(@RequestBody TaskDTO taskDTO) {
        TaskDO taskDO = new TaskDO(taskDTO);
        int taskAffected = taskManageService.add(taskDO);
        if ( taskAffected > 0){
            int taskId = taskDO.getId();
            taskDTO.setId(taskId);
            if (taskDTO.getWorkload()!=null && taskDTO.getWorkload()!=0
                    && taskDTO.getDevEmployeeId()!=null && taskDTO.getDevEmployeeId()!=0
                    && taskDTO.getSprintId()!=null && taskDTO.getSprintId()!=0){
                int workloadRowAffected = workloadService.addRWWorkLoad(taskDTO);
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

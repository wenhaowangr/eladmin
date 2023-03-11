package me.zhengjie.modules.system.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.domain.entity.EmployeeDO;
import me.zhengjie.modules.system.domain.vo.SprintVO;
import me.zhengjie.modules.system.service.EmployeeManageService;
import me.zhengjie.modules.system.service.SprintManageService;
import me.zhengjie.utils.SecurityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Api(tags = "首页")
@RestController
@RequestMapping("/index")
@RequiredArgsConstructor
public class HomeController {

    @Resource
    SprintManageService sprintManageService;
    @Resource
    EmployeeManageService employeeManageService;

    @ApiOperation("查询当前时间所在冲刺")
    @GetMapping(value = "/getCurSprint")
    public ResponseEntity<Object> getCurSprint() {
        return new ResponseEntity<>(sprintManageService.getSprintByDate(new Date()), HttpStatus.OK);
    }

    @ApiOperation("查询冲刺列表")
    @GetMapping(value = "/getSprintAndReq")
    public ResponseEntity<Object> getAllSprintAndReq() {
        List<SprintVO> res =  sprintManageService.getSprintAndReq();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @ApiOperation("我的任务(本冲刺)")
    @GetMapping(value = "/getMyCurTask")
    public ResponseEntity<Object> getMyCurTask() {
        String currentUsername = SecurityUtils.getCurrentUsername();
        return new ResponseEntity<>(sprintManageService.getMyCurTask(currentUsername), HttpStatus.OK);
    }


    @ApiOperation("查询开发者")
    @GetMapping(value = "/getAllEmployee")
    public ResponseEntity<Object> getAllEmployee() {
        return new ResponseEntity<>(employeeManageService.getAllEmployee(), HttpStatus.OK);
    }


}

package me.zhengjie.modules.system.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.service.vo.SprintVO;
import me.zhengjie.modules.system.service.BusinessLineManageService;
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
    @Resource
    BusinessLineManageService businessLineManageService;

    @ApiOperation("查询当前时间所在冲刺")
    @GetMapping(value = "/getCurSprint")
    public ResponseEntity<Object> getCurSprint() {
        return new ResponseEntity<>(sprintManageService.getSprintByDate(new Date()), HttpStatus.OK);
    }

    @ApiOperation("冲刺日历")
    @GetMapping(value = "/getSprintAndReq")
    public ResponseEntity<Object> getAllSprintAndReq() {
        List<SprintVO> res =  sprintManageService.getSprintAndReq();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @ApiOperation("我的任务(本冲刺)")
    @GetMapping(value = "/getMyCurTask")
    public ResponseEntity<Object> getMyCurTask() {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        return new ResponseEntity<>(sprintManageService.getMyCurTask(currentUserId), HttpStatus.OK);
    }

    @ApiOperation("查询开发者")
    @GetMapping(value = "/getAllEmployee")
    public ResponseEntity<Object> getAllEmployee() {
        return new ResponseEntity<>(employeeManageService.getAllEmployee(), HttpStatus.OK);
    }

    @ApiOperation("冲刺列表")
    @GetMapping(value = "/getAllSprint")
    public ResponseEntity<Object> getAllSprint() {
        return new ResponseEntity<>(sprintManageService.getAllSprint(), HttpStatus.OK);
    }

    @ApiOperation("查询所有条线及下属需求")
    @GetMapping(value = "/getAllBusinessLineAndReq")
    public ResponseEntity<Object> getAllBusinessLineAndReq() {
        return new ResponseEntity<>(businessLineManageService.getAllBusinessLineAndReq(), HttpStatus.OK);
    }

}

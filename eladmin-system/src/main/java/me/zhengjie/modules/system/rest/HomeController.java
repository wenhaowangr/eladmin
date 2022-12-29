package me.zhengjie.modules.system.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.domain.vo.PageVO;
import me.zhengjie.modules.system.domain.vo.RequirementVO;
import me.zhengjie.modules.system.domain.vo.SprintVO;
import me.zhengjie.modules.system.service.SprintManageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "首页")
@RestController
@RequestMapping("/index")
@RequiredArgsConstructor
public class HomeController {

    @Resource
    SprintManageService sprintManageService;

    @ApiOperation("查询当前时间所在冲刺")
    @GetMapping(value = "/getCurSprint")
    public ResponseEntity<Object> getCurSprint() {
        return new ResponseEntity<>(sprintManageService.getCurSprint(), HttpStatus.OK);
    }

    @ApiOperation("查询冲刺列表")
    @GetMapping(value = "/getSprintAndReq")
    public ResponseEntity<Object> getAllSprintAndReq() {
        List<SprintVO> res =  sprintManageService.getSprintAndReq();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}

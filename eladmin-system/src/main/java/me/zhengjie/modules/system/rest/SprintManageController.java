
package me.zhengjie.modules.system.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.domain.entity.SprintDO;
import me.zhengjie.modules.system.domain.entity.SprintManageFilter;
import me.zhengjie.modules.system.domain.vo.PageVO;
import me.zhengjie.modules.system.service.*;
import me.zhengjie.modules.system.service.SprintManageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * @author Zheng Jie
 * @date 2018-11-23
 */
@Api(tags = "冲刺管理")
@RestController
@RequestMapping("/sprint")
@RequiredArgsConstructor
public class SprintManageController {

    @Resource
    SprintManageService sprintManageService;

    @ApiOperation("新增冲刺")
    @PostMapping(value = "/add")
    public ResponseEntity<Object> addSprint(@Validated @RequestBody SprintDO sprintDO) {
        int rowAffected = sprintManageService.add(sprintDO);
        if (rowAffected > 0) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ApiOperation("删除冲刺")
    @PostMapping(value = "/delete")
    public ResponseEntity<Object> deleteSprint(@RequestBody int id) {
        int rowAffected = sprintManageService.delete(id);
        if (rowAffected > 0) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ApiOperation("查询冲刺")
    @GetMapping(value = "/query")
    public ResponseEntity<Object> querySprint(@ModelAttribute SprintManageFilter filter) {
        PageVO<SprintDO> sprintDOPageVO = sprintManageService.querySprintByPage(filter);
        return new ResponseEntity<>(sprintDOPageVO, HttpStatus.OK);
    }

    @ApiOperation("编辑冲刺")
    @PostMapping(value = "/update")
    public ResponseEntity<Object> updateSprint(@ModelAttribute SprintDO sprintDO) {
        int rowAffected = sprintManageService.update(sprintDO);
        if (rowAffected > 0) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


}

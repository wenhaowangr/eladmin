
package me.zhengjie.modules.system.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.CheckUtils;
import me.zhengjie.modules.system.domain.entity.*;
import me.zhengjie.modules.system.domain.vo.PageVO;
import me.zhengjie.modules.system.service.*;
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
@Api(tags = "条线管理")
@RestController
@RequestMapping("/businessLine")
@RequiredArgsConstructor
public class BusinessLineController {

    @Resource
    BusinessLineManageService businessLineManageService;

    @ApiOperation("新增条线")
    @PostMapping(value = "/add")
    public ResponseEntity<Object> addBusinessLine(@Validated @RequestBody BusinessLineDO businessLineDO){
        int rowAffected = businessLineManageService.add(businessLineDO);
        if (rowAffected > 0){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ApiOperation("删除条线")
    @PostMapping(value = "/delete")
    public ResponseEntity<Object> deleteBusinessLine(@RequestBody int id){
        int rowAffected = businessLineManageService.delete(id);
        if (rowAffected > 0) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ApiOperation("查询条线")
    @GetMapping(value = "/query")
    public ResponseEntity<Object> queryBusinessLine(@ModelAttribute BusinessLineManageFilter filter) {

        CheckUtils.checkMemberIds("成员ID格式错误!", filter.getMemberIds());
        PageVO<BusinessLineDO> sprintDOPageVO = businessLineManageService.queryBusinessLineByPage(filter);
        return new ResponseEntity<>(sprintDOPageVO, HttpStatus.OK);
    }

    @ApiOperation("编辑条线")
    @PostMapping(value = "/update")
    public ResponseEntity<Object> updateBusinessLine(@RequestBody BusinessLineDO businessLineDO) {

        CheckUtils.checkMemberIds("成员ID格式错误!", businessLineDO.getMemberIds());
        int rowAffected = businessLineManageService.update(businessLineDO);
        if (rowAffected > 0) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }



}

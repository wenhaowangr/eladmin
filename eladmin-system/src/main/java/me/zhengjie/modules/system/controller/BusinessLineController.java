
package me.zhengjie.modules.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.service.vo.BusinessLineManageFilter;
import me.zhengjie.modules.system.service.vo.PageVO;
import me.zhengjie.modules.system.service.*;
import me.zhengjie.modules.system.service.dto.BusinessLineDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Object> addBusinessLine(@Validated @RequestBody BusinessLineDTO businessLineDTO){
        int rowAffected = businessLineManageService.add(businessLineDTO);
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

        PageVO<BusinessLineDTO> businessLineByPage = businessLineManageService.queryBusinessLineByPage(filter);
        return new ResponseEntity<>(businessLineByPage, HttpStatus.OK);
    }

    @ApiOperation("编辑条线")
    @PostMapping(value = "/update")
    public ResponseEntity<Object> updateBusinessLine(@RequestBody BusinessLineDTO businessLineDTO) {

        int rowAffected = businessLineManageService.update(businessLineDTO);
        if (rowAffected > 0) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


}

package me.zhengjie.modules.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.zhengjie.modules.system.entity.RequirementDO;
import me.zhengjie.modules.system.service.vo.RequirementManageFilter;
import me.zhengjie.modules.system.service.vo.PageVO;
import me.zhengjie.modules.system.service.RequirementManageService;
import me.zhengjie.modules.system.service.dto.RequirementDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "需求管理")
@RestController
@RequestMapping("/requirement")
public class RequirementManageController {
    @Resource
    RequirementManageService requirementManageService;

    @ApiOperation("查询需求")
    @GetMapping(value = "/query")
    public ResponseEntity<Object> queryRequirement(@ModelAttribute RequirementManageFilter requirementManageFilter) {

        PageVO<RequirementDTO> requirementVOPageVO = requirementManageService.queryRequirement(requirementManageFilter);
        return new ResponseEntity<>(requirementVOPageVO, HttpStatus.OK);
    }

    @ApiOperation("编辑需求")
    @PostMapping(value = "/update")
    public ResponseEntity<Object> updateTask(@RequestBody RequirementDO requirementDO) {
        int rowAffected = requirementManageService.update(requirementDO);
        if ( rowAffected > 0){
            return new ResponseEntity<>(HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation("新增需求")
    @PostMapping(value = "/add")
    public ResponseEntity<Object> addTask(@RequestBody RequirementDO requirementDO) {
        int rowAffected = requirementManageService.add(requirementDO);
        if ( rowAffected > 0){
            return new ResponseEntity<>(HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation("删除需求")
    @PostMapping(value = "/delete")
    public ResponseEntity<Object> deleteTask(@RequestBody int id) {
        int rowAffected = requirementManageService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

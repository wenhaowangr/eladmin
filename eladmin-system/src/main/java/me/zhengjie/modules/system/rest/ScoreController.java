package me.zhengjie.modules.system.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.zhengjie.modules.system.domain.DevScore;
import me.zhengjie.modules.system.domain.entity.DevScoreCriteria;
import me.zhengjie.modules.system.service.ScoreManageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "量化得分")
@RestController
@RequestMapping("/score")
public class ScoreController {


    @Resource
    ScoreManageService scoreManageService;


    @ApiOperation("开发量化得分")
    @GetMapping(value = "/queryDevScore")
    public ResponseEntity<Object> queryTask(@ModelAttribute DevScoreCriteria criteria) {
        List<DevScore> devScoreList = scoreManageService.queryDevScore(criteria);
        return new ResponseEntity<>(devScoreList, HttpStatus.OK);
    }

}

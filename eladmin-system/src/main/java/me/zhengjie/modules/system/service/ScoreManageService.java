package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.domain.DevScore;
import me.zhengjie.modules.system.domain.entity.DevScoreCriteria;


import java.util.List;


public interface ScoreManageService {


    /**
     * 查询开发任务量化得分
     * @param criteria
     * @return
     */
    List<DevScore> queryDevScore(DevScoreCriteria criteria);

}

package com.unionpay.modules.system.service;

import com.unionpay.modules.system.service.dto.DevScoreDTO;
import com.unionpay.modules.system.entity.DevScoreCriteria;


import java.util.List;


public interface ScoreManageService {


    /**
     * 查询开发任务量化得分
     * @param criteria
     * @return
     */
    List<DevScoreDTO> queryDevScore(DevScoreCriteria criteria);

}

package com.unionpay.modules.system.service;

import com.unionpay.modules.system.entity.BusinessLineDO;
import com.unionpay.modules.system.service.vo.BusinessLineManageFilter;
import com.unionpay.modules.system.service.vo.BusinessLineVO;
import com.unionpay.modules.system.service.vo.PageVO;
import com.unionpay.modules.system.service.dto.BusinessLineDTO;

import java.util.List;

public interface BusinessLineManageService {

    int add(BusinessLineDTO businessLineDTO);

    int delete(int id);

    List<BusinessLineDO> findAllBusinessLines();

    PageVO<BusinessLineDTO> queryBusinessLineByPage(BusinessLineManageFilter filter);

    int update(BusinessLineDTO businessLineDTO);

    BusinessLineDO getBusinessLineByName(String name);

    List<BusinessLineVO> getAllBusinessLineAndReq();
}

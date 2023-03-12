package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.entity.BusinessLineDO;
import me.zhengjie.modules.system.service.vo.BusinessLineManageFilter;
import me.zhengjie.modules.system.service.vo.BusinessLineVO;
import me.zhengjie.modules.system.service.vo.PageVO;
import me.zhengjie.modules.system.service.dto.BusinessLineDTO;

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

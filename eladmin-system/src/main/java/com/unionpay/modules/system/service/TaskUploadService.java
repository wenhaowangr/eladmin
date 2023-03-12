package com.unionpay.modules.system.service;

import com.unionpay.modules.system.service.dto.TaskDTO;

import java.util.List;
import java.util.Map;

public interface TaskUploadService {

    List<TaskDTO> excel2TaskDOList(Map<Integer, List<String>> excelInfo, Map<Integer, String> resultMap);

}

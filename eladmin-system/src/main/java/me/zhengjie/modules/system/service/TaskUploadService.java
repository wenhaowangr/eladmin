package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.service.dto.TaskDTO;

import java.util.List;
import java.util.Map;

public interface TaskUploadService {

    List<TaskDTO> excel2TaskDOList(Map<Integer, List<String>> excelInfo, Map<Integer, String> resultMap);

}

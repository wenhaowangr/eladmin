package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.domain.entity.TaskDO;

import java.util.List;
import java.util.Map;

public interface TaskUploadService {

    List<TaskDO> excel2TaskDOList(Map<Integer, List<String>> excelInfo, Map<Integer, String> resultMap);

}

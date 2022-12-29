package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.domain.entity.TaskDO;
import me.zhengjie.modules.system.domain.entity.TaskFilter;
import me.zhengjie.modules.system.domain.vo.PageVO;
import me.zhengjie.modules.system.domain.vo.TaskVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface TaskManageService {

    PageVO<TaskVO> queryTaskByPage(TaskFilter filter);

    int add(TaskDO taskDO);

    int delete(int id);

    List<TaskDO> findAllTask();

    int update(TaskDO taskDO);

    List<TaskDO> queryTaskByRequirementId(int requirementId);

    void download(List<TaskVO> taskVOList, HttpServletResponse response) throws IOException;

    void upload(MultipartFile file);

}

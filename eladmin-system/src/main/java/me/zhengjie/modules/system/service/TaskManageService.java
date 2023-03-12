package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.domain.entity.TaskDO;
import me.zhengjie.modules.system.service.vo.TaskFilter;
import me.zhengjie.modules.system.domain.entity.TaskInfoDO;
import me.zhengjie.modules.system.service.vo.PageVO;
import me.zhengjie.modules.system.service.vo.TaskVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface TaskManageService {

    PageVO<TaskVO> queryTaskByPage(TaskFilter filter);

    int add(TaskDO taskDO);

    int delete(int id);

    void batchDelete(List<Integer> ids);

    List<TaskDO> findAllTask();

    int update(TaskDO taskDO);

    /**
     * 给定需求ID，查询需求下的任务信息
     * @param requirementId
     * @return
     */
    List<TaskDO> queryTaskByRequirementId(int requirementId);

    /**
     * 给定冲刺ID，查询冲刺ID下的任务信息
     * @param sprintId
     * @return
     */
    List<TaskDO> queryTaskBySprintId(int sprintId);

    /**
     * 给定冲刺ID，查询冲刺ID下的任务信息
     * @param sprintIds
     * @return
     */
    Map<Integer, List<TaskDO>> queryTaskBySprintIds(List<Integer> sprintIds);

    /**
     * 给定冲刺ID，查询冲刺ID下的任务相关的工作量、开发者信息
     * @param sprintIds
     * @return
     */
    Map<Integer, List<TaskInfoDO>> queryWorkloadOfTaskBySprintIds(List<Integer> sprintIds);

    void download(List<TaskVO> taskVOList, HttpServletResponse response) throws IOException;

    /**
     * 上传任务信息
     * @param file
     * @return
     */
    Map<Integer, String> upload(MultipartFile file);

    void updateTaskStatus(int id, int status);

}

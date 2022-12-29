package me.zhengjie.modules.system.service.impl;

import me.zhengjie.modules.system.dao.mapper.TaskMapper;
import me.zhengjie.modules.system.domain.entity.TaskDO;
import me.zhengjie.modules.system.domain.entity.TaskFilter;
import me.zhengjie.modules.system.domain.vo.PageVO;
import me.zhengjie.modules.system.domain.vo.TaskVO;
import me.zhengjie.modules.system.service.TaskManageService;
import me.zhengjie.modules.system.service.dto.RoleDto;
import me.zhengjie.utils.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaskManageServiceImpl implements TaskManageService {

    @Resource
    TaskMapper taskMapper;

    private static SimpleDateFormat yyyyMMdd =new SimpleDateFormat("yyyy/MM/dd");

    @Override
    public PageVO<TaskVO> queryTaskByPage(TaskFilter filter) {

        List<TaskVO> taskVOList = taskMapper.queryTaskByPage(filter);

        Integer pageNum = filter.getPageNum();
        Integer pageSize = filter.getPageSize();
        Integer taskTotalCount = taskMapper.queryTaskTotalCount(filter);

        return new PageVO<>(pageSize, pageNum, taskTotalCount, taskVOList);
    }

    @Override
    public int add(TaskDO taskDO) {

        return taskMapper.insertTask(taskDO);
    }

    @Override
    public int delete(int id) {

        return taskMapper.deleteTask(id);
    }

    @Override
    public List<TaskDO> findAllTask() {

        return taskMapper.findAllTask();
    }

    @Override
    public int update(TaskDO taskDO) {

        return taskMapper.updateTask(taskDO);
    }

    @Override
    public List<TaskDO> queryTaskByRequirementId(int requirementId) {

        return taskMapper.queryTaskByRequirementId(requirementId);
    }

    @Override
    public void download(List<TaskVO> taskVOList, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (TaskVO taskVO : taskVOList) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("条线", taskVO.getBusinessLineName());
            map.put("需求", taskVO.getRequirementName());
            map.put("任务名", taskVO.getName());
            map.put("用户故事", taskVO.getStory());
            map.put("任务详细描述", taskVO.getDescription());
            map.put("开发者", taskVO.getDevEmployeeName());
            map.put("工作量", taskVO.getWorkload());
            map.put("状态", taskVO.getStatus());
            map.put("冲刺", taskVO.getSprintName());
            map.put("优先级", taskVO.getPriority());
            map.put("DUE", yyyyMMdd.format(taskVO.getDueDate()));
            map.put("备注", taskVO.getRemark());
            list.add(map);
        }

        FileUtil.downloadExcel(list, response);
    }

    @Override
    public void upload(MultipartFile file) {

        Map<Integer, List<String>> maps = FileUtil.readExcel(file);
        /**
         * {0=[条线, 需求, 任务名, 用户故事, 任务详细描述, 开发者, 工作量, 状态, 冲刺, 优先级, DUE, 备注],
         * 1=[画像, 画像中心第二批次需求, VOP批导优化-前端, 用户故事：blablabla, 任务详细描述：blablabla, 王文灏, 100, 0, sprint15, 13, 2030/01/01, 数据开发：EmployeeX],
         * 2=[画像, 画像中心第一批次需求, 1226测试任务, 用户故事, 描述, 王文灏, 0, 0, sprint16, 13, 2025/12/27, 备注]}
         */

        /**
         * 1. 找到标题行
         * 2. 判断每一行cell数量相同
         * 3. 判断条线名字、需求名字、开发者、是否存在，并查询到对应的ID
         * 4. 构建TaskDO insert
         */
        System.out.println(maps);

    }

}

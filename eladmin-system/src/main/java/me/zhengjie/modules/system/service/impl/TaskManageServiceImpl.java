package me.zhengjie.modules.system.service.impl;

import me.zhengjie.modules.system.dao.mapper.*;
import me.zhengjie.modules.system.domain.entity.*;
import me.zhengjie.modules.system.domain.vo.PageVO;
import me.zhengjie.modules.system.domain.vo.TaskVO;
import me.zhengjie.modules.system.service.*;
import me.zhengjie.utils.FileUtil;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TaskManageServiceImpl implements TaskManageService {

    @Resource
    TaskMapper taskMapper;
    @Resource
    BusinessLineMapper businessLineMapper;
    @Resource
    SprintMapper sprintMapper;
    @Resource
    RequirementMapper requirementMapper;
    @Resource
    EmployeeMapper employeeMapper;

    private static final int EXCEL_COLUMN_NUM = 14;

    private static SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");

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
    public Map<Integer, String> upload(MultipartFile file) {

        Map<Integer, List<String>> excelInfo = FileUtil.readExcel(file);
        // key: 行数, value: 失败原因
        Map<Integer, String> resultMap = new HashMap<>();
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

        List<TaskDO> taskDOList = excel2TaskDOList(excelInfo, resultMap);

        if (MapUtils.isEmpty(resultMap)) {
            for (TaskDO taskDO : taskDOList) {
                add(taskDO);
            }
        }
        return resultMap;
    }

    /**
     * 将excel的内容转换成TaskDO
     *
     * @param excelInfo
     * @param resultMap
     * @return
     */
    public List<TaskDO> excel2TaskDOList(Map<Integer, List<String>> excelInfo, Map<Integer, String> resultMap) {

        List<TaskDO> taskDOList = new ArrayList<>();
        List<String> firstRow = new ArrayList<>();
        int row = 0;
        try {
            for (Map.Entry<Integer, List<String>> entry : excelInfo.entrySet()) {
                row++;
                StringBuffer rowResult = new StringBuffer();
                List<String> content = entry.getValue();
                if (content.size() != EXCEL_COLUMN_NUM) {
                    continue;
                }
                if (content.contains("任务ID") || content.contains("评估工作量")) {
                    firstRow = content;
                    continue;
                }
                TaskVO taskVO = excelRow2TaskVO(firstRow, content, rowResult);
                TaskDO taskDO = taskVO2TaskDO(taskVO, rowResult);
                taskDOList.add(taskDO);
                if (rowResult.length() != 0) {
                    resultMap.put(row, rowResult.toString());
                }
            }
        } catch (Exception e) {
            resultMap.put(-1, "运行异常！");
        }
        return taskDOList;
    }

    /**
     * 将每一行的内容转换成TaskVO
     *
     * @param firstRow 表头标题
     * @param content  行内容
     * @return
     */
    private TaskVO excelRow2TaskVO(List<String> firstRow, List<String> content, StringBuffer rowResult){

        TaskVO taskVO = new TaskVO();
        for (int i = 0; i < firstRow.size(); i++) {
            String colName = firstRow.get(i);
            String cellContent = content.get(i);
            if ("条线".equals(colName)) {
                taskVO.setBusinessLineName(cellContent);
            } else if ("需求".equals(colName)) {
                taskVO.setRequirementName(cellContent);
            } else if ("用户故事".equals(colName)) {
                taskVO.setStory(cellContent);
            } else if ("任务名称".equals(colName)) {
                taskVO.setName(cellContent);
            } else if ("任务详细描述".equals(colName)) {
                taskVO.setDescription(cellContent);
            } else if ("优先级".equals(colName)) {
                try {
                    taskVO.setPriority(Integer.valueOf(cellContent));
                } catch (Exception e) {
                    rowResult.append("优先级格式错误!");
                }
            } else if ("开发者".equals(colName)) {
                taskVO.setDevEmployeeName(cellContent);
            } else if ("评估工作量".equals(colName)) {
                try {
                    taskVO.setWorkload(Double.valueOf(cellContent));
                } catch (Exception e) {
                    rowResult.append("评估工作量格式错误!");
                }
            } else if ("计划完成时间".equals(colName)) {
                try {
                    taskVO.setDueDate(yyyyMMdd.parse(cellContent));
                } catch (Exception e) {
                    rowResult.append("计划完成时间格式错误!");
                }
            } else if ("备注（是否外包开发）".equals(colName)) {
                taskVO.setRemark(cellContent);
            } else if ("冲刺".equals(colName)) {
                taskVO.setSprintName(cellContent);
            }
        }
        return taskVO;
    }


    public TaskDO taskVO2TaskDO(TaskVO taskVO, StringBuffer rowResult) {

        String name = taskVO.getName();
        Date dueDate = taskVO.getDueDate();
        String story = taskVO.getStory();
        String description = taskVO.getDescription();
        String remark = taskVO.getRemark();
        Integer priority = taskVO.getPriority();
        Integer status = 2;

        Integer businessLineId = 0;
        Integer requirementId = 0;
        Integer springId = 0;
        Integer employeeId = 0;

        String businessLineName = taskVO.getBusinessLineName();
        BusinessLineDO businessLineDO = businessLineMapper.getBusinessLineByName(businessLineName);
        if (businessLineDO == null) {
            rowResult.append("条线不存在!");
        } else {
            businessLineId = businessLineDO.getId();
        }

        String requirementName = taskVO.getRequirementName();
        RequirementDO requirementDO = requirementMapper.getRequirementByName(requirementName);
        if (requirementDO == null) {
            rowResult.append("需求不存在!");
        } else {
            requirementId = requirementDO.getId();
        }

        String sprintName = taskVO.getSprintName();
        SprintDO sprintDO = sprintMapper.getSprintByName(sprintName);
        if (sprintDO == null) {
            rowResult.append("冲刺不存在!");
        } else {
            springId = sprintDO.getId();
        }

        String devEmployeeName = taskVO.getDevEmployeeName();
        EmployeeDO employeeDO = employeeMapper.getEmployeeByName(devEmployeeName);
        if (employeeDO == null) {
            rowResult.append("开发者不存在!");
        } else {
            employeeId = employeeDO.getId();
        }

        return new TaskDO(name, businessLineId, requirementId, springId, employeeId, dueDate, story, description, remark, priority, status);
    }

    @Override
    public void updateTaskStatus(int id, int status) {

        taskMapper.updateTaskStatus(id, status);
    }

}

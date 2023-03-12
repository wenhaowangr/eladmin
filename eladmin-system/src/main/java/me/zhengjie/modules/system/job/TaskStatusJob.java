package me.zhengjie.modules.system.job;

import lombok.extern.slf4j.Slf4j;
import me.zhengjie.modules.system.enums.TaskStateEnum;
import me.zhengjie.modules.system.entity.SprintDO;
import me.zhengjie.modules.system.service.vo.TaskFilter;
import me.zhengjie.modules.system.service.vo.PageVO;
import me.zhengjie.modules.system.service.vo.TaskVO;
import me.zhengjie.modules.system.service.SprintManageService;
import me.zhengjie.modules.system.service.TaskManageService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class TaskStatusJob {

    @Resource
    SprintManageService sprintManageService;

    @Resource
    TaskManageService taskManageService;

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

    @Scheduled(cron = "0/5 * * * * *")
    public void scheduled() {
        process();
    }

    private void process() {
        try {
            //1.查询当前冲刺
            Date date = new Date();
            SprintDO sprintDO = sprintManageService.getSprintByDate(date);
            //2.判断是不是冲刺的第一天
            String beginDateStr = simpleDateFormat.format(sprintDO.getBeginDate());
            String curDateStr = simpleDateFormat.format(date);
            if (!beginDateStr.equals(curDateStr)) {
                return;
            }
            //3.查询冲上一个冲刺里的所有任务
            SprintDO lastSprint = sprintManageService.getSprintByDate(getYesterday());
            if (lastSprint == null) {
                log.error("updateTaskStatus error, lastSprint is null");
                return;
            }
            TaskFilter taskFilter = buildTaskFilter(lastSprint.getId());
            PageVO<TaskVO> taskVOPageVO = taskManageService.queryTaskByPage(taskFilter);
            List<TaskVO> taskVOList = taskVOPageVO.getData();

            //4.更新任务状态为"已完成"
            updateTaskStatus(taskVOList);
        } catch (Exception e) {
            log.error("TaskStatusJob process failed, e:{}", e.getMessage());
        }
    }

    private void updateTaskStatus(List<TaskVO> taskVOList) {
        if (CollectionUtils.isEmpty(taskVOList)) {
            return;
        }
        for (TaskVO taskVO : taskVOList) {
            if (taskVO.getStatus() != TaskStateEnum.CCZ.getCode()) {
                continue;
            }
            taskManageService.updateTaskStatus(taskVO.getId(), taskVO.getStatus());
        }
    }

    private Date getYesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return calendar.getTime();
    }

    private TaskFilter buildTaskFilter(int sprintId) {
        TaskFilter taskFilter = new TaskFilter();
        taskFilter.setSprintId(sprintId);
        return taskFilter;
    }
}

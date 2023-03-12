package me.zhengjie.modules.system.service.impl;

import lombok.extern.slf4j.Slf4j;
import me.zhengjie.modules.system.service.dto.DevScoreDTO;
import me.zhengjie.modules.system.entity.DevScoreCriteria;
import me.zhengjie.modules.system.entity.TaskInfoDO;
import me.zhengjie.modules.system.service.ScoreManageService;
import me.zhengjie.modules.system.service.TaskManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ScoreManageServiceImpl implements ScoreManageService {


    @Autowired
    TaskManageService taskManageService;

    @Autowired
    EmployeeManageServiceImpl employeeManageService;

    @Override
    public List<DevScoreDTO> queryDevScore(DevScoreCriteria criteria) {

        List<DevScoreDTO> devScores = new ArrayList<>();
        int businessLineId = criteria.getBusinessLineId();
        List<Integer> sprintIds = criteria.getSprintIds();
        if (CollectionUtils.isEmpty(sprintIds)) {
            return devScores;
        }

        // 查询冲刺下的所有任务
        Map<Integer, List<TaskInfoDO>> sprintTaskMap = taskManageService.queryWorkloadOfTaskBySprintIds(sprintIds);
        List<TaskInfoDO> allSprintTaskDOs = new ArrayList<>();
        sprintTaskMap.forEach((key, value) -> {
            allSprintTaskDOs.addAll(value);
        });

        // 筛选条线
        List<TaskInfoDO> taskInfoDOs = allSprintTaskDOs;
        if (businessLineId != 0) {
            taskInfoDOs = allSprintTaskDOs.stream().filter(taskDO -> taskDO.getBusinessLineId() == businessLineId).collect(Collectors.toList());
        }
        return getDevScore(taskInfoDOs);
    }

    /**
     * 根据TaskInfoDO计算devScore
     *
     * @param taskInfoDOs
     */
    private List<DevScoreDTO> getDevScore(List<TaskInfoDO> taskInfoDOs) {

        HashMap<Integer, DevScoreDTO> employeeScoreMap = new HashMap<>();

        for (TaskInfoDO taskInfoDO : taskInfoDOs) {
            int devEmployeeId = taskInfoDO.getDevEmployeeId();
            DevScoreDTO devScore;
            if (employeeScoreMap.containsKey(devEmployeeId)) {
                // 更新得分
                devScore = employeeScoreMap.get(devEmployeeId);
                double newWorkload = devScore.getWorkload() + taskInfoDO.getWorkLoad();
                devScore.setWorkload(newWorkload);
            } else {
                // 放入得分
                devScore = new DevScoreDTO();
                devScore.setEmployeeId(devEmployeeId);
                devScore.setEmployeeName(taskInfoDO.getDevEmployeeName());
                devScore.setWorkload(taskInfoDO.getWorkLoad());
            }
            employeeScoreMap.put(devEmployeeId, devScore);
        }
        // 计算百分比
        calWorkLoadPercent(employeeScoreMap);
        return covertMapToList(employeeScoreMap);
    }

    /**
     * 计算employeeScoreMap中的工作量百分比
     *
     * @param employeeScoreMap
     */
    private void calWorkLoadPercent(HashMap<Integer, DevScoreDTO> employeeScoreMap) {

        // 计算总workload
        double workloadSum = 0.0;
        for (Map.Entry<Integer, DevScoreDTO> entry : employeeScoreMap.entrySet()) {
            workloadSum += entry.getValue().getWorkload();
        }
        // 计算workload百分比
        for (Map.Entry<Integer, DevScoreDTO> entry : employeeScoreMap.entrySet()) {
            DevScoreDTO devScore = entry.getValue();
            BigDecimal workload = BigDecimal.valueOf(devScore.getWorkload());
            BigDecimal workloadPercent = workload.divide(BigDecimal.valueOf(workloadSum), 2, RoundingMode.DOWN);
            devScore.setWorkloadPercent(workloadPercent.doubleValue());
            employeeScoreMap.put(entry.getKey(), devScore);
        }
    }

    /**
     * 将map转换成list, 同时对list内的元素进行降序排列
     *
     * @param employeeScoreMap
     * @return
     */
    private List<DevScoreDTO> covertMapToList( HashMap<Integer, DevScoreDTO> employeeScoreMap) {

        List<DevScoreDTO> devScores = new ArrayList<>(employeeScoreMap.values());
        devScores.sort(new Comparator<DevScoreDTO>() {
            @Override
            public int compare(DevScoreDTO o1, DevScoreDTO o2) {
                return (int) (o2.getWorkloadPercent() * 100) - (int) (o1.getWorkloadPercent() * 100);
            }
        });
        return devScores;
    }

}

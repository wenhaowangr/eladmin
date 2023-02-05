package me.zhengjie;

import com.google.common.collect.Lists;
import me.zhengjie.modules.system.domain.entity.TaskDO;
import me.zhengjie.modules.system.domain.entity.TaskInfoDO;
import me.zhengjie.modules.system.service.TaskManageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

public class TaskManageServiceTests extends EladminSystemApplicationTests {

    @Autowired
    TaskManageService taskManageService;

    @Test
    public void testQueryWorkloadOfTaskBySprintIds() {

        Map<Integer, List<TaskInfoDO>> taskIdInfoMap = taskManageService.queryWorkloadOfTaskBySprintIds(Lists.newArrayList(10, 11));
        System.out.println(taskIdInfoMap);
    }

    @Test
    public void testQueryTaskBySprintIds() {

        Map<Integer, List<TaskDO>> taskIdDOMap = taskManageService.queryTaskBySprintIds(Lists.newArrayList(10, 11));
        System.out.println(taskIdDOMap);
    }

}


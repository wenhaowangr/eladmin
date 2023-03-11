package me.zhengjie.modules.system.domain.entity;

import lombok.Data;
import me.zhengjie.modules.system.domain.BasePage;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
public class SprintManageFilter extends BasePage {

    String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date beginDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date endDate;

}

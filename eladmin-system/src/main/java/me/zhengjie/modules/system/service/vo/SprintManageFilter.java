package me.zhengjie.modules.system.service.vo;

import lombok.Data;
import me.zhengjie.modules.system.service.dto.BasePage;
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

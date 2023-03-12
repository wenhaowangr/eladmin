package com.unionpay.modules.system.service.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.unionpay.modules.system.service.dto.BasePage;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@EqualsAndHashCode(callSuper = true)
@Data
public class SprintManageFilter extends BasePage {

    String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date beginDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date endDate;

}

package me.zhengjie.modules.system.service.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.zhengjie.modules.system.service.dto.BasePage;

import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessLineManageFilter extends BasePage {

    private String name;
    private Integer managerId;
    private List<Integer> memberIds;

}

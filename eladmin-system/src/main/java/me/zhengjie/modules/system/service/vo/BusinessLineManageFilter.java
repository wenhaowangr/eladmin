package me.zhengjie.modules.system.service.vo;

import lombok.Data;
import me.zhengjie.modules.system.service.dto.BasePage;

import java.util.List;


@Data
public class BusinessLineManageFilter extends BasePage {

    private String name;
    private Integer managerId;
    private List<Integer> memberIds;

}

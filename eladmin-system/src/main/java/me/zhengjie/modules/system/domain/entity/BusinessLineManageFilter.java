package me.zhengjie.modules.system.domain.entity;

import lombok.Data;
import me.zhengjie.modules.system.domain.BasePage;

import java.util.List;


@Data
public class BusinessLineManageFilter extends BasePage {

    private String name;
    private Integer managerId;
    private List<Integer> memberIds;

}

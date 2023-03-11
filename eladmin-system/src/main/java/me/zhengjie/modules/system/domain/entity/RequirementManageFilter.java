package me.zhengjie.modules.system.domain.entity;

import lombok.Data;
import me.zhengjie.modules.system.domain.BasePage;


@Data
public class RequirementManageFilter extends BasePage {

    String requirementName;

    Integer businessLineId;

}

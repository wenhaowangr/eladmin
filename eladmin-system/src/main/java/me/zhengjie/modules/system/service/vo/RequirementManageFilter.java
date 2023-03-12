package me.zhengjie.modules.system.service.vo;

import lombok.Data;
import me.zhengjie.modules.system.service.dto.BasePage;


@Data
public class RequirementManageFilter extends BasePage {

    String requirementName;

    Integer businessLineId;

}

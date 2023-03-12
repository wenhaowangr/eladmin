package me.zhengjie.modules.system.service.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.zhengjie.modules.system.service.dto.BasePage;


@EqualsAndHashCode(callSuper = true)
@Data
public class RequirementManageFilter extends BasePage {

    String requirementName;

    Integer businessLineId;

}

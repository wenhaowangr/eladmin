package me.zhengjie.modules.system;

public enum WorkTypeEnum {

    RW("RW", 1, "任务得分"),
    TX("TX", 2, "条线负责人得分"),
    PS("PS", 3, "需求评审得分");

    // rw-任务得分，tx-条线负责人得分，ps-需求评审得分
    private String name;
    private String desc;
    private int code;

    WorkTypeEnum(String name, int code, String desc) {
        this.name = name;
        this.desc = desc;
        this.code = code;
    }

}

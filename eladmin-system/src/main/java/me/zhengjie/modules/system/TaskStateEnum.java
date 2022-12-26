package me.zhengjie.modules.system;

public enum TaskStateEnum {

    DP("待排", 0, "待排"),
    CCZ("冲刺中", 1, "冲刺中"),
    YWC("已完成", 2, "已完成");

    private String name;
    private String desc;
    private int code;

    TaskStateEnum(String name, int code, String desc) {
        this.name = name;
        this.desc = desc;
        this.code = code;
    }

}

package me.zhengjie.modules.system;

import lombok.Getter;

import java.util.Objects;

public enum TaskStateEnum {

    DP("待排", 0, "待排"),
    CCZ("冲刺中", 1, "冲刺中"),
    YWC("已完成", 2, "已完成");

    @Getter
    private String name;
    @Getter
    private String desc;
    @Getter
    private int code;

    TaskStateEnum(String name, int code, String desc) {
        this.name = name;
        this.desc = desc;
        this.code = code;
    }


    public static String getNameByCode(int code) {
        TaskStateEnum[] values = TaskStateEnum.values();
        for (TaskStateEnum taskStateEnum : values) {
            if (taskStateEnum.getCode() == code) {
                return taskStateEnum.getName();
            }
        }
        return "";
    }

    public static int getCodeByName(String name) {
        TaskStateEnum[] values = TaskStateEnum.values();
        for (TaskStateEnum taskStateEnum : values) {
            if (Objects.equals(taskStateEnum.getName(), name)) {
                return taskStateEnum.getCode();
            }
        }
        return -1;
    }

}

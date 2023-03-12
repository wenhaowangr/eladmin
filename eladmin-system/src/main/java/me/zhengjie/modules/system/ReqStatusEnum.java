package me.zhengjie.modules.system;

import lombok.Getter;

public enum ReqStatusEnum {

    DOING("进行中", 0),
    COMPLETED("已完成", 1);

    @Getter
    private final String name;
    @Getter
    private final int code;

    ReqStatusEnum(String name, int code) {
        this.name = name;
        this.code = code;
    }

}

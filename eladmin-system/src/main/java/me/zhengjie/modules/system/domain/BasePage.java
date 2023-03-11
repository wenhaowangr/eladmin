package me.zhengjie.modules.system.domain;

import lombok.Data;

@Data
public class BasePage {

    //偏移量
    private Integer offset;
    //每页条数
    private Integer pageSize;
    //页数
    private Integer pageNum;

    // 计算分页的起始位置
    public Integer getOffset() {
        return ((pageNum == null || pageNum < 1 ? 1 : pageNum) - 1) * (pageSize == null ? 3 : pageSize);
    }

}

package me.zhengjie.modules.system.utils;

import com.alibaba.fastjson.JSON;
import me.zhengjie.exception.BizException;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CheckUtils {

    public static <T> void checkNonNullWithMsg(String msg, T...objs) throws RuntimeException{
        for (T obj : objs) {
            if (obj == null) {
                throw new RuntimeException(msg);
            }
        }
    }

    public static <T> void checkMemberIds(String msg, String memberIds) throws RuntimeException{

        try {
            JSON.parseArray(memberIds, String.class);
        } catch (Exception e) {
            throw new RuntimeException(msg);
        }
    }

    // 起止时间规范校验
    public static <T> void checkBeginDateEarlierThanEndDate(String msg, Date beginDate, Date endDate) throws RuntimeException{

        if (beginDate == null && endDate == null) {
            return;
        }
        if ((beginDate != null && endDate == null) || (beginDate == null && endDate != null)) {
            throw new RuntimeException(msg);
        }
        if (beginDate.after(endDate)) {
            throw new RuntimeException(msg);
        }
    }
}

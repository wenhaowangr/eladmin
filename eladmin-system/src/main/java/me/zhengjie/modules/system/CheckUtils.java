package me.zhengjie.modules.system;

import com.alibaba.fastjson.JSON;

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

}

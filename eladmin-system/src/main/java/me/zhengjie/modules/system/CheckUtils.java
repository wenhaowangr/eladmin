package me.zhengjie.modules.system;

import java.util.Calendar;
import java.util.Date;

public class CheckUtils {

    public static <T> void checkNonNullWithMsg(String msg, T...objs) throws RuntimeException{
        for (T obj : objs) {
            if (obj == null) {
                throw new RuntimeException(msg);
            }
        }
    }

}

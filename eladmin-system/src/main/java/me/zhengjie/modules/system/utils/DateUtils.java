package me.zhengjie.modules.system.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static String calculateQuarter(Date beginDate, Date endDate) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);

        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);

        if (month <= 3) {
            return year + "Q1";
        } else if (month <= 6) {
            return year + "Q2";
        } else if (month <= 9) {
            return year + "Q3";
        } else {
            return year + "Q4";
        }
    }

    public static void main(String[] args) {
        Date date = new Date();
        System.out.println(calculateQuarter(date, date));
    }

}

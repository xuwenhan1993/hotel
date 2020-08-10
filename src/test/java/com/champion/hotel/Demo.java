package com.champion.hotel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author xuwenhan
 * @version v1.0
 * @create 2020/8/4
 */
public class Demo {
    public static void main(String[] args) throws ParseException {
        // 多退少补
        Date predictOutDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2020-08-03 12:00:00");
        System.out.println(predictOutDate);
        // 退房时间，按12点算
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 12);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        System.out.println(today.getTime());
        System.out.println(today.getTime().getTime() - predictOutDate.getTime() );
        System.out.println(1000*3600*24);
        // 差
        long days = (today.getTime().getTime() - predictOutDate.getTime()  ) /1000/60/60/24;
        System.out.println(days);
    }
}

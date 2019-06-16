package com.xupt.util;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DataUtil {

    // 判断当前日期是周几
    public static int getWeekDay(String strDate) throws ParseException {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date date=simpleDateFormat.parse(strDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK)-1;
    }

    // 获得学期第n周的日期
    public static List<String> getWeek(String startDate, int week) throws ParseException {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date date=simpleDateFormat.parse(startDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR,(week - 1)*7);

        List<String> list = new ArrayList<>();
        // 获得周一到周五的日期
        for(int i=1;i<=7;i++){
            date = calendar.getTime();
            String str = simpleDateFormat.format(date);
            calendar.add(Calendar.DAY_OF_YEAR,1);
            list.add(str);
        }
        return list;
    }

    // 当前时间增加指定天数后的时间
    public static String getTime(String time,int addDay) throws ParseException {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR,addDay);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static List<String> getTime(String startDate, String endDate) throws ParseException {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date sDate = simpleDateFormat.parse(startDate);
        Date eDate = simpleDateFormat.parse(endDate);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sDate);

        List<String> list = new ArrayList<>();
        while(true){
            Date time = calendar.getTime();
            String str = simpleDateFormat.format(time);
            if(str.compareTo(endDate)>1){
                return list;
            }
            int weekday = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            list.add(str);
            calendar.add(Calendar.DAY_OF_YEAR,1);
        }
    }


    // 获取当前时间偏转15分钟
    public static String getNowDateAdd() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE,15);
        return formatter.format(calendar.getTime());
    }

    // 获取当前时间
    public static String getNowDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        return formatter.format(calendar.getTime());
    }
}

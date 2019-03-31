package com.jl.mis.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 操作时间
 *
 * @author 郑国超
 * @Version 1.0
 * @Data 2018/5/29 19:09
 */
public class DateTime {
    /**
     * 获取当前时间
     *
     * @return
     */
    public static Timestamp getTimestamp(){
        Date date= new Date();
        //获取当前时间
        Timestamp timestamp=new Timestamp(date.getTime());
        return timestamp;
    }

    /**
     * 计算时间戳 返回相差时间
     *
     * @param timestamp 被减数
     * @param timestamp1 减数
     * @return 时间相差 精确到秒
     */
    public static String timestamp(Timestamp timestamp,Timestamp timestamp1){
        //时间差
        long timestamps=(timestamp.getTime()-timestamp1.getTime());
        long ss=timestamps/1000; //共计秒数
        int mm = (int)ss/60;   //共计分钟数
        int hh=(int)ss/3600;  //共计小时数
        String second=null; //秒
        String minute=null; //分
        String hour=null; //时
        if (mm>60){
            mm=mm%60;
        }
        if (ss>60){
            ss=ss%60;
        }
        if (mm<10){
            minute=String.valueOf(mm);
            minute="0"+minute;
        }else{
            minute=String.valueOf(mm);
        }
        if (ss<10){
            second=String.valueOf(ss);
            second="0"+second;
        }else{
            second=String.valueOf(ss);
        }

        if (hh<10){
            hour=String.valueOf(hh);
            hour="0"+hour;
        }
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("hh:mm:ss");
        String time=hour+":"+minute+":"+second;
        return time;
    }
}

package com.jl.mis.websocket;

import org.apache.ibatis.annotations.Lang;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author
 * @Version 1.0
 * @Data 2018/7/5 18:04
 */
public class Main {
    public static void main(String[] args) {
        Timestamp timestamp=new Timestamp(2018,8,7,10,28,6,0);
        Timestamp timestamp1=new Timestamp(2018,8,7,10,51,2,0);
//        Timestamp timestamp=new Timestamp(2018,8,7,17,7,27,0);
//         Timestamp timestamp1=new Timestamp(2018,8,7,17,9,28,0);
        System.out.println(timestamp(timestamp1,timestamp));

    }
    public static String timestamp(Timestamp timestamp, Timestamp timestamp1){
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

package com.xxx.handnote.utils;

import java.util.Calendar;

/**
 * Created by niuapp on 2016/7/11 11:59.
 * Project : HandNote.
 * Email : *******
 * --> 专属工具类
 */
public class HN_Utils {

    /**
     *
     * 通过毫秒值比较时间 年月日
     * @param time1
     * @param time2
     * @return -1 <     0 ==    1 >
     */
    public static int comperTime(long time1, long time2){
        //先获取一个日历对象
        Calendar calendar = Calendar.getInstance();
        //将Date类型数据放入日历对象
        calendar.setTimeInMillis(time1);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.setTimeInMillis(time2);
        //比较 从年开始 到日
        if (year > calendar.get(Calendar.YEAR)){
            return 1;
        }else if (year < calendar.get(Calendar.YEAR)){
            return -1;
        }

        if (month > calendar.get(Calendar.MONTH)){
            return 1;
        }else if (month < calendar.get(Calendar.MONTH)){
            return -1;
        }

        if (day > calendar.get(Calendar.DAY_OF_MONTH)){
            return 1;
        }else if (day < calendar.get(Calendar.DAY_OF_MONTH)){
            return -1;
        }else {
            return 0;
        }

    }
}

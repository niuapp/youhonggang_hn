package com.xxx.handnote.utils;

import android.content.Intent;

import java.util.Map;
import java.util.Set;

/**
 * Created by niuapp on 2016/3/16 10:41.
 * Project : YuntooApk.
 * Email : *******
 * -->给Intent填参数
 */
public class IntentExtraUtils {

    /**
     * 把Map集合中的数据填如intent中
     * @param intent
     * @param map
     */
    public static void putIntentExtra(Intent intent, Map map){
        Set<Map.Entry<String, String>> set = map.entrySet();
        for (Map.Entry<String, String> entry : set){
            intent.putExtra(entry.getKey(), entry.getValue());
        }
    }

}

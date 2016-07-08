package com.xxx.handnote.base;

import java.util.Map;

/**
 * Created by niuapp on 2016/3/18 14:31.
 * Project : YuntooApk.
 * Email : *******
 * --> 继续任务
 */
public interface OnContinueRunnable {

    /**
     * @param map 可以用来传递信息
     */
    void continueRunnable(Map<String, String> map);//继续任务 类似发送Runnable 执行run任务
}

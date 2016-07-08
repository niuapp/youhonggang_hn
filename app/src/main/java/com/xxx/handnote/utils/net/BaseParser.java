package com.xxx.handnote.utils.net;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2015/12/10.
 * Json 解析工具父类
 */
public abstract class BaseParser<T> {

    protected Gson gson = new Gson();
    /**
     * @param json 要解析的 json串
     * @return 解析后的JavaBean
     */
    public abstract T parserJson(String json);
}

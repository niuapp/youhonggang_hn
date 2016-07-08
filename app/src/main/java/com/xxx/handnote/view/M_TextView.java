package com.xxx.handnote.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by niuapp on 2016/4/22 10:39.
 * Project : YuntooApk.
 * Email : *******
 * -->  方便统一更改字体的 TextView
 */
public class M_TextView extends TextView {
    private Context context;
    private static Typeface typeFace;

    public M_TextView(Context context) {
        super(context);
        initView(context);
    }
    public M_TextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }
    public M_TextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void initView(Context context) {

//        try {
//            if (typeFace == null) {
//                typeFace = Typeface.createFromAsset(context.getAssets(), "fonts/simkai.ttf");
//            }
//            setTypeface(typeFace);//更改字体
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}

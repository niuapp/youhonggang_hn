package com.xxx.handnote.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.xxx.handnote.R;
import com.xxx.handnote.utils.UIUtils;


/**
 * Created by Administrator on 2016/3/14.
 */
public class LoadingView extends View {

    private Context context;

    public LoadingView(Context context) {
        super(context);
        initView(context);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**
     * 初始化
     * @param context
     */
    private void initView(Context context){
        this.context = context;


        //播放帧动画
        setBackground(UIUtils.getDrawable(R.drawable.loading));
        AnimationDrawable animationDrawable = (AnimationDrawable) getBackground();
        animationDrawable.start();
    }

//    private int width;
//    private int height;
//
//    private int rectW;
//    private int lineWidth;
//
//    private int currentLeftX = 0;
//    private int currentLeftY = 0;
//    private int currentRightX = 0;
//    private int currentRightY = 0;
//
//    private int xAugmenter;
//    private int yAugmenter;
//
//    private int currentState = 1;
//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
//        width = getMeasuredWidth();
//        height = getMeasuredHeight();
//
//        int baseValue = width < height ? width : height;
//        rectW = (int) (baseValue / 3.0);
//        lineWidth = (int) (baseValue / 3.0);
//
//        xAugmenter = (width- lineWidth) / 25;
//        yAugmenter = (height- lineWidth) / 25;
//
//        currentLeftX = 0;
//        currentLeftY = lineWidth;
//        currentRightX = rectW;
//        currentRightY = currentLeftY + rectW;
//    }
//
//    private Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//
//            removeCallbacksAndMessages(null);
//            sendEmptyMessageDelayed(0, 30);
//            invalidate();
//        }
//    };
//
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//
//        Paint paintLine = new Paint(Paint.ANTI_ALIAS_FLAG);//线
//        paintLine.setColor(context.getResources().getColor(R.color.colorBlack));
//        paintLine.setStrokeWidth(lineWidth);
//        paintLine.setStyle(Paint.Style.FILL);
//
//        canvas.drawLine(0, 0, width, 0, paintLine);
//        canvas.drawLine(width, 0, width, height, paintLine);
//
//        Paint paintPoint = new Paint(Paint.ANTI_ALIAS_FLAG); //方块
//
//        paintPoint.setColor(context.getResources().getColor(R.color.colorRed));
//        paintPoint.setStyle(Paint.Style.FILL);
//
//        switch (currentState){
//            case 1: //左
//                currentLeftY = currentLeftY - yAugmenter;
//                currentRightY = currentRightY - yAugmenter;
//                if (currentRightY <= lineWidth *0.6 + rectW) currentState = 4;
//                break;
//            case 2: //下
//                currentLeftX = currentLeftX - xAugmenter;
//                currentRightX = currentRightX - xAugmenter;
//                if (currentRightX <= rectW) currentState = 1;
//                break;
//            case 3: //右
//                currentLeftY = currentLeftY + yAugmenter;
//                currentRightY = currentRightY + yAugmenter;
//                if (currentRightY >= height) currentState = 2;
//                break;
//            case 4: //上
//                currentLeftX = currentLeftX + xAugmenter;
//                currentRightX = currentRightX + xAugmenter;
//                if (currentRightX >= width - lineWidth *0.6) currentState = 3;
//                break;
//        }
//
//        // 方块
//        canvas.drawRect(currentLeftX, currentLeftY, currentRightX, currentRightY, paintPoint);
//
//        handler.sendEmptyMessageDelayed(0, 30);
//    }
}

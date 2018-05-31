package com.cdkj.myxb.weight.views;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;


/**
 * 继承ScrollView实现滑动监听
 */

public class MyScrollView extends ScrollView {
    private MyOnScrollListener onScrollListener;

    private stateChangeListener stopListener;

    private int lastScrollY;

    public MyScrollView(Context context) {
        this(context, null);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setStopListener(stateChangeListener stopListener) {
        this.stopListener = stopListener;
    }

    public void setOnScrollListener(MyOnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }


    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int scrollY = MyScrollView.this.getScrollY();
            if (lastScrollY != scrollY) {
                lastScrollY = scrollY;
                handler.sendMessage(handler.obtainMessage());
            }else{
                if(stopListener!=null)
                {
                    stopListener.stop(scrollY);
                }

                return;
            }

            if (onScrollListener != null) {
                onScrollListener.onScroll(scrollY);
            }
        };

    };


    @Override
    public boolean onTouchEvent(MotionEvent ev) {


        if (onScrollListener != null) {
            onScrollListener.onScroll(lastScrollY = this.getScrollY());
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                handler.sendMessage(handler.obtainMessage());
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if(onScrollListener != null){
            onScrollListener.onCurrentY(t);
        }
    }

    /**
     * 滑动事件
     */
    @Override
    public void fling(int velocityY) {
        super.fling(velocityY);//这里设置滑动的速度
    }

    public interface  stateChangeListener{
        void stop(int y);
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        int action = event.getActionMasked();
        if(action == MotionEvent.ACTION_DOWN){
            return false;
        }else{
            return true;
        }
    }

    public interface MyOnScrollListener {
        void onScroll(int y);

        void onCurrentY( int y );
    }


}

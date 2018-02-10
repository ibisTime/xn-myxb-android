package com.cdkj.myxb.weight.views;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cdkj.myxb.R;
import com.jakewharton.rxbinding2.view.RxView;


/**
 * 用于帮助中心页面展示隐藏
 * <p>
 * Created by cdkj on 2017/12/1.
 */

public class HelpItemShowAndHideLayout extends LinearLayout {

    private TextView tvTitle;
    private TextView tvInfo;
    private ImageView imageLeft;
    private ImageView imageRight;

    public HelpItemShowAndHideLayout(Context context) {
        this(context, null);
    }

    public HelpItemShowAndHideLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HelpItemShowAndHideLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HelpItemShowAndHideLayoutSyle, 0, 0);

        String txtTitle = typedArray.getString(R.styleable.HelpItemShowAndHideLayoutSyle_txt_title);
        String txtInfo = typedArray.getString(R.styleable.HelpItemShowAndHideLayoutSyle_txt_info);

        int imgLeftId = typedArray.getResourceId(R.styleable.HelpItemShowAndHideLayoutSyle_img_left, R.drawable.default_pic);
        int imgRightId = typedArray.getResourceId(R.styleable.HelpItemShowAndHideLayoutSyle_img_right, R.drawable.more_right);


        typedArray.recycle();
        init(context);
        tvTitle.setText(txtTitle);
        tvInfo.setText(txtInfo);
        imageLeft.setImageResource(imgLeftId);
        imageRight.setImageResource(imgRightId);
    }


    private void init(Context context) {
        this.setOrientation(VERTICAL);
        LayoutInflater.from(context).inflate(R.layout.layout_help_show, this, true);
        tvTitle = findViewById(R.id.tv_help_title);
        tvInfo = findViewById(R.id.tv_help_info);

        imageRight = findViewById(R.id.img_right);
        imageLeft = findViewById(R.id.img_left);

        LinearLayout linRow = findViewById(R.id.llinayout_row);
        //指示图标动画
        ObjectAnimator animStart = ObjectAnimator.ofFloat(imageRight, "rotation", 0f, 90f);
        ObjectAnimator animEnd = ObjectAnimator.ofFloat(imageRight, "rotation", 90f, 0f);

        linRow.setOnClickListener(v -> {
            if (tvInfo.getVisibility() == GONE) {
                animStart.start();
                tvInfo.setVisibility(VISIBLE);

            } else {
                animEnd.start();
                tvInfo.setVisibility(GONE);
            }
        });


    }


}

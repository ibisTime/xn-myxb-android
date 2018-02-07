package com.cdkj.baselibrary.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cdkj.baselibrary.R;

/**
 * 展示类似设置界面行信息  字体颜色默认黑色 需要其他属性可自行添加修改
 * ————————————————
 * 图片  文本16           文本14   图片
 * ————————————————
 * Created by cdkj on 2017/12/1.
 */

public class RowInfoLayout extends LinearLayout {

    private TextView tvLeft;
    private TextView tvRight;
    private ImageView imgLeft;
    private ImageView imgRight;
    private String txtLeft;
    private String txtRight;
    private int imgRightId;
    private int imgLeftId;

    private Context context;

    public RowInfoLayout(Context context) {
        this(context, null);
    }

    public RowInfoLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RowInfoLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RowInfoLayout, 0, 0);
        txtLeft = typedArray.getString(R.styleable.RowInfoLayout_txt_left);
        txtRight = typedArray.getString(R.styleable.RowInfoLayout_txt_right);
        imgLeftId = typedArray.getResourceId(R.styleable.RowInfoLayout_img_left, R.drawable.default_pic);
        imgRightId = typedArray.getResourceId(R.styleable.RowInfoLayout_img_right, R.drawable.default_pic);
        typedArray.recycle();

        init(context);

        setData();
    }

    private void setData() {
        tvLeft.setText(txtLeft);
        tvRight.setText(txtRight);
        imgLeft.setImageResource(imgLeftId);
        imgRight.setImageResource(imgRightId);
    }

    public void setTvLeftColor(@ColorRes int color) {
        tvLeft.setTextColor(ContextCompat.getColor(context, color));
    }

    public void setTvRightColor(@ColorRes int color) {
        tvRight.setTextColor(ContextCompat.getColor(context, color));
    }


    private void init(Context context) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.layout_row_info, this, true);
        tvLeft = findViewById(R.id.tv_left);
        tvRight = findViewById(R.id.tv_right);
        imgLeft = findViewById(R.id.img_left);
        imgRight = findViewById(R.id.img_right);
    }


}

package com.cdkj.baselibrary.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cdkj.baselibrary.R;

/**
 * 展示类似设置界面行信息  字体颜色默认黑色 需要其他属性可自行添加修改
 * ————————————————
 * 文本16           文本14   图片
 * ————————————————
 * Created by cdkj on 2017/12/1.
 */

public class RowMoreInfoLayout extends LinearLayout {

    private TextView tvLeft;
    private TextView tvCenter;
    private String txtLeft;
    private String txtCenter;
    private String txtCenterHint;


    public RowMoreInfoLayout(Context context) {
        this(context, null);
    }

    public RowMoreInfoLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RowMoreInfoLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RowMoreInfoLayout, 0, 0);
        txtLeft = typedArray.getString(R.styleable.RowMoreInfoLayout_txt_title);
        txtCenter = typedArray.getString(R.styleable.RowMoreInfoLayout_txt_info);
        txtCenterHint = typedArray.getString(R.styleable.RowMoreInfoLayout_txt_info_hint);
        typedArray.recycle();
        init(context);
        setData();
    }

    private void setData() {
        tvLeft.setText(txtLeft);
        if (!TextUtils.isEmpty(txtCenterHint)) {
            tvCenter.setHint(txtCenterHint);
        }
        if (!TextUtils.isEmpty(txtCenter)) {
            tvCenter.setText(txtCenter);
        }

    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_more_row, this, true);
        tvLeft = findViewById(R.id.tv_left);
        tvCenter = findViewById(R.id.tv_center);
    }

    public void setTextCenter(String str) {
        this.txtCenter = str;
        tvCenter.setText(str);
    }

    public String getCenterText() {
        return tvCenter.getText().toString();
    }

}

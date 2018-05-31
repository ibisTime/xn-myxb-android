package com.cdkj.myxb.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;

import com.cdkj.myxb.R;
import com.cdkj.myxb.databinding.ItemHelpQuestionListBinding;
import com.cdkj.myxb.models.QuestionModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by cdkj on 2018/5/1.
 */

public class QuestionAdapter extends BaseQuickAdapter<QuestionModel,BaseViewHolder> {

    public QuestionAdapter(@Nullable List<QuestionModel> data) {
        super(R.layout.item_help_question_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, QuestionModel item) {
        ItemHelpQuestionListBinding mBinding = DataBindingUtil.bind(helper.itemView);

        mBinding.helpItem.setTvTitle(item.getTitle());
        mBinding.helpItem.setTvInfo(item.getContent());
        mBinding.helpItem.isLeftImgShow(false);

    }
}

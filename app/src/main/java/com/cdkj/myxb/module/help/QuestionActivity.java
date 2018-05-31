package com.cdkj.myxb.module.help;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.cdkj.myxb.adapters.QuestionAdapter;
import com.cdkj.myxb.models.QuestionModel;
import com.cdkj.myxb.module.common.AbsRefreshListActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cdkj on 2018/5/1.
 */

public class QuestionActivity extends AbsRefreshListActivity {

    /**
     * @param context
     */
    public static void open(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, QuestionActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mBaseBinding.titleView.setMidTitle("常见问题");

        initRefreshHelper(6);
        mRefreshHelper.onDefaluteMRefresh(true);
    }

    @Override
    public RecyclerView.Adapter getListAdapter(List listData) {
        QuestionAdapter questionAdapter = new QuestionAdapter(listData);


        return questionAdapter;
    }

    @Override
    public void getListRequest(int pageIndex, int limit, boolean isShowDialog) {

        disMissLoading();

        List<QuestionModel> list = new ArrayList<>();

        for (int i = 0; i < limit; i++){
            QuestionModel model = new QuestionModel();
            model.setTitle("title"+1);
            model.setContent("contentcontentcontentcontentcontent"+i);
            list.add(model);
        }

        mRefreshHelper.setData(list,"",0);

    }


}

package com.example.cqe.citysidebar;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 国家筛选控制器
 * Author: CQE
 * Date: 2018/1/25.
 */

public class CountryController implements LetterSideBar.OnTouchLetterListener {

    private Context context;
    private View mRootView;
    private TextView mTvMask;
    private LetterSideBar mLsSidebar;
//    private RecyclerView mCountryRecyclerView;
//    private CountryAdapter countryAdapter;
    private List<CountryBean> mList = new ArrayList<>();
    private ListView listView;
    private CityAdapter mAdapter;


    public CountryController(Context context, View mRootView) {
        this.context = context;
        this.mRootView = mRootView;

        initView();
        mList = DBManager.getInstance(context).getAllCountry();
        mAdapter = new CityAdapter(context,mList);
        listView.setAdapter(mAdapter);
//        countryAdapter = new CountryAdapter(context,mList);
//        mCountryRecyclerView.setAdapter(countryAdapter);
    }

    private void initView() {
        mTvMask = mRootView.findViewById(R.id.tv_mask);
        mLsSidebar = mRootView.findViewById(R.id.letter_bar);
        listView = mRootView.findViewById(R.id.list_view);
//        mCountryRecyclerView = mRootView.findViewById(R.id.recycler_view);
//        mCountryRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mLsSidebar.setOverLayTextView(mTvMask);
        mLsSidebar.setOnTouchLetterListener(this);
    }

    /**
     * 处理选择字母时的回调
     * @param letter 字母
     */
    @Override
    public void onLetterSelected(String letter) {
//        int position = countryAdapter.getPosition(letter);
//        if (position != -1){
//            mCountryRecyclerView.scrollToPosition(position);
//        }
        int position = mAdapter.getPosition(letter);
        if (position != -1){
            listView.setSelection(position);
        }
    }
}

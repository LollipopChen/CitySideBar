package com.example.cqe.citysidebar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

/**
 * 国家列表适配器
 * Author: CQE
 * Date: 2018/1/25.
 */

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {

    private Context context;
    private List<CountryBean> mList;
    private HashMap<CountryBean, Integer> mLetterPos = new LinkedHashMap<>();


    public CountryAdapter(Context context,List<CountryBean> mList) {
        this.context = context;
        this.mList = mList;

        mList.add(0, new CountryBean()); // CountryBean
        // record city's pinyin bound
//        mLetterPos.put(mList.get(0), 1);
        for (int i = 1; i < mList.size(); i++) {
            CountryBean prev = mList.get(i - 1);
            CountryBean cur = mList.get(i);
            if (!TextUtils.equals(Util.getFirstLetter(prev.getPinyin())
                    , Util.getFirstLetter(cur.getPinyin()))) {
                mLetterPos.put(cur, i);
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_country_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CountryBean country = mList.get(position);
        if (mLetterPos.containsKey(country)) {
            holder.mTvLetter.setVisibility(View.VISIBLE);
            String letter = Util.getFirstLetter(country.getPinyin());
            if (!TextUtils.isEmpty(letter)) {
                holder.mTvLetter.setText(letter.toUpperCase());
            }
        } else {
            holder.mTvLetter.setVisibility(View.GONE);
        }
        holder.mCountry.setText(country.getName());

        if (position == 0){
            holder.itemView.setVisibility(View.GONE);
        }else {
            holder.itemView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public int getPosition(String letter) {

        Set<CountryBean> set = mLetterPos.keySet();
        Iterator<CountryBean> it = set.iterator();
        CountryBean countryBean = null;
        while (it.hasNext()) {
            countryBean = it.next();
            if (TextUtils.equals(countryBean.getFirstLetter(), letter.toUpperCase())) {
                return mLetterPos.get(countryBean);
            }
        }
        return -1;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTvLetter;
        TextView mCountry;

        ViewHolder(View itemView) {
            super(itemView);
            mTvLetter = itemView.findViewById(R.id.letter);
            mCountry = itemView.findViewById(R.id.country);
        }
    }
}

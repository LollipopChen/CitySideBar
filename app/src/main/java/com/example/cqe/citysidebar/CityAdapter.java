package com.example.cqe.citysidebar;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;


public class CityAdapter extends BaseAdapter {

    // ListView Type
    private static final int TYPE_CITY_ITEM = 1;
//    private static final int TYPE_SEARCH_VIEW = 0;
    private static final int TYPE_VIEW_COUNT = 2; // 所有的View类型

    private Context mContext;
    private List<CountryBean> mCities;
    private HashMap<CountryBean, Integer> mLetterPos = new LinkedHashMap<>();

    public CityAdapter(Context context, List<CountryBean> cities) {
        mContext = context;
        mCities = cities;
//        mCities.add(0, new CountryBean()); // 添加一个空的CityBean
        // record city's pinyin bound
        mLetterPos.put(cities.get(0), 1);
        for (int i = 1; i < mCities.size(); i++) {
            CountryBean prev = mCities.get(i - 1);
            CountryBean cur = mCities.get(i);
            if (!TextUtils.equals(Util.getFirstLetter(prev.getPinyin())
                    , Util.getFirstLetter(cur.getPinyin()))) {
                mLetterPos.put(cur, i);
            }
        }
        Log.e("CityAdapter2",mLetterPos.toString());
    }

    @Override
    public int getCount() {
        return mCities.size();
    }

    @Override
    public Object getItem(int i) {
        return mCities.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
//        int viewType = getItemViewType(i);
//        switch (viewType) {
//            case TYPE_SEARCH_VIEW: // 搜索框
//                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_search, viewGroup, false);
//                break;
//            case TYPE_CITY_ITEM: // 城市Item
                ViewHolder holder;
                if (convertView == null || convertView.getTag() == null) {
                    holder = new ViewHolder();
                    LayoutInflater inflater = LayoutInflater.from(mContext);
                    convertView = inflater.inflate(R.layout.item_country_layout, viewGroup, false);
                    holder.mTvCity = (TextView) convertView.findViewById(R.id.country);
                    holder.mTvLetter = (TextView) convertView.findViewById(R.id.letter);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                CountryBean countryBean = mCities.get(i);
                if (mLetterPos.containsKey(countryBean)) {
                    holder.mTvLetter.setVisibility(View.VISIBLE);
                    String letter = Util.getFirstLetter(countryBean.getPinyin());
                    if (!TextUtils.isEmpty(letter)) {
                        holder.mTvLetter.setText(letter.toUpperCase());
                    }
                } else {
                    holder.mTvLetter.setVisibility(View.GONE);
                }
                holder.mTvCity.setText(countryBean.getName());
//                break;
//        }
        return convertView;
    }

//    @Override
//    public int getItemViewType(int position) {
//        L.d("getItemViewType : " + position);
//        return position >= TYPE_VIEW_COUNT - 1 ? TYPE_CITY_ITEM : TYPE_CITY_ITEM;
//    }


    private class ViewHolder {
        private TextView mTvLetter;
        private TextView mTvCity;
    }

    public int getPosition(String letter) {
        Set<CountryBean> set = mLetterPos.keySet();
        Iterator<CountryBean> it = set.iterator();
        CountryBean countryBean = null;
        while (it.hasNext()) {
            countryBean = it.next();
            if (TextUtils.equals(countryBean.getFirstLetter(), letter.toUpperCase())) {
                if (countryBean.getFirstLetter().equals("A")){
                    return mLetterPos.get(countryBean)-1;
                }
                return mLetterPos.get(countryBean);
            }
        }
        return -1;
    }
}

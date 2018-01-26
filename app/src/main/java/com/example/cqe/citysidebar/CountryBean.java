package com.example.cqe.citysidebar;

import android.text.TextUtils;

/**
 * 国家
 * Author: CQE
 * Date: 2018/1/25.
 */

public class CountryBean {
    private int id;
    private String name;
    private String pinyin;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getFirstLetter() {
        if (TextUtils.isEmpty(pinyin)) return null;
        return pinyin.substring(0, 1);
    }

    @Override
    public String toString() {
        return "CountryBean{ id ="+ id + ",name = " + name + ",pinyin = " + pinyin + "}";
    }
}

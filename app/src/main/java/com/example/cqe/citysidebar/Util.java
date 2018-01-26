package com.example.cqe.citysidebar;

import android.text.TextUtils;

/**
 * Author: CQE
 * Date: 2018/1/25.
 */

class Util {
    public static String getFirstLetter(String pinyin) {
        if (TextUtils.isEmpty(pinyin)) return null;
        return pinyin.substring(0, 1);
    }
}

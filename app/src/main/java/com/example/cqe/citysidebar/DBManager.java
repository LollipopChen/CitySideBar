package com.example.cqe.citysidebar;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class DBManager {
    private static DBManager sInstance;
    private Context mContext;
    public final String DB_NAME = "china_cities.db";
    public final String DB_PATH;

    public interface COUNTRIES_COLUMN {
        String TAB_NAME = "country";
        String COL_ID = "id";
        String COL_NAME = "name";
        String COL_PINYIN = "pinyin";
    }

    public static DBManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (DBManager.class) {
                if (sInstance == null) {
                    sInstance = new DBManager(context);
                }
            }
        }
        return sInstance;
    }


    private DBManager(Context context) {
        mContext = context.getApplicationContext();
        DB_PATH = File.separator + "data" + Environment.getDataDirectory().getAbsolutePath() +
                File.separator + mContext.getPackageName() + File.separator + "databases" + File.separator;
        Log.e("DBManager",DB_PATH);
        loadDefaultCityList();
    }


    private void loadDefaultCityList() {
        File databaseFile = new File(DB_PATH + DB_NAME);
        if (databaseFile.exists()) {
            return;
        }
        try {
            InputStream is = mContext.getResources().getAssets().open(DB_NAME);
            File path = new File(DB_PATH);
            if (path.mkdirs()) {
                FileOutputStream fos = new FileOutputStream(databaseFile);
                int len;
                byte[] buf = new byte[1024];
                while ((len = is.read(buf)) != -1) {
                    fos.write(buf, 0, len);
                }
                fos.close();
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<CountryBean> getAllCountry() {
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DB_NAME, null);
        Cursor cursor = database.query(COUNTRIES_COLUMN.TAB_NAME, null, null, null, null, null, "pinyin asc");
        List<CountryBean> allCountries = new ArrayList<>();
        while (cursor.moveToNext()) {
            allCountries.add(cursor2Pojo(cursor));
        }
        cursor.close();
        Log.e("DBManager",allCountries.toString());
        return allCountries;
    }


    private CountryBean cursor2Pojo(Cursor cursor) {
        if (cursor == null) return null;
        CountryBean CountryBean = new CountryBean();
        CountryBean.setId(cursor.getInt(cursor.getColumnIndex(COUNTRIES_COLUMN.COL_ID)));
        CountryBean.setName(cursor.getString(cursor.getColumnIndex(COUNTRIES_COLUMN.COL_NAME)));
        CountryBean.setPinyin(cursor.getString(cursor.getColumnIndex(COUNTRIES_COLUMN.COL_PINYIN)));
        return CountryBean;
    }


}

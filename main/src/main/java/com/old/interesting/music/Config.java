package com.old.interesting.music;

import android.graphics.Typeface;
import android.text.TextUtils;

import java.util.Locale;

/**
 * Created by sjning
 * created on: 2018/10/25 上午10:21
 * description:
 */
public final class Config {

    private Config() {
    }

    final public static int COUNT = 3;
    public static int PLAY_ADS_COUNT = 0;
    public static int MAX_PLAY_COUNT = 3;
    public static int SEARCH_COUNT = 25;
    public static int ALL_PLAY_TEAM_PAGE = 90;
    public static int BROW_PLAY_TEAM_PAGE = 30;

    public static Typeface tf3;
    public static Typeface tf4;

    public static final String GENIUS = "genius";

    public static final String API_SERACH = "http://mobilecdn.kugou.com";
    public static final String API_GET_SONG = "http://www.kugou.com";

    public static final String API_LIN_HOST = "http://39.107.89.143";

    public static String FROM_US = "us";

    /**
     * 如果当前语言是日语，则URL不加参数
     */
    static {
        Locale locale = Locale.getDefault();
        // 获取当前系统语言
        String lang = locale.getLanguage();
        if (TextUtils.equals(lang, "ja")) {
            FROM_US = null;
        }
    }

}

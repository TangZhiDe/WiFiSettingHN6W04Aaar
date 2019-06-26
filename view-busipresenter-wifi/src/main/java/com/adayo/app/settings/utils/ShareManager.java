package com.adayo.app.settings.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class ShareManager {
    private SharedPreferences share;
    private SharedPreferences.Editor editor;
    private String TAG = "ShareManager";
    private String shareName = "nFWifi";
    private ShareManager() {
        super();
    }

    public void clear() {
        editor.clear().commit();
    }

    public ShareManager(Context context) {
        super();
        share = context.getSharedPreferences(shareName, Context.MODE_PRIVATE);
        editor = share.edit();
    }



    /**
     * 获取isSet
     * @return
     */
    public boolean isSet(){
        boolean result = share.getBoolean("isSet", false);
        return result;
    }

    /**
     * 设置isSet
     * @param isSet
     */
    public void setIsSet(boolean isSet){
        editor.putBoolean("isSet", isSet).commit();
    }
}

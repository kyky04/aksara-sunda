package com.uinbdg.kas.pref;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by knalb on 21/07/17.
 */

public class PrefData {
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    public static Context context;

    public static String sharepref = "myPref";

    public static String key_nama = "nama";

    public static  void setNama(Context context,String values){
        sharedPreferences = context.getSharedPreferences(sharepref,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(key_nama,values);
        editor.commit();
    }

    public static String getNama(Context context){
        sharedPreferences = context.getSharedPreferences(sharepref,Context.MODE_PRIVATE);
        return sharedPreferences.getString(key_nama,"");
    }
}

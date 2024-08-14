package moye.module.betterxuntu.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SPUtils {
    public static SPUtils xsp;
    public SharedPreferences sp;

    private SPUtils() {}

    public static synchronized SPUtils getInstance() {
        if (xsp == null) {
            xsp = new SPUtils();
        }
        return xsp;
    }

    public void init(Context context) {
        try{
            sp = context.getSharedPreferences("settings", Context.MODE_WORLD_READABLE);
        }catch(SecurityException e){
            sp = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        }
    }

    public static String getString(String key, String def) {
        return SPUtils.getInstance().sp.getString(key, def);
    }

    public static int getInt(String key, int def) {
        return SPUtils.getInstance().sp.getInt(key, def);
    }

    public static float getFloat(String key, float def) {
        return SPUtils.getInstance().sp.getFloat(key, def);
    }

    public static long getLong(String key, long def) {
        return SPUtils.getInstance().sp.getLong(key, def);
    }
    public static boolean getBoolean(String key, boolean def) {
        return SPUtils.getInstance().sp.getBoolean(key, def);
    }

    public static boolean putString(String key, String v) {
        return SPUtils.getInstance().sp.edit().putString(key, v).commit();
    }

    public static boolean putInt(String key, int v) {
        return SPUtils.getInstance().sp.edit().putInt(key, v).commit();
    }

    public static boolean putBoolean(String key, boolean v) {
        return SPUtils.getInstance().sp.edit().putBoolean(key, v).commit();
    }
    public static boolean putFloat(String key, float v) {
        return SPUtils.getInstance().sp.edit().putFloat(key, v).commit();
    }

    public static boolean putLong(String key, long v) {
        return SPUtils.getInstance().sp.edit().putLong(key, v).commit();
    }
}


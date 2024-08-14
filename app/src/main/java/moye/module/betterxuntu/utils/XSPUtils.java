package moye.module.betterxuntu.utils;

import de.robv.android.xposed.XSharedPreferences;

public class XSPUtils {
    
    public static XSharedPreferences xsp;

    public static void initXSP(String packageName) {
        XSPUtils.xsp = new XSharedPreferences(packageName,"settings");
        XSPUtils.xsp.makeWorldReadable();
    }

    public static boolean getBoolean(String key,boolean def){
        return xsp.getBoolean(key,def);
    }

    public static String getString(String key,String def){
        return xsp.getString(key,def);
    }

    public static int getInt(String key,int def){
        return xsp.getInt(key,def);
    }
    public static float getFloat(String key,float def){
        return xsp.getFloat(key,def);
    }
    public static long getLong(String key,long def){
        return xsp.getLong(key,def);
    }
}

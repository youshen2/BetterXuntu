package moye.module.betterxuntu.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import de.robv.android.xposed.XposedBridge;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import moye.module.betterxuntu.model.Application;
import org.json.JSONArray;

public class AppUtils {
    public static String modulePackageName = "moye.module.betterxuntu";

    public static boolean isModuleActive(){
        return false;
    }

    private static final ArrayList<String> containSkip = new ArrayList<String>(){{
        add("com.android.sprd");
        add("com.android.providers");
        add("com.android.location");
        add("org.simalliance");
        add("com.spreadtrum");
        add("com.adups");
    }};
    private static final ArrayList<String> equalSkip = new ArrayList<String>(){{
        add("android");
        add("com.xiaoxun.sdk");
        add("ado.install.xiaoxun.com.xiaoxuninstallapk");
        add("com.android.systemui");
        add("com.android.modemassert");
        add("com.xxun.watch.storydownloadservice");
        add("com.android.defcontainer");
        add("com.android.carrierconfig");
        add("com.android.shell");
        add("com.android.nfc");
        add("com.android.apps.tag");
        add("com.android.phone");
        add("com.android.bluetooth");
    }};

    public static ArrayList<Application> getApplicationList(Context context,String saveKey) {
        SPUtils.getInstance().init(context);
        ArrayList<Application> packages = new ArrayList<>();
        try {
            PackageManager pm = context.getPackageManager();
            List<PackageInfo> packageInfos = pm.getInstalledPackages(0);
            JSONArray chooseList = new JSONArray(SPUtils.getString(saveKey,"[]"));
            for (PackageInfo info : packageInfos){
                boolean isContinue = false;
                for (String n : containSkip){
                    if(info.packageName.contains(n)) {
                        isContinue = true;
                        break;
                    }
                }
                for (String n : equalSkip){
                    if(info.packageName.equals(n)) {
                        isContinue = true;
                        break;
                    }
                }
                if(isContinue) continue;
                
                boolean isInList = false;
                for(int n = 0; n < chooseList.length();n++){
                    if(chooseList.getString(n).equals(info.packageName)){
                        isInList = true;
                        break;
                    }
                }
                Application app = new Application((String) pm.getApplicationLabel(info.applicationInfo),info.packageName,((info.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 1),isInList);
                packages.add(app);
            }
        } catch (Exception e) {
            printExceptionStack(e,"加载应用列表");
        }
        return packages;
    }
    
    public static void printLog(String content){
        XposedBridge.log("[BetterXuntu] " + content);
    }
    
    public static void printExceptionStack(Exception e,String tag){
        Writer write = new StringWriter();
        PrintWriter printWrite = new PrintWriter(write);
        e.printStackTrace(printWrite);
        printLog("===============================");
        printLog("Error | " + tag + " > \n" + write);
        printLog("===============================");
    }
        
    public static void printThrowableStack(Throwable t,String tag){
        Writer write = new StringWriter();
        PrintWriter printWrite = new PrintWriter(write);
        t.printStackTrace(printWrite);
        printLog("===============================");
        printLog("Error | " + tag + " > \n" + write);
        printLog("===============================");
    }
}

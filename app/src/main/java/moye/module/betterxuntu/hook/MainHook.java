package moye.module.betterxuntu.hook;

import android.content.ContentResolver;
import android.content.Context;

import android.content.pm.PackageInfo;
import android.os.Build;

import de.robv.android.xposed.XC_MethodHook;
import java.util.List;
import org.json.JSONArray;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import moye.module.betterxuntu.utils.AppUtils;
import moye.module.betterxuntu.utils.XSPUtils;

public class MainHook implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam param){
        XSPUtils.initXSP(AppUtils.modulePackageName);

        //模块激活状态Hook
        if(param.packageName.equals(AppUtils.modulePackageName)){
            Class<?> cs = XposedHelpers.findClass(AppUtils.modulePackageName + ".utils.AppUtils",param.classLoader);
            XposedHelpers.findAndHookMethod(cs,"isModuleActive", XC_MethodReplacement.returnConstant(true));
        }

        //防右滑设置
        if(XSPUtils.getBoolean("module_fuckswipe",false)){
            if(XSPUtils.getBoolean("module_fuckswipe_global",false)){
                try{
                    Class<?> cs = XposedHelpers.findClass("com.android.internal.policy.impl.SystemGesturesPointerEventListener",param.classLoader);
                    if(Build.VERSION.SDK_INT > 19) cs = XposedHelpers.findClass("com.android.server.policy.SystemGesturesPointerEventListener",param.classLoader);
                    XposedHelpers.findAndHookMethod(cs,"simulateKeystroke",int.class, new XC_MethodReplacement(){
                        @Override
                        protected Object replaceHookedMethod(MethodHookParam methodHookParam) {
                            return null;
                        }
                    });
                }catch(Exception e){
                    AppUtils.printExceptionStack(e,"防右滑");
                }catch(Throwable t){
                    AppUtils.printThrowableStack(t,"防右滑");
                }
            }
            
            try{
                JSONArray swipeList = new JSONArray(XSPUtils.getString("module_fuckswipe_list","[]"));
                for(int i = 0;i<swipeList.length();i++){
                    if(swipeList.getString(i).equals(param.packageName)){
                        AppUtils.printLog("防返回 | 生效于" + param.packageName);
                        Class<?> cs = XposedHelpers.findClass("android.app.Activity",param.classLoader);
                        XposedHelpers.findAndHookMethod(cs,"onBackPressed",new XC_MethodReplacement(){
                            @Override
                            protected Object replaceHookedMethod(MethodHookParam arg0) throws Throwable {
                                return null;
                            }
                        });
                        break;
                    }
                }
            }catch(Exception e){
                AppUtils.printExceptionStack(e,"防右滑");
            }catch(Throwable t){
                AppUtils.printThrowableStack(t,"防右滑");
            }
        }
        
        
        //启动器设置
        if(param.packageName.equals("com.xxun.xunlauncher")){
            if(XSPUtils.getBoolean("module_xunlauncher_silence",false)){
                try{
                    Class<?> cs = XposedHelpers.findClass("android.provider.Settings.System",param.classLoader);
                    if(XSPUtils.getBoolean("module_xunlauncher_silence",false)) XposedHelpers.findAndHookMethod(cs, "getString", ContentResolver.class, String.class, new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) {
                            if(((String)param.args[1]).contains("SilenceList_result")) param.setResult("false");
                        }
                    });
                }catch(Exception e){
                    AppUtils.printExceptionStack(e,"启动器");
                }catch(Throwable t){
                    AppUtils.printThrowableStack(t,"启动器");
                }
            }
            
            if(XSPUtils.getBoolean("module_xunlauncher_sos",false)){
                try {
                	Class<?> cs = XposedHelpers.findClass("com.xxun.xunlauncher.utils.SosRecordUtils",param.classLoader);
                    XposedHelpers.findAndHookMethod(cs,"sendSos",Context.class, new XC_MethodReplacement(){
                        @Override
                        protected Object replaceHookedMethod(MethodHookParam methodHookParam) {
                            return null;
                        }
                    });
                } catch(Exception e) {
                	AppUtils.printExceptionStack(e,"启动器 | 禁止SOS");
                }catch(Throwable t){
                    AppUtils.printThrowableStack(t,"启动器 | 禁止SOS");
                }
            }
             
            if(XSPUtils.getBoolean("module_xunlauncher_simsync",false)){
                try {
                	Class<?> cs = XposedHelpers.findClass("com.xxun.xunlauncher.ui.activity.MainActivity",param.classLoader);
                    XposedHelpers.findAndHookMethod(cs,"sendSmsForSimSync", new XC_MethodReplacement(){
                        @Override
                        protected Object replaceHookedMethod(MethodHookParam methodHookParam) {
                            return null;
                        }
                    });
                } catch(Exception e) {
                	AppUtils.printExceptionStack(e,"启动器 | 禁止SIM卡同步");
                }catch(Throwable t){
                    AppUtils.printThrowableStack(t,"启动器 | 禁止SIM卡同步");
                }
            }                       
            
            if(XSPUtils.getBoolean("module_xunlauncher_noticewhite",false)){
                try {
                	Class<?> cs = XposedHelpers.findClass("com.xxun.xunlauncher.service.NotificationMonitor",param.classLoader);
                    XposedHelpers.findAndHookMethod(cs,"inWhitelist", String.class, XC_MethodReplacement.returnConstant(true));
                } catch(Exception e) {
                	AppUtils.printExceptionStack(e,"启动器 | 显示所有通知");
                }catch(Throwable t){
                    AppUtils.printThrowableStack(t,"启动器 | 显示所有通知");
                }
            }
            
            try {
            	XposedHelpers.findAndHookMethod("android.app.ApplicationPackageManager", param.classLoader, "getInstalledPackages", int.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        List<PackageInfo> packageInfos = (List) param.getResult();
                        JSONArray hideList = new JSONArray(XSPUtils.getString("module_xunlauncher_hidelist","[]"));
                            
                        List<PackageInfo> new_packageInfos = packageInfos;
                        for (int n = 0;n < packageInfos.size();n++){
                            for(int i = 0;i < hideList.length();i++){
                                if(hideList.getString(i).contains(packageInfos.get(n).packageName)){
                                    new_packageInfos.remove(packageInfos.get(n));
                                    break;
                                }
                            }
                        }
                        param.setResult(new_packageInfos);
                    }
                });
            } catch(Exception e) {
            	AppUtils.printExceptionStack(e,"启动器 | 隐藏应用");
            }catch(Throwable t){
                AppUtils.printThrowableStack(t,"启动器 | 隐藏应用");
            }
        }
        
        
        //米兔设置
        if(param.packageName.equals("com.xxun.watch.xunsettings")){
            if(XSPUtils.getBoolean("module_xunsettings_debugitem",false)){
                try {
                    Class<?> cs = XposedHelpers.findClass("android.os.SystemProperties",param.classLoader);
                    XposedHelpers.findAndHookMethod(cs, "get", String.class, new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) {
                            if(((String)param.args[0]).contains("ro.build.type")) param.setResult("userdebug");
                        }
                    });
                } catch(Exception e) {
                    AppUtils.printExceptionStack(e,"米兔设置 | 显示调试菜单");
                }catch(Throwable t){
                    AppUtils.printThrowableStack(t,"米兔设置 | 显示调试菜单");
                }
            }
            
            if(XSPUtils.getBoolean("module_xunsettings_newversion",false)){
                try {
                    Class<?> cs = XposedHelpers.findClass("com.xxun.watch.xunsettings.activity.NewVersionsDilogActivity",param.classLoader);
                    XposedHelpers.findAndHookMethod(cs, "showOtaInfo", int.class, new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            param.args[0] = 2;
                        }
                    });
                    XposedHelpers.findAndHookMethod(cs, "sendOpenAskUpdateBroacast", Context.class, String.class, String.class, String.class, String.class, String.class, new XC_MethodReplacement() {
                        @Override
                        protected Object replaceHookedMethod(MethodHookParam arg0) throws Throwable {
                            return null;
                        }
                    });
                } catch(Exception e) {
                    AppUtils.printExceptionStack(e,"米兔设置 | 禁止提示新版本");
                }catch(Throwable t){
                    AppUtils.printThrowableStack(t,"米兔设置 | 禁止提示新版本");
                }
            }
        }
        
        
        //杂项设置
        if(param.packageName.equals("com.android.packageinstaller")){
            if(XSPUtils.getBoolean("module_other_packageinstallerwear",false)){
                try {
                    Class<?> cs = XposedHelpers.findClass("com.android.packageinstaller.DeviceUtils",param.classLoader);
                    XposedHelpers.findAndHookMethod(cs, "isWear", Context.class, XC_MethodReplacement.returnConstant(false));
                } catch(Exception e) {
                	AppUtils.printExceptionStack(e,"杂项设置 | 干掉不支持安装卸载");
                }catch(Throwable t){
                    AppUtils.printThrowableStack(t,"杂项设置 | 干掉不支持安装卸载");
                }
            }
        }
        
        AppUtils.printLog("Method Hooked");
    }
}

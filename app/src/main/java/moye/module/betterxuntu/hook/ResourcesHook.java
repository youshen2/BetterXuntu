package moye.module.betterxuntu.hook;
import android.content.res.XResources;
import android.util.TypedValue;
import de.robv.android.xposed.IXposedHookZygoteInit;
import moye.module.betterxuntu.utils.AppUtils;
import moye.module.betterxuntu.utils.XSPUtils;

public class ResourcesHook implements IXposedHookZygoteInit {
    @Override
    public void initZygote(StartupParam param) throws Throwable {
        XSPUtils.initXSP(AppUtils.modulePackageName);
        
        //系统界面设置
        if(XSPUtils.getBoolean("module_systemui_fixstatusbarheight",false)){
            try {
            	XResources.setSystemWideReplacement("android","dimen","status_bar_height","48dip");
            } catch(Exception e) {
                AppUtils.printExceptionStack(e,"系统界面设置 | 修复状态栏高度");
            }catch(Throwable t){
                AppUtils.printThrowableStack(t,"系统界面设置 | 修复状态栏高度");
            }
        }
        
        AppUtils.printLog("Resources Hooked");
    }
}

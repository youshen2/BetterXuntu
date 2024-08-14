package moye.module.betterxuntu.model;

public class Application {
    public String appName;
    public String packageName;
    public boolean isSystemApp;
    public boolean isChecked;
    public Application(String appName,String packageName,boolean isSystemApp,boolean isChecked){
        this.appName = appName;
        this.packageName = packageName;
        this.isSystemApp = isSystemApp;
        this.isChecked = isChecked;
    }
}

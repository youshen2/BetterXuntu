package moye.module.betterxuntu.activity;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;

public class BaseActivity extends Activity {
    @Override
    protected void attachBaseContext(Context old) {
        Configuration configuration = old.getResources().getConfiguration();
        configuration.densityDpi = 250;
        Context newBase = old.createConfigurationContext(configuration);
        super.attachBaseContext(newBase);
    }
}

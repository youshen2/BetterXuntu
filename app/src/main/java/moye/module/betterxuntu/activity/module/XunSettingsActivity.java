package moye.module.betterxuntu.activity.module;

import android.os.Bundle;
import android.widget.Toast;
import com.google.android.material.switchmaterial.SwitchMaterial;
import moye.module.betterxuntu.R;
import moye.module.betterxuntu.activity.BaseActivity;
import moye.module.betterxuntu.utils.SPUtils;

public class XunSettingsActivity extends BaseActivity {
    SwitchMaterial debugItemSwitch,newversionSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_xunsettings);
        
        SPUtils.getInstance().init(this);

        debugItemSwitch = findViewById(R.id.module_xunsettings_debugitem_switch);
        newversionSwitch = findViewById(R.id.module_xunsettings_newversion_switch);
        debugItemSwitch.setChecked(SPUtils.getBoolean("module_xunsettings_debugitem",false));
        newversionSwitch.setChecked(SPUtils.getBoolean("module_xunsettings_newversion",false));
    }
    
    private void save(){
        SPUtils.putBoolean("module_xunsettings_debugitem",debugItemSwitch.isChecked());
        SPUtils.putBoolean("module_xunsettings_newversion",newversionSwitch.isChecked());
        Toast.makeText(getApplicationContext(), "设置已保存", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        save();
        super.onDestroy();
    }
}

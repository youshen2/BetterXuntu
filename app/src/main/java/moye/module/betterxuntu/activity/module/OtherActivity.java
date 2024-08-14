package moye.module.betterxuntu.activity.module;

import android.os.Bundle;
import android.widget.Toast;
import com.google.android.material.switchmaterial.SwitchMaterial;
import moye.module.betterxuntu.activity.BaseActivity;
import moye.module.betterxuntu.R;
import moye.module.betterxuntu.utils.SPUtils;

public class OtherActivity extends BaseActivity {
    SwitchMaterial packageInstallerWear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_other);
        
        SPUtils.getInstance().init(this);

        packageInstallerWear = findViewById(R.id.packageinstaller_wear_switch);
        packageInstallerWear.setChecked(SPUtils.getBoolean("module_other_packageinstallerwear",false));
    }
    
    private void save(){
        SPUtils.putBoolean("module_other_packageinstallerwear",packageInstallerWear.isChecked());
        Toast.makeText(getApplicationContext(), "设置已保存", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        save();
        super.onDestroy();
    }
}

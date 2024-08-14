package moye.module.betterxuntu.activity.module;

import android.os.Bundle;
import android.widget.Toast;
import com.google.android.material.switchmaterial.SwitchMaterial;
import moye.module.betterxuntu.activity.BaseActivity;
import moye.module.betterxuntu.R;
import moye.module.betterxuntu.utils.SPUtils;

public class SystemuiActivity extends BaseActivity {
    SwitchMaterial fixStatusbarHeightView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_systemui);
        
        SPUtils.getInstance().init(this);

        fixStatusbarHeightView = findViewById(R.id.fix_statusbar_height_switch);
        fixStatusbarHeightView.setChecked(SPUtils.getBoolean("module_systemui_fixstatusbarheight",false));
    }
    
    private void save(){
        SPUtils.putBoolean("module_systemui_fixstatusbarheight",fixStatusbarHeightView.isChecked());
        Toast.makeText(getApplicationContext(), "重启后生效", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        save();
        super.onDestroy();
    }
}

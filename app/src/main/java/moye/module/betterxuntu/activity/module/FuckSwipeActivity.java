package moye.module.betterxuntu.activity.module;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.switchmaterial.SwitchMaterial;

import moye.module.betterxuntu.view.BasicCreate;
import org.json.JSONArray;

import java.util.ArrayList;

import moye.module.betterxuntu.R;
import moye.module.betterxuntu.activity.BaseActivity;
import moye.module.betterxuntu.model.Application;
import moye.module.betterxuntu.utils.AppUtils;
import moye.module.betterxuntu.utils.SPUtils;

public class FuckSwipeActivity extends BaseActivity {

    private SwitchMaterial totalSwitch,globalSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_fuckswipe);

        SPUtils.getInstance().init(this);

        totalSwitch = findViewById(R.id.fuck_swipe_switch);
        globalSwitch = findViewById(R.id.fuck_swipe_global_switch);
        totalSwitch.setChecked(SPUtils.getBoolean("module_fuckswipe",false));
        globalSwitch.setChecked(SPUtils.getBoolean("module_fuckswipe_global",false));

        Toast.makeText(this,"正在加载应用列表",Toast.LENGTH_SHORT).show();

        BasicCreate.createApplicationChooseList(this,this,findViewById(R.id.application_list),"module_fuckswipe_list",true);
    }

    private void save(){
        SPUtils.putBoolean("module_fuckswipe",totalSwitch.isChecked());
        SPUtils.putBoolean("module_fuckswipe_global",globalSwitch.isChecked());
        Toast.makeText(getApplicationContext(), "重启后生效", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        save();
        super.onDestroy();
    }
}

package moye.module.betterxuntu.activity.module;

import android.os.Bundle;
import android.widget.Toast;
import com.google.android.material.switchmaterial.SwitchMaterial;
import moye.module.betterxuntu.activity.BaseActivity;
import moye.module.betterxuntu.utils.SPUtils;
import moye.module.betterxuntu.R;
import moye.module.betterxuntu.view.BasicCreate;

public class XunLauncherActivity extends BaseActivity {
    SwitchMaterial silenceView,sosView,simSyncView,noticeWhiteView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_xunlauncher);
        
        SPUtils.getInstance().init(this);

        silenceView = findViewById(R.id.silence_switch);
        sosView = findViewById(R.id.sos_view);
        simSyncView = findViewById(R.id.simsync_view);
        noticeWhiteView = findViewById(R.id.noticewhite_switch);
        silenceView.setChecked(SPUtils.getBoolean("module_xunlauncher_silence",false));
        sosView.setChecked(SPUtils.getBoolean("module_xunlauncher_sos",false));
        simSyncView.setChecked(SPUtils.getBoolean("module_xunlauncher_simsync",false));
        noticeWhiteView.setChecked(SPUtils.getBoolean("module_xunlauncher_noticewhite",false));
        
        BasicCreate.createApplicationChooseList(this,this,findViewById(R.id.application_list),"module_xunlauncher_hidelist",false);
    }
    
    private void save(){
        SPUtils.putBoolean("module_xunlauncher_silence",silenceView.isChecked());
        SPUtils.putBoolean("module_xunlauncher_sos",sosView.isChecked());
        SPUtils.putBoolean("module_xunlauncher_simsync",simSyncView.isChecked());
        SPUtils.putBoolean("module_xunlauncher_noticewhite",noticeWhiteView.isChecked());
        Toast.makeText(getApplicationContext(), "重启启动器后生效", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        save();
        super.onDestroy();
    }
}

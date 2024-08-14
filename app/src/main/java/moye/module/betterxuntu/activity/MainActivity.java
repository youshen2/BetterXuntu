package moye.module.betterxuntu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import android.widget.Toast;
import com.google.android.material.card.MaterialCardView;

import moye.module.betterxuntu.R;
import moye.module.betterxuntu.activity.module.FuckSwipeActivity;
import moye.module.betterxuntu.activity.module.OtherActivity;
import moye.module.betterxuntu.activity.module.SystemuiActivity;
import moye.module.betterxuntu.activity.module.XunLauncherActivity;
import moye.module.betterxuntu.activity.module.XunSettingsActivity;
import moye.module.betterxuntu.utils.AppUtils;
import moye.module.betterxuntu.utils.SPUtils;

public class MainActivity extends BaseActivity {
    
    private int version_card_clicked = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SPUtils.getInstance().init(this);

        try {
            TextView versionView = findViewById(R.id.version_textview);
            versionView.setText("版本号：" + getPackageManager().getPackageInfo(getPackageName(),0).versionName);
            TextView activeView = findViewById(R.id.active_textview);
            if(AppUtils.isModuleActive()){
                activeView.setText("已激活");
                activeView.setTextColor(getResources().getColor(R.color.success));
            }

            MaterialCardView fuckSwipeView = findViewById(R.id.fuck_swipe);
            fuckSwipeView.setOnClickListener(view -> {
                Intent intent = new Intent(this, FuckSwipeActivity.class);
                startActivity(intent);
            });
            
            MaterialCardView xunlauncherView = findViewById(R.id.xunlauncher);
            xunlauncherView.setOnClickListener(view -> {
                Intent intent = new Intent(this, XunLauncherActivity.class);
                startActivity(intent);
            });

            MaterialCardView xunsettingsView = findViewById(R.id.xunsettings);
            xunsettingsView.setOnClickListener(view -> {
                Intent intent = new Intent(this, XunSettingsActivity.class);
                startActivity(intent);
            });
            
            MaterialCardView systemuiView = findViewById(R.id.systemui);
            systemuiView.setOnClickListener(view -> {
                Intent intent = new Intent(this, SystemuiActivity.class);
                startActivity(intent);
            });
        
            MaterialCardView otherView = findViewById(R.id.other);
            otherView.setOnClickListener(view -> {
                Intent intent = new Intent(this, OtherActivity.class);
                startActivity(intent);
            });
            
            MaterialCardView aboutView = findViewById(R.id.about);
            aboutView.setOnClickListener(view -> {
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
            });
            
            if(SPUtils.getBoolean("first_tip",true)){
                Intent intent = new Intent(this,DialogActivity.class);
                intent.putExtra("title","免责声明");
                intent.putExtra("content",getResources().getString(R.string.keep_safe));
                startActivity(intent);
                SPUtils.putBoolean("first_tip",false);
            }
            
            MaterialCardView versionCard = findViewById(R.id.version_card);
            versionCard.setOnClickListener(view -> {
                if(version_card_clicked++ == 4){
                    Toast.makeText(this,"再点也不会有才彩蛋的",Toast.LENGTH_SHORT).show();
                    version_card_clicked = 0;
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
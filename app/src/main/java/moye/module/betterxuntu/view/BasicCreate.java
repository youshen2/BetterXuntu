package moye.module.betterxuntu.view;
import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.checkbox.MaterialCheckBox;
import java.util.ArrayList;
import moye.module.betterxuntu.R;
import moye.module.betterxuntu.model.Application;
import moye.module.betterxuntu.utils.AppUtils;
import moye.module.betterxuntu.utils.SPUtils;
import org.json.JSONArray;

public class BasicCreate {
    public static void createApplicationChooseList(Context context,Activity activity,LinearLayout rootView,String saveKey,boolean showSystemApp){
        new Thread(() -> { //问就是可以避免RecyclerView依旧卡死的问题（一个个加载也可以了）
            Looper.prepare();
            ArrayList<Application> applicationList = AppUtils.getApplicationList(context,saveKey);
            for (Application app : applicationList){
                if(app.isSystemApp && !showSystemApp) continue;
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.holder_application_choose, null);

                TextView appName,packageName;
                MaterialCheckBox appCheck;
                MaterialCardView cardView;

                appName = view.findViewById(R.id.app_name);
                packageName = view.findViewById(R.id.package_name);
                appCheck = view.findViewById(R.id.app_check);
                cardView = view.findViewById(R.id.card_view);

                appName.setText(app.appName);
                packageName.setText(app.packageName);
                appCheck.setChecked(app.isChecked);

                cardView.setOnClickListener(a -> {
                    try {
                        JSONArray list = new JSONArray(SPUtils.getString(saveKey,"[]"));
                        if(appCheck.isChecked()){
                            for (int i = 0;i < list.length();i++){
                                if(list.getString(i).equals(app.packageName)){
                                    list.remove(i);
                                    break;
                                }
                            }
                        }else list.put(app.packageName);
                        SPUtils.putString(saveKey,list.toString());
                        appCheck.setChecked(!appCheck.isChecked());
                    }catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(context,"设置失败",Toast.LENGTH_SHORT).show();
                    }
                });

                activity.runOnUiThread(() -> rootView.addView(view));
            }
            Looper.loop();
        }).start();
    }
}

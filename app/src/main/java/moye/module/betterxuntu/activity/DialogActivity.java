package moye.module.betterxuntu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import com.google.android.material.card.MaterialCardView;
import moye.module.betterxuntu.R;

public class DialogActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        
        Intent intent = getIntent();
        TextView titleView = findViewById(R.id.title);
        TextView contentView = findViewById(R.id.content);
        titleView.setText(intent.getStringExtra("title"));
        contentView.setText(intent.getStringExtra("content"));
        MaterialCardView okBtn = findViewById(R.id.ok_btn);
        okBtn.setOnClickListener(view -> finish());
    }
}

package com.vst.myapplication.UI.Register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.vst.myapplication.MainActivity;
import com.vst.myapplication.R;
import com.vst.myapplication.UI.Login.LoginNew;
import com.vst.myapplication.databinding.RegisterBinding;
import com.vst.myapplication.databinding.RegisterNoticeBinding;

public class register_notice_activity extends AppCompatActivity {
    RegisterNoticeBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.register_notice);

        binding.next.setOnClickListener(v -> {
            startActivity(new Intent(register_notice_activity.this, register_activity.class));
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Finish the activity and close the app
        finishAffinity();
    }
}

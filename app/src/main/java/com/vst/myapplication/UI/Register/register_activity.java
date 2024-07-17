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

public class register_activity extends AppCompatActivity {
    RegisterBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.register);

        binding.btnNext.setOnClickListener(v -> {
            startActivity(new Intent(register_activity.this, LoginNew.class));
            finish();

        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

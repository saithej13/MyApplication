package com.vst.myapplication.UI.Login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.vst.myapplication.MainActivity;
import com.vst.myapplication.R;
import com.vst.myapplication.UI.Register.register_notice_activity;
import com.vst.myapplication.databinding.ForgetpasswordBinding;

public class forgetpassword_activity extends AppCompatActivity{
    ForgetpasswordBinding binding;
@Override
protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.forgetpassword);

    binding.btnSendOtp.setOnClickListener(v -> {
        startActivity(new Intent(forgetpassword_activity.this, verifyotp_activity.class));
        finish();

    });
}

@Override
public void onBackPressed() {
    super.onBackPressed();
}
}

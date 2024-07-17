package com.vst.myapplication.UI.Login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.vst.myapplication.MainActivity;
import com.vst.myapplication.R;
import com.vst.myapplication.databinding.ForgetpasswordBinding;
import com.vst.myapplication.databinding.VerifyotpBinding;

public class verifyotp_activity extends AppCompatActivity {
    VerifyotpBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.verifyotp);

        binding.btnVerify.setOnClickListener(v -> {
            startActivity(new Intent(verifyotp_activity.this, newpassword_activity.class));
            finish();

        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

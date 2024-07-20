package com.vst.myapplication.UI.Login;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.vst.myapplication.MainActivity;
import com.vst.myapplication.R;
import com.vst.myapplication.UI.Dashboard.DashboardFragment;
import com.vst.myapplication.UI.Register.register_notice_activity;
import com.vst.myapplication.UI.onboarding.onboarding;
import com.vst.myapplication.databinding.LoginBinding;
import com.vst.myapplication.databinding.RegisterNoticeBinding;

public class LoginNew extends AppCompatActivity {
    LoginBinding binding;
    String version ="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.login);
        try {
            version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        binding.txtversion.setText("App Version  :  "+version);
        binding.btnSignIn.setOnClickListener(v -> {
            startActivity(new Intent(LoginNew.this, MainActivity.class));
            finish();

        });
        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginNew.this, register_notice_activity.class));
            }
        });
        binding.btnForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(LoginNew.this, forgetpassword_activity.class));
                startActivity(new Intent(LoginNew.this, onboarding.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Finish the activity and close the app
        finishAffinity();
    }

}

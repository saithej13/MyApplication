package com.vst.myapplication.UI.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.vst.myapplication.MainActivity;
import com.vst.myapplication.R;
import com.vst.myapplication.UI.Dashboard.DashboardFragment;
import com.vst.myapplication.databinding.LoginBinding;

public class LoginNew extends AppCompatActivity {
    LoginBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.login);

        binding.btnSignIn.setOnClickListener(v -> {
            /*FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
//                        .replace(R.id.frame, new Dashboard1(), "")
                    .replace(R.id.frame, new DashboardFragment(), "")
                    .commitAllowingStateLoss();*/

            startActivity(new Intent(LoginNew.this, MainActivity.class));
            finish();

        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Finish the activity and close the app
        finishAffinity();
    }

}

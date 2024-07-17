package com.vst.myapplication.UI.Login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.vst.myapplication.MainActivity;
import com.vst.myapplication.R;
import com.vst.myapplication.databinding.NewpasswordBinding;
import com.vst.myapplication.databinding.VerifyotpBinding;

public class newpassword_activity  extends AppCompatActivity {
    NewpasswordBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.newpassword);

        binding.btnSave.setOnClickListener(v -> {
            startActivity(new Intent(newpassword_activity.this, LoginNew.class));
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

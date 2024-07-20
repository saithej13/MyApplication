package com.vst.myapplication.UI.Login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

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
//        binding.edtotp1.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if(editable.length()==1){
//                    binding.edtotp1.setFocusable(false);
//                    binding.edtotp2.setFocusable(true);
//                }
//            }
//        });
//        binding.edtotp2.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if(editable.length()==1){
//                    binding.edtotp2.setFocusable(false);
//                    binding.edtotp3.setFocusable(true);
//                }
//            }
//        });
//        binding.edtotp3.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if(editable.length()==1){
//                    binding.edtotp3.setFocusable(false);
//                    binding.edtotp4.setFocusable(true);
//                }
//            }
//        });
//        binding.edtotp4.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if(editable.length()==1){
//                    binding.edtotp4.setFocusable(false);
//                }
//            }
//        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

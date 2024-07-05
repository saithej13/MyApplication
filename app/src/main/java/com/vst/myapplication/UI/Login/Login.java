package com.vst.myapplication.UI.Login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.google.gson.JsonObject;
import com.vst.myapplication.R;
import com.vst.myapplication.Services.ProjectRepository;
import com.vst.myapplication.UI.Dashboard;
import com.vst.myapplication.UI.MCollection.milkCollection;
import com.vst.myapplication.UI.Register.register;
import com.vst.myapplication.UI.Register.register_notice;
import com.vst.myapplication.Utils.BaseFragment;
import com.vst.myapplication.databinding.LoginBinding;

public class Login extends BaseFragment {
    LoginBinding binding;
    ProjectRepository repository;
    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState, LifecycleOwner viewLifecycleOwner) {
        binding = DataBindingUtil.inflate(inflater, R.layout.login, parent, false);
        repository = new ProjectRepository();
        binding.setLifecycleOwner(viewLifecycleOwner);
        setupUI(inflater,parent,viewLifecycleOwner);
        return binding.getRoot();
    }
    private void setupUI(LayoutInflater inflater, ViewGroup parent, LifecycleOwner viewLifecycleOwner) {
        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonObject payload = new JsonObject();
                payload.addProperty("MOBILENO",binding.edtUsername.getText().toString());
                payload.addProperty("PASSWORD",binding.edtpassword.getText().toString());
                payload.addProperty("DEVICEID","");
                repository.getUser(payload).observe(viewLifecycleOwner, new Observer<JsonObject>() {
                    @Override
                    public void onChanged(JsonObject jsonObject) {
                        if(jsonObject!=null) {
                            FragmentManager fragmentManager = getParentFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                                    .replace(R.id.frame, new Dashboard(), "")
                                    .addToBackStack("")
                                    .commitAllowingStateLoss();
                        }
                    }
                });

            }
        });
        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .replace(R.id.frame, new register_notice(), "")
                        .addToBackStack("")
                        .commitAllowingStateLoss();
            }
        });
        binding.btnForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .replace(R.id.frame, new forgetpassword(), "")
                        .addToBackStack("")
                        .commitAllowingStateLoss();
            }
        });
    }
}

package com.vst.myapplication.UI.Register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import com.vst.myapplication.R;
import com.vst.myapplication.Services.ProjectRepository;
import com.vst.myapplication.UI.Dashboard;
import com.vst.myapplication.UI.Rates.rates_VM;
import com.vst.myapplication.Utils.BaseFragment;
import com.vst.myapplication.databinding.RegisterBinding;
import com.vst.myapplication.databinding.RegisterNoticeBinding;

public class register extends BaseFragment {
    RegisterBinding binding;
    ProjectRepository repository;

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState, LifecycleOwner viewLifecycleOwner) {
        binding = DataBindingUtil.inflate(inflater, R.layout.register, parent, false);
        repository = new ProjectRepository();
        binding.setLifecycleOwner(viewLifecycleOwner);
        setupUI(inflater,parent,viewLifecycleOwner);
        return binding.getRoot();
    }
    private void setupUI(LayoutInflater inflater, ViewGroup parent, LifecycleOwner viewLifecycleOwner) {
        // Implement any additional UI setup here
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FragmentManager fragmentManager = getParentFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
//                        .add(R.id.frame, new Dashboard(), "")
//                        .addToBackStack("")
//                        .commitAllowingStateLoss();
                showCustomDialog(getContext(),"Title","Message","firstbtn","secondbtn","from");
            }
        });
    }
}

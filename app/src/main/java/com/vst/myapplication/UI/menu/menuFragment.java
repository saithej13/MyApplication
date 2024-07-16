package com.vst.myapplication.UI.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;

import com.vst.myapplication.R;
import com.vst.myapplication.Services.ProjectRepository;
import com.vst.myapplication.UI.Farmers.farmers;
import com.vst.myapplication.UI.Login.Login;
import com.vst.myapplication.UI.MCollection.milkCollection;
import com.vst.myapplication.UI.Rates.rates;
import com.vst.myapplication.Utils.BaseFragment;
import com.vst.myapplication.Utils.Settings;
import com.vst.myapplication.databinding.MenuBinding;

public class menuFragment extends BaseFragment {
    MenuBinding binding;
    private ProjectRepository repository;
    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState, LifecycleOwner viewLifecycleOwner) {
        binding = DataBindingUtil.inflate(inflater, R.layout.menu, parent, false);
        repository = new ProjectRepository();
        binding.setLifecycleOwner(viewLifecycleOwner);
        setupUI(inflater,parent,viewLifecycleOwner,savedInstanceState);
        return binding.getRoot();
    }
    private void setupUI(LayoutInflater inflater, ViewGroup parent, LifecycleOwner viewLifecycleOwner,Bundle savedInstanceState) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        binding.milkcollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle mBundle = new Bundle();
                mBundle.putBoolean("isTitle", true);
                milkCollection milkFragment = new milkCollection();
                milkFragment.setArguments(mBundle);
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .replace(R.id.frame, milkFragment, "")
                        .addToBackStack("")
                        .commitAllowingStateLoss();
            }
        });
        binding.farmers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle mBundle = new Bundle();
                mBundle.putBoolean("isTitle", true);
                farmers farmersFragment = new farmers();
                farmersFragment.setArguments(mBundle);
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .replace(R.id.frame, farmersFragment, "")
                        .addToBackStack("")
                        .commitAllowingStateLoss();
            }
        });
        binding.rates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle mBundle = new Bundle();
                mBundle.putBoolean("isTitle", true);
                rates rateFragment = new rates();
                rateFragment.setArguments(mBundle);
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .replace(R.id.frame, rateFragment, "")
                        .addToBackStack("")
                        .commitAllowingStateLoss();
            }
        });
        binding.settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .replace(R.id.frame, new Settings(), "")
                        .addToBackStack("")
                        .commitAllowingStateLoss();
            }
        });
    }
}

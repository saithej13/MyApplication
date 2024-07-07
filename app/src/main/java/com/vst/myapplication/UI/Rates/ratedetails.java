package com.vst.myapplication.UI.Rates;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import com.vst.myapplication.R;
import com.vst.myapplication.Services.ProjectRepository;
import com.vst.myapplication.Utils.BaseFragment;
import com.vst.myapplication.databinding.RatedetailsBinding;
import com.vst.myapplication.databinding.RatedetailscardcellBinding;

public class ratedetails extends BaseFragment {
    RatedetailscardcellBinding binding;

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState, LifecycleOwner viewLifecycleOwner) {
        binding = DataBindingUtil.inflate(inflater, R.layout.ratedetails, parent, false);
        binding.setLifecycleOwner(viewLifecycleOwner);
        setupUI(inflater,parent,viewLifecycleOwner);
        return binding.getRoot();
    }
    private void setupUI(LayoutInflater inflater, ViewGroup parent, LifecycleOwner viewLifecycleOwner) {

    }
}

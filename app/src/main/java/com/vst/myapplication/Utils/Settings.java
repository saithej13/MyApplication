package com.vst.myapplication.Utils;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;

import com.vst.myapplication.R;
import com.vst.myapplication.Services.ProjectRepository;
import com.vst.myapplication.databinding.SettingsBinding;

public class Settings extends BaseFragment {
    private ProjectRepository repository;
    SettingsBinding binding;

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState, LifecycleOwner viewLifecycleOwner) {
        binding = DataBindingUtil.inflate(inflater, R.layout.settings, parent, false);
        repository = new ProjectRepository();
        binding.setLifecycleOwner(viewLifecycleOwner);
        setupUI(inflater, parent, viewLifecycleOwner, savedInstanceState);
        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    private void setupUI(LayoutInflater inflater, ViewGroup parent, LifecycleOwner viewLifecycleOwner, Bundle savedInstanceState) {
//        final String[] selectPrinter =attendance_types.toArray(new String[0]);
        binding.tvprinter.setText(preference.getStringFromPreference(Preference.Select_Printer,"")+"");
        binding.tvprintersize.setText(preference.getStringFromPreference(Preference.Select_Printer_Size,"")+"");
        binding.tvRCF.setText(preference.getStringFromPreference(Preference.Select_Rate_collection_formate,"")+"");
        binding.tvbillperiod.setText(preference.getIntFromPreference(Preference.Select_Bill_Period,0)+"");


        final String[] selectPrinter = {"Printer 1", "Printer 2", "Printer 3"};
        binding.tvprinter.setOnClickListener(v -> setDropDown(v, binding.tvprinter, selectPrinter));

        final String[] selectPrinterSize = {"1", "2", "3"};
        binding.tvprintersize.setOnClickListener(v -> setDropDown(v, binding.tvprintersize, selectPrinterSize));

        final String[] selectRCF = {"1", "2", "3"};
        binding.tvRCF.setOnClickListener(v -> setDropDown(v, binding.tvRCF, selectRCF));

        binding.tvSubmit.setOnClickListener(v -> {
//            AppConstants.Select_Printer = binding.tvprinter.getText().toString();
//            AppConstants.Select_Printer_Size = binding.tvprintersize.getText().toString();
//            AppConstants.Select_Rate_collection_formate = binding.tvRCF.getText().toString();

            preference.saveStringInPreference(Preference.Select_Printer, binding.tvprinter.getText().toString());
            preference.saveStringInPreference(Preference.Select_Printer_Size, binding.tvprintersize.getText().toString());
            preference.saveStringInPreference(Preference.Select_Rate_collection_formate, binding.tvRCF.getText().toString());
            preference.saveIntInPreference(Preference.Select_Bill_Period, Integer.parseInt(binding.tvbillperiod.getText().toString()));
            preference.commitPreference();
        });

    }

    private void setDropDown(View v, TextView tv, String[] stringArray) {
        PopupMenu popupMenu = new PopupMenu(getContext(), v);
        for (int i = 0; i < stringArray.length; i++) {
            if (!stringArray[i].equals(tv.getText().toString()))
                popupMenu.getMenu().add(stringArray[i]);
        }
        popupMenu.setOnMenuItemClickListener(item -> {
            String selectedOption = item.getTitle().toString();
            tv.setText(selectedOption);
            return true;
        });
        popupMenu.show();
    }

    

}

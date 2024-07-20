package com.vst.myapplication.UI.Advance;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;

import com.vst.myapplication.R;
import com.vst.myapplication.Room.roomRepository;
import com.vst.myapplication.Services.ProjectRepository;
import com.vst.myapplication.Utils.BaseFragment;
import com.vst.myapplication.Utils.CalendarUtils;
import com.vst.myapplication.databinding.AddadvanceBinding;

import java.util.Calendar;

public class addadvance extends BaseFragment {
    AddadvanceBinding binding;
    roomRepository roomrepo;
    private ProjectRepository repository;
    int mdate,mmonth,myear;
    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState, LifecycleOwner viewLifecycleOwner) {
        binding = DataBindingUtil.inflate(inflater, R.layout.addadvance, parent, false);
        repository = new ProjectRepository();
        roomrepo = new roomRepository();
        binding.setLifecycleOwner(viewLifecycleOwner);
        setupUI(inflater,parent,viewLifecycleOwner);
        return binding.getRoot();
    }
    private void setupUI(LayoutInflater inflater, ViewGroup parent, LifecycleOwner viewLifecycleOwner) {
        binding.tvSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cal = Calendar.getInstance();
                mdate = cal.get(Calendar.DATE);
                mmonth = cal.get(Calendar.MONTH);
                myear = cal.get(Calendar.YEAR);
                new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int monthOfYear, int month, int date) {
                        int tmonth = month+1;
                        String selDate = (monthOfYear + "-" + tmonth + "-" + date);
                        binding.tvSelectDate.setText(CalendarUtils.getFormatedDatefromString(selDate));

                    }
                }, myear, mmonth, mdate).show();
            }
        });
        final String[] selectcustomertype = {"Farmer", "Customer"};
        binding.tvselectcustomertype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDropDown(view, binding.tvselectcustomertype, selectcustomertype);
            }
        });
        final String[] selectcustomer = {"Farmer", "Customer"};
        binding.tvselectcustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDropDown(view, binding.tvselectcustomer, selectcustomer);
            }
        });
    }
}

package com.vst.myapplication.UI.Advance;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import com.vst.myapplication.R;
import com.vst.myapplication.Room.roomRepository;
import com.vst.myapplication.Services.ProjectRepository;
import com.vst.myapplication.UI.Rates.rates_VM;
import com.vst.myapplication.Utils.BaseFragment;
import com.vst.myapplication.Utils.CalendarUtils;
import com.vst.myapplication.Utils.StringUtils;
import com.vst.myapplication.dataObject.advanceDO;
import com.vst.myapplication.dataObject.farmerDO;
import com.vst.myapplication.databinding.AddadvanceBinding;

import java.util.Calendar;

public class addadvance extends BaseFragment {
    AddadvanceBinding binding;
    roomRepository roomrepo;
    private ProjectRepository repository;
    int mdate,mmonth,myear;
    advancesVM advancesV_M;
    int SLNO = 0;
    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState, LifecycleOwner viewLifecycleOwner) {
        binding = DataBindingUtil.inflate(inflater, R.layout.addadvance, parent, false);
        advancesV_M = new ViewModelProvider(this ).get(advancesVM.class);
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
        binding.tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String customer = binding.tvselectcustomer.getText().toString();
                String Custtype = binding.tvselectcustomertype.getText().toString();
                String date = binding.tvSelectDate.getText().toString();
                String amount = binding.etamount.getText().toString();
                String remarks = binding.etremarks.getText().toString();
                if(!customer.isEmpty()&&!date.isEmpty()&&!amount.isEmpty()){
                    //Insert
                    advanceDO advance = new advanceDO();
                    advance.SLNO = SLNO;
                    advance.ID = "1";
                    advance.TDATE = date;
                    advance.NAME = customer;
                    advance.CUSTOMERTYPE = Custtype;
                    advance.AMOUNT = amount;
                    advance.REMARKS = remarks;
                    advancesV_M.insertAdvance(advance);
                    //farmersVM.insertFarmer(farmerDo);
                }
                else{
                    showCustomDialog(getContext(),"Error","Please Fillup all the Details","OK",null,"");
                }
            }
        });
    }
}

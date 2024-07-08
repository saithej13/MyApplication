package com.vst.myapplication.UI.Rates;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.vst.myapplication.R;
import com.vst.myapplication.Services.ProjectRepository;
import com.vst.myapplication.Utils.BaseFragment;
import com.vst.myapplication.Utils.CalendarUtils;
import com.vst.myapplication.dataObject.RateAndDetails;
import com.vst.myapplication.dataObject.rateDO;
import com.vst.myapplication.dataObject.ratedetailsDO;
import com.vst.myapplication.databinding.RatedetailsBinding;
import com.vst.myapplication.databinding.RatedetailscardcellBinding;
import com.vst.myapplication.databinding.RatesEntryPopup2Binding;
import com.vst.myapplication.databinding.RatesdetailedlistBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

public class ratedetails extends BaseFragment {
    RatesdetailedlistBinding binding;
    boolean addRate=false;
    ImageView ivdropdownmilktype;
    ImageView iveditrate;
    ImageView ivdeleterate;
    LinearLayout startdate;
    LinearLayout enddate;
    int mdate,mmonth,myear;
    private Dialog dialog;
    rates_VM ratesVm;
    rateDO rateDo;
    Vector<ratedetailsDO> vecratedetailsdo;
    ratedetailsAdapter ratedetailsadapter;
    RatesEntryPopup2Binding ratesEntryPopupBinding;


    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState, LifecycleOwner viewLifecycleOwner) {
        binding = DataBindingUtil.inflate(inflater, R.layout.ratesdetailedlist, parent, false);
        binding.setLifecycleOwner(viewLifecycleOwner);
        ratesVm = new ViewModelProvider(this ).get(rates_VM.class);
        vecratedetailsdo = new Vector<ratedetailsDO>();
        ratedetailsadapter = new ratedetailsAdapter(getContext(),vecratedetailsdo);
        setupUI(inflater,parent,viewLifecycleOwner);
        return binding.getRoot();
    }
    private void setupUI(LayoutInflater inflater, ViewGroup parent, LifecycleOwner viewLifecycleOwner) {
        List<ratedetailsDO> ratedetailsList = new ArrayList<>();
        addRate = getArguments().getBoolean("addRate");
        ivdropdownmilktype = binding.llOverView.dropdownmilktype;
        iveditrate = binding.llOverView.ivedit;
        ivdeleterate = binding.llOverView.ivdelete;
        startdate = binding.llOverView.llStartDate;
        enddate = binding.llOverView.llEndDate;
        if(addRate){
            ivdropdownmilktype.setVisibility(View.VISIBLE);
            iveditrate.setVisibility(View.GONE);
            ivdeleterate.setVisibility(View.GONE);
        }else {
            ivdropdownmilktype.setVisibility(View.GONE);
            iveditrate.setVisibility(View.VISIBLE);
            ivdeleterate.setVisibility(View.VISIBLE);
        }
        if (rateDo != null) {
            binding.llOverView.tvstartdate.setText(rateDo.STARTDATE);
            binding.llOverView.tvenddate.setText(rateDo.ENDDATE);
            binding.llOverView.tvmilktype.setText(rateDo.MILKTYPE);
            vecratedetailsdo.add(rateDo.ratedetailsDO);
            ratedetailsAdapter ratedetailsadapter = new ratedetailsAdapter(getContext(),vecratedetailsdo);
            binding.rcvRatedetails.setLayoutManager(new LinearLayoutManager(parent.getContext()));
//            ratesAdapter = new RatesAdapter(parent.getContext(), rateAndDetails);
            binding.rcvRatedetails.setAdapter(ratedetailsadapter);
            binding.rcvRatedetails.setHasFixedSize(true);
        }

//        binding.llOverView.ivsave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(!binding.llOverView.tvmilktype.getText().toString().isEmpty()&&!binding.llOverView.tvstartdate.getText().toString().isEmpty()&&!binding.llOverView.tvenddate.getText().toString().isEmpty())
//                {
//                    rateDo.BCODE = "1";
//                    rateDo.MILKTYPE = binding.llOverView.tvmilktype.getText().toString();
//                    rateDo.STARTDATE = CalendarUtils.getFormatedDatefromString(binding.llOverView.tvstartdate.getText().toString());
//                    rateDo.ENDDATE = CalendarUtils.getFormatedDatefromString(binding.llOverView.tvenddate.getText().toString());
//                }
//                else {
//                    showCustomDialog(getContext(),"Error","Please Select Milk Type , Start Date and End Date!","OK",null,"");
//                }
//            }
//        });
        startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cal = Calendar.getInstance();
                mdate = cal.get(Calendar.DATE);
                mmonth = cal.get(Calendar.MONTH);
                myear = cal.get(Calendar.YEAR);
                new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int monthOfYear, int month, int date) {
                        String selDate = (monthOfYear + "-" + month + "-" + date);
                        binding.llOverView.tvstartdate.setText(CalendarUtils.getFormatedDatefromString(selDate));
                    }
                }, myear, mmonth, mdate).show();
            }
        });
        enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cal = Calendar.getInstance();
                mdate = cal.get(Calendar.DATE);
                mmonth = cal.get(Calendar.MONTH);
                myear = cal.get(Calendar.YEAR);
                new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int monthOfYear, int month, int date) {
                        String selDate = (monthOfYear + "-" + month + "-" + date);
                        binding.llOverView.tvenddate.setText(CalendarUtils.getFormatedDatefromString(selDate));

                    }
                }, myear, mmonth, mdate).show();
            }
        });
        binding.llOverView.llmtype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getContext() != null) {
                    final String[] arraySpinner = new String[]{
                            "Cow", "Buff", "Both"
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Select an option");
                    builder.setItems(arraySpinner, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String selectedOption = arraySpinner[which];
                            binding.llOverView.tvmilktype.setText(selectedOption);
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    Log.e("nothing phone3", "");
                }
            }
        });
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog == null || !dialog.isShowing()) {
                    ratesEntryPopupBinding = DataBindingUtil.inflate(inflater, R.layout.rates_entry_popup2, parent, false);
                    dialog = new Dialog(parent.getContext(), R.style.Dialog);
                    dialog.setContentView(ratesEntryPopupBinding.getRoot());
                    ratesEntryPopupBinding.btnOK.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ratedetailsDO ratedetailsDo = new ratedetailsDO();
                            ratedetailsDo.FATMIN = Double.parseDouble(ratesEntryPopupBinding.etfatmin.getText().toString());
                            ratedetailsDo.FATMAX = Double.parseDouble(ratesEntryPopupBinding.etfatmax.getText().toString());
                            ratedetailsDo.SNFMIN = Double.parseDouble(ratesEntryPopupBinding.etsnfmin.getText().toString());
                            ratedetailsDo.SNFMAX = Double.parseDouble(ratesEntryPopupBinding.etsnfmax.getText().toString());
                            ratedetailsDo.RATE = Double.parseDouble(ratesEntryPopupBinding.etRate.getText().toString());
                            vecratedetailsdo.add(ratedetailsDo);
                            ratedetailsAdapter ratedetailsadapter = new ratedetailsAdapter(getContext(),vecratedetailsdo);
                            binding.rcvRatedetails.setLayoutManager(new LinearLayoutManager(parent.getContext()));
                            binding.rcvRatedetails.setAdapter(ratedetailsadapter);
                            binding.rcvRatedetails.setHasFixedSize(true);
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            }
        });
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!binding.llOverView.tvmilktype.getText().toString().isEmpty()&&!binding.llOverView.tvstartdate.getText().toString().isEmpty()&&!binding.llOverView.tvenddate.getText().toString().isEmpty())
                {
                    try{
                        RateAndDetails rateAndDetails = new RateAndDetails(rateDo, vecratedetailsdo);
                        if (!rateAndDetails.rateDetailsList.isEmpty()) {
                            ratesVm.insertRate(rateAndDetails);
                        }else {
                            showCustomDialog(getContext(),"Error","Add Atleast one RateDetail to Save!","OK",null,"");
                        }
                    }
                    catch (Exception ex){
                        showCustomDialog(getContext(),"Exception",""+ex.getMessage(),"OK",null,"");
                    }

                }
                else {
                    showCustomDialog(getContext(),"Error","Please Select Milk Type , Start Date and End Date!","OK",null,"");
                }

            }
        });
    }
    public void refreshData(){


    }
}

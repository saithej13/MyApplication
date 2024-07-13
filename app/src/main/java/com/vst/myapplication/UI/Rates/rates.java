package com.vst.myapplication.UI.Rates;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.vst.myapplication.R;
import com.vst.myapplication.Services.ProjectRepository;
import com.vst.myapplication.UI.Farmers.FarmersAdapter;
import com.vst.myapplication.UI.Farmers.famers_VM;
import com.vst.myapplication.UI.Login.Login;
import com.vst.myapplication.Utils.BaseFragment;
import com.vst.myapplication.Utils.CalendarUtils;
import com.vst.myapplication.Utils.NetworkUtils;
import com.vst.myapplication.dataObject.RateAndDetails;
import com.vst.myapplication.dataObject.farmerDO;
import com.vst.myapplication.dataObject.rateDO;
import com.vst.myapplication.dataObject.ratedetailsDO;
import com.vst.myapplication.databinding.FarmerEntryPopupBinding;
import com.vst.myapplication.databinding.FarmersBinding;
import com.vst.myapplication.databinding.RatesBinding;
import com.vst.myapplication.databinding.RatesEntryPopupBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class rates extends BaseFragment implements RatesAdapter.ItemClickListener{
    private Dialog dialog;
    boolean active=true;
    RatesBinding binding;
    RatesAdapter ratesAdapter;
    RatesEntryPopupBinding ratesEntryPopupBinding;
    rates_VM ratesVm;
    int mdate,mmonth,myear;

    RateAndDetails[] rateAndDetails;

    private ProjectRepository repository;



    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState, LifecycleOwner viewLifecycleOwner) {
        binding = DataBindingUtil.inflate(inflater, R.layout.rates, parent, false);
        repository = new ProjectRepository();
        ratesVm = new ViewModelProvider(this ).get(rates_VM.class);
        binding.setLifecycleOwner(viewLifecycleOwner);
        setupUI(inflater,parent,viewLifecycleOwner);
        return binding.getRoot();
    }
    private void setupUI(LayoutInflater inflater, ViewGroup parent, LifecycleOwner viewLifecycleOwner) {
        // Implement any additional UI setup here
        ratesAdapter = new RatesAdapter(parent.getContext(), null,getActivity());
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("tbltest");
//        myRef.setValue("Hello, World!");

        if (NetworkUtils.isNetworkAvailable(parent.getContext())) {
//            repository.getfarmers().observe(this, new Observer<JSONObject>() {
            repository.getrates().observe(this, new Observer<JsonObject>() {
                @Override
                public void onChanged(JsonObject jsonObject) {
                    if (jsonObject != null) {
                        Log.d("TAG", jsonObject.toString());
                        Gson gson = new Gson();
                        rateAndDetails = gson.fromJson(jsonObject.getAsJsonArray("Data"),RateAndDetails[].class);
                        if (rateAndDetails != null) {
                            if (rateAndDetails.length > 0) {
                                binding.rcvrates.setLayoutManager(new LinearLayoutManager(parent.getContext()));
                                ratesAdapter = new RatesAdapter(parent.getContext(), rateAndDetails,getActivity());
                                binding.rcvrates.setAdapter(ratesAdapter);
                                binding.rcvrates.setHasFixedSize(true);
                            } else {
                                rateAndDetails = new RateAndDetails[]{};
                                if (ratesAdapter != null) {
                                    ratesAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }
                }
            });

            binding.fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle mBundle = new Bundle();
                    mBundle.putBoolean("addRate", true);
                    ratedetails ratedetail = new ratedetails();
                    ratedetail.setArguments(mBundle);
                    FragmentManager fragmentManager = getParentFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                            .replace(R.id.frame, ratedetail, "")
                            .addToBackStack("")
                            .commitAllowingStateLoss();


//                    if (dialog == null || !dialog.isShowing()) {
//                        ratesEntryPopupBinding = DataBindingUtil.inflate(inflater, R.layout.rates_entry_popup, parent, false);
//                        dialog = new Dialog(parent.getContext(), R.style.Dialog);
//                        dialog.setContentView(ratesEntryPopupBinding.getRoot());
//                        ratesEntryPopupBinding.tvFromDate.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                //R.style.DialogTheme,
//                                final Calendar cal = Calendar.getInstance();
//                                mdate = cal.get(Calendar.DATE);
//                                mmonth = cal.get(Calendar.MONTH);
//                                myear = cal.get(Calendar.YEAR);
//                                new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
//                                    @Override
//                                    public void onDateSet(DatePicker view, int monthOfYear, int month, int date) {
//                                        String selDate = (monthOfYear + "-" + month + "-" + date);
////                                    String selectedDate = CalendarUtils.getOrderSummaryDate(yearSel, monthOfYear, dayOfMonth);
//                                        ratesEntryPopupBinding.tvFromDate.setText(CalendarUtils.getFormatedDatefromString(selDate));
//                                    }
//                                }, myear, mmonth, mdate).show();
//                            }
//                        });
//                        ratesEntryPopupBinding.tvToDate.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                //R.style.DialogTheme,
//                                final Calendar cal = Calendar.getInstance();
//                                mdate = cal.get(Calendar.DATE);
//                                mmonth = cal.get(Calendar.MONTH);
//                                myear = cal.get(Calendar.YEAR);
//                                new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
//                                    @Override
//                                    public void onDateSet(DatePicker view, int monthOfYear, int month, int date) {
//                                        String selDate = (monthOfYear + "-" + month + "-" + date);
////                                    String selectedDate = CalendarUtils.getOrderSummaryDate(yearSel, monthOfYear, dayOfMonth);
//                                        ratesEntryPopupBinding.tvToDate.setText(CalendarUtils.getFormatedDatefromString(selDate));
//
//                                    }
//                                }, myear, mmonth, mdate).show();
//                            }
//                        });
//                        ratesEntryPopupBinding.mtype.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                if (getContext() != null) {
//                                    final String[] arraySpinner = new String[]{
//                                            "Cow", "Buff", "Both"
//                                    };
//                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                                    builder.setTitle("Select an option");
//                                    builder.setItems(arraySpinner, new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            String selectedOption = arraySpinner[which];
//                                            ratesEntryPopupBinding.mtype.setText(selectedOption);
//                                        }
//                                    });
//                                    AlertDialog dialog = builder.create();
//                                    dialog.show();
//                                } else {
//                                    Log.e("nothing phone3", "");
//                                }
//                            }
//                        });
//                        ratesEntryPopupBinding.close.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//
//                                dialog.dismiss();
//                            }
//                        });
//                        ratesEntryPopupBinding.btnOK.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                rateDO rateDo = new rateDO();
//                                rateDo.STARTDATE= "2024-01-01";
//                                rateDo.ENDDATE= "2024-12-31";
//                                rateDo.MILKTYPE = "Cow";
//                                rateDo.BCODE = "1";
//                                Vector<rateDO> vecrateDO = new Vector<>();
//                                vecrateDO.add(rateDo);
//
//                                ratedetailsDO ratedetailsDo = new ratedetailsDO();
//                                ratedetailsDo.FATMIN = 3.0;
//                                ratedetailsDo.FATMAX = 6.0;
//                                ratedetailsDo.SNFMIN = 8.0;
//                                ratedetailsDo.SNFMAX = 8.5;
//                                ratedetailsDo.RATE = 320.0;
//                                ratedetailsDO ratedetailsDo1 = new ratedetailsDO();
//                                ratedetailsDo1.FATMIN = 2.5;
//                                ratedetailsDo1.FATMAX = 2.9;
//                                ratedetailsDo1.SNFMIN = 8.0;
//                                ratedetailsDo1.SNFMAX = 8.5;
//                                ratedetailsDo1.RATE = 200.0;
//                                List<ratedetailsDO> ratedetailsList = new ArrayList<>();
//                                ratedetailsList.add(ratedetailsDo1);
//                                ratedetailsList.add(ratedetailsDo1);
//                                RateAndDetails rateAndDetails = new RateAndDetails(rateDo,ratedetailsList);
//                                ratesVm.insertRate(rateAndDetails);
////                                if (!TextUtils.isEmpty(ratesEntryPopupBinding.tvFromDate.getText().toString()) && !TextUtils.isEmpty(ratesEntryPopupBinding.tvToDate.getText().toString()) && !TextUtils.isEmpty(ratesEntryPopupBinding.mtype.getText().toString()) && !TextUtils.isEmpty(ratesEntryPopupBinding.etfatmin.getText().toString()) && !TextUtils.isEmpty(ratesEntryPopupBinding.etfatmax.getText().toString()) && !TextUtils.isEmpty(ratesEntryPopupBinding.etsnfmin.getText().toString()) && !TextUtils.isEmpty(ratesEntryPopupBinding.etsnfmax.getText().toString()) && !TextUtils.isEmpty(ratesEntryPopupBinding.etRate.getText().toString())) {
////                                    rateDO rateDo = new rateDO();
////                                    rateDo.STARTDATE= ratesEntryPopupBinding.tvFromDate.getText().toString();
////                                    rateDo.ENDDATE= ratesEntryPopupBinding.tvToDate.getText().toString();
////                                    rateDo.MILKTYPE = ratesEntryPopupBinding.mtype.getText().toString();
////                                    rateDo.FATMIN = Double.parseDouble(ratesEntryPopupBinding.etfatmin.getText().toString());
////                                    rateDo.FATMAX = Double.parseDouble(ratesEntryPopupBinding.etfatmax.getText().toString());
////                                    rateDo.SNFMIN = Double.parseDouble(ratesEntryPopupBinding.etsnfmin.getText().toString());
////                                    rateDo.SNFMAX = Double.parseDouble(ratesEntryPopupBinding.etsnfmax.getText().toString());
////                                    rateDo.RATE = Double.parseDouble(ratesEntryPopupBinding.etRate.getText().toString());
////                                    ratesVm.insertRate(rateDo);
////                                } else {
////                                    Toast.makeText(getContext(), "please fill all the fields", Toast.LENGTH_SHORT).show();
////                                }
//                            }
//                        });
//                        ratesEntryPopupBinding.btnCancle.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                dialog.dismiss();
//                            }
//                        });
//                        dialog.show();
//                    }
                }
            });
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.d("TAG", "onItemClick"+position);
    }
}

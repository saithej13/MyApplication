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
import com.vst.myapplication.Utils.BaseFragment;
import com.vst.myapplication.Utils.CalendarUtils;
import com.vst.myapplication.Utils.NetworkUtils;
import com.vst.myapplication.dataObject.farmerDO;
import com.vst.myapplication.dataObject.rateDO;
import com.vst.myapplication.databinding.FarmerEntryPopupBinding;
import com.vst.myapplication.databinding.FarmersBinding;
import com.vst.myapplication.databinding.RatesBinding;
import com.vst.myapplication.databinding.RatesEntryPopupBinding;

import java.util.Calendar;
import java.util.HashMap;

public class rates extends BaseFragment {
    private Dialog dialog;
    boolean active=true;
    RatesBinding binding;
    RatesAdapter ratesAdapter;
    RatesEntryPopupBinding ratesEntryPopupBinding;
    rates_VM ratesVm;
    int mdate,mmonth,myear;

    rateDO[] rateDOs;

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
        ratesAdapter = new RatesAdapter(parent.getContext(), null);
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
                        rateDOs = gson.fromJson(jsonObject.getAsJsonArray("Data"),rateDO[].class);
                       // rateDOs = gson.fromJson(jsonObject.toString(), rateDO[].class);
                        if (rateDOs != null) {
//                            Arrays.sort(farmerDOs, new Comparator<farmerDOs>() {
//                                @Override
//                                public int compare(farmerDOs order1, farmerDOs order2) {
//                                    return order2.getOrderDate().compareTo(order1.getOrderDate());
//                                }
//                            });
                            if (rateDOs.length > 0) {
//                                tvNoData.setVisibility(View.GONE);
//                                layoutViewPager.setVisibility(View.VISIBLE);
                                //setupTabs(orders);
                                binding.rcvrates.setLayoutManager(new LinearLayoutManager(parent.getContext()));
                                ratesAdapter = new RatesAdapter(parent.getContext(), rateDOs);
                                binding.rcvrates.setAdapter(ratesAdapter);
                                binding.rcvrates.setHasFixedSize(true);
                            } else {
//                                tvNoData.setVisibility(View.VISIBLE);
//                                layoutViewPager.setVisibility(View.GONE);
                                rateDOs = new rateDO[]{};
                                if (ratesAdapter != null) {
                                    ratesAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }
                }
            });

//        farmersVM.getFarmerdata();
//        farmersAdapter.setFarmers();
            binding.fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialog == null || !dialog.isShowing()) {
                        ratesEntryPopupBinding = DataBindingUtil.inflate(inflater, R.layout.rates_entry_popup, parent, false);
                        dialog = new Dialog(parent.getContext(), R.style.Dialog);
                        dialog.setContentView(ratesEntryPopupBinding.getRoot());
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("tbltest");
                        myRef.setValue("Hello, World!");
                        addQuoteToDB("quotetest","authortest");
                        ratesEntryPopupBinding.tvFromDate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //R.style.DialogTheme,
                                final Calendar cal = Calendar.getInstance();
                                mdate = cal.get(Calendar.DATE);
                                mmonth = cal.get(Calendar.MONTH);
                                myear = cal.get(Calendar.YEAR);
                                new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int monthOfYear, int month, int date) {
                                        String selDate = (monthOfYear + "-" + month + "-" + date);
//                                    String selectedDate = CalendarUtils.getOrderSummaryDate(yearSel, monthOfYear, dayOfMonth);
                                        ratesEntryPopupBinding.tvFromDate.setText(CalendarUtils.getFormatedDatefromString(selDate));
                                    }
                                }, myear, mmonth, mdate).show();
                            }
                        });
                        ratesEntryPopupBinding.tvToDate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //R.style.DialogTheme,
                                final Calendar cal = Calendar.getInstance();
                                mdate = cal.get(Calendar.DATE);
                                mmonth = cal.get(Calendar.MONTH);
                                myear = cal.get(Calendar.YEAR);
                                new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int monthOfYear, int month, int date) {
                                        String selDate = (monthOfYear + "-" + month + "-" + date);
//                                    String selectedDate = CalendarUtils.getOrderSummaryDate(yearSel, monthOfYear, dayOfMonth);
                                        ratesEntryPopupBinding.tvToDate.setText(CalendarUtils.getFormatedDatefromString(selDate));

                                    }
                                }, myear, mmonth, mdate).show();
                            }
                        });
                        ratesEntryPopupBinding.mtype.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
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
                                            ratesEntryPopupBinding.mtype.setText(selectedOption);
                                        }
                                    });
                                    AlertDialog dialog = builder.create();
                                    dialog.show();
                                } else {
                                    Log.e("nothing phone3", "");
                                }
                            }
                        });
                        ratesEntryPopupBinding.close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                dialog.dismiss();
                            }
                        });
                        ratesEntryPopupBinding.btnOK.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (!TextUtils.isEmpty(ratesEntryPopupBinding.tvFromDate.getText().toString()) && !TextUtils.isEmpty(ratesEntryPopupBinding.tvToDate.getText().toString()) && !TextUtils.isEmpty(ratesEntryPopupBinding.mtype.getText().toString()) && !TextUtils.isEmpty(ratesEntryPopupBinding.etfatmin.getText().toString()) && !TextUtils.isEmpty(ratesEntryPopupBinding.etfatmax.getText().toString()) && !TextUtils.isEmpty(ratesEntryPopupBinding.etsnfmin.getText().toString()) && !TextUtils.isEmpty(ratesEntryPopupBinding.etsnfmax.getText().toString()) && !TextUtils.isEmpty(ratesEntryPopupBinding.etRate.getText().toString())) {
                                    rateDO rateDo = new rateDO();
                                    rateDo.STARTDATE= ratesEntryPopupBinding.tvFromDate.getText().toString();
                                    rateDo.ENDDATE= ratesEntryPopupBinding.tvToDate.getText().toString();
                                    rateDo.MILKTYPE = ratesEntryPopupBinding.mtype.getText().toString();
                                    rateDo.FATMIN = Double.parseDouble(ratesEntryPopupBinding.etfatmin.getText().toString());
                                    rateDo.FATMAX = Double.parseDouble(ratesEntryPopupBinding.etfatmax.getText().toString());
                                    rateDo.SNFMIN = Double.parseDouble(ratesEntryPopupBinding.etsnfmin.getText().toString());
                                    rateDo.SNFMAX = Double.parseDouble(ratesEntryPopupBinding.etsnfmax.getText().toString());
                                    rateDo.RATE = Double.parseDouble(ratesEntryPopupBinding.etRate.getText().toString());
                                    ratesVm.insertRate(rateDo);
                                } else {
                                    Toast.makeText(getContext(), "please fill all the fields", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        ratesEntryPopupBinding.btnCancle.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }
                }
            });
        }
    }
    private void addQuoteToDB(String quote, String author) {
        HashMap<String, Object> quoteHashmap = new HashMap<>();
        quoteHashmap.put("quote",quote);
        quoteHashmap.put("author",author);

        //instantiate database connection
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference quotesRef = database.getReference("quotes");

        String key = quotesRef.push().getKey();
        quoteHashmap.put("key",key);

        quotesRef.child(key).setValue(quoteHashmap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getContext(), "Added", Toast.LENGTH_SHORT).show();
//
            }
        });

    }
}

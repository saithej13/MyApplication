package com.vst.myapplication.UI.MCollection;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vst.myapplication.R;
import com.vst.myapplication.Services.ProjectRepository;
import com.vst.myapplication.UI.Farmers.FarmersAdapter;
import com.vst.myapplication.UI.Rates.RatesAdapter;
import com.vst.myapplication.UI.Rates.rates_VM;
import com.vst.myapplication.Utils.BaseFragment;
import com.vst.myapplication.Utils.CalendarUtils;
import com.vst.myapplication.Utils.NetworkUtils;
import com.vst.myapplication.dataObject.farmerDO;
import com.vst.myapplication.dataObject.milkDO;
import com.vst.myapplication.dataObject.rateDO;
import com.vst.myapplication.databinding.McollectionBinding;
import com.vst.myapplication.databinding.RatesBinding;
import com.vst.myapplication.databinding.RatesEntryPopupBinding;

import java.util.Calendar;
import java.util.List;

public class milkCollection extends BaseFragment {
    private Dialog dialog;
    boolean active=true;
    McollectionBinding binding;
    milkCollectionAdapter milkCollectionAdapter;
    milkCollection_VM milkCollectionVm;
    int mdate,mmonth,myear;
    boolean restrict_mtype =false;
    farmerDO[] farmerDOs;

    milkDO[] milkDOS;
    rateDO[] rateDOs;

    private ProjectRepository repository;
    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState, LifecycleOwner viewLifecycleOwner) {
        binding = DataBindingUtil.inflate(inflater, R.layout.mcollection, parent, false);
        repository = new ProjectRepository();
        milkCollectionVm = new ViewModelProvider(this ).get(milkCollection_VM.class);
        binding.setLifecycleOwner(viewLifecycleOwner);
        setupUI(inflater,parent,viewLifecycleOwner);
        return binding.getRoot();
    }
    private void setupUI(LayoutInflater inflater, ViewGroup parent, LifecycleOwner viewLifecycleOwner) {
        // Implement any additional UI setup here
        milkCollectionAdapter = new milkCollectionAdapter(parent.getContext(), null);
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        if (hour < 16) {
            binding.shiftm.setImageResource(R.drawable.ic_morning);
            binding.textshiftm.setText("M");
        }
        else {
            binding.shiftm.setImageResource(R.drawable.ic_evening);
            binding.textshiftm.setText("E");
        }
        binding.layoutmtype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!binding.textmtypec.getText().equals("Cow")) {
                    binding.textmtypec.setText("Cow");
                    binding.mtypec.setImageResource(R.drawable.ic_cow);
                } else {
                    binding.textmtypec.setText("Buff");
                    binding.mtypec.setImageResource(R.drawable.ic_buffalo);
                }
            }
        });
        binding.layoutshift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!binding.textshiftm.getText().equals("M")){
                    binding.textshiftm.setText("M");
                    binding.shiftm.setImageResource(R.drawable.ic_morning);
                }
                else {
                    binding.textshiftm.setText("E");
                    binding.shiftm.setImageResource(R.drawable.ic_evening);
                }
                refreshData();
            }
        });
        binding.farmerid.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!binding.farmerid.getText().toString().isEmpty()&&TextUtils.isDigitsOnly(binding.farmerid.getText())) {
                    JsonObject jsonObject1 = new JsonObject();
                    jsonObject1.addProperty("FARMERID",binding.farmerid.getText().toString());
                    repository.getfarmerbyid(jsonObject1).observe(getViewLifecycleOwner(), new Observer<JsonObject>() {
                        @Override
                        public void onChanged(JsonObject jsonObject) {
                            if (jsonObject != null) {
                                Log.d("TAG", jsonObject.toString());
                                Gson gson = new Gson();
                                farmerDOs = gson.fromJson(jsonObject.getAsJsonArray("Data"), farmerDO[].class);
                                if (farmerDOs != null) {
                                    if (farmerDOs.length > 0) {
                                        binding.fname.setText(farmerDOs[0].FARMERNAME);
                                        if (farmerDOs[0].MILKTYPE.equals("Both")) {
                                            restrict_mtype = false;
                                        } else {
                                            restrict_mtype = true;
                                            binding.textmtypec.setText(farmerDOs[0].MILKTYPE);
                                            if (binding.textmtypec.getText().equals("Cow")) {
                                                binding.mtypec.setImageResource(R.drawable.ic_cow);
                                            } else {
                                                binding.textmtypec.setText("Buff");
                                                binding.mtypec.setImageResource(R.drawable.ic_buffalo);
                                            }
                                        }
//
                                    } else {
                                        farmerDOs = new farmerDO[]{};
                                        binding.fname.setText("");
                                    }
                                }
                            }
                        }
                    });
//                    milkCollectionVm.getFarmerbycode(parent.getContext(),Integer.parseInt(binding.farmerid.getText().toString())).observe(parent.getContext(), new Observer<List<tblfarmers>>() {
//                        @Override
//                        public void onChanged(List<tblfarmers> tblfarmers) {
//                            if (!tblfarmers.isEmpty()) {
//                                fname.setText(tblfarmers.get(0).getFirstname());
//                                if(tblfarmers.get(0).getMilktype().equals("Both")){
//                                    restrict_mtype = false;
//                                }
//                                else {
//                                    restrict_mtype = true;
//                                    textmtypec.setText(tblfarmers.get(0).getMilktype());
//                                    if (textmtypec.getText().equals("Cow")) {
//                                        mtypec.setImageResource(R.drawable.ic_cow);
//                                    } else {
//                                        textmtypec.setText("Buff");
//                                        mtypec.setImageResource(R.drawable.ic_buffalo);
//                                    }
//                                }
//                            }
//                            else {
//                                binding.farmerid.setText("");
//                                Toast.makeText(parent.getContext(), "Farmer Code does not found!", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
                }
                else
                    Toast.makeText(parent.getContext(),"Enter Farmer Code",Toast.LENGTH_SHORT).show();
            }
        });
        binding.textdate.setText(CalendarUtils.getFormatedDatefromString(CalendarUtils.getDatePattern2()));
        binding.msnf.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calc();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.mfat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calc();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.mqty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calc();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!binding.farmerid.getText().toString().isEmpty()||!binding.fname.getText().toString().isEmpty()||
                        !binding.textdate.getText().toString().isEmpty()||!binding.mqty.getText().toString().isEmpty()||
                        !binding.mfat.getText().toString().isEmpty()||!binding.msnf.getText().toString().isEmpty()||
                        !binding.rate.getText().toString().isEmpty()||!binding.amt.getText().toString().isEmpty()||
                        !binding.textshiftm.getText().toString().isEmpty()||!binding.textmtypec.getText().toString().isEmpty()
                ) {
                    //                    {
                    //     "TDATE":"2024-06-29",
                    //     "SHIFT":"M",
                    //     "FARMERID":1,
                    //     "FARMERNAME":"TEST",
                    //     "MILKTYPE":"COW",
                    //     "QUANTITY":1.0,
                    //     "FAT":3.0,
                    //     "SNF":8.0,
                    //     "RATE":30.0,
                    //     "AMOUNT":30.0
                    // }
//                    JsonObject jsonObject1 = new JsonObject();
//                    jsonObject1.addProperty("TDATE",binding.textdate.getText().toString());
//                    jsonObject1.addProperty("SHIFT",binding.textshiftm.getText().toString());
//                    jsonObject1.addProperty("FARMERID",binding.farmerid.getText().toString());
//                    jsonObject1.addProperty("FARMERNAME",binding.fname.getText().toString());
//                    jsonObject1.addProperty("MILKTYPE",binding.textmtypec.getText().toString());
//                    jsonObject1.addProperty("QUANTITY",Double.parseDouble(binding.mqty.getText().toString()));
//                    jsonObject1.addProperty("FAT",Double.parseDouble(binding.mfat.getText().toString()));
//                    jsonObject1.addProperty("SNF",Double.parseDouble(binding.msnf.getText().toString()));
//                    jsonObject1.addProperty("RATE",Double.parseDouble(binding.rate.getText().toString()));
//                    jsonObject1.addProperty("AMOUNT",Double.parseDouble(binding.amt.getText().toString()));
                    milkDO milkDo = new milkDO();
                    milkDo.TDATE = binding.textdate.getText().toString();
                    milkDo.SHIFT = binding.textshiftm.getText().toString();
                    milkDo.FARMERID = binding.farmerid.getText().toString();
                    milkDo.FARMERNAME = binding.fname.getText().toString();
                    milkDo.MILKTYPE = binding.textmtypec.getText().toString();
                    milkDo.QUANTITY = Double.parseDouble(binding.mqty.getText().toString());
                    milkDo.FAT = Double.parseDouble(binding.mfat.getText().toString());
                    milkDo.SNF = Double.parseDouble(binding.msnf.getText().toString());
                    milkDo.RATE = Double.parseDouble(binding.rate.getText().toString());
                    milkDo.AMOUNT = Double.parseDouble(binding.amt.getText().toString());
                    milkCollectionVm.insertMdata(milkDo);
                    clearfields();
                    refreshData();
                    //print(mdata);
                }
                else {
                    Toast.makeText(getContext(),"please make sure all the fields are filled up",Toast.LENGTH_SHORT).show();
                }
            }
        });
        if (NetworkUtils.isNetworkAvailable(parent.getContext())) {
//            repository.getfarmers().observe(this, new Observer<JSONObject>() {
//            repository.getfarmers().observe(this, new Observer<JsonArray>() {
//                @Override
//                public void onChanged(JsonArray jsonArray) {
//                    if (jsonArray != null) {
//                        Log.d("TAG", jsonArray.toString());
//                        Gson gson = new Gson();
//                        milkDOS = gson.fromJson(jsonArray.toString(), milkDO[].class);
//                        if (milkDOS != null) {
//                            if (milkDOS.length > 0) {
//                                binding.rcvmData.setLayoutManager(new LinearLayoutManager(parent.getContext()));
//                                milkCollectionAdapter = new milkCollectionAdapter(parent.getContext(), milkDOS);
//                                binding.rcvmData.setAdapter(milkCollectionAdapter);
//                                binding.rcvmData.setHasFixedSize(true);
//                            } else {
//                                milkDOS = new milkDO[]{};
//                                if (milkCollectionAdapter != null) {
//                                    milkCollectionAdapter.notifyDataSetChanged();
//                                }
//                            }
//                        }
//                    }
//                }
//            });


        }
    }
    public void clearfields(){
        binding.farmerid.setText("");
        binding.fname.setText("");
        binding.mqty.setText("");
        binding.mfat.setText("");
        binding.msnf.setText("");
        binding.rate.setText("0.0");
        binding.amt.setText("0.0");
    }
    public void getTotal(){
        try {
            if(milkDOS!=null&&milkDOS.length>0) {
                double qty = 0, amt = 0, ltrfat = 0, ltrsnf = 0;
                for (int i = 0; i < milkDOS.length; i++) {
                    qty += milkDOS[i].QUANTITY;
                    ltrfat += (milkDOS[i].FAT * milkDOS[i].QUANTITY) / 100;
                    ltrsnf += (milkDOS[i].SNF * milkDOS[i].QUANTITY) / 100;
                    amt += milkDOS[i].AMOUNT;
                }
                binding.subtotal.setText(String.valueOf(qty));
                binding.avgfat.setText(String.valueOf((ltrfat / qty) / 100));
                binding.avgsnf.setText(String.valueOf((ltrsnf / qty) / 100));
                binding.totalamt.setText(String.valueOf(amt));
            }
            else{
                binding.subtotal.setText("0");
                binding.avgfat.setText("0");
                binding.avgsnf.setText("0");
                binding.totalamt.setText("0");
            }
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    public void refreshData(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("TDATE",binding.textdate.getText().toString());
        jsonObject.addProperty("SHIFT",binding.textshiftm.getText().toString());
        repository.getMData(jsonObject).observe(this, new Observer<JsonObject>() {
            @Override
            public void onChanged(JsonObject jsonObject) {
                if (jsonObject != null) {
                    Log.d("TAG", jsonObject.toString());
                    Gson gson = new Gson();
                    milkDOS = gson.fromJson(jsonObject.getAsJsonArray("Data"),milkDO[].class);
                    // rateDOs = gson.fromJson(jsonObject.toString(), rateDO[].class);
                    if (milkDOS != null) {
                        if (milkDOS.length > 0) {
                            binding.rcvmData.setLayoutManager(new LinearLayoutManager(getContext()));
                            milkCollectionAdapter = new milkCollectionAdapter(getContext(), milkDOS);
                            binding.rcvmData.setAdapter(milkCollectionAdapter);
                            binding.rcvmData.setHasFixedSize(true);
                            getTotal();
                        } else {
                            milkDOS = new milkDO[]{};
                            if (milkCollectionAdapter != null) {
                                milkCollectionAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                }
            }
        });
    }
    public double calc(){
        final double[] amount = {0};
        final double[] rate1 = {0};
        final double[] snfmax = {0};
        try {
            if (binding.mfat.getText().toString().isEmpty()) {
                //Toast.makeText(getContext(), "FAT should not be Empty", Toast.LENGTH_SHORT).show();
            } else if (binding.msnf.getText().toString().isEmpty()) {
                //Toast.makeText(getContext(), "SNF should not be Empty", Toast.LENGTH_SHORT).show();
            } else if (binding.mqty.getText().toString().isEmpty()) {
                //Toast.makeText(getContext(), "QTY should not be Empty", Toast.LENGTH_SHORT).show();
            } else {
                double fat = Double.parseDouble(binding.mfat.getText().toString());
                double snf = Double.parseDouble(binding.msnf.getText().toString());
                double qty = Double.parseDouble(binding.mqty.getText().toString());
//                    {
//                        "MILKTYPE":"COW",
//                            "TDATE":"2024-01-01",
//                            "FAT":3.0,
//                            "SNF":8.0
//                    }
                    JsonObject jsonObject1 = new JsonObject();
                    jsonObject1.addProperty("MILKTYPE",binding.textmtypec.getText().toString());
                    jsonObject1.addProperty("TDATE",binding.textdate.getText().toString());
                    jsonObject1.addProperty("FAT",fat);
                    jsonObject1.addProperty("SNF",snf);
                    repository.getTSrate(jsonObject1).observe(this, new Observer<JsonObject>() {
                        @Override
                        public void onChanged(JsonObject jsonObject) {
                            if (jsonObject != null) {
                                Log.d("TAG", jsonObject.toString());
                                Gson gson = new Gson();
                                rateDOs = gson.fromJson(jsonObject.getAsJsonArray("Data"),rateDO[].class);
                                if (rateDOs != null) {
                                    if (rateDOs.length > 0) {
//                                        binding.fname.setText(farmerDOs[0].FARMERNAME);
                                        if(binding.textmtypec.getText().equals("Cow"))
                                        {
                                            rate1[0] = (rateDOs[0].RATE / 100);
                                            snfmax[0] = (rateDOs[0].SNFMAX);
                                            Log.d("rate[0]", "" + rate1[0]);
                                            rate1[0] = (fat + snf) * (rate1[0]);
                                            amount[0] = rate1[0]*qty;
                                            binding.rate.setText(String.format("%.1f",rate1[0]));
                                            binding.amt.setText(String.format("%.1f",amount[0]));
                                        }
                                        else {
                                            rate1[0] = (rateDOs[0].RATE / 100);
                                            snfmax[0] = (rateDOs[0].SNFMAX);
                                            Log.d("rate[0]", "" + rate1[0]);
                                            //(BMRATE)
                                            rate1[0] = (fat * rate1[0]) - (snfmax[0] - snf);
                                            amount[0] = rate1[0]*qty;
                                            binding.rate.setText(String.format("%.1f",rate1[0]));
                                            binding.amt.setText(String.format("%.1f",amount[0]));
                                        }
                                    } else {
                                        rateDOs = new rateDO[]{};
                                        binding.rate.setText(String.format("%.1f",(rate1[0])));
                                        binding.amt.setText(String.format("%.1f",(amount[0])));
                                    }
                                }
                            }
                        }
                    });
            }
        }
        catch (Exception ex){
            Toast.makeText(getContext(), ""+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return rate1[0];
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshData();
    }
}

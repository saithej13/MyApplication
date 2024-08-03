package com.vst.myapplication.UI.MCollection;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.util.StringUtil;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.vst.myapplication.R;
import com.vst.myapplication.Room.roomRepository;
import com.vst.myapplication.Services.ProjectRepository;
import com.vst.myapplication.Utils.BaseFragment;
import com.vst.myapplication.Utils.CalendarUtils;
import com.vst.myapplication.Utils.ImagePrintDocumentAdapter;
import com.vst.myapplication.Utils.MyApplicationNew;
import com.vst.myapplication.Utils.NetworkUtils;
import com.vst.myapplication.Utils.Preference;
import com.vst.myapplication.Utils.Settings;
import com.vst.myapplication.Utils.StringUtils;
import com.vst.myapplication.dataObject.RateAndDetails;
import com.vst.myapplication.dataObject.farmerDO;
import com.vst.myapplication.dataObject.milkDO;
import com.vst.myapplication.dataObject.rateDO;
import com.vst.myapplication.databinding.McollectionBinding;

import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class milkCollection extends BaseFragment implements milkCollectionAdapter.ItemClickListener{
    private Dialog dialog;
    boolean active=true;
    McollectionBinding binding;
    milkCollectionAdapter milkCollectionAdapter;
    milkCollection_VM milkCollectionVm;
    int mdate,mmonth,myear;
    boolean restrict_mtype =false;
    farmerDO[] farmerDOs;

    milkDO[] milkDOS;
//    rateDO[] rateDOs;
    Preference preference;
    ProjectRepository repository;
    roomRepository roomrepo;
    public static String prnt="";
    OutputStream outputStream;
    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState, LifecycleOwner viewLifecycleOwner) {
        binding = DataBindingUtil.inflate(inflater, R.layout.mcollection, parent, false);
        preference = new Preference(getContext());
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
        binding.textdate.setText(CalendarUtils.getFormatedDatefromString(CalendarUtils.getDatePattern2()));
        if(MyApplicationNew.RoomDB){
            binding.farmerid.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!binding.farmerid.getText().toString().isEmpty()&&TextUtils.isDigitsOnly(binding.farmerid.getText())) {
                        int BCODE = preference.getIntFromPreference("BCODE",0);
                        milkCollectionVm.getFarmerbycodeRoom(getViewLifecycleOwner(),Integer.parseInt(binding.farmerid.getText().toString()),BCODE).observe(getViewLifecycleOwner(), new Observer<List<farmerDO>>() {
                            @Override
                            public void onChanged(List<farmerDO> tblfarmers) {
                                if (!tblfarmers.isEmpty()) {
                                    binding.fname.setText(tblfarmers.get(0).FARMERNAME);
                                    if(tblfarmers.get(0).MILKTYPE.equals("Both")){
                                        restrict_mtype = false;
                                    }
                                    else {
                                        restrict_mtype = true;
                                        binding.textmtypec.setText(tblfarmers.get(0).MILKTYPE);
                                        if (binding.textmtypec.getText().equals("Cow")) {
                                            binding.mtypec.setImageResource(R.drawable.ic_cow);
                                        } else {
                                            binding.textmtypec.setText("Buff");
                                            binding.mtypec.setImageResource(R.drawable.ic_buffalo);
                                        }
                                    }
                                }
                                else {
                                    binding.farmerid.setText("");
                                    showCustomDialog(getContext(),"Farmer Code does not found!","Please enter correct Farmer Code","OK",null,"");
                                }
                            }
                        });
                    }
//                    else
//                        showCustomDialog(getContext(),"Farmer Code","Please enter Farmer Code","OK",null,"");
                }
            });
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
                    if(!binding.farmerid.getText().toString().isEmpty()&&!binding.fname.getText().toString().isEmpty()&&
                            !binding.textdate.getText().toString().isEmpty()&&!binding.mqty.getText().toString().isEmpty()&&
                            !binding.mfat.getText().toString().isEmpty()&&!binding.msnf.getText().toString().isEmpty()&&
                            !binding.rate.getText().toString().isEmpty()&&!binding.amt.getText().toString().isEmpty()&&
                            !binding.textshiftm.getText().toString().isEmpty()&&!binding.textmtypec.getText().toString().isEmpty()
                    ) {
                        if(StringUtils.getInt(binding.rate.getText().toString())==0)
                        {
                            showCustomDialog(getContext(),"Error","Rate Cannot be 0","OK",null,"");
                        }
                        else {
                            milkDO milkDo = new milkDO();
                            milkDo.TDATE = CalendarUtils.getFormatedDatefromString3(binding.textdate.getText().toString());
                            milkDo.SHIFT = binding.textshiftm.getText().toString();
                            milkDo.FARMERID = binding.farmerid.getText().toString();
                            milkDo.FARMERNAME = binding.fname.getText().toString();
                            milkDo.MILKTYPE = binding.textmtypec.getText().toString();
                            milkDo.QUANTITY = Double.parseDouble(binding.mqty.getText().toString());
                            milkDo.FAT = Double.parseDouble(binding.mfat.getText().toString());
                            milkDo.SNF = Double.parseDouble(binding.msnf.getText().toString());
                            milkDo.RATE = Double.parseDouble(binding.rate.getText().toString());
                            milkDo.AMOUNT = Double.parseDouble(binding.amt.getText().toString());
                            milkDo.BCODE = preference.getIntFromPreference("BCODE",0);
                            milkCollectionVm.insertMdataRoom(milkDo);
                            clearfields();
                            refreshData();
//                            print(milkDo);
//                            getPrintSlip(milkDo);
                        }
                    }
                    else {
                        showCustomDialog(getContext(),"Error","please make sure all the fields are filled up","OK",null,"");
                    }
                }
            });
        }
        else {
            if (NetworkUtils.isNetworkAvailable(parent.getContext())) {
                binding.farmerid.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!binding.farmerid.getText().toString().isEmpty() && TextUtils.isDigitsOnly(binding.farmerid.getText())) {
                            JsonObject jsonObject1 = new JsonObject();
                            jsonObject1.addProperty("FARMERID", binding.farmerid.getText().toString());
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
                        } else
                            Toast.makeText(parent.getContext(), "Enter Farmer Code", Toast.LENGTH_SHORT).show();
                    }
                });
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
                        if (!binding.farmerid.getText().toString().isEmpty() || !binding.fname.getText().toString().isEmpty() ||
                                !binding.textdate.getText().toString().isEmpty() || !binding.mqty.getText().toString().isEmpty() ||
                                !binding.mfat.getText().toString().isEmpty() || !binding.msnf.getText().toString().isEmpty() ||
                                !binding.rate.getText().toString().isEmpty() || !binding.amt.getText().toString().isEmpty() ||
                                !binding.textshiftm.getText().toString().isEmpty() || !binding.textmtypec.getText().toString().isEmpty()
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
                            milkDo.BCODE = preference.getIntFromPreference("BCODE",0);
                            milkCollectionVm.insertMdata(milkDo);
                            clearfields();
                            refreshData();
//                            print(milkDo);
//                            getPrintSlip(milkDo);
                        } else {
                            Toast.makeText(getContext(), "please make sure all the fields are filled up", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else {
                showCustomDialog(getContext(),"Network Error","Please check your internet connection","OK",null,"");
            }

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
            jsonObject.addProperty("TDATE", CalendarUtils.getFormatedDatefromString3(binding.textdate.getText().toString()));
            jsonObject.addProperty("SHIFT", binding.textshiftm.getText().toString());
            jsonObject.addProperty("BCODE", preference.getIntFromPreference("BCODE",0));
            repository.getMData(getViewLifecycleOwner(),jsonObject).observe(this, new Observer<JsonObject>() {
                @Override
                public void onChanged(JsonObject jsonObject) {
                    if (jsonObject != null) {
                        Log.d("TAG", jsonObject.toString());
                        Gson gson = new Gson();
                        milkDOS = gson.fromJson(jsonObject.getAsJsonArray("Data"), milkDO[].class);
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
//                if(MyApplicationNew.RoomDB)
//                {
//                    milkCollectionVm.getGetrates(binding.textmtypec.getText().toString(),CalendarUtils.convertDateToFormattedString(binding.textdate.getText().toString()),fat, snf).observe(this, new Observer<List<rateDO>>() {
//                        @Override
//                        public void onChanged(List<rateDO> mdata) {
//                            if (mdata != null && !mdata.isEmpty()) {
//                                if(mdata.get(0).MILKTYPE.equalsIgnoreCase("Cow"))
//                                {
////                                    rate1[0] = (mdata.get(0).RATE / 100);
////                                    snfmax[0] = (mdata.get(0).SNFMAX);
//                                    Log.d("rate[0]", "" + rate1[0]);
//                                    rate1[0] = (fat + snf) * (rate1[0]);
//                                    amount[0] = rate1[0] * qty;
//                                    binding.rate.setText(String.format("%.1f", rate1[0]));
//                                    binding.amt.setText(String.format("%.1f", amount[0]));
//                                }
//                                else {
////                                    rate1[0] = (mdata.get(0).RATE / 100);
////                                    snfmax[0] = (mdata.get(0).SNFMAX);
//                                    Log.d("rate[0]", "" + rate1[0]);
//                                    //(BMRATE)
//                                    rate1[0] = (fat * rate1[0]) - (snfmax[0] - snf);
//                                    amount[0] = rate1[0]*qty;
//                                    binding.rate.setText(String.format("%.1f",rate1[0]));
//                                    binding.amt.setText(String.format("%.1f",amount[0]));
//                                }
//                            }
//                            else {
//                                binding.rate.setText(String.format("%.1f",(rate1[0])));
//                                binding.amt.setText(String.format("%.1f",(amount[0])));
//                            }
//                        }
//                    });
//                }
//                else {
                    JsonObject jsonObject1 = new JsonObject();
                    jsonObject1.addProperty("MILKTYPE", binding.textmtypec.getText().toString());
                    jsonObject1.addProperty("TDATE", CalendarUtils.convertDateToFormattedString(binding.textdate.getText().toString()));
                    jsonObject1.addProperty("FAT", fat);
                    jsonObject1.addProperty("SNF", snf);
                    jsonObject1.addProperty("BCODE", preference.getIntFromPreference("BCODE",0));
                    repository.getTSrate(jsonObject1).observe(this, new Observer<JsonObject>() {
                        @Override
                        public void onChanged(JsonObject jsonObject) {
                            if (jsonObject != null) {
                                Log.d("TAG", jsonObject.toString());
                                Gson gson = new Gson();
//                                Double rateDOs=0.0;
//                                JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();
//                                JsonElement rateElement = jsonObject.get("rate");
//                                double rate = rateElement.getAsDouble();
                                JsonArray jsonArray = jsonObject.getAsJsonArray("Data");
                                if (jsonArray.size() > 0) {
                                    JsonObject jsonObject1 = jsonArray.get(0).getAsJsonObject();
                                    String rateString = jsonObject1.get("rate").getAsString();
                                    double rate10 = StringUtils.getFloat(rateString);
                                    rate1[0] = (rate10/100);
//                                Log.d("rateDOs",""+jsonObject.getAsJsonArray("Data").getAsJsonObject().get("rate").getAsDouble());
                                    Log.d("rateDOs", "" + rate1);

//                                rateDOs = gson.fromJson(jsonObject.getAsJsonArray("Data"));
                                        if (rate1[0] > 0) {
//                                        binding.fname.setText(farmerDOs[0].FARMERNAME);
                                            if (binding.textmtypec.getText().equals("Cow")) {
//                                                rate1[0] = (rateDOs[0].RATE / 100);
//                                            snfmax[0] = (rateDOs[0].SNFMAX);
                                                amount[0] = ((fat + snf) * rate1[0]) * qty;
                                                binding.rate.setText(String.format("%.1f", ((fat + snf) * rate1[0])));
                                                binding.amt.setText(String.format("%.1f", amount[0]));
                                            } else {
//                                            rate1[0] = (rateDOs[0].RATE / 100);
//                                            snfmax[0] = (rateDOs[0].SNFMAX);
                                                //(BMRATE)
                                                amount[0] = ((fat * rate1[0]) - (snfmax[0] - snf)) * qty;
                                                binding.rate.setText(String.format("%.1f", ((fat * rate1[0]) - (snfmax[0] - snf))));
                                                binding.amt.setText(String.format("%.1f", amount[0]));
                                            }
                                        } else {
                                            binding.rate.setText(String.format("%.1f", rate1[0]));
                                            binding.amt.setText(String.format("%.1f", (amount[0])));
                                        }
                                    }
                            }
                        }
                    });
//                }
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
    public void print(milkDO mdata){
        prnt = " Date:" + mdata.TDATE + ", Shift:" + mdata.SHIFT + " \n Farmer Code:" + mdata.FARMERID + " \n Name:" + mdata.FARMERNAME + " \n " + mdata.MILKTYPE + " Milk \n Qty:" + mdata.QUANTITY + " \n Fat:" + mdata.FAT + " \n Snf:" + mdata.SNF + " \n Rate:" + mdata.RATE + " \n Amount:" + mdata.AMOUNT + "\n \n \n \n";
        showCustomDialog(getContext(),"Print","Would you like to Print?","Yes","No","PRINT");
    }
    void printData() throws  IOException{
        try{
            String msg = prnt;
            outputStream.write(msg.getBytes());
            Toast.makeText(getContext(),"Printing Text...",Toast.LENGTH_LONG).show();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void onButtonYesClick(String from) throws JSONException {
        super.onButtonYesClick(from);
        if (from.equals("PRINT")) {
//            getPrintSlip();

//            try {
//                getPrintSlip();
////                printData();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }
    public static Bitmap getPrintSlip(milkDO mdata){
        File myDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/PrinterTest");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        int salesHeight = 1, freeGoodsHeight = 1, damageHeight = 1;
//            n = generator.nextInt(n);
//        n = generator.nextInt((200 - 100) + 1) + 100;
//        String fname = "Image-" + n + ".jpg";
        String fname = "Image-" + 100 + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        Bitmap bmp = null;
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            int width = 200;
            int height = 200 + ((salesHeight + freeGoodsHeight + damageHeight) *30);
            bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            // NEWLY ADDED CODE STARTS HERE [
            Canvas canvas = new Canvas(bmp);
            Paint paintBG = new Paint();
            paintBG.setColor(Color.WHITE);
            canvas.drawRect(0, 0, width, height, paintBG);
            int het1 = 40;
            String[] as1 = null;
            String[] Header = null;
            int Headerh1 = 20+40;
//            String formateHeader1 	= "%1$-12.12s %2$-15.15s %3$-13.13s %4$-15.15s %5$-16.16s %6$-15.15s %7$-15.15s %8$-10.10s %9$-15.15s \r\n";
            String rightalign = "%1$12.12s";
            Header = new String[]{"Company Name","Branch Name","Collection CenterName","Village Name","Address","Contact","______________________________________________"};
            if (Header != null) {
                for (int i = 0; i < Header.length; i++) {
                    canvas.drawText(Header[i], ((width/2)-(Header[i].length()*8)),Headerh1, getPaintObjHeaderNew(22));
                    Headerh1 += 30;
                }
            }
            as1 = new String[]{"TDATE : "+mdata.TDATE,"SHIFT : "+mdata.SHIFT,
                    "Farmer Code:" + mdata.FARMERID,"Name:" + mdata.FARMERNAME,
                    "Milk Type:" +mdata.MILKTYPE,"Milk Qty:" + mdata.QUANTITY,
                    "Fat:" + mdata.FAT, "Snf:" + mdata.SNF,"Rate:" + mdata.RATE,
                    "Amount:" + mdata.AMOUNT
            };
//            as1  = getInner();
            int h1 = 20+Headerh1;
            if (as1 != null) {
                for (int i = 0; i < as1.length; i++) {
                    canvas.drawText(as1[i], 30,h1, getPaintObjHeaderNew(16));
                    h1 += 20;
                }
            }
            String[] Footer = null;
            int Footerf1 = h1+20;
            Footer = new String[]{"______________________________________________"};
            if (Footer != null) {
                for (int i = 0; i < Footer.length; i++) {
                    canvas.drawText(Footer[i], 30,Footerf1, getPaintObjHeaderNew(22));
                    Footerf1 += 30;
                }
            }
            canvas.drawText(String.format(rightalign,"Footer Text"),300,Footerf1+10,getPaintObjHeaderNew(22));
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
//            Settings.printImage(bmp);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bmp != null)
                bmp.recycle();
        }
        return BitmapFactory.decodeFile(file.getAbsolutePath());
        //future belongs to everyone
    }

    public static Bitmap getPrintSummaryslip(milkDO mdata){

        File myDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/PrinterTest");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        int salesHeight = 1, freeGoodsHeight = 1, damageHeight = 1;
            n = generator.nextInt(n);
        n = generator.nextInt((200 - 100) + 1) + 100;
        String fname = "Image-" + 100 + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        Bitmap bmp = null;
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            int width = 400;
            int height = 500 + ((salesHeight + freeGoodsHeight + damageHeight) *30);
            bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bmp);
            Paint paintBG = new Paint();
            canvas.drawRect(5,5,390,520,getPaintObjbody(8));
            paintBG.setColor(Color.WHITE);
            canvas.drawRect(0, 0, width, height, paintBG);
            canvas.drawRect(10, 10, 385, 515, paintBG);
            int het1 = 40;
            String[] as1 = null;
            String[] as2 = null;
            String[] Header = null;
            int Headerh1 = 20+40;
//            String formateHeader1 	= "%1$-12.12s %2$-15.15s %3$-13.13s %4$-15.15s %5$-16.16s %6$-15.15s %7$-15.15s %8$-10.10s %9$-15.15s \r\n";
            String rightalign = "%1$12.12s";
            //30 CHARACTERS FOR THE TEXTSIZE 28
            // WIDTH START FROM 10 TO 380
//            String companyName = " ***   Company Name1234   *** ";
//            int strheadlen = companyName.length();
//            int strHeaderPos = (110 - companyName.length()) / 2;
//            String formateTitle = "%1$-" + strHeaderPos + "." + strHeaderPos + "s %2$-" + strheadlen + "." + strheadlen + "s \r\n";
//            String str = String.format(formateTitle, "", "" + companyName.toUpperCase());
//            canvas.drawText(str,5,5,getPaintObjHeaderNew(22));
            Header = new String[]{" ***   Company Name1234   *** ","BRANCH NAME","VILLAGE","ADDRESS","CONTACT","---------------------------------------"};
            if (Header != null) {
                for (int i = 0; i < Header.length; i++) {
//                    canvas.drawText(Header[i], ((width/2)-Header[i].length()),Headerh1, getPaintObjHeaderNew(22));
                    Log.d("width"+i,""+((width/2)-(Header[i].length())));
//                    float  f1 = (190/Header[i].length());
                    canvas.drawText(Header[i], (float) ((width/2)-(Header[i].length())*6.5),Headerh1, getPaintObjHeaderNew(28));
//                    width = 200 - getlength(as1[i].trim(), getPaintObjHeader());
//                    canvas.drawText(Header[i],width,Headerh1, getPaintObjHeaderNew(28));
//                    canvas.drawText(Header[i], (float) ((width/2)-(Header[i].length())*6.5),Headerh1, getPaintObjHeaderNew(28));
                    Headerh1 += 30;
                }
            }
//            as1 = new String[]{"TDATE : "+mdata.TDATE,"SHIFT : "+mdata.SHIFT,
//                    "Farmer Code:" + mdata.FARMERID,"Name:" + mdata.FARMERNAME,
//                    "Milk Type:" +mdata.MILKTYPE,"Milk Qty:" + mdata.QUANTITY,
//                    "Fat:" + mdata.FAT, "Snf:" + mdata.SNF,"Rate:" + mdata.RATE,
//                    "Amount:" + mdata.AMOUNT
//            };
            as1 = new String[]{"TDATE : ","SHIFT : ",
                    "Farmer Code:" ,"Name:" ,
                    "Milk Type:" ,"Milk Qty:",
                    "Fat:" , "Snf:" ,"Rate:" ,
                    "Amount:"
            };
            as2 = new String[]{mdata.TDATE,mdata.SHIFT,
                    mdata.FARMERID,mdata.FARMERNAME,
                    mdata.MILKTYPE,""+mdata.QUANTITY,
                   ""+ mdata.FAT, "" + mdata.SNF,"" + mdata.RATE,
                    "" + mdata.AMOUNT
            };
//            as1  = getInner();


            int h1 = 20+Headerh1;
            if (as1 != null) {
                for (int i = 0; i < as1.length; i++) {
                    canvas.drawText(as1[i], 10,h1, getPaintObjbody(28));
                    h1 += 30;
                }
            }
            int h2 = 20+Headerh1;
            if (as2 != null) {
                for (int i = 0; i < as2.length; i++) {
                    canvas.drawText(as2[i], (float)(390-as2[i].length()*18),h2, getPaintObjbody(28));
                    h2 += 30;
                }
            }
//            String[] Footer = null;
//            int Footerf1 = h1+20;
//            Footer = new String[]{"______________________________________________"};
//            if (Footer != null) {
//                for (int i = 0; i < Footer.length; i++) {
//                    canvas.drawText(Footer[i], 30,Footerf1, getPaintObjHeaderNew(22));
//                    Footerf1 += 30;
//                }
//            }
//            String formateHeader1 	= "%1$-10.12s %2$-5.5s %3$-6.6s %4$-6.6s %5$-7.7s %6$-5.5s %7$-5.5s %8$-5.5s %9$-6.6s %10$-5.5s\r\n";
//            canvas.drawText(String.format(formateHeader1,"TDATE","M/E","CODE","NAME","B/C","QTY","FAT","SNF","RATE","AMT"), 30,Footerf1+10, getPaintObjHeaderNew(16));
//            int footerheight=Footerf1+50;
//            for(int i=0 ;i<5;i++){
//                canvas.drawText(String.format(formateHeader1,"01JUN2024","M","111","NAME123456789012345","BUFF","111.00","3.5","8.5","32.00","4800.00"), 30,footerheight, getPaintObjHeaderNew(16));
//                footerheight+=20;
//            }
//            canvas.drawText(String.format(rightalign,"Footer Text"),300,Footerf1+10,getPaintObjHeaderNew(22));
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
            if (Settings.outputStream == null) {
//                showCustomDialog(getContext(),"Print","Would you like to Print?","Yes","No","PRINT");
                Toast.makeText(MyApplicationNew.mContext,"Printer is not Connected!",Toast.LENGTH_SHORT).show();
            }else {
                Settings.printImage(bmp);
            }
            out.flush();
            out.close();
//            Settings.printBitmap(bmp);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bmp != null)
                bmp.recycle();
        }
        return BitmapFactory.decodeFile(file.getAbsolutePath());
    }
    public String getInner(){
        String singleText = "%1$30.30s \r\n";
        String s;
        s="";
        s += "\r\n";
        s+=String.format(singleText, "HI Sai");
        return s;
    }
    private static Paint getPaintObjHeaderNew(int textsize) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK); // Text Color
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setStrokeWidth(12); // Text Size
        paint.setTypeface(Typeface.create(Typeface.DEFAULT_BOLD, Typeface.BOLD));
        paint.setTextSize(textsize);
        return paint;
    }
    private static Paint getPaintObjbody(int textsize) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK); // Text Color
        paint.setTypeface(Typeface.DEFAULT);
        paint.setStrokeWidth(1); // Text Size
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        paint.setTextSize(textsize);
        return paint;
    }
    private static int getlength(String finalVal, Paint paint) {
        Rect result = new Rect();
        paint.getTextBounds(finalVal, 0, finalVal.length(), result);
        Log.d("WIDTH        :", String.valueOf(result.width()));
        Log.d("HEIGHT       :", String.valueOf(result.height()));
        return StringUtils.getInt(String.valueOf(result.width()));
    }
    private static Paint getPaintObjHeader() {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK); // Text Color
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setStrokeWidth(12); // Text Size
        paint.setTypeface(Typeface.create(Typeface.DEFAULT_BOLD, Typeface.BOLD));
        paint.setTextSize(20);
        return paint;
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.d("TAG", "onItemClick"+position);
    }
}

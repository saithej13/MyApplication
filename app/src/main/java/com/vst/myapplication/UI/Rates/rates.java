package com.vst.myapplication.UI.Rates;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
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
import com.vst.myapplication.Utils.Preference;
import com.vst.myapplication.dataObject.RateAndDetails;
import com.vst.myapplication.dataObject.farmerDO;
import com.vst.myapplication.dataObject.rateDO;
import com.vst.myapplication.dataObject.ratedetailsDO;
import com.vst.myapplication.databinding.FarmerEntryPopupBinding;
import com.vst.myapplication.databinding.FarmersBinding;
import com.vst.myapplication.databinding.RatesBinding;
import com.vst.myapplication.databinding.RatesEntryPopupBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class rates extends BaseFragment implements RatesAdapter.ItemClickListener{
    private Dialog dialog;
    boolean active=true;
    RatesBinding binding;
    RatesAdapter ratesAdapter;
    RatesEntryPopupBinding ratesEntryPopupBinding;
    rates_VM ratesVm;
    int mdate,mmonth,myear;
    Preference preference;
    RateAndDetails[] rateAndDetails;
    private ProjectRepository repository;



    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState, LifecycleOwner viewLifecycleOwner) {
        binding = DataBindingUtil.inflate(inflater, R.layout.rates, parent, false);
        preference = new Preference(getContext());
        repository = new ProjectRepository();
        ratesVm = new ViewModelProvider(this ).get(rates_VM.class);
        binding.setLifecycleOwner(viewLifecycleOwner);
        rateAndDetails = new RateAndDetails[]{};
        ratesAdapter = new RatesAdapter(getContext(),rateAndDetails,getActivity());
        ratesAdapter.setClickListener(this);
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
            int BCODE = preference.getIntFromPreference("BCODE",0);
            repository.getrates(BCODE).observe(this, new Observer<JsonObject>() {
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

                }
            });
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.d("TAG", "onItemClick"+position);
    }


}

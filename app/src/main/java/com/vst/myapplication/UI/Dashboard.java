package com.vst.myapplication.UI;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.vst.myapplication.R;
import com.vst.myapplication.Services.ProjectRepository;
import com.vst.myapplication.UI.MCollection.milkCollectionAdapter;
import com.vst.myapplication.UI.Rates.rates_VM;
import com.vst.myapplication.Utils.BaseFragment;
import com.vst.myapplication.Utils.CalendarUtils;
import com.vst.myapplication.dataObject.milkDO;
import com.vst.myapplication.databinding.ActivityMainBinding;
import com.vst.myapplication.databinding.Dashboard1Binding;

import java.util.Calendar;

public class Dashboard extends BaseFragment {
    Dashboard1Binding binding;
    String shift="M";
    milkDO[] milkDOS;
    private ProjectRepository repository;


    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState, LifecycleOwner viewLifecycleOwner) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dashboard1, parent, false);
        repository = new ProjectRepository();
        binding.setLifecycleOwner(viewLifecycleOwner);
        setupUI(inflater,parent,viewLifecycleOwner);
        return binding.getRoot();
    }
    private void setupUI(LayoutInflater inflater, ViewGroup parent, LifecycleOwner viewLifecycleOwner) {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        if (hour < 16) {
            shift="M";
        }
        else {
            shift="E";
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshData();
    }

    public void refreshData(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("TDATE", CalendarUtils.getDatePattern3());
        jsonObject.addProperty("SHIFT",shift);
        repository.getMData(jsonObject).observe(this, new Observer<JsonObject>() {
            @Override
            public void onChanged(JsonObject jsonObject) {
                if (jsonObject != null) {
                    Log.d("TAG", jsonObject.toString());
                    Gson gson = new Gson();
                    milkDOS = gson.fromJson(jsonObject.getAsJsonArray("Data"), milkDO[].class);
                    // rateDOs = gson.fromJson(jsonObject.toString(), rateDO[].class);
                    if (milkDOS != null) {
                        if(milkDOS!=null&&milkDOS.length>0) {
                            double buffqty = 0, cowqty = 0,qty = 0, amt = 0, ltrfat = 0, ltrsnf = 0;
                            for (int i = 0; i < milkDOS.length; i++) {
                                qty += milkDOS[i].QUANTITY;
                                if (milkDOS[i].MILKTYPE.equals("Buff")) {
                                    buffqty += milkDOS[i].QUANTITY;
                                } else if (milkDOS[i].MILKTYPE.equals("Cow")) {
                                    cowqty += milkDOS[i].QUANTITY;
                                }
                                ltrfat += ((milkDOS[i].FAT * milkDOS[i].QUANTITY)/100);
//                        ltrfat += ((mdata.get(i).getFat() * mdata.get(i).getQuantity()) / 100);
                                ltrsnf += ((milkDOS[i].SNF * milkDOS[i].QUANTITY) / 100);
                                amt += milkDOS[i].AMOUNT;
                            }
                            binding.txtcowqty.setText(String.format("%.1f",cowqty));
                            binding.txtbuffqty.setText(String.format("%.1f",buffqty));
                            binding.txttotalamt.setText(String.format("%.1f",amt));
                            binding.txttotalqty.setText(String.format("%.1f",qty));
                            binding.txtavgfat.setText(String.format("%.1f",(ltrfat / qty) * 100));
//                    binding.txtavgsnf.setText(String.format("%.1f",(ltrsnf)));
                            binding.txtavgsnf.setText(String.format("%.1f",(ltrsnf / qty) * 100));
                        }
                        else {
                            binding.txtcowqty.setText(String.valueOf(0));
                            binding.txtbuffqty.setText(String.valueOf(0));
                            binding.txttotalamt.setText(String.valueOf(0));
                            binding.txttotalqty.setText(String.valueOf(0));
                            binding.txtavgfat.setText(String.valueOf(0));
                            binding.txtavgsnf.setText(String.valueOf(0));
                        }
//                        if (milkDOS.length > 0) {
//                            binding.rcvmData.setLayoutManager(new LinearLayoutManager(getContext()));
//                            milkCollectionAdapter = new milkCollectionAdapter(getContext(), milkDOS);
//                            binding.rcvmData.setAdapter(milkCollectionAdapter);
//                            binding.rcvmData.setHasFixedSize(true);
//                            getTotal();
//                        } else {
//                            milkDOS = new milkDO[]{};
//                            if (milkCollectionAdapter != null) {
//                                milkCollectionAdapter.notifyDataSetChanged();
//                            }
//                        }
                    }
                }
            }
        });
    }
}
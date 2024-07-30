package com.vst.myapplication.UI.cusotmer;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.vst.myapplication.R;
import com.vst.myapplication.Room.roomRepository;
import com.vst.myapplication.Services.ProjectRepository;
import com.vst.myapplication.UI.Advance.advancesVM;
import com.vst.myapplication.Utils.BaseFragment;
import com.vst.myapplication.Utils.CalendarUtils;
import com.vst.myapplication.Utils.NetworkUtils;
import com.vst.myapplication.dataObject.advanceDO;
import com.vst.myapplication.dataObject.customerDO;
import com.vst.myapplication.databinding.AddadvanceBinding;
import com.vst.myapplication.databinding.AddcustomersBinding;

import org.json.JSONException;

import java.util.Calendar;

public class addcustomer extends BaseFragment {
    AddcustomersBinding binding;
    roomRepository roomrepo;
    private ProjectRepository repository;
    int mdate,mmonth,myear;
    boolean edit=false;
    int SLNO = 0;
    customerDO[] data;
    customerVM customerV_M;
    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState, LifecycleOwner viewLifecycleOwner) {
        binding = DataBindingUtil.inflate(inflater, R.layout.addcustomers, parent, false);
        customerV_M = new ViewModelProvider(this ).get(customerVM.class);
        repository = new ProjectRepository();
        roomrepo = new roomRepository();
        binding.setLifecycleOwner(viewLifecycleOwner);
        binding.etCustomercode.setEnabled(false);
        setupUI(inflater,parent,viewLifecycleOwner);
        return binding.getRoot();
    }
    private void setupUI(LayoutInflater inflater, ViewGroup parent, LifecycleOwner viewLifecycleOwner) {
        edit = getArguments().getBoolean("edit");
        SLNO = getArguments().getInt("SLNO");
        if(edit)
        {
            if (NetworkUtils.isNetworkAvailable(parent.getContext())) {
                repository.getcustomerbyslno(SLNO).observe(this, new Observer<JsonObject>() {
                    @Override
                    public void onChanged(JsonObject jsonObject) {
                        if (jsonObject != null) {
                            Log.d("TAG", jsonObject.toString());
                            Gson gson = new Gson();
                            data = gson.fromJson(jsonObject.getAsJsonArray("Data"), customerDO[].class);
                            if (data != null) {
                                if (data.length > 0) {
                                    binding.etCustomercode.setText(data[0].CUSTOMERCODE);
                                    binding.etCustomerName.setText(data[0].CUSTOMERNAME);
                                    binding.etMobileno.setText(data[0].MOBILENO);
                                    binding.tbcustomeractivestatus.setChecked(data[0].ISACTIVE);
                                } else {
                                    data = new customerDO[]{};
                                }
                            }
                        }
                    }
                });
            }
        }
        else {
            try {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int customerId = repository.getNextCustomerId();
                        binding.etCustomercode.setText(customerId + "");
                    }
                }).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        binding.tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String customercode = binding.etCustomercode.getText().toString();
                String customername = binding.etCustomerName.getText().toString();
                String mobileno = binding.etMobileno.getText().toString();
                Boolean active = binding.tbcustomeractivestatus.isChecked();
                if(!customercode.isEmpty()&&!customername.isEmpty()&&!mobileno.isEmpty()){
                    //Insert
                    customerDO customer = new customerDO();
                    customer.SLNO = SLNO;
                    customer.CUSTOMERCODE = customercode;
                    customer.CUSTOMERNAME = customername;
                    customer.MOBILENO = mobileno;
                    customer.ISACTIVE = active;
                    customerV_M.insertUpdateCustomer(customer);
                    showCustomDialog(getContext(),"Success","Customer Details Saved","OK",null,"success");
                    //farmersVM.insertFarmer(farmerDo);
                }
                else{
                    showCustomDialog(getContext(),"Error","Please Fillup all the Details","OK",null,"");
                }
            }
        });
    }
    @Override
    public void onButtonYesClick(String from) throws JSONException {
        super.onButtonYesClick(from);
        if(from.equalsIgnoreCase("success")){
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }
}

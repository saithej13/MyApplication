package com.vst.myapplication.UI.Farmers;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.vst.myapplication.R;
import com.vst.myapplication.Room.roomRepository;
import com.vst.myapplication.Services.ProjectRepository;
import com.vst.myapplication.UI.cusotmer.customerVM;
import com.vst.myapplication.Utils.BaseFragment;
import com.vst.myapplication.Utils.NetworkUtils;
import com.vst.myapplication.dataObject.customerDO;
import com.vst.myapplication.dataObject.farmerDO;
import com.vst.myapplication.databinding.AddcustomersBinding;
import com.vst.myapplication.databinding.AddfarmerBinding;

import org.json.JSONException;

public class addfarmer extends BaseFragment {
    AddfarmerBinding binding;
    roomRepository roomrepo;
    private ProjectRepository repository;
    int mdate,mmonth,myear;
    boolean edit=false;
    int SLNO = 0;
    farmerDO[] data;
    famers_VM farmersVM;
    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState, LifecycleOwner viewLifecycleOwner) {
        binding = DataBindingUtil.inflate(inflater, R.layout.addfarmer, parent, false);
        farmersVM = new ViewModelProvider(this ).get(famers_VM.class);
        repository = new ProjectRepository();
        roomrepo = new roomRepository();
        binding.setLifecycleOwner(viewLifecycleOwner);
        setupUI(inflater,parent,viewLifecycleOwner);
        return binding.getRoot();
    }
    private void setupUI(LayoutInflater inflater, ViewGroup parent, LifecycleOwner viewLifecycleOwner) {
        edit = getArguments().getBoolean("edit");
        SLNO = getArguments().getInt("SLNO");
        binding.tvmilktype.setOnClickListener(new View.OnClickListener() {
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
                            binding.tvmilktype.setText(selectedOption);
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    Log.e("nothing phone3", "");
                }
            }
        });
        if(edit)
        {
            if (NetworkUtils.isNetworkAvailable(parent.getContext())) {
                repository.getfarmerbyslno(SLNO).observe(this, new Observer<JsonObject>() {
                    @Override
                    public void onChanged(JsonObject jsonObject) {
                        if (jsonObject != null) {
                            Log.d("TAG", jsonObject.toString());
                            Gson gson = new Gson();
                            data = gson.fromJson(jsonObject.getAsJsonArray("Data"), farmerDO[].class);
                            if (data != null) {
                                if (data.length > 0) {
                                    binding.etfarmercode.setText(data[0].FARMERID+"");
                                    binding.etfarmername.setText(data[0].FARMERNAME);
                                    binding.etMobileno.setText(data[0].MOBILENO);
                                    binding.tvmilktype.setText(data[0].MILKTYPE);
                                    binding.tbcustomeractivestatus.setChecked(Boolean.getBoolean(String.valueOf(data[0].ISACTIVE)));
                                } else {
                                    data = new farmerDO[]{};
                                }
                            }
                        }
                    }
                });
            }
        }
        binding.tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String farmercode = binding.etfarmercode.getText().toString();
                String farmername = binding.etfarmername.getText().toString();
                String mobileno = binding.etMobileno.getText().toString();
                String milktype = binding.tvmilktype.getText().toString();
                Boolean active = binding.tbcustomeractivestatus.isChecked();
                if (!TextUtils.isEmpty(farmercode) && !TextUtils.isEmpty(farmername) && !TextUtils.isEmpty(mobileno) && !TextUtils.isEmpty(milktype)) {
                    farmerDO farmerDo = new farmerDO();
                    farmerDo.FARMERID = Integer.parseInt(farmercode);
                    farmerDo.FARMERNAME = farmername;
                    farmerDo.ISACTIVE = active;
                    farmerDo.MILKTYPE = mobileno;
                    farmerDo.MOBILENO = mobileno;
                    farmersVM.insertUpdateFarmer(farmerDo);
                    binding.etfarmercode.setText("");
                    binding.etfarmername.setText("");
                    binding.tvmilktype.setText("");
                    binding.etMobileno.setText("");
                    showCustomDialog(getContext(),"Success","Farmer Added!","OK",null,"success");
                } else {
                    showCustomDialog(getContext(),"Error","please make sure all the fields are filled up","OK",null,"");
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

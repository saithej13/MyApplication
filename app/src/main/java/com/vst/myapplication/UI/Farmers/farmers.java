package com.vst.myapplication.UI.Farmers;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.vst.myapplication.R;
import com.vst.myapplication.Room.roomRepository;
import com.vst.myapplication.Services.ProjectRepository;
import com.vst.myapplication.Utils.BaseFragment;
import com.vst.myapplication.Utils.MyApplicationNew;
import com.vst.myapplication.Utils.NetworkUtils;
import com.vst.myapplication.dataObject.farmerDO;
import com.vst.myapplication.dataObject.milkDO;
import com.vst.myapplication.databinding.FarmerEntryPopupBinding;
import com.vst.myapplication.databinding.FarmersBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class farmers extends BaseFragment {
    private Dialog dialog;
    boolean active=true;
    FarmersBinding binding;
    famers_VM farmersVM;
    farmerDO[] farmerDOs;
    FarmersAdapter farmersAdapter;
FarmerEntryPopupBinding farmerEntrybinding;
    private ProjectRepository repository;
    roomRepository roomrepo;
    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState, LifecycleOwner viewLifecycleOwner) {
        binding = DataBindingUtil.inflate(inflater, R.layout.farmers, parent, false);
        repository = new ProjectRepository();
        roomrepo = new roomRepository();
        farmersVM = new ViewModelProvider(this ).get(famers_VM.class);
        binding.setLifecycleOwner(viewLifecycleOwner);
        setupUI(inflater,parent,viewLifecycleOwner);
        return binding.getRoot();
    }
    private void setupUI(LayoutInflater inflater, ViewGroup parent, LifecycleOwner viewLifecycleOwner) {
        // Implement any additional UI setup here
        farmersAdapter = new FarmersAdapter(parent.getContext(), null);
        if(MyApplicationNew.RoomDB)
        {
            roomrepo.getFarmersData(getViewLifecycleOwner()).observe(this, new Observer<List<farmerDO>>() {
                @Override
                public void onChanged(List<farmerDO> farmer) {
                    Gson gson = new Gson();
                    JsonArray jsonArray = gson.toJsonTree(farmer).getAsJsonArray();
                    farmerDOs = gson.fromJson(jsonArray, farmerDO[].class);
                    if (farmerDOs.length > 0) {
                        binding.recFarmes.setLayoutManager(new LinearLayoutManager(parent.getContext()));
                        farmersAdapter = new FarmersAdapter(parent.getContext(), farmerDOs);
                        binding.recFarmes.setAdapter(farmersAdapter);
                        binding.recFarmes.setHasFixedSize(true);
                    } else {
                        farmerDOs = new farmerDO[]{};
                        if (farmersAdapter != null) {
                            farmersAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
        else {
            if (NetworkUtils.isNetworkAvailable(parent.getContext())) {
//            repository.getfarmers().observe(this, new Observer<JSONObject>() {
                repository.getfarmers().observe(this, new Observer<JsonObject>() {
                    @Override
                    public void onChanged(JsonObject jsonObject) {
                        if (jsonObject != null) {
                            Log.d("TAG", jsonObject.toString());
                            Gson gson = new Gson();
                            farmerDOs = gson.fromJson(jsonObject.getAsJsonArray("Data"), farmerDO[].class);

//                        Gson gson = new Gson();
//                        farmerDOs = gson.fromJson(jsonArray.toString(), farmerDO[].class);
                            if (farmerDOs != null) {
//                            Arrays.sort(farmerDOs, new Comparator<farmerDOs>() {
//                                @Override
//                                public int compare(farmerDOs order1, farmerDOs order2) {
//                                    return order2.getOrderDate().compareTo(order1.getOrderDate());
//                                }
//                            });
                                if (farmerDOs.length > 0) {
//                                tvNoData.setVisibility(View.GONE);
//                                layoutViewPager.setVisibility(View.VISIBLE);
                                    //setupTabs(orders);
                                    binding.recFarmes.setLayoutManager(new LinearLayoutManager(parent.getContext()));
                                    farmersAdapter = new FarmersAdapter(parent.getContext(), farmerDOs);
                                    binding.recFarmes.setAdapter(farmersAdapter);
                                    binding.recFarmes.setHasFixedSize(true);
                                } else {
//                                tvNoData.setVisibility(View.VISIBLE);
//                                layoutViewPager.setVisibility(View.GONE);
                                    farmerDOs = new farmerDO[]{};
                                    if (farmersAdapter != null) {
                                        farmersAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                        }
                    }
                });
//        farmersVM.getFarmerdata();
//        farmersAdapter.setFarmers();
            }
        }
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog == null || !dialog.isShowing()) {
                    farmerEntrybinding = DataBindingUtil.inflate(inflater, R.layout.farmer_entry_popup, parent, false);
                    dialog = new Dialog(parent.getContext(), R.style.Dialog);
                    dialog.setContentView(farmerEntrybinding.getRoot());
                    //DataBindingUtil.setContentView((Activity) getContext(), R.layout.farmer_entry_popup);
                    farmerEntrybinding.mtype.setOnClickListener(new View.OnClickListener() {
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
                                        farmerEntrybinding.mtype.setText(selectedOption);
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            } else {
                                Log.e("nothing phone3", "");
                            }
                        }
                    });
                    farmerEntrybinding.close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    farmerEntrybinding.btnCancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    farmerEntrybinding.btnOK.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!TextUtils.isEmpty(farmerEntrybinding.fname.getText().toString()) && !TextUtils.isEmpty(farmerEntrybinding.code.getText().toString()) && !TextUtils.isEmpty(farmerEntrybinding.mobileno.getText().toString()) && !TextUtils.isEmpty(farmerEntrybinding.mtype.getText().toString())) {
                                farmerDO farmerDo = new farmerDO();
                                farmerDo.FARMERID = Integer.parseInt(farmerEntrybinding.code.getText().toString());
                                farmerDo.FARMERNAME = farmerEntrybinding.fname.getText().toString();
                                farmerDo.ISACTIVE = active;
                                farmerDo.MILKTYPE = farmerEntrybinding.mtype.getText().toString();
                                farmerDo.MOBILENO = farmerEntrybinding.mobileno.getText().toString();
                                farmersVM.insertFarmer(farmerDo);
                                showCustomDialog(getContext(),"Success","Farmer Added!","OK",null,"");
                                farmerEntrybinding.code.setText("");
                                farmerEntrybinding.fname.setText("");
                                farmerEntrybinding.mtype.setText("");
                                farmerEntrybinding.mobileno.setText("");
//                                isDuplicateFarmer(Integer.parseInt(edtFcode.getText().toString()), new DuplicateFarmerCallback() {
//                                    @Override
//                                    public void onResult(boolean isDuplicate) {
//                                        if (!isDuplicate) {
//                                            // No duplicates found
////                                            tblfarmers tfarmers = new tblfarmers();
//
////                                            farmersVM.insertFarmersVM(tfarmers);
//
//                                        } else {
//                                            // Duplicate found
//                                            Toast.makeText(farmers_newActivity.this,"Farmer Code Already Exists!",Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                });
                            } else {
                                showCustomDialog(getContext(),"Error","please make sure all the fields are filled up","OK",null,"");
                            }
                        }
                    });
                    farmerEntrybinding.isactive.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (active) {
                                farmerEntrybinding.isactive.setImageResource(R.drawable.inactive);
                                active = false;
                            } else {
                                farmerEntrybinding.isactive.setImageResource(R.drawable.active);
                                active = true;
                            }
                        }
                    });
                    dialog.show();
                }
            }
        });
    }
}

package com.vst.myapplication.UI.cusotmer;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.vst.myapplication.R;
import com.vst.myapplication.Room.roomRepository;
import com.vst.myapplication.Services.ProjectRepository;
import com.vst.myapplication.UI.Advance.advancesAdapter;
import com.vst.myapplication.UI.Rates.RatesAdapter;
import com.vst.myapplication.Utils.BaseFragment;
import com.vst.myapplication.Utils.NetworkUtils;
import com.vst.myapplication.dataObject.advanceDO;
import com.vst.myapplication.dataObject.customerDO;
import com.vst.myapplication.dataObject.farmerDO;
import com.vst.myapplication.databinding.AdvanceBinding;
import com.vst.myapplication.databinding.CustomerBinding;

public class customer extends BaseFragment implements RatesAdapter.ItemClickListener{
    CustomerBinding binding;
    roomRepository roomrepo;
    private ProjectRepository repository;
    customerAdapter cadapter;
    customerDO[] data;
    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState, LifecycleOwner viewLifecycleOwner) {
        binding = DataBindingUtil.inflate(inflater, R.layout.customer, parent, false);
        repository = new ProjectRepository();
        roomrepo = new roomRepository();
        binding.setLifecycleOwner(viewLifecycleOwner);
        cadapter = new customerAdapter(getContext(),data);
//        cadapter.setClickListener(this);
        setupUI(inflater,parent,viewLifecycleOwner);
        return binding.getRoot();
    }
    private void setupUI(LayoutInflater inflater, ViewGroup parent, LifecycleOwner viewLifecycleOwner) {
        if (NetworkUtils.isNetworkAvailable(parent.getContext())) {
            repository.getcustomers().observe(this, new Observer<JsonObject>() {
                @Override
                public void onChanged(JsonObject jsonObject) {
                    if (jsonObject != null) {
                        Log.d("TAG", jsonObject.toString());
                        Gson gson = new Gson();
                        data = gson.fromJson(jsonObject.getAsJsonArray("Data"), customerDO[].class);
                        if (data != null) {
                            if (data.length > 0) {
                                binding.reccustomers.setLayoutManager(new LinearLayoutManager(parent.getContext()));
                                cadapter = new customerAdapter(parent.getContext(), data);
                                binding.reccustomers.setAdapter(cadapter);
                                binding.reccustomers.setHasFixedSize(true);
                            } else {
                                data = new customerDO[]{};
                                if (cadapter != null) {
                                    cadapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }
                }
            });
        }
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .replace(R.id.frame, new addcustomer(), "")
                        .addToBackStack("")
                        .commitAllowingStateLoss();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}

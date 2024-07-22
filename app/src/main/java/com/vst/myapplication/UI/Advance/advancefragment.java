package com.vst.myapplication.UI.Advance;

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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.vst.myapplication.R;
import com.vst.myapplication.Room.roomRepository;
import com.vst.myapplication.Services.ProjectRepository;
import com.vst.myapplication.UI.Farmers.famers_VM;
import com.vst.myapplication.UI.Rates.RatesAdapter;
import com.vst.myapplication.UI.Rates.ratedetails;
import com.vst.myapplication.UI.cusotmer.addcustomer;
import com.vst.myapplication.Utils.BaseFragment;
import com.vst.myapplication.Utils.NetworkUtils;
import com.vst.myapplication.dataObject.RateAndDetails;
import com.vst.myapplication.dataObject.advanceDO;
import com.vst.myapplication.databinding.AdvanceBinding;

public class advancefragment extends BaseFragment implements advancesAdapter.ItemClickListener{
    AdvanceBinding binding;
    roomRepository roomrepo;
    private ProjectRepository repository;
    advancesAdapter adapter;
    advanceDO[] data;

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState, LifecycleOwner viewLifecycleOwner) {
        binding = DataBindingUtil.inflate(inflater, R.layout.advance, parent, false);
        repository = new ProjectRepository();
        roomrepo = new roomRepository();
        binding.setLifecycleOwner(viewLifecycleOwner);
        adapter = new advancesAdapter(getContext(),data);
        adapter.setClickListener(this);
        setupUI(inflater,parent,viewLifecycleOwner);
        return binding.getRoot();
    }
    private void setupUI(LayoutInflater inflater, ViewGroup parent, LifecycleOwner viewLifecycleOwner) {
        if (NetworkUtils.isNetworkAvailable(parent.getContext())) {
            repository.getAdvances().observe(this, new Observer<JsonObject>() {
                @Override
                public void onChanged(JsonObject jsonObject) {
                    if (jsonObject != null) {
                        Log.d("TAG", jsonObject.toString());
                        Gson gson = new Gson();
                        data = gson.fromJson(jsonObject.getAsJsonArray("Data"), advanceDO[].class);
                        if (data != null) {
                            if (data.length > 0) {
                                binding.recadvances.setLayoutManager(new LinearLayoutManager(parent.getContext()));
                                adapter = new advancesAdapter(parent.getContext(), data);
                                binding.recadvances.setAdapter(adapter);
                                binding.recadvances.setHasFixedSize(true);
                            } else {
                                data = new advanceDO[]{};
                                if (adapter != null) {
                                    adapter.notifyDataSetChanged();
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
                Bundle mBundle = new Bundle();
                mBundle.putBoolean("edit", false);
                mBundle.putInt("SLNO", 0);
                addadvance advance = new addadvance();
                advance.setArguments(mBundle);
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .replace(R.id.frame, advance, "")
                        .addToBackStack("")
                        .commitAllowingStateLoss();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.d("TAG", "onItemClick"+position);
    }
}

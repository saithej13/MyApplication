package com.vst.myapplication.UI.Farmers;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.vst.myapplication.R;
import com.vst.myapplication.Services.ProjectRepository;
import com.vst.myapplication.UI.Rates.RatesAdapter;
import com.vst.myapplication.UI.cusotmer.addcustomer;
import com.vst.myapplication.UI.cusotmer.customerAdapter;
import com.vst.myapplication.Utils.BaseFragment;
import com.vst.myapplication.dataObject.farmerDO;
import com.vst.myapplication.databinding.FarmerscellBinding;

import java.util.Vector;

public class FarmersAdapter extends RecyclerView.Adapter<FarmersAdapter.ViewHolder> implements Filterable {

    FarmerscellBinding binding;

    private farmerDO[] mData;
    Context context;
    private LayoutInflater inflater;
    private FarmersAdapter.ItemClickListener mClickListener;
    @Override
    public Filter getFilter() {
        return null;
    }
    public FarmersAdapter(Context context,farmerDO[] data) {
        this.inflater = LayoutInflater.from(context);
        this.mData = data;
    }
    @NonNull
    @Override
    public FarmersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View farmerscell = LayoutInflater.from(parent.getContext()).inflate(R.layout.farmerscell, parent, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.farmerscell, parent, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull FarmersAdapter.ViewHolder holder, int position) {
        farmerDO farmer = mData[position];
        if (farmer != null) {
            ((TextView) holder.itemView.findViewById(R.id.txtfcode)).setText(String.valueOf(farmer.FARMERID));
            ((TextView) holder.itemView.findViewById(R.id.txtfname)).setText(String.valueOf(farmer.FARMERNAME));
            ((TextView) holder.itemView.findViewById(R.id.txtmno)).setText(String.valueOf(farmer.MOBILENO));
            ((TextView) holder.itemView.findViewById(R.id.txtmytpe)).setText(String.valueOf(farmer.MILKTYPE));
            ((Switch) holder.itemView.findViewById(R.id.active)).setChecked(farmer.ISACTIVE);
            ((ImageView) holder.itemView.findViewById(R.id.ivedit)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle mBundle = new Bundle();
                    mBundle.putBoolean("edit", true);
                    mBundle.putInt("SLNO", farmer.FARMERID);
                    addfarmer farmer = new addfarmer();
                    farmer.setArguments(mBundle);
                    FragmentManager fragmentManager =  ((FragmentActivity) view.getContext()).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                            .replace(R.id.frame, farmer, "")
                            .addToBackStack("")
                            .commitAllowingStateLoss();
                }
            });
            ((ImageView) holder.itemView.findViewById(R.id.ivdelete)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   new ProjectRepository().deleteFarmerID(farmer.FARMERID);
                }
            });
//            if(farmer.getIsactive())
//                holder.farmerscellBinding.active.setChecked(true);
//            else
//            holder.farmerscellBinding.active.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        if(mData!=null && mData.length>0) {
            //textview hide
            return mData.length;
        }
        else {
            //textview gone
            return 0;
        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    public void setClickListener(FarmersAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}

package com.vst.myapplication.UI.Farmers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.vst.myapplication.R;
import com.vst.myapplication.dataObject.farmerDO;
import com.vst.myapplication.databinding.FarmerscellBinding;

import java.util.Vector;

public class FarmersAdapter extends RecyclerView.Adapter<FarmersAdapter.ViewHolder> implements Filterable {

    FarmerscellBinding binding;

    private farmerDO[] mData;
    Context context;
    private LayoutInflater inflater;
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
}

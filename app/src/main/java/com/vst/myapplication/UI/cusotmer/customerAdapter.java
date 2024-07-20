package com.vst.myapplication.UI.cusotmer;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.vst.myapplication.R;
import com.vst.myapplication.UI.Advance.advancesAdapter;
import com.vst.myapplication.UI.Rates.RatesAdapter;
import com.vst.myapplication.dataObject.advanceDO;
import com.vst.myapplication.dataObject.customerDO;
import com.vst.myapplication.databinding.CustomerBinding;

public class customerAdapter extends RecyclerView.Adapter<customerAdapter.ViewHolder> implements Filterable {
    private LayoutInflater inflater;
    customerDO[] mData;
    CustomerBinding binding;

    public customerAdapter(Context context, customerDO[] data) {
        this.inflater = LayoutInflater.from(context);
        this.mData = data;
    }
    @Override
    public Filter getFilter() {
        return null;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(inflater, R.layout.customercell, parent, false);
        return new customerAdapter.ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        customerDO customer = mData[position];
        if (customer != null) {
            ((TextView) holder.itemView.findViewById(R.id.txtccode)).setText(String.valueOf(customer.CUSTOMERCODE));
            ((TextView) holder.itemView.findViewById(R.id.txtcname)).setText(String.valueOf(customer.CUSTOMERNAME));
            ((TextView) holder.itemView.findViewById(R.id.txtmno)).setText(String.valueOf(customer.MOBILENO));
            ((TextView) holder.itemView.findViewById(R.id.txtcusttype)).setText(customer.ISACTIVE);
//            if(farmer.getIsactive())
//                holder.farmerscellBinding.active.setChecked(true);
//            else
//            holder.farmerscellBinding.active.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        @Override
        public void onClick(View view) {
            Log.d("position",""+getAdapterPosition());
        }
    }
}

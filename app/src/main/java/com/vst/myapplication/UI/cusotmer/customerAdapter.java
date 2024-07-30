package com.vst.myapplication.UI.cusotmer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;
import com.vst.myapplication.R;
import com.vst.myapplication.Services.ProjectRepository;
import com.vst.myapplication.UI.Advance.addadvance;
import com.vst.myapplication.UI.Advance.advancesAdapter;
import com.vst.myapplication.UI.Rates.RatesAdapter;
import com.vst.myapplication.dataObject.advanceDO;
import com.vst.myapplication.dataObject.customerDO;
import com.vst.myapplication.databinding.CustomerBinding;
import com.vst.myapplication.databinding.CustomercellBinding;

public class customerAdapter extends RecyclerView.Adapter<customerAdapter.ViewHolder> implements Filterable {
    private LayoutInflater inflater;
    customerDO[] mData;
    CustomercellBinding binding;
    private customerAdapter.ItemClickListener mClickListener;

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
            ((TextView) holder.itemView.findViewById(R.id.txtcode)).setText(String.valueOf(customer.CUSTOMERCODE));
            ((TextView) holder.itemView.findViewById(R.id.txtcname)).setText(String.valueOf(customer.CUSTOMERNAME));
            ((TextView) holder.itemView.findViewById(R.id.txtmno)).setText(String.valueOf(customer.MOBILENO));
//            ((TextView) holder.itemView.findViewById(R.id.txtcusttype)).setText(customer.ISACTIVE);
            ((ImageView) holder.itemView.findViewById(R.id.ivedit)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle mBundle = new Bundle();
                    mBundle.putBoolean("edit", true);
                    mBundle.putInt("SLNO", customer.SLNO);
                    addcustomer customer = new addcustomer();
                    customer.setArguments(mBundle);
                    FragmentManager fragmentManager =  ((FragmentActivity) view.getContext()).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                            .replace(R.id.frame, customer, "")
                            .addToBackStack("")
                            .commitAllowingStateLoss();
                }
            });
            ((ImageView) holder.itemView.findViewById(R.id.ivdelete)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            JsonObject jsonObject = new JsonObject();
                            jsonObject.addProperty("SLNO",customer.SLNO);
                            final boolean isDeletd = new ProjectRepository().deleteCustomerID(jsonObject);
                            if(isDeletd){
                                ((Activity) view.getContext()).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        notifyItemRemoved(position);
                                    }
                                });
                            }
                        }
                    }).start();
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        @Override
        public void onClick(View view) {
            Log.d("position",""+getAdapterPosition());
        }
    }
    public void setClickListener(customerAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}

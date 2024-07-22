package com.vst.myapplication.UI.Advance;

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
import com.vst.myapplication.UI.Farmers.FarmersAdapter;
import com.vst.myapplication.UI.Rates.RatesAdapter;
import com.vst.myapplication.dataObject.advanceDO;
import com.vst.myapplication.dataObject.farmerDO;
import com.vst.myapplication.databinding.AdvancecellBinding;
import com.vst.myapplication.databinding.CustomerBinding;
import com.vst.myapplication.databinding.FarmerscellBinding;

public class advancesAdapter extends RecyclerView.Adapter<advancesAdapter.ViewHolder> implements Filterable {
    private LayoutInflater inflater;
    private advanceDO[] mData;
    AdvancecellBinding binding;
    private advancesAdapter.ItemClickListener mClickListener;

    public advancesAdapter(Context context, advanceDO[] data) {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.advancecell, parent, false);
        return new advancesAdapter.ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        advanceDO advance = mData[position];
        if (advance != null) {
            ((TextView) holder.itemView.findViewById(R.id.txtcode)).setText(String.valueOf(advance.ID));
            ((TextView) holder.itemView.findViewById(R.id.txtcname)).setText(String.valueOf(advance.NAME));
            ((TextView) holder.itemView.findViewById(R.id.txtamount)).setText(String.valueOf("Amount : "+advance.AMOUNT));
            ((TextView) holder.itemView.findViewById(R.id.txtcusttype)).setText(advance.CUSTOMERTYPE);
            ((ImageView) holder.itemView.findViewById(R.id.ivedit)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle mBundle = new Bundle();
                    mBundle.putBoolean("edit", true);
                    mBundle.putInt("SLNO", advance.SLNO);
                    addadvance advance = new addadvance();
                    advance.setArguments(mBundle);
                    FragmentManager fragmentManager =  ((FragmentActivity) view.getContext()).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                            .replace(R.id.frame, advance, "")
                            .addToBackStack("")
                            .commitAllowingStateLoss();
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
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
    public void setClickListener(advancesAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}

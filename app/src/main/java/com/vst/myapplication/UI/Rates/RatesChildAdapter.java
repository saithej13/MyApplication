package com.vst.myapplication.UI.Rates;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.vst.myapplication.R;
import com.vst.myapplication.dataObject.ratedetailsDO;
import com.vst.myapplication.databinding.RatedetailsBinding;

import java.util.List;
import java.util.Vector;

public class RatesChildAdapter extends RecyclerView.Adapter<RatesChildAdapter.SubViewHolder>{

    RatedetailsBinding binding;
    Vector<ratedetailsDO> vecratedetailsdo;
    private LayoutInflater inflater;
    private Context context;
    public RatesChildAdapter(Context context, Vector<ratedetailsDO> data) {
        this.inflater = LayoutInflater.from(context);
        this.vecratedetailsdo = data;
    }
    @NonNull
    @Override
    public SubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(inflater, R.layout.ratedetails, parent, false);
        return new RatesChildAdapter.SubViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull SubViewHolder holder, int position) {
        if(position% 2 == 0){
            holder.itemView.findViewById(R.id.llitems).setBackgroundResource(R.color.white);
        }else {
            holder.itemView.findViewById(R.id.llitems).setBackgroundResource(R.color.cardbgcolor3);
        }
        if(position==vecratedetailsdo.size()-1){
            holder.itemView.findViewById(R.id.borderLine).setVisibility(View.VISIBLE);
        }
        ratedetailsDO rate = vecratedetailsdo.get(position);
        if (rate != null) {
//            ((TextView) holder.itemView.findViewById(R.id.txtbcode)).setText("1");
            ((TextView) holder.itemView.findViewById(R.id.tvfatmin)).setText("FATMIN : "+String.valueOf(rate.FATMIN));
            ((TextView) holder.itemView.findViewById(R.id.tvfatmax)).setText("FATMAX : "+String.valueOf(rate.FATMAX));
            ((TextView) holder.itemView.findViewById(R.id.tvsnfmin)).setText("SNFMIN : "+String.valueOf(rate.SNFMIN));
            ((TextView) holder.itemView.findViewById(R.id.tvsnfmax)).setText("SNFMAX : "+String.valueOf(rate.SNFMAX));
            ((TextView) holder.itemView.findViewById(R.id.tvrate)).setText(String.valueOf(rate.RATE));
            ((ImageView) holder.itemView.findViewById(R.id.ivedit)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("AdapterPosition",""+holder.getAdapterPosition());
//                    if (mClickListener != null) mClickListener.onItemClick(v, holder.getAdapterPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(vecratedetailsdo!=null && vecratedetailsdo.size()>0) {
            //textview hide
            return vecratedetailsdo.size();
        }
        else {
            //textview gone
            return 0;
        }
    }

    public class SubViewHolder extends RecyclerView.ViewHolder {

        public SubViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

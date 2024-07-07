package com.vst.myapplication.UI.Rates;

import android.content.Context;
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
import com.vst.myapplication.UI.Farmers.FarmersAdapter;
import com.vst.myapplication.Utils.CalendarUtils;
import com.vst.myapplication.dataObject.farmerDO;
import com.vst.myapplication.dataObject.rateDO;
import com.vst.myapplication.databinding.FarmerscellBinding;
import com.vst.myapplication.databinding.RatedetailscardcellBinding;
import com.vst.myapplication.databinding.RatescellBinding;

public class RatesAdapter extends RecyclerView.Adapter<RatesAdapter.ViewHolder> implements Filterable {
    RatedetailscardcellBinding binding;
    Context context;
    private rateDO[] mData;
    private LayoutInflater inflater;
    @Override
    public Filter getFilter() {
        return null;
    }
    public RatesAdapter(Context context,rateDO[] data) {
        this.inflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @NonNull
    @Override
    public RatesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(inflater, R.layout.ratedetailscardcell, parent, false);
        return new RatesAdapter.ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull RatesAdapter.ViewHolder holder, int position) {
        rateDO rate = mData[position];
        if (rate != null) {
            ((TextView) holder.itemView.findViewById(R.id.txtbcode)).setText("1");
            ((TextView) holder.itemView.findViewById(R.id.txtfdate)).setText(CalendarUtils.getFormatedDatefromString(String.valueOf(rate.STARTDATE)));
            ((TextView) holder.itemView.findViewById(R.id.txttdate)).setText(CalendarUtils.getFormatedDatefromString(String.valueOf(rate.ENDDATE)));
            ((TextView) holder.itemView.findViewById(R.id.txtmtype)).setText(String.valueOf(rate.MILKTYPE));
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

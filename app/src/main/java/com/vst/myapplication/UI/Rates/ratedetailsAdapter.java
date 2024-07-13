package com.vst.myapplication.UI.Rates;

import static android.provider.Settings.Global.getString;
import static java.security.AccessController.getContext;

import android.app.Dialog;
import android.content.Context;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vst.myapplication.R;
import com.vst.myapplication.Utils.CalendarUtils;
import com.vst.myapplication.dataObject.RateAndDetails;
import com.vst.myapplication.dataObject.ratedetailsDO;
import com.vst.myapplication.databinding.RatedetailsBinding;
import com.vst.myapplication.databinding.RatesEntryPopupBinding;

import java.util.Vector;

public class ratedetailsAdapter extends RecyclerView.Adapter<ratedetailsAdapter.ViewHolder> implements Filterable {
    private LayoutInflater inflater;
    View borderLine;
    RatedetailsBinding binding;
    private ItemClickListener mClickListener;
    Vector<ratedetailsDO> mData;
    RatesEntryPopupBinding ratesEntryPopupBinding;
    private FragmentActivity fragmentActivity;
    @Override
    public Filter getFilter() {
        return null;
    }
    public ratedetailsAdapter(Context context, Vector<ratedetailsDO> data,FragmentActivity fragmentActivity) {
        this.inflater = LayoutInflater.from(context);
        this.mData = data;
        this.fragmentActivity = fragmentActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(inflater, R.layout.ratedetails, parent, false);
        return new ratedetailsAdapter.ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(position% 2 == 0){
            holder.itemView.findViewById(R.id.llitems).setBackgroundResource(R.color.white);
        }else {
            holder.itemView.findViewById(R.id.llitems).setBackgroundResource(R.color.cardbgcolor3);
        }
        if(position==mData.size()-1){
            holder.itemView.findViewById(R.id.borderLine).setVisibility(View.VISIBLE);
        }
        ratedetailsDO rate = mData.get(position);
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
                    if (mClickListener != null) mClickListener.onItemClick(v, holder.getAdapterPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(mData!=null && mData.size()>0) {
            //textview hide
            return mData.size();
        }
        else {
            //textview gone
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}

package com.vst.myapplication.UI.MCollection;

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
import com.vst.myapplication.UI.Rates.RatesAdapter;
import com.vst.myapplication.Utils.CalendarUtils;
import com.vst.myapplication.dataObject.milkDO;
import com.vst.myapplication.dataObject.rateDO;
import com.vst.myapplication.databinding.McollectioncellBinding;

public class milkCollectionAdapter extends RecyclerView.Adapter<milkCollectionAdapter.ViewHolder> implements Filterable {
    McollectioncellBinding binding;
    Context context;
    private RatesAdapter.ItemClickListener mClickListener;
    private milkDO[] mData;
    private LayoutInflater inflater;
    @Override
    public Filter getFilter() {
        return null;
    }
    public milkCollectionAdapter(Context context,milkDO[] data) {
        this.inflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @NonNull
    @Override
    public milkCollectionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(inflater, R.layout.mcollectioncell, parent, false);
        return new milkCollectionAdapter.ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull milkCollectionAdapter.ViewHolder holder, int position) {
        milkDO milkDOs = mData[position];
        if (milkDOs != null) {
            ((TextView) holder.itemView.findViewById(R.id.txtccode)).setText(milkDOs.FARMERID);
            ((TextView) holder.itemView.findViewById(R.id.txtcname)).setText(milkDOs.FARMERNAME);
            ((TextView) holder.itemView.findViewById(R.id.txtdate)).setText(CalendarUtils.getFormatedDatefromString(milkDOs.TDATE));
            ((TextView) holder.itemView.findViewById(R.id.txtshift)).setText(milkDOs.SHIFT);
            ((TextView) holder.itemView.findViewById(R.id.txtmtype)).setText(milkDOs.MILKTYPE);
            ((TextView) holder.itemView.findViewById(R.id.txtqty)).setText(String.valueOf(milkDOs.QUANTITY));
            ((TextView) holder.itemView.findViewById(R.id.txtfat)).setText(String.valueOf(milkDOs.FAT));
            ((TextView) holder.itemView.findViewById(R.id.txtsnf)).setText(String.valueOf(milkDOs.SNF));
            ((TextView) holder.itemView.findViewById(R.id.txtrate)).setText(String.valueOf(milkDOs.RATE));
            ((TextView) holder.itemView.findViewById(R.id.txtamt)).setText(String.valueOf(milkDOs.AMOUNT));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    milkCollection.getPrintSlip(milkDOs);
                }
            });
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
    public void setClickListener(RatesAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}

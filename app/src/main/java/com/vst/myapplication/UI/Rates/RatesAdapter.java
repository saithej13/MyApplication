package com.vst.myapplication.UI.Rates;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vst.myapplication.R;
import com.vst.myapplication.UI.Farmers.FarmersAdapter;
import com.vst.myapplication.Utils.CalendarUtils;
import com.vst.myapplication.dataObject.RateAndDetails;
import com.vst.myapplication.dataObject.farmerDO;
import com.vst.myapplication.dataObject.rateDO;
import com.vst.myapplication.dataObject.ratedetailsDO;
import com.vst.myapplication.databinding.FarmerscellBinding;
import com.vst.myapplication.databinding.RatedetailscardcellBinding;
import com.vst.myapplication.databinding.RatescellBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class RatesAdapter extends RecyclerView.Adapter<RatesAdapter.ViewHolder> implements Filterable {
    RatedetailscardcellBinding binding;
    private RateAndDetails[] mData;
    private LayoutInflater inflater;
    private int expandedPosition = -1;
    private ItemClickListener mClickListener;
    private FragmentActivity fragmentActivity;
    private HashMap<Integer, Boolean> expandMap = new HashMap<>();
    Vector<rateDO> vecratedo;
    ratedetailsDO ratedetailsDo = new ratedetailsDO();
    Vector<ratedetailsDO> vecratedetailsdo = new Vector<>();
    HashMap<Integer,ratedetailsDO> hashMap=new HashMap<>();
    List<ratedetailsDO> rateDetailsList = new ArrayList<>();
    @Override
    public Filter getFilter() {
        return null;
    }
    public RatesAdapter(Context context, RateAndDetails[] data,FragmentActivity fragmentActivity) {
        this.inflater = LayoutInflater.from(context);
        this.mData = data;
        this.fragmentActivity = fragmentActivity;
        for (int i = 0; i < (mData == null ? 0 : mData.length); i++) {
            expandMap.put(i, false);
        }
    }

    @NonNull
    @Override
    public RatesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(inflater, R.layout.ratedetailscardcell, parent, false);
        return new RatesAdapter.ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull RatesAdapter.ViewHolder holder, int position) {
        RateAndDetails rate = mData[position];
        rateDetailsList = mData[position].rateDetailsList;
        if (rate != null) {
//            List<ratedetailsDO> itemslistItems=rate.rateDetailsList;
//            hashMap.put(position,rate.rateDetailsList.get(position));
//            vecratedetailsdo = new Vector<>(rate.rateDetailsList);
//            vecratedetailsdo.clear();
//            vecratedetailsdo.add(rate.rateDetailsList.get(position));
            ((ImageView) holder.itemView.findViewById(R.id.dropdownmilktype)).setVisibility(View.GONE);
            ((TextView) holder.itemView.findViewById(R.id.tvstartdate)).setText(String.valueOf(rate.rate.STARTDATE));
            ((TextView) holder.itemView.findViewById(R.id.tvenddate)).setText(String.valueOf(rate.rate.ENDDATE));
            ((TextView) holder.itemView.findViewById(R.id.tvmilktype)).setText(String.valueOf(rate.rate.MILKTYPE));
            ((ImageView) holder.itemView.findViewById(R.id.ivedit)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle mBundle = new Bundle();
                    mBundle.putBoolean("addRate", false);
                    mBundle.putInt("SLNO", rate.rate.SLNO);
                    ratedetails ratedetail = new ratedetails();
                    ratedetail.setArguments(mBundle);
                    FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                            .replace(R.id.frame, ratedetail, "")
                            .addToBackStack("")
                            .commitAllowingStateLoss();
                }
            });
            ((ImageView) holder.itemView.findViewById(R.id.ivdelete)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("RATESLNO",""+rate.rate.SLNO);
                    //delete rate based on slno
                }
            });


//            binding.llmain.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.d("expandedPosition",""+expandedPosition);
//                    Log.d("position",""+position);
//                }
//            });

//            boolean isExpanded = expandedPosition == position;
//            binding.rcvChild.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
//            ExpandState finalExpandState = expandState;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    boolean expanded = Boolean.TRUE.equals(expandMap.get(position));
//                    expandMap.put(position, !expanded);
//                    notifyDataSetChanged();
//                    Boolean isexpand = expandMap.get(position);
                    getRate(mData);
                    expandMap.put(position,true);
                    notifyItemChanged(position);
                }
            });
            boolean isExpanded = expandMap.get(position);
            if (isExpanded) {
                binding.rcvChild.setVisibility(View.VISIBLE);
                holder.itemView.setActivated(true);
            } else {
                binding.rcvChild.setVisibility(View.GONE);
                holder.itemView.setActivated(false);
            }
//            binding.rcvChild.setVisibility(expandState.isExpanded ? View.VISIBLE : View.GONE);
//            holder.itemView.setActivated(expandState.isExpanded);

            if (isExpanded) {
                Log.d("expanded","expanded");
                RatesChildAdapter ratedetailsAdapter = new RatesChildAdapter(fragmentActivity, rateDetailsList);
                binding.rcvChild.setLayoutManager(new LinearLayoutManager(fragmentActivity));
                binding.rcvChild.setAdapter(ratedetailsAdapter);
                binding.rcvChild.setHasFixedSize(true);
//                notifyDataSetChanged();
            }

//            ((LinearLayout) holder.itemView.findViewById(R.id.llmain)).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.d("expandedPosition",""+expandedPosition);
//                    Log.d("position",""+position);
//                    if (vecratedetailsdo != null && vecratedetailsdo.size() > 0) {
//                        expandedPosition = position;
////                        RatesChildAdapter myRecyclerViewAdapter = new RatesChildAdapter(v.getContext(),vecratedetailsdo );
////                        LinearLayoutManager llm = new LinearLayoutManager(context);
////                        llm.setOrientation(LinearLayoutManager.VERTICAL);
////                        ((RecyclerView) holder.itemView.findViewById(R.id.rcv_child)).setLayoutManager(llm);
////                        ((RecyclerView) holder.itemView.findViewById(R.id.rcv_child)).setAdapter(myRecyclerViewAdapter);
//
////                        myRecyclerViewAdapter.notifyDataSetChanged();
//                        vecratedetailsdo.clear();
//                        vecratedetailsdo.add(rate.rateDetailsList.get(position));
//                        RatesChildAdapter ratedetailsadapter = new RatesChildAdapter(fragmentActivity,vecratedetailsdo);
//                        binding.rcvChild.setLayoutManager(new LinearLayoutManager(fragmentActivity));
//                        binding.rcvChild.setAdapter(ratedetailsadapter);
//                        binding.rcvChild.setHasFixedSize(true);
//
//                    }
//                }
//            });
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.length;
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
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
    public Bitmap getRate(RateAndDetails[] data){
        File myDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/PrinterTest");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        int salesHeight = 1, freeGoodsHeight = 1, damageHeight = 1;
//            n = generator.nextInt(n);
        n = generator.nextInt((200 - 100) + 1) + 100;
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        Bitmap bmp = null;
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            int width = 920;
            int height = 950 + ((salesHeight + freeGoodsHeight + damageHeight) *30);
            bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            // NEWLY ADDED CODE STARTS HERE [
            Canvas canvas = new Canvas(bmp);
            Paint paintBG = new Paint();
            paintBG.setColor(Color.WHITE);
            canvas.drawRect(0, 0, width, height, paintBG);
            int het1 = 40;
            String[] as1 = null;
            as1 = new String[]{"HI1", "Hi2", "HI3"};
//            as1  = getInner();
            int h1 = 20;
            if (as1 != null) {
                for (int i = 0; i < as1.length; i++) {
                    canvas.drawText(as1[i], 60, h1, getPaintObjHeaderNew());
                    h1 += 10;
                }
            }
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bmp != null)
                bmp.recycle();
        }
        return BitmapFactory.decodeFile(file.getAbsolutePath());
    }
    public String getInner(){
        String singleText = "%1$30.30s \r\n";
        String s;
        s="";
        s += "\r\n";
        s+=String.format(singleText, "HI Sai");
        return s;
    }
    private Paint getPaintObjHeaderNew() {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK); // Text Color
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setStrokeWidth(12); // Text Size
        paint.setTypeface(Typeface.create(Typeface.DEFAULT_BOLD, Typeface.BOLD));
        paint.setTextSize(16);
        return paint;
    }
}

package com.vst.myapplication.UI.Rates;

import static android.provider.Settings.Global.getString;
import static android.view.View.GONE;
import static java.security.AccessController.getContext;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Environment;
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

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Random;
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
    public ratedetailsAdapter(Context context, Vector<ratedetailsDO> data,FragmentActivity fragmentActivity,ItemClickListener mClickListener) {
        this.inflater = LayoutInflater.from(context);
        this.mData = data;
        this.fragmentActivity = fragmentActivity;
        this.mClickListener = mClickListener;
    }
    public void refresh(Vector<ratedetailsDO> data){
        this.mData = data;
        notifyDataSetChanged();
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
            ((TextView) holder.itemView.findViewById(R.id.tvdetailid)).setText(rate.DETAILID+"");
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
            ((ImageView) holder.itemView.findViewById(R.id.ivdelte)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Log.d("AdapterPosition",""+holder.getAdapterPosition());
//                    if (mClickListener != null) mClickListener.onItemClick(v, holder.getAdapterPosition());
                    getRate();
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
    public Bitmap getRate(){
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

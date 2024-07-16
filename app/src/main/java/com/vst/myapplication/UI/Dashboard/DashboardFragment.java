package com.vst.myapplication.UI.Dashboard;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.vst.myapplication.R;
import com.vst.myapplication.Services.ProjectRepository;
import com.vst.myapplication.UI.CustomGraphDesign.DoubleBarChart;
import com.vst.myapplication.UI.Login.Login;
import com.vst.myapplication.Utils.BaseFragment;
import com.vst.myapplication.Utils.CalendarUtils;
import com.vst.myapplication.dataObject.milkDO;
import com.vst.myapplication.databinding.DashboradfragmentBinding;

import org.json.JSONException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

public class DashboardFragment extends BaseFragment {
    DashboradfragmentBinding binding;
    ProjectRepository repository;
    milkDO[] milkDOS;
    String shift="M";

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState, LifecycleOwner viewLifecycleOwner) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dashboradfragment, parent, false);
        repository = new ProjectRepository();
        binding.setLifecycleOwner(viewLifecycleOwner);
        setupUI(inflater,parent,viewLifecycleOwner,savedInstanceState);
        return binding.getRoot();
    }
    private void setupUI(LayoutInflater inflater, ViewGroup parent, LifecycleOwner viewLifecycleOwner,Bundle savedInstanceState) {
        overATQ();
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        if (hour < 16) {
            shift="M";
        }
        else {
            shift="E";
        }

        xlabels123 = new ArrayList<>(Arrays.asList(xLabels));
        growPercentage1.add(1);growPercentage1.add(-2);growPercentage1.add(3);growPercentage1.add(3);growPercentage1.add(-4);growPercentage1.add(5);growPercentage1.add(6);growPercentage1.add(7);

        BarChart BarChart1 = binding.barChartDouble;
        DoubleBarChart doubleBarChart=new DoubleBarChart(BarChart1,xlabels123,growPercentage1);
        doubleBarChart.setDetails1(getBarEntriesOne(),"Oct-2023",Color.parseColor("#3ACA06"));
        doubleBarChart.setDetails2(getBarEntriesTwo(),"Oct-2022",Color.parseColor("#1F7102"));
        doubleBarChart.isValueSelected(true);
        doubleBarChart.setAboveImageEnable(true);
        doubleBarChart.setAboveTextColor(Color.BLACK);
        doubleBarChart.createBarChart();
    }
    ArrayList<String> xlabels123 = new ArrayList<>();
    String[] xLabels = new String[]{"MTD","LM","YTD","LY","QW","PS","SK"};
    ArrayList<Integer> growPercentage1 = new ArrayList<>();

    @Override
    public void onButtonYesClick(String from) throws JSONException {
        super.onButtonYesClick(from);
        if (from.equals("logout")) {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .add(R.id.frame, new Login(), "")
                    .commitAllowingStateLoss();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        refreshData();
//        requireActivity().getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);
    }
    private final OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            showCustomDialog(getContext(), getResources().getString(R.string.warning), getResources().getString(R.string.do_you_want_to_logout), getResources().getString(R.string.Yes), getResources().getString(R.string.No), "logout");
        }
    };

    public void refreshData(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("TDATE", CalendarUtils.getDatePattern3());
        jsonObject.addProperty("SHIFT",shift);
        repository.getMData(getViewLifecycleOwner(),jsonObject).observe(this, new Observer<JsonObject>() {
            @Override
            public void onChanged(JsonObject jsonObject) {
                if (jsonObject != null) {
                    Log.d("TAG", jsonObject.toString());
                    Gson gson = new Gson();
                    milkDOS = gson.fromJson(jsonObject.getAsJsonArray("Data"), milkDO[].class);
                    // rateDOs = gson.fromJson(jsonObject.toString(), rateDO[].class);
                    if (milkDOS != null) {
                        if(milkDOS!=null&&milkDOS.length>0) {
                            double buffqty = 0, cowqty = 0,qty = 0, amt = 0, ltrfat = 0, ltrsnf = 0;
                            for (int i = 0; i < milkDOS.length; i++) {
                                qty += milkDOS[i].QUANTITY;
                                if (milkDOS[i].MILKTYPE.equals("Buff")) {
                                    buffqty += milkDOS[i].QUANTITY;
                                } else if (milkDOS[i].MILKTYPE.equals("Cow")) {
                                    cowqty += milkDOS[i].QUANTITY;
                                }
                                ltrfat += ((milkDOS[i].FAT * milkDOS[i].QUANTITY)/100);
//                        ltrfat += ((mdata.get(i).getFat() * mdata.get(i).getQuantity()) / 100);
                                ltrsnf += ((milkDOS[i].SNF * milkDOS[i].QUANTITY) / 100);
                                amt += milkDOS[i].AMOUNT;
                            }
                            binding.txtcowqty.setText(String.format("%.1f",cowqty));
                            binding.txtbuffqty.setText(String.format("%.1f",buffqty));
                            binding.txttotalamt.setText(String.format("%.1f",amt));
                            binding.txttotalqty.setText(String.format("%.1f",qty));
                            binding.txtavgfat.setText(String.format("%.1f",(ltrfat / qty) * 100));
//                    binding.txtavgsnf.setText(String.format("%.1f",(ltrsnf)));
                            binding.txtavgsnf.setText(String.format("%.1f",(ltrsnf / qty) * 100));
                        }
                        else {
                            binding.txtcowqty.setText(String.valueOf(0));
                            binding.txtbuffqty.setText(String.valueOf(0));
                            binding.txttotalamt.setText(String.valueOf(0));
                            binding.txttotalqty.setText(String.valueOf(0));
                            binding.txtavgfat.setText(String.valueOf(0));
                            binding.txtavgsnf.setText(String.valueOf(0));
                        }
                    }
                }
            }
        });
    }


    private void overATQ() {
//        LineChart lineChart = findViewById(R.id.over_ATQ);
        LineChart lineChart = binding.overATQ;
        lineChart.setExtraOffsets(10,0,10,13);
        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.setClickable(false);
        lineChart.getDescription().setEnabled(false);// Remove description label on X-axis

        String[] days50 = generateDates(50);
        ArrayList<Entry> values = generateRandomData(50);
        ArrayList<Entry> values2 = generateRandomData(50);
        ArrayList<Entry> values3 = generateRandomData(50);
        Log.d("values", values.toString());
        Log.d("values1", values2.toString());
        Log.d("values3", values3.toString());

        int customColor1 = Color.parseColor("#3EA81A");
        int customColor2 = Color.parseColor("#005BAA");
        int customColor3 = Color.parseColor("#FFAA00");

        LineDataSet dataSet1 = new LineDataSet(values, "Invt.(Inc.Transit)");
        dataSet1.setColor(customColor1);
        dataSet1.setCircleColor(customColor1);
        dataSet1.setLineWidth(2);
        dataSet1.setCircleRadius(6f); // Adjust the radius as needed
        dataSet1.setCircleHoleRadius(3f); // Adjust the hole radius as needed
        dataSet1.setCircleHoleColor(Color.WHITE);
//        dataSet1.setCircleHoleColor(Color.parseColor("#98BDDC"));
        dataSet1.setDrawValues(false); // Disable drawing values on points


        LineDataSet dataSet2 = new LineDataSet(values2, "Over - Ageing");
//        dataSet2.setColor(Color.RED);
        dataSet2.setColor(customColor2);
        dataSet2.setCircleColor(customColor2);
        dataSet2.setDrawHighlightIndicators(true);
        dataSet2.setHighLightColor(Color.RED);
        dataSet2.setLineWidth(2);
        dataSet2.setCircleRadius(6f); // Adjust the radius as needed
        dataSet2.setCircleHoleRadius(3f); // Adjust the hole radius as needed
        dataSet2.setCircleHoleColor(Color.WHITE);
        dataSet2.setValueTextColor(customColor1);
        dataSet2.setValueTextSize(12f);
        dataSet2.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return (int)value+"%";
            }
        });
        LineDataSet dataSet3 = new LineDataSet(values3, "FWD Ageing");
        dataSet3.setColor(customColor3);
        dataSet3.setCircleColor(customColor3);
        dataSet3.setLineWidth(2);
        dataSet3.setCircleRadius(6f); // Adjust the radius as needed
        dataSet3.setCircleHoleRadius(3f); // Adjust the hole radius as needed
        dataSet3.setCircleHoleColor(Color.WHITE);
        dataSet3.setDrawValues(false); // Disable drawing values on points


        // Combine datasets into LineData
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet1);
//        dataSets.add(dataSet2);
//        dataSets.add(dataSet3);

        LineData lineData = new LineData(dataSets);
        lineChart.setData(lineData);

        // Customize X-axis
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setAxisMinimum(0f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(days50));
//        xAxis.setValueFormatter(new IndexAxisValueFormatter(LabelsForSingleBarCharts));
//        xAxis.setLabelCount(20, true);
//        xAxis.setAxisLineWidth(1f);
        lineChart.setVisibleXRangeMaximum(7);

        xAxis.setYOffset(10f);
        // Customize left Y-axis
        YAxis leftYAxis = lineChart.getAxisLeft();
        leftYAxis.setGranularity(0f);
        leftYAxis.setAxisMinimum(0f);
        leftYAxis.setDrawGridLines(true);

        // Customize right Y-axis
        YAxis rightYAxis = lineChart.getAxisRight();
        rightYAxis.setEnabled(false); // Disable right Y-axis
        rightYAxis.setDrawGridLines(false); // Disable grid lines on the right

        // Customize chart appearance
        Legend legend = lineChart.getLegend();
        legend.setForm(Legend.LegendForm.SQUARE);
        lineChart.getAxisRight().setEnabled(false);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);

        // Set extra offset at the top to create a gap
//        float extraTopOffset = getResources().getDimension(R.dimen.legend_top_offset);
//        lineChart.setExtraTopOffset(extraTopOffset);

        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) lineChart.getLayoutParams();
//        layoutParams.topMargin = (int) getResources().getDimensionPixelSize(R.dimen.chart_top_margin);
//        layoutParams.bottomMargin = (int) getResources().getDimensionPixelSize(R.dimen.chart_bottom_margin);
//        layoutParams.leftMargin = (int) getResources().getDimensionPixelSize(R.dimen.legend_left_offset);
        lineChart.setLayoutParams(layoutParams);
        lineChart.invalidate();
    }
    private String[] generateDates(int length) {
        String[] dates = new String[length];
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMM", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        dates[0] = "";
        for (int i = 1; i < length; i++) {
            dates[i] = dateFormat.format(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1); // Adjust the interval as needed
        }
        return dates;
    }
    private ArrayList<Entry> generateRandomData(int length) {
        ArrayList<Entry> randomData = new ArrayList<>();
        Random random = new Random();
        randomData.add(new Entry(0,0));
        for (int i = 1; i < length; i++) {
            randomData.add(new Entry(i, random.nextInt(10))); // Adjust the range as needed
        }
        return randomData;
    }
    private ArrayList<BarEntry> getBarEntriesOne() {
        ArrayList barEntries = new ArrayList<>();

        barEntries.add(new BarEntry(1f, 220));
        barEntries.add(new BarEntry(2f, 200));
        barEntries.add(new BarEntry(3f, 180));
        barEntries.add(new BarEntry(4f, 162));
        barEntries.add(new BarEntry(5f, 142));
        barEntries.add(new BarEntry(6f, 120));
        barEntries.add(new BarEntry(7f, 100));
        return barEntries;
    }
    private ArrayList<BarEntry> getBarEntriesTwo() {
        ArrayList barEntries = new ArrayList<>();

        barEntries.add(new BarEntry(1f, 200));
        barEntries.add(new BarEntry(2f, 180));
        barEntries.add(new BarEntry(3f, 160));
        barEntries.add(new BarEntry(4f, 140));
        barEntries.add(new BarEntry(5f, 80));
        barEntries.add(new BarEntry(6f, 100));
        barEntries.add(new BarEntry(7f, 80));
        return barEntries;
    }
    private ArrayList<BarEntry> getBarEntriesThree() {
        ArrayList barEntries = new ArrayList<>();

        barEntries.add(new BarEntry(1f, 180));
        barEntries.add(new BarEntry(2f, 160));
        barEntries.add(new BarEntry(3f, 140));
        barEntries.add(new BarEntry(4f, 120));
        barEntries.add(new BarEntry(5f, 100));
        barEntries.add(new BarEntry(6f, 80));
        barEntries.add(new BarEntry(7f, 60));
        return barEntries;
    }
    private ArrayList<BarEntry> getBarEntriesFour() {
        ArrayList barEntries = new ArrayList<>();

        barEntries.add(new BarEntry(1f, 180));
        barEntries.add(new BarEntry(2f, 150));
        barEntries.add(new BarEntry(3f, 80));
        barEntries.add(new BarEntry(4f, 120));
        barEntries.add(new BarEntry(5f, 105));
        barEntries.add(new BarEntry(6f, 80));
        barEntries.add(new BarEntry(7f, 60));
        return barEntries;
    }
}

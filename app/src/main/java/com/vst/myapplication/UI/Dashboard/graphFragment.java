package com.vst.myapplication.UI.Dashboard;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.vst.myapplication.R;
import com.vst.myapplication.Services.ProjectRepository;
import com.vst.myapplication.Utils.BaseFragment;
import com.vst.myapplication.databinding.GraphBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

public class graphFragment extends BaseFragment {
    GraphBinding binding;
    ProjectRepository repository;
    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState, LifecycleOwner viewLifecycleOwner) {
        binding = DataBindingUtil.inflate(inflater, R.layout.graph, parent, false);
        repository = new ProjectRepository();
        binding.setLifecycleOwner(viewLifecycleOwner);
        setupUI(inflater,parent,viewLifecycleOwner,savedInstanceState);
        return binding.getRoot();
    }
    private void setupUI(LayoutInflater inflater, ViewGroup parent, LifecycleOwner viewLifecycleOwner,Bundle savedInstanceState) {
        overATQ();
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
}

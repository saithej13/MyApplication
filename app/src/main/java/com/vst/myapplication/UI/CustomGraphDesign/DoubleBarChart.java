package com.vst.myapplication.UI.CustomGraphDesign;

import android.graphics.Color;
import android.graphics.Typeface;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class DoubleBarChart {
    private BarChart barChart;
    private List<BarEntry> barEntries1;
    private List<BarEntry> barEntries2;
    private List<String> xlabels;
    private String label1="",label2="";
    private int barColor1=0,barColor2=0;
    private ArrayList<Integer> growPercentage= new ArrayList<>();
    private int angle=0;
    private int aboveTextColor=0;
    private boolean aboveImage=true;
    private boolean isValueSelected=true;
    float setVisibleXRangeMaximum=4.1f;
    XAxis xAxis;
    public DoubleBarChart(BarChart chart, List<BarEntry> entries1,
                          List<BarEntry> entries2, List<String> xlabels, ArrayList<Integer> growPercentage) {
        this.barChart = chart;
        this.barEntries1 = entries1;
        this.barEntries2 = entries2;
        this.xlabels = xlabels;
        this.growPercentage = growPercentage;
    }
    public void setbarDetails(String label1,String label2,int label3,int label4){
        this.label1 = label1;
        this.label2 = label2;
        this.barColor1=label3;
        this.barColor2=label4;
    }


    public DoubleBarChart(BarChart chart, List<String> xlabels, ArrayList<Integer> percantage){
        this.barChart = chart;
        this.xlabels = xlabels;
        this.growPercentage = percantage;
    }
    public void setDetails1(List<BarEntry> entries,String label,int color){
        this.barEntries1 = entries;
        this.label1 = label;
        this.barColor1 = color;
    }
    public void setDetails2(List<BarEntry> entries,String label,int color){
        this.barEntries2 = entries;
        this.label2 = label;
        this.barColor2 = color;
    }

    public void setAboveImageEnable(Boolean angle){ this.aboveImage=angle;}
    public void setAboveTextColor(Integer angle){ this.aboveTextColor=angle;}
    public void isValueSelected(Boolean angle){ this.isValueSelected=angle;}

    public void setLabelRotationAngle(int angle){ this.angle=angle;}
    public void setVisibleXRangeMaximum(float max){ this.setVisibleXRangeMaximum=max;}
    public void createBarChart() {
//        barChart.clear();
        barChart.setExtraOffsets(5,30,0,15);
        barChart.setDrawValueAboveBar(false);
        AboveAndCurveCustomBarChart aboveAndCurveCustomBarChart= new AboveAndCurveCustomBarChart(
                barChart.getContext(), barChart,barChart.getAnimator(),barChart.getViewPortHandler(),growPercentage);
        aboveAndCurveCustomBarChart.setRadius(20);
        aboveAndCurveCustomBarChart.textNeededBarPosition(2);
        aboveAndCurveCustomBarChart.disaplayImage(aboveImage);
        if(aboveTextColor!=0) aboveAndCurveCustomBarChart.aboveValueTextColor(aboveTextColor);
        barChart.setRenderer(aboveAndCurveCustomBarChart);

        float barSpace = 0.1f;
        float groupSpace = 0.1f;

        BarDataSet barDataSet1 = new BarDataSet(barEntries1, label1);
        barDataSet1.setColor(barColor1);

        BarDataSet barDataSet2 = new BarDataSet(barEntries2, label2);
        barDataSet2.setColor(barColor2);

        BarData data = new BarData(barDataSet1, barDataSet2);
        data.setValueTextSize(8f);
        data.setBarWidth(0.35f);
        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(Typeface.DEFAULT_BOLD);


        barDataSet1.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int qwe=(int) value;
//                float qwe= value;
                if(qwe>0){
                    if(isValueSelected){
                        return qwe+"L";
                    }else{
                        return qwe+"";
                    }
                }else return "";
            }
        });
        barDataSet2.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int qwe=(int) value;
//                float qwe= value;
                if(qwe>0){
                    if(isValueSelected){
                        return qwe+"L";
                    }else{
                        return qwe+"";
                    }
                }else return "";
            }
        });

        Legend legend = barChart.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setYOffset(8f);

        xAxis = barChart.getXAxis();
        String[] xLabels1 = xlabels.toArray(new String[0]);// Convert ArrayList to array
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xLabels1));
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularityEnabled(true);
        xAxis.setDrawGridLines(false);
//        xAxis.setLabelRotationAngle(315);
        xAxis.setLabelRotationAngle(angle);
        xAxis.setAxisMaximum(barEntries1.size()+.5f);

        YAxis rightYAxis = barChart.getAxisRight();
        rightYAxis.setEnabled(false);

        YAxis leftYAxis = barChart.getAxisLeft();
        leftYAxis.setAxisMinimum(0f);
        leftYAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftYAxis.setDrawGridLines(false);

        barChart.animateXY(1500,1500);
        barChart.animate();
        barChart.setDoubleTapToZoomEnabled(false);//to stop zoom on double tap
        barChart.setHighlightPerTapEnabled(false);// to disable the highlight on selection of the bar

        barChart.setData(data);
        barChart.getDescription().setEnabled(false);
        barChart.setDragEnabled(true);
        barChart.getXAxis().setAxisMinimum(0);
//        barChart.setVisibleXRangeMaximum(setVisibleXRangeMaximum);
        barChart.setVisibleXRangeMinimum(2);
        barChart.setVisibleXRangeMaximum(2);
        barChart.groupBars(0, groupSpace, barSpace);
        barChart.invalidate();
    }

}

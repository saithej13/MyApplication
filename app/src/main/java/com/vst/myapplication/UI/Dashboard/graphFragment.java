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
    }


}

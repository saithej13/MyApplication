package com.vst.myapplication.UI.Rates;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.vst.myapplication.R;
import com.vst.myapplication.Services.ProjectRepository;
import com.vst.myapplication.UI.MCollection.milkCollectionAdapter;
import com.vst.myapplication.Utils.BaseFragment;
import com.vst.myapplication.Utils.CalendarUtils;
import com.vst.myapplication.dataObject.RateAndDetails;
import com.vst.myapplication.dataObject.milkDO;
import com.vst.myapplication.dataObject.rateDO;
import com.vst.myapplication.dataObject.ratedetailsDO;
import com.vst.myapplication.databinding.RatedetailsBinding;
import com.vst.myapplication.databinding.RatedetailscardcellBinding;
import com.vst.myapplication.databinding.RatesEntryPopup2Binding;
import com.vst.myapplication.databinding.RatesdetailedlistBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

public class ratedetails extends BaseFragment implements ratedetailsAdapter.ItemClickListener{
    RatesdetailedlistBinding binding;
    private int selectedPosition=-1;
    boolean addRate=false;
    int SLNO = 0;
    ImageView ivdropdownmilktype;
    ImageView iveditrate;
    ImageView ivdeleterate;
    LinearLayout startdate;
    LinearLayout enddate;
    int mdate,mmonth,myear;
    private Dialog dialog;
    rates_VM ratesVm;
    rateDO rateDo;
    RateAndDetails rateAndDetails[];
    Vector<ratedetailsDO> vecratedetailsdo;
    ratedetailsAdapter ratedetailsadapter;
    RatesEntryPopup2Binding ratesEntryPopupBinding;


    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState, LifecycleOwner viewLifecycleOwner) {
        binding = DataBindingUtil.inflate(inflater, R.layout.ratesdetailedlist, parent, false);
        binding.setLifecycleOwner(viewLifecycleOwner);
        ratesVm = new ViewModelProvider(this ).get(rates_VM.class);
        vecratedetailsdo = new Vector<ratedetailsDO>();
        ratedetailsadapter = new ratedetailsAdapter(getContext(),vecratedetailsdo,getActivity());
        ratedetailsadapter.setClickListener(this);
        setupUI(inflater,parent,viewLifecycleOwner);
        return binding.getRoot();
    }
    private void setupUI(LayoutInflater inflater, ViewGroup parent, LifecycleOwner viewLifecycleOwner) {
        List<ratedetailsDO> ratedetailsList = new ArrayList<>();
        addRate = getArguments().getBoolean("addRate");
        SLNO = getArguments().getInt("SLNO");
        ivdropdownmilktype = binding.llOverView.dropdownmilktype;
        iveditrate = binding.llOverView.ivedit;
        ivdeleterate = binding.llOverView.ivdelete;
        startdate = binding.llOverView.llStartDate;
        enddate = binding.llOverView.llEndDate;
        if(addRate){
            ivdropdownmilktype.setVisibility(View.VISIBLE);
            iveditrate.setVisibility(View.GONE);
            ivdeleterate.setVisibility(View.GONE);
        }else {
            ivdropdownmilktype.setVisibility(View.GONE);
            iveditrate.setVisibility(View.VISIBLE);
            ivdeleterate.setVisibility(View.VISIBLE);
        }
        if (rateDo != null) {
            binding.llOverView.tvstartdate.setText(rateDo.STARTDATE);
            binding.llOverView.tvenddate.setText(rateDo.ENDDATE);
            binding.llOverView.tvmilktype.setText(rateDo.MILKTYPE);
            vecratedetailsdo.add(rateDo.ratedetailsDO);
            ratedetailsAdapter ratedetailsadapter = new ratedetailsAdapter(getContext(),vecratedetailsdo,getActivity());
            binding.rcvRatedetails.setLayoutManager(new LinearLayoutManager(parent.getContext()));
//            ratesAdapter = new RatesAdapter(parent.getContext(), rateAndDetails);
            binding.rcvRatedetails.setAdapter(ratedetailsadapter);
            binding.rcvRatedetails.setHasFixedSize(true);
        }

//        binding.llOverView.ivsave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(!binding.llOverView.tvmilktype.getText().toString().isEmpty()&&!binding.llOverView.tvstartdate.getText().toString().isEmpty()&&!binding.llOverView.tvenddate.getText().toString().isEmpty())
//                {
//                    rateDo.BCODE = "1";
//                    rateDo.MILKTYPE = binding.llOverView.tvmilktype.getText().toString();
//                    rateDo.STARTDATE = CalendarUtils.getFormatedDatefromString(binding.llOverView.tvstartdate.getText().toString());
//                    rateDo.ENDDATE = CalendarUtils.getFormatedDatefromString(binding.llOverView.tvenddate.getText().toString());
//                }
//                else {
//                    showCustomDialog(getContext(),"Error","Please Select Milk Type , Start Date and End Date!","OK",null,"");
//                }
//            }
//        });
        startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cal = Calendar.getInstance();
                mdate = cal.get(Calendar.DATE);
                mmonth = cal.get(Calendar.MONTH);
                myear = cal.get(Calendar.YEAR);
                new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int monthOfYear, int month, int date) {
                        String selDate = (monthOfYear + "-" + month + "-" + date);
                        binding.llOverView.tvstartdate.setText(CalendarUtils.getFormatedDatefromString(selDate));
                    }
                }, myear, mmonth, mdate).show();
            }
        });
        enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cal = Calendar.getInstance();
                mdate = cal.get(Calendar.DATE);
                mmonth = cal.get(Calendar.MONTH);
                myear = cal.get(Calendar.YEAR);
                new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int monthOfYear, int month, int date) {
                        String selDate = (monthOfYear + "-" + month + "-" + date);
                        binding.llOverView.tvenddate.setText(CalendarUtils.getFormatedDatefromString(selDate));

                    }
                }, myear, mmonth, mdate).show();
            }
        });
        binding.llOverView.llmtype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getContext() != null) {
                    final String[] arraySpinner = new String[]{
                            "Cow", "Buff", "Both"
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Select an option");
                    builder.setItems(arraySpinner, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String selectedOption = arraySpinner[which];
                            binding.llOverView.tvmilktype.setText(selectedOption);
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    Log.e("nothing phone3", "");
                }
            }
        });
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showpopup(null);
            }
        });
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!binding.llOverView.tvmilktype.getText().toString().isEmpty()&&!binding.llOverView.tvstartdate.getText().toString().isEmpty()&&!binding.llOverView.tvenddate.getText().toString().isEmpty())
                {
                    try{
                        rateDo = new rateDO();
                        rateDo.MILKTYPE = binding.llOverView.tvmilktype.getText().toString();
                        rateDo.STARTDATE = binding.llOverView.tvstartdate.getText().toString();
                        rateDo.ENDDATE = binding.llOverView.tvenddate.getText().toString();
                        RateAndDetails rateAndDetails = new RateAndDetails(rateDo, vecratedetailsdo);
                        if (!rateAndDetails.rateDetailsList.isEmpty()) {
                            ratesVm.insertRate(rateAndDetails);
                        }else {
                            showCustomDialog(getContext(),"Error","Add Atleast one RateDetail to Save!","OK",null,"");
                        }
                    }
                    catch (Exception ex){
                        showCustomDialog(getContext(),"Exception",""+ex.getMessage(),"OK",null,"");
                    }

                }
                else {
                    showCustomDialog(getContext(),"Error","Please Select Milk Type , Start Date and End Date!","OK",null,"");
                }

            }
        });
    }
    public void refreshData(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("SLNO", SLNO);
        ratesVm.getRates(jsonObject).observe(this, new Observer<JsonObject>() {
            @Override
            public void onChanged(JsonObject jsonObject) {
                if (jsonObject != null) {
                    Log.d("TAG", jsonObject.toString());
                    Gson gson = new Gson();
                    rateAndDetails = gson.fromJson(jsonObject.getAsJsonArray("Data"), RateAndDetails[].class);
                    if (rateAndDetails != null) {
                        if (rateAndDetails.length > 0) {
                            rateDo = rateAndDetails[0].rate;
                            binding.llOverView.tvstartdate.setText(rateDo.STARTDATE);
                            binding.llOverView.tvenddate.setText(rateDo.ENDDATE);
                            binding.llOverView.tvmilktype.setText(rateDo.MILKTYPE);
                            for(int i=0;i<rateAndDetails[0].rateDetailsList.size();i++)
                            {
                                ratedetailsDO ratedetailsDo = new ratedetailsDO();
                                ratedetailsDo.FATMIN = rateAndDetails[0].rateDetailsList.get(i).FATMIN;
                                ratedetailsDo.FATMAX = rateAndDetails[0].rateDetailsList.get(i).FATMAX;
                                ratedetailsDo.SNFMIN = rateAndDetails[0].rateDetailsList.get(i).SNFMIN;
                                ratedetailsDo.SNFMAX = rateAndDetails[0].rateDetailsList.get(i).SNFMAX;
                                ratedetailsDo.RATE = rateAndDetails[0].rateDetailsList.get(i).RATE;
                                vecratedetailsdo.add(ratedetailsDo);
                            }
                            if(vecratedetailsdo.size()>0){
                                ratedetailsAdapter ratedetailsadapter = new ratedetailsAdapter(getContext(),vecratedetailsdo,getActivity());
                                binding.rcvRatedetails.setLayoutManager(new LinearLayoutManager(getContext()));
                                binding.rcvRatedetails.setAdapter(ratedetailsadapter);
                                binding.rcvRatedetails.setHasFixedSize(true);
                            }
                            else {
                                rateAndDetails = new RateAndDetails[]{};
                                if (ratedetailsadapter != null) {
                                    ratedetailsadapter.notifyDataSetChanged();
                                }
                            }

                        }
                    }
                }
//                //parent row with single record
//                rateDo = new rateDO();
//                rateDo.MILKTYPE = binding.llOverView.tvmilktype.getText().toString();
//                rateDo.STARTDATE = binding.llOverView.tvstartdate.getText().toString();
//                rateDo.ENDDATE = binding.llOverView.tvenddate.getText().toString();
//                //child rows having multiple records
//                ratedetailsDO ratedetailsDo = new ratedetailsDO();
//                ratedetailsDo.FATMIN = Double.parseDouble(ratesEntryPopupBinding.etfatmin.getText().toString());
//                ratedetailsDo.FATMAX = Double.parseDouble(ratesEntryPopupBinding.etfatmax.getText().toString());
//                ratedetailsDo.SNFMIN = Double.parseDouble(ratesEntryPopupBinding.etsnfmin.getText().toString());
//                ratedetailsDo.SNFMAX = Double.parseDouble(ratesEntryPopupBinding.etsnfmax.getText().toString());
//                ratedetailsDo.RATE = Double.parseDouble(ratesEntryPopupBinding.etRate.getText().toString());

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshData();
    }

    public void showpopup(ratedetailsDO selectedRateDetail){
        if (dialog == null || !dialog.isShowing()) {
            ViewGroup rootView = (ViewGroup) getView().getRootView();
            ratesEntryPopupBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.rates_entry_popup2, rootView, false);
            dialog = new Dialog(getContext(), R.style.Dialog);
            dialog.setContentView(ratesEntryPopupBinding.getRoot());
            if (selectedRateDetail != null) {
                ratesEntryPopupBinding.etfatmin.setText(String.valueOf(selectedRateDetail.FATMIN));
                ratesEntryPopupBinding.etfatmax.setText(String.valueOf(selectedRateDetail.FATMAX));
                ratesEntryPopupBinding.etsnfmin.setText(String.valueOf(selectedRateDetail.SNFMIN));
                ratesEntryPopupBinding.etsnfmax.setText(String.valueOf(selectedRateDetail.SNFMAX));
                ratesEntryPopupBinding.etRate.setText(String.valueOf(selectedRateDetail.RATE));
            }
            ratesEntryPopupBinding.btnOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!ratesEntryPopupBinding.etfatmin.getText().toString().isEmpty()&&!ratesEntryPopupBinding.etfatmax.getText().toString().isEmpty()&&!ratesEntryPopupBinding.etsnfmin.getText().toString().isEmpty()&&!ratesEntryPopupBinding.etsnfmax.getText().toString().isEmpty()&&!ratesEntryPopupBinding.etRate.getText().toString().isEmpty()) {
                        ratedetailsDO ratedetailsDo = new ratedetailsDO();
                        ratedetailsDo.FATMIN = Double.parseDouble(ratesEntryPopupBinding.etfatmin.getText().toString());
                        ratedetailsDo.FATMAX = Double.parseDouble(ratesEntryPopupBinding.etfatmax.getText().toString());
                        ratedetailsDo.SNFMIN = Double.parseDouble(ratesEntryPopupBinding.etsnfmin.getText().toString());
                        ratedetailsDo.SNFMAX = Double.parseDouble(ratesEntryPopupBinding.etsnfmax.getText().toString());
                        ratedetailsDo.RATE = Double.parseDouble(ratesEntryPopupBinding.etRate.getText().toString());
                        if (selectedRateDetail != null) {
                            // Update existing item
                            vecratedetailsdo.set(selectedPosition, ratedetailsDo);
                        } else {
                            // Add new item
                            vecratedetailsdo.add(ratedetailsDo);
                        }

                        ratedetailsadapter.notifyDataSetChanged();
                        dialog.dismiss();
//                        vecratedetailsdo.add(ratedetailsDo);
//                        ratedetailsAdapter ratedetailsadapter = new ratedetailsAdapter(getContext(), vecratedetailsdo, getActivity());
//                        binding.rcvRatedetails.setLayoutManager(new LinearLayoutManager(getContext()));
//                        binding.rcvRatedetails.setAdapter(ratedetailsadapter);
//                        binding.rcvRatedetails.setHasFixedSize(true);
//                        dialog.dismiss();
                    }
                }
            });
            ratesEntryPopupBinding.btnCancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        }
    }

//    @Override
//    public void onItemClick(View view, int position) {
//        selectedPosition=position;
//        showpopup(view,selectedPosition);
//    }
    @Override
    public void onItemClick(View view, int position) {
        Log.d("ITEM IS CLIECKED",""+position);
        ratedetailsDO selectedRateDetail = vecratedetailsdo.get(position);
        showpopup(selectedRateDetail);
    }
}

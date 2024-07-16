package com.vst.myapplication.Utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.vst.myapplication.R;
import com.vst.myapplication.MainActivity;
import com.vst.myapplication.UI.Dashboard.Dashboard1;
import com.vst.myapplication.UI.Dashboard.DashboardFragment;
import com.vst.myapplication.UI.Dashboard.graphFragment;
import com.vst.myapplication.UI.menu.menuFragment;

import org.json.JSONException;

public abstract class BaseFragment extends Fragment {
    LayoutInflater inflater;
    public MainActivity homeActivity;
    CustomDialog customDialog;
    View view1;
    Preference preference =  new Preference(getContext());;
    Button btnYes;
    Button btnNo;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanseState) {
        this.inflater = inflater;
        View view = provideYourFragmentView(inflater, parent, savedInstanseState,getViewLifecycleOwner());

        return view;
    }
    ImageView ivdashboard,ivmenu,ivprofile;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeActivity = (MainActivity) requireActivity();
        ivdashboard = requireActivity().findViewById(R.id.dashboard);
        ivmenu = requireActivity().findViewById(R.id.menu);
        ivprofile = requireActivity().findViewById(R.id.profile);
        ivdashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMenuOne();
            }
        });
        ivmenu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    if (BaseFragment.this instanceof menuFragment) {

                    } else {
                        menuFragment menuFragment = new menuFragment();
                        FragmentManager fragmentManager = getParentFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                                .add(R.id.frame, menuFragment, "")
//                                .addToBackStack("")
                                .commitAllowingStateLoss();
                        ivdashboard.setImageResource(R.drawable.dashboardb);
//                        ivmenu.setImageResource(R.drawable.menub);
                        ivprofile.setImageResource(R.drawable.profile);
                        ivmenu.setImageResource(R.drawable.menuc);
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

    }

    public void loadMenuOne(){
        try {
            if (BaseFragment.this instanceof DashboardFragment) {
//                        clickLogOut();
            } else {
//                        Dashboard1 dashboardFragment = new Dashboard1();
                DashboardFragment dashboardFragment = new DashboardFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                        .add(R.id.frame, dashboardFragment, "")
//                                .addToBackStack("")
                        .commitAllowingStateLoss();
//                        ivdashboard.setImageResource(R.drawable.dashboardb);
                ivdashboard.setImageResource(R.drawable.dashboardc);
                ivmenu.setImageResource(R.drawable.menub);
                ivprofile.setImageResource(R.drawable.profile);
//                        updateImageViews();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LinearLayout llbottomnav = requireActivity().findViewById(R.id.llbottomnav);
                        if(BaseFragment.this instanceof Dashboard1
                                || BaseFragment.this instanceof menuFragment
                                || BaseFragment.this instanceof DashboardFragment
                                || BaseFragment.this instanceof graphFragment)
                        {
//                            setBottomNavigation(true);
                            llbottomnav.setVisibility(View.VISIBLE);
                        }
                        else {
                            llbottomnav.setVisibility(View.GONE);
                        }
                    }
                });
            }
        }).start();
    }

    public abstract View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState, LifecycleOwner viewLifecycleOwner);
    class RunshowCustomDialogs implements Runnable {
        private final String strTitle;// Title of the dialog
        private final String strMessage;// Message to be shown in dialog
        private final String firstBtnName;
        private final String secondBtnName;
        private final String from;
        private String params;
        private Object paramateres;
        private boolean isCancelable = false;
        private View.OnClickListener posClickListener;
        private View.OnClickListener negClickListener;
        private final Context context;

        public RunshowCustomDialogs(Context context, String strTitle, String strMessage, String firstBtnName, String secondBtnName, String from, boolean isCancelable) {
            this.strTitle = strTitle;
            this.strMessage = strMessage;
            this.firstBtnName = firstBtnName;
            this.secondBtnName = secondBtnName;
            this.isCancelable = isCancelable;
            this.context = context;
            if (from != null) this.from = from;
            else this.from = "";
        }

        public RunshowCustomDialogs(Context context, String strTitle, String strMessage, String firstBtnName, String secondBtnName, String from, boolean isCancelable, String params) {
            this.strTitle = strTitle;
            this.strMessage = strMessage;
            this.firstBtnName = firstBtnName;
            this.secondBtnName = secondBtnName;
            this.isCancelable = isCancelable;
            this.context = context;
            if (from != null) this.from = from;
            else this.from = "";

            if (params != null) this.params = params;
            else this.params = "";
        }

        public RunshowCustomDialogs(Context context, String strTitle, String strMessage, String firstBtnName, String secondBtnName, String from, boolean isCancelable, Object params) {
            this.strTitle = strTitle;
            this.strMessage = strMessage;
            this.firstBtnName = firstBtnName;
            this.secondBtnName = secondBtnName;
            this.isCancelable = isCancelable;
            this.context = context;
            if (from != null) this.from = from;
            else this.from = "";

            if (params != null) this.paramateres = params;
            else this.paramateres = "";
        }


        @Override
        public void run() {
            if (customDialog != null && customDialog.isShowing()) customDialog.dismiss();

            View view;

            view = inflater.inflate(R.layout.custom_common_popup, null);

            preference = new Preference(context);

            customDialog = new CustomDialog(context, view, preference.getIntFromPreference(Preference.DEVICE_DISPLAY_WIDTH, 320) - 30, ViewGroup.LayoutParams.WRAP_CONTENT, true);

            customDialog.setCancelable(isCancelable);
            TextView tvTitle = (TextView) view.findViewById(R.id.tvTitlePopup);
            TextView tvMessage = (TextView) view.findViewById(R.id.tvMessagePopup);
            AppCompatButton btnYes =  view.findViewById(R.id.btnYesPopup);
            AppCompatButton btnNo =  view.findViewById(R.id.btnNoPopup);

            tvTitle.setTypeface(AppConstants.Montserrat_SemiBold);
            tvMessage.setTypeface(AppConstants.Montserrat_Regular);
            btnYes.setTypeface(AppConstants.Montserrat_SemiBold);
            btnNo.setTypeface(AppConstants.Montserrat_SemiBold);

            tvTitle.setText("" + strTitle);
            tvMessage.setText("" + strMessage);
            btnYes.setText("" + firstBtnName);
            if (secondBtnName != null && !secondBtnName.equalsIgnoreCase("")) {
                btnNo.setText("" + secondBtnName);
            } else {
                btnNo.setVisibility(View.GONE);
                btnYes.setBackground(getResources().getDrawable(R.drawable.roundcorner_new_app_color_one));

            }
            if (strTitle.toLowerCase().contains("success") || strTitle.toLowerCase().contains("Verified") || strTitle.toLowerCase().contains("Warning !") || strTitle.toLowerCase().contains("Warning"))
                //   tvTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.paymode_checked, 0, 0, 0);

                if (secondBtnName != null && !secondBtnName.equalsIgnoreCase("")) {
                    btnNo.setText("" + secondBtnName);
                } else {
                    btnNo.setVisibility(View.GONE);
                    btnYes.setBackground(getResources().getDrawable(R.drawable.roundcorner_new_app_color_one));
                }
            if (posClickListener == null) btnYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    customDialog.dismiss();

                    if (from != null && from.equalsIgnoreCase("cancelorder"))
                        onButtonYesClick(from, params);
                    else if (from != null && (from.equalsIgnoreCase("EXCESS_LOAD") || from.equalsIgnoreCase("StoreCheckObject") || from.equalsIgnoreCase("Excess_Stock")))
                        onButtonYesClick(from, paramateres);
                    else if (from != null && from.equalsIgnoreCase("RejectVarience"))
                        onButtonYesClick(from, paramateres);
                    else {
                        try {
                            onButtonYesClick(from);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
            else btnYes.setOnClickListener(posClickListener);

            if (negClickListener == null) btnNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    customDialog.dismiss();
                    if (from.equalsIgnoreCase("EXCESS_LOAD") || from.equalsIgnoreCase("advorderdone_checkout") || from.equalsIgnoreCase("Excess_Stock")) {
                        onButtonNoClick(from, paramateres);
                    } else {
                        onButtonNoClick(from);
                    }
                }
            });
            else btnNo.setOnClickListener(negClickListener);
            try {
                if (!customDialog.isShowing()) customDialog.showCustomDialog();
            } catch (Exception e) {
            }
        }
    }

    public void showCustomDialog(Context context, String strTitle, String strMessage, String firstBtnName, String secondBtnName, String from, View.OnClickListener posClickListener) {
        getActivity().runOnUiThread(new RunshowCustomDialogs(context, strTitle, strMessage, firstBtnName, secondBtnName, from, true));
    }

    /**
     * Method to Show the alert dialog
     **/
    public void showCustomDialog(Context context, String strTitle, String strMessage, String firstBtnName, String secondBtnName, String from) {
        getActivity().runOnUiThread(new RunshowCustomDialogs(context, strTitle, strMessage, firstBtnName, secondBtnName, from, true));
    }

    /**
     * Method to Show the alert dialog
     **/
    public void showCustomDialog(Context context, String strTitle, String strMessage, String firstBtnName, String secondBtnName, String from, String params) {
        getActivity().runOnUiThread(new RunshowCustomDialogs(context, strTitle, strMessage, firstBtnName, secondBtnName, from, true, params));
    }

    /**
     * Method to Show the alert dialog
     **/
    public void showCustomDialog(Context context, String strTitle, String strMessage, String firstBtnName, String secondBtnName, String from, Object params) {
        getActivity().runOnUiThread(new RunshowCustomDialogs(context, strTitle, strMessage, firstBtnName, secondBtnName, from, false, params));
    }

    /**
     * Method to Show the alert dialog
     **/
    public void showCustomDialog(Context context, String strTitle, String strMessage, String firstBtnName, String secondBtnName, String from, boolean isCancelable) {
        getActivity().runOnUiThread(new RunshowCustomDialogs(context, strTitle, strMessage, firstBtnName, secondBtnName, from, isCancelable));
    }

    public void onButtonYesClick(String from, Object params) {
    }

    public void onButtonNoClick(String from, Object params) {
    }

    public void onButtonYesClick(String from) throws JSONException {
    }


    public void onButtonNoClick(String from) {
    }


}

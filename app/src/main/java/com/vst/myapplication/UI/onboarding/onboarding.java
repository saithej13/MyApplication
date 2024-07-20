package com.vst.myapplication.UI.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.tabs.TabLayout;
import com.vst.myapplication.R;
import com.vst.myapplication.UI.Login.LoginNew;
import com.vst.myapplication.UI.Login.forgetpassword_activity;
import com.vst.myapplication.UI.Login.verifyotp_activity;
import com.vst.myapplication.Utils.Preference;
import com.vst.myapplication.databinding.AddadvanceBinding;
import com.vst.myapplication.databinding.Dashboard1Binding;
import com.vst.myapplication.databinding.ForgetpasswordBinding;
import com.vst.myapplication.databinding.OnboardingBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

public class onboarding extends AppCompatActivity {
    OnboardingBinding binding;
    IntroViewPagerAdapter introViewPagerAdapter;
    int position = 0 ;
    Preference preference;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.onboarding);
        preference = new Preference(getApplicationContext());
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("Fresh Food","We conduct rigorous quality testing to ensure that the milk we deliver is of the highest quality. Our team of experts conducts 26 tests every day to ensure that the milk is pure, fresh, and meets the highest standards of quality.",R.drawable.img1));
        mList.add(new ScreenItem("Fast Delivery","We offer hassle-free doorstep delivery of fresh milk every day, providing you with added convenience and ensuring that you never run out of milk.",R.drawable.img2));
        mList.add(new ScreenItem("Easy Payment","We offer personalized subscription plans that are tailored to your individual needs. With our flexible plans, you can easily manage your milk delivery schedule and customize your orders to ensure that you always have fresh milk on hand. Our user-friendly mobile app makes it easy to adjust your subscription plan, skip deliveries, or make any necessary changes to your order.",R.drawable.img3));

        // setup viewpager
        introViewPagerAdapter = new IntroViewPagerAdapter(this,mList);
        binding.screenViewpager.setAdapter(introViewPagerAdapter);

        // setup tablayout with viewpager

        binding.tabIndicator.setupWithViewPager(binding.screenViewpager);

        // next button click Listner

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position = binding.screenViewpager.getCurrentItem();
                if (position < mList.size()) {
                    position++;
                    binding.screenViewpager.setCurrentItem(position);
                }

                if (position == mList.size()-1) { // when we rech to the last screen
                    // TODO : show the GETSTARTED Button and hide the indicator and the next button
                    loaddLastScreen();
                }
            }
        });

        // tablayout add change listener


        binding.tabIndicator.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == mList.size()-1) {
                    loaddLastScreen();
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        // Get Started button click listener

        binding.btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), LoginNew.class);
                startActivity(intent);
                // also we need to save a boolean value to storage so next time when the user run the app
                // we could know that he is already checked the intro screen activity
                // i'm going to use shared preferences to that process
                savePrefsData();
                finish();
            }
        });

        // skip button click listener

        binding.tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.screenViewpager.setCurrentItem(mList.size());
            }
        });
    }
    private void loaddLastScreen() {

        binding.btnNext.setVisibility(View.INVISIBLE);
        binding.btnGetStarted.setVisibility(View.VISIBLE);
        binding.tvSkip.setVisibility(View.INVISIBLE);
        binding.tabIndicator.setVisibility(View.INVISIBLE);
        // TODO : ADD an animation the getstarted button
        // setup animation
        binding.btnGetStarted.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_animation));



    }
    private void savePrefsData() {
        preference.saveBooleanInPreference("isIntroOpnend",true );
        preference.commitPreference();
    }
    private boolean restorePrefData() {
        Boolean isIntroActivityOpnendBefore = preference.getbooleanFromPreference("isIntroOpnend",false );
        return  isIntroActivityOpnendBefore;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

package com.vst.myapplication.UI;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.vst.myapplication.MainActivity;
import com.vst.myapplication.R;
import com.vst.myapplication.Utils.Preference;
import com.vst.myapplication.Utils.SharedPreferences;
import com.vst.myapplication.databinding.SplashscreenBinding;
import com.vst.myapplication.Utils.AppConstants;

public class Splashscreen extends AppCompatActivity {
    SplashscreenBinding binding;
    Preference preference;
    SharedPreferences preferences;
    private static final int REQUEST_CODE_SCAN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.splashscreen);
//        binding.btnscan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Splashscreen.this, MainActivity.class);
//                startActivityForResult(intent, REQUEST_CODE_SCAN);
//            }
//        });

        preference = new Preference(getApplicationContext());
        preferences = new SharedPreferences();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        preference.saveIntInPreference(Preference.DEVICE_DISPLAY_WIDTH, displayMetrics.widthPixels);
        preference.saveIntInPreference(Preference.DEVICE_DISPLAY_HEIGHT, displayMetrics.heightPixels);
        preference.commitPreference();
        new Handler().postDelayed(() -> {
            Intent intent1 = new Intent(Splashscreen.this, MainActivity.class);
            startActivity(intent1);
            finish();
        }, 2000);

        AppConstants.DATABASE_PATH = getApplication().getFilesDir().toString() + "/";
        AppConstants.Montserrat_Regular = Typeface.createFromAsset(getApplicationContext().getAssets(), "Montserrat-Regular.ttf");
        AppConstants.Montserrat_Medium = Typeface.createFromAsset(getApplicationContext().getAssets(), "Montserrat-Medium.ttf");
        AppConstants.Montserrat_SemiBold = Typeface.createFromAsset(getApplicationContext().getAssets(), "Montserrat-SemiBold.ttf");
        AppConstants.Montserrat_Bold = Typeface.createFromAsset(getApplicationContext().getAssets(), "Montserrat-Bold.ttf");

        AppConstants.montserrat_black = Typeface.createFromAsset(getApplicationContext().getAssets(), "Montserrat-Black.ttf");
        AppConstants.montserrat_light = Typeface.createFromAsset(getApplicationContext().getAssets(), "Montserrat-Light.ttf");
        AppConstants.montserrat_extralight = Typeface.createFromAsset(getApplicationContext().getAssets(), "Montserrat-ExtraLight.ttf");
        AppConstants.montserrat_thin = Typeface.createFromAsset(getApplicationContext().getAssets(), "Montserrat-Thin.ttf");
        AppConstants.montserrat_extrabold = Typeface.createFromAsset(getApplicationContext().getAssets(), "Montserrat-ExtraBold.ttf");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String result = data.getStringExtra("SCAN_RESULT");
                Log.d("PRINTBYSCAN", "" + result);
                // Handle the scanned result
            }
        }
    }
}

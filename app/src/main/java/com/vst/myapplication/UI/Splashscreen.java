package com.vst.myapplication.UI;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.vst.myapplication.MainActivity;
import com.vst.myapplication.R;
import com.vst.myapplication.Utils.Preference;
import com.vst.myapplication.Utils.SharedPreferences;
import com.vst.myapplication.databinding.SplashscreenBinding;

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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String result = data.getStringExtra("SCAN_RESULT");
                Log.d("PRINTBYSCAN",""+result);
                // Handle the scanned result
            }
        }
    }
}

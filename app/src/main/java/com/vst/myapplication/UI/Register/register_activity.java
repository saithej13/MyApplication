package com.vst.myapplication.UI.Register;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.vst.myapplication.MainActivity;
import com.vst.myapplication.R;
import com.vst.myapplication.UI.Login.LoginNew;
import com.vst.myapplication.UI.Login.verifyotp_activity;
import com.vst.myapplication.databinding.RegisterBinding;

import java.util.List;
import java.util.Locale;

public class register_activity extends AppCompatActivity {
    RegisterBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.register);
        binding.edtdeviceid.setText(getUniqueID());
        binding.btnNext.setOnClickListener(v -> {
            startActivity(new Intent(register_activity.this, verifyotp_activity.class));
        });
    }

    public String getUniqueID() {
        String myAndroidDeviceId = "";
        TelephonyManager mTelephony = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (!TextUtils.isEmpty(mTelephony.getDeviceId())) {
            myAndroidDeviceId = mTelephony.getDeviceId();
        } else {
            myAndroidDeviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);//777817287dc5bc9
        }
        if (!TextUtils.isEmpty(myAndroidDeviceId))
            myAndroidDeviceId = myAndroidDeviceId.toUpperCase();
        return myAndroidDeviceId;
    }
//    @Override
//    public void onLocationChanged(@NonNull Location location) {
//
//        Toast.makeText(this, "" + location.getLatitude() + "," + location.getLongitude(), Toast.LENGTH_SHORT).show();
//
//        try {
//            Geocoder geocoder = new Geocoder(register_activity.this, Locale.getDefault());
//            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//            String address = addresses.get(0).getAddressLine(0);
//            binding.edtaddress.setText(address);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
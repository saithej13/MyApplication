package com.vst.myapplication.UI.Register;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.vst.myapplication.MainActivity;
import com.vst.myapplication.R;
import com.vst.myapplication.Services.ProjectRepository;
import com.vst.myapplication.UI.Login.LoginNew;
import com.vst.myapplication.UI.Login.newpassword_activity;
import com.vst.myapplication.UI.Login.verifyotp_activity;
import com.vst.myapplication.databinding.RegisterBinding;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class register_activity extends AppCompatActivity {
    RegisterBinding binding;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private String verificationId;
    private FirebaseAuth mAuth;
    ProjectRepository repository;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        repository = new ProjectRepository();
        binding = DataBindingUtil.setContentView(this, R.layout.register);
        binding.edtdeviceid.setText(getUniqueID());
        binding.btnNext.setOnClickListener(v -> {
            try {
                JsonObject payload = new JsonObject();
                payload.addProperty("USERNAME", binding.edtusername.getText().toString());
                payload.addProperty("PASSWORD", binding.edtpassword.getText().toString());
                payload.addProperty("MOBILENO", binding.edtMobileno.getText().toString());
                payload.addProperty("BRANCHNAME", binding.edtbranchname.getText().toString());
                payload.addProperty("ADDRESS", binding.edtaddress.getText().toString());
                payload.addProperty("DEVICEID", binding.edtdeviceid.getText().toString());
                repository.adduser(payload).observe(register_activity.this, new Observer<JsonObject>() {
                    @Override
                    public void onChanged(JsonObject jsonObject) {
                        if (jsonObject != null) {
                            Gson gson = new Gson();
                            if (jsonObject.get("code").toString().equals("200")) {
                                startActivity(new Intent(register_activity.this, LoginNew.class));
                                finish();
                            } else {
                                String message = "";
                                message = jsonObject.get("error").getAsString();
                                Toast.makeText(register_activity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
            catch (Exception e){
                e.printStackTrace();
            }
            finally {
//                startActivity(new Intent(register_activity.this, verifyotp_activity.class));
            }
        });
    }

//    public String getUniqueID() {
//        String myAndroidDeviceId = "";
//        TelephonyManager mTelephony = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//        if (!TextUtils.isEmpty(mTelephony.getDeviceId())) {
//            myAndroidDeviceId = mTelephony.getDeviceId();
//        } else {
//            myAndroidDeviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);//777817287dc5bc9
//        }
//        if (!TextUtils.isEmpty(myAndroidDeviceId))
//            myAndroidDeviceId = myAndroidDeviceId.toUpperCase();
//        return myAndroidDeviceId;
//    }
@SuppressLint("HardwareIds")
public String getUniqueID() {
    String myAndroidDeviceId = "";
    TelephonyManager mTelephony = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

    // Check for permission
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
        if (mTelephony != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                myAndroidDeviceId = mTelephony.getImei();
            } else {
                myAndroidDeviceId = mTelephony.getDeviceId();
            }
        }
    }

    // Fallback to ANDROID_ID
    if (TextUtils.isEmpty(myAndroidDeviceId)) {
        myAndroidDeviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    if (!TextUtils.isEmpty(myAndroidDeviceId)) {
        myAndroidDeviceId = myAndroidDeviceId.toUpperCase();
    }

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

    private void requestPhoneStatePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {
            // Show an explanation to the user
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                getUniqueID();
            } else {
                // Permission denied
            }
        }
    }

    public void Sendfirebasesms() {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
//                        .setPhoneNumber("+917780293187")
                        .setPhoneNumber("+91"+binding.edtMobileno.getText().toString())
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Log.d("ER",""+e);
            Toast.makeText(register_activity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken token) {
            super.onCodeSent(s, token);
            verificationId = s;
            Toast.makeText(register_activity.this, "OTP Sent", Toast.LENGTH_SHORT).show();
            // Move to the next screen to enter the OTP
        }
    };
    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }
    private void signInWithCredential(PhoneAuthCredential credential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(register_activity.this, "Verification successful", Toast.LENGTH_SHORT).show();
                            // Handle successful verification
                        } else {
                            Log.d("ER",""+task.getException().getMessage());
                            Toast.makeText(register_activity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}

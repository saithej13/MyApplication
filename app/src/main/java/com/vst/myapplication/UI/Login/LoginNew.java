package com.vst.myapplication.UI.Login;

import static java.security.AccessController.getContext;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.vst.myapplication.MainActivity;
import com.vst.myapplication.R;
import com.vst.myapplication.Services.ProjectRepository;
import com.vst.myapplication.UI.Dashboard.DashboardFragment;
import com.vst.myapplication.UI.Payments.payment;
import com.vst.myapplication.UI.Register.register_notice_activity;
import com.vst.myapplication.UI.onboarding.onboarding;
import com.vst.myapplication.Utils.BaseFragment;
import com.vst.myapplication.Utils.NetworkUtils;
import com.vst.myapplication.Utils.Preference;
import com.vst.myapplication.databinding.LoginBinding;
import com.vst.myapplication.databinding.RegisterNoticeBinding;

public class LoginNew extends AppCompatActivity {
    LoginBinding binding;
    String version ="";
    ProjectRepository repository;
    Preference preference;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.login);
        preference = new Preference(getApplicationContext());
        repository = new ProjectRepository();
        try {
            version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        binding.txtversion.setText("App Version  :  "+version);
//        binding.txtversion.setText(""+getUniqueID());
        binding.btnSignIn.setOnClickListener(v -> {
            if(NetworkUtils.isNetworkAvailable(this)) {
                try {
//                    preference.saveIntInPreference("BCODE",1);
//                    preference.commitPreference();
//                    startActivity(new Intent(LoginNew.this, MainActivity.class));
//                    finish();
                    showLoading(true);
                    JsonObject payload = new JsonObject();
                    payload.addProperty("MOBILENO", binding.edtUsername.getText().toString());
                    payload.addProperty("PASSWORD", binding.edtpassword.getText().toString());
//                    payload.addProperty("MOBILENO", "9705966305");
//                    payload.addProperty("PASSWORD", "1234");
                    payload.addProperty("DEVICEID", getUniqueID());
                    repository.getUser(payload).observe(this, new Observer<JsonObject>() {
                        @Override
                        public void onChanged(JsonObject jsonObject) {
                            if (jsonObject != null) {
                                //hideloading
                                showLoading(false);
                                Log.d("Login", "" + jsonObject.toString());
                                Log.d("code", "" + jsonObject.get("code").toString());
                                Gson gson = new Gson();
                                if(jsonObject.get("code").toString().equals("200")) {
                                    JsonArray jsonarray = gson.fromJson(jsonObject.getAsJsonArray("Data"), JsonArray.class);
                                    JsonObject jsondata = new JsonObject();
                                    if (jsonarray.size() > 0) {
                                        JsonElement firstElement = jsonarray.get(0);
                                        if (firstElement.isJsonObject()) {
                                            jsondata = firstElement.getAsJsonObject();
                                            preference.saveIntInPreference("BCODE",jsondata.get("USERID").getAsInt());
                                            preference.saveIntInPreference("USERID",jsondata.get("USERID").getAsInt());
                                            preference.saveStringInPreference("USERNAME",jsondata.get("USERNAME").getAsString());
                                            preference.saveStringInPreference("PASSWORD",jsondata.get("PASSWORD").getAsString());
                                            preference.saveStringInPreference("MOBILENO",jsondata.get("MOBILENO").getAsString());
                                            preference.saveStringInPreference("BRANCHNAME",jsondata.get("BRANCHNAME").getAsString());
                                            preference.saveStringInPreference("ADDRESS",jsondata.get("ADDRESS").getAsString());
                                            preference.saveStringInPreference("DEVICEID",jsondata.get("DEVICEID").getAsString());
                                            preference.saveStringInPreference("STARTDATE",jsondata.get("STARTDATE").getAsString());
                                            preference.saveStringInPreference("ENDDATE",jsondata.get("ENDDATE").getAsString());
                                            preference.saveBooleanInPreference("ISACTIVE",jsondata.get("ISACTIVE").getAsBoolean());
                                            preference.saveIntInPreference("ROLEID",jsondata.get("ROLEID").getAsInt());
                                            preference.saveBooleanInPreference("ISLOGGEDIN", true);
                                            preference.commitPreference();
                                            if(preference.getbooleanFromPreference("ISLOGGEDIN",false)) {
                                                startActivity(new Intent(LoginNew.this, MainActivity.class));
                                                finish();
                                            }
                                        }
                                    }
                                    Log.d("data",""+jsondata);
                                }
                                else{
                                    String message ="";
                                    message = jsonObject.get("error").getAsString();
                                    Toast.makeText(LoginNew.this,message,Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }
                catch (Exception e){
                    //hideloading
                    showLoading(false);
                    e.printStackTrace();
                }
            }
            else {
                Toast.makeText(this,"Network Error, Please check your internet connection",Toast.LENGTH_SHORT).show();
            }
        });
        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginNew.this, register_notice_activity.class));
            }
        });
        binding.btnForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginNew.this, forgetpassword_activity.class));
//                startActivity(new Intent(LoginNew.this, onboarding.class));
            }
        });
        binding.txtversion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginNew.this, onboarding.class));
//                startActivity(new Intent(LoginNew.this, payment.class));

            }
        });
    }

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Finish the activity and close the app
        finishAffinity();
    }
    private void showLoading(boolean isLoading) {
        if (isLoading) {
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.textView.setText("Loading...");
            binding.textView.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.GONE);
            binding.textView.setVisibility(View.GONE);
        }
    }
}

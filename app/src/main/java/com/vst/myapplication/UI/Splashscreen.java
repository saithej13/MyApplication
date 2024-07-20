package com.vst.myapplication.UI;

import static com.vst.myapplication.Utils.MyApplicationNew.mContext;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import com.vst.myapplication.MainActivity;
import com.vst.myapplication.R;
import com.vst.myapplication.UI.Login.LoginNew;
import com.vst.myapplication.UI.Register.register_notice_activity;
import com.vst.myapplication.UI.onboarding.onboarding;
import com.vst.myapplication.Utils.Preference;
import com.vst.myapplication.databinding.SplashscreenBinding;
import com.vst.myapplication.Utils.AppConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Splashscreen extends AppCompatActivity {
    SplashscreenBinding binding;
    Preference preference;
    private ProgressDialog mPDialog;
    private static final int REQUEST_CODE_SCAN = 1;
    String version = "";
    boolean ISAPPFIRSTLAUNCH = false;
    private static final int PERMISSION_REQUEST_CODE = 200;
    public static final String[] NORMAL_PERMISSIONS = {
            Manifest.permission.SYSTEM_ALERT_WINDOW,
            Manifest.permission.WRITE_SETTINGS,
//            "com.mediatek.permission.CTA_ENABLE_BT",
//            "com.google.android.providers.gsf.permission.READ_GSERVICES",
            Manifest.permission.READ_PHONE_STATE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.splashscreen);
//        preference= new SharedPreferences(getApplicationContext());
//        binding.btnscan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Splashscreen.this, MainActivity.class);
//                startActivityForResult(intent, REQUEST_CODE_SCAN);
//            }
//        });
        try {
            version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        preference = new Preference(getApplicationContext());
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        preference.saveIntInPreference(Preference.DEVICE_DISPLAY_WIDTH, displayMetrics.widthPixels);
        preference.saveIntInPreference(Preference.DEVICE_DISPLAY_HEIGHT, displayMetrics.heightPixels);

        preference.saveStringInPreference(Preference.Select_Printer, "Printer 1");
        preference.saveStringInPreference(Preference.Select_Printer_Size, "3");
        preference.saveStringInPreference(Preference.Select_Rate_collection_formate, "fat+snf+clr");
        preference.saveIntInPreference(Preference.Select_Bill_Period, 15);
        preference.commitPreference();


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
        ISAPPFIRSTLAUNCH = preference.getbooleanFromPreference("ISAPPFIRSTLAUNCH", true);
        Log.d("ISAPPFIRSTLAUNCH", "" + ISAPPFIRSTLAUNCH);
        new Handler().postDelayed(() -> {
//            Intent intent1 = new Intent(Splashscreen.this, MainActivity.class);
//            if(version.equals(serverversion))
            if (version.equals("1.0")) {
                if (ISAPPFIRSTLAUNCH) {
                    preference.saveBooleanInPreference("ISAPPFIRSTLAUNCH", false);
                    preference.commitPreference();
                    Intent intent1 = new Intent(Splashscreen.this, onboarding.class);
                    startActivity(intent1);
                    finish();
                } else {
                    Intent intent1 = new Intent(Splashscreen.this, LoginNew.class);
                    startActivity(intent1);
                    finish();
                }
            } else {
//                updateApp();
                updateApp update = new updateApp();
                update.setContext(Splashscreen.this);
                update.execute("https://raw.githubusercontent.com/saithej13/MyApplication/master/app-debug.apk");
            }
        }, 2000);
//        ArrayList<String> permissionsToRequest = getRequestPermissionList();
//        if (!permissionsToRequest.isEmpty()) {
//            // Request permissions using ActivityCompat.requestPermissions()
//            ActivityCompat.requestPermissions(this,
//                    permissionsToRequest.toArray(new String[0]),
//                    PERMISSION_REQUEST_CODE);
//        } else {
//            // All permissions are already granted, proceed with app logic
//
//        }

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

    private void requestPermission(ArrayList<String> permissionList) {

        if (permissionList.size() > 0)
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[0]), PERMISSION_REQUEST_CODE);
        else {
            //move to login/ register
        }
    }

    //    public ArrayList<String> getRequestPermissionList() {
//        ArrayList<String> permissions = new ArrayList<>();
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_PERMISSIONS);
//            if (info.requestedPermissions != null) {
//                for (String perm : info.requestedPermissions) {
//                    if (!Arrays.asList(NORMAL_PERMISSIONS).contains(perm)) {
//                        int result = ContextCompat.checkSelfPermission(getApplicationContext(), perm);
//                        if (result != PackageManager.PERMISSION_GRANTED) {
//                            permissions.add(perm);
//                        }
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return permissions;
//    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, final String permissions[], int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case PERMISSION_REQUEST_CODE:
//                int i = 0;
//                if (grantResults.length > 0) {
//
//                    boolean deviceLocation = false;
//                    for (int j = 0; j < grantResults.length; j++) {
//                        deviceLocation = grantResults[j] == PackageManager.PERMISSION_GRANTED;
//                        if (!deviceLocation) {
//                            i = j;
//                            break;
//                        }
//                    }
//                    if (deviceLocation) {
//                        //move to loign
//                        Toast.makeText(Splashscreen.this, "Permission Granted, Now you can access device data", Toast.LENGTH_LONG).show();
//                    } else {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                        builder.setTitle("Permission Denied")
//                        .setMessage("Permission Denied, You cannot access device data!")
//                                .setCancelable(false)
//                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        //do things
//                                        finishAffinity();
//                                    }
//                                });
//                        AlertDialog alert = builder.create();
//                        alert.show();
////                        Toast.makeText(Splashscreen.this, "Permission Denied, You cannot access device data", Toast.LENGTH_LONG).show();
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
////							if (shouldShowRequestPermissionRationale(String.valueOf(grantResults[i]))) {
////								showMessageOKCancel("You need to allow access to all the permissions",
////										new DialogInterface.OnClickListener() {
////											@Override
////											public void onClick(DialogInterface dialog, int which) {
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
////													requestPermissions(new String[]{READ_PHONE_STATE},PERMISSION_REQUEST_CODE);
//                                requestPermissions(permissions, PERMISSION_REQUEST_CODE);
//                            }
////											}
////										});
//                            return;
////							}
//                        }
//
//                    }
//                }
//
//
//                break;
//        }
//    }
    public class updateApp extends AsyncTask<String, Integer, String> {
        void setContext(Activity context) {
            mContext = context;
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mPDialog = new ProgressDialog(mContext);
                    mPDialog.setMessage("Downloading Update ,Please wait...");
                    mPDialog.setIndeterminate(true);
                    mPDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    mPDialog.setCancelable(false);
                    mPDialog.show();
                }
            });
        }

        @Override
        protected String doInBackground(String... arg0) {
            try {
                URL url = new URL(arg0[0]);
                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                c.setRequestMethod("GET");
                c.setDoOutput(true);
                c.connect();
                int lenghtOfFile = c.getContentLength();
                String PATH = Objects.requireNonNull(mContext.getExternalFilesDir(null)).getAbsolutePath();
                File file = new File(PATH);
                boolean isCreate = file.mkdirs();
                File outputFile = new File(file, "my_apk.apk");
                if (outputFile.exists()) {
                    boolean isDelete = outputFile.delete();
                }
                FileOutputStream fos = new FileOutputStream(outputFile);
                InputStream is = c.getInputStream();
                byte[] buffer = new byte[1024];
                int len1;
                long total = 0;
                while ((len1 = is.read(buffer)) != -1) {
                    total += len1;
                    fos.write(buffer, 0, len1);
                    publishProgress((int) ((total * 100) / lenghtOfFile));
                }
                fos.close();
                is.close();
                if (mPDialog != null)
                    mPDialog.dismiss();
                installApk();
            } catch (Exception e) {
                Log.e("UpdateAPP", "Update error! " + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (mPDialog != null)
                mPDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (mPDialog != null) {
                mPDialog.setIndeterminate(false);
                mPDialog.setMax(100);
                mPDialog.setProgress(values[0]);
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (mPDialog != null)
                mPDialog.dismiss();
            if (result != null)
                Toast.makeText(mContext, "Download error: " + result, Toast.LENGTH_LONG).show();
            else {
                Toast.makeText(mContext, "File Downloaded", Toast.LENGTH_SHORT).show();
                installApk();
            }
        }

        private void installApk() {
            try {
                String PATH = Objects.requireNonNull(mContext.getExternalFilesDir(null)).getAbsolutePath();
                File file = new File(PATH + "/my_apk.apk");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                if (Build.VERSION.SDK_INT >= 24) {
                    Uri downloaded_apk = FileProvider.getUriForFile(mContext, mContext.getApplicationContext().getPackageName() + ".provider", file);
                    intent.setDataAndType(downloaded_apk, "application/vnd.android.package-archive");
                    List<ResolveInfo> resInfoList = mContext.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                    for (ResolveInfo resolveInfo : resInfoList) {
                        mContext.grantUriPermission(mContext.getApplicationContext().getPackageName() + ".provider", downloaded_apk, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    }
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(intent);
                } else {
                    intent.setAction(Intent.ACTION_INSTALL_PACKAGE);
                    //intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
                    intent.putExtra(Intent.EXTRA_RETURN_RESULT, true);
                    intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

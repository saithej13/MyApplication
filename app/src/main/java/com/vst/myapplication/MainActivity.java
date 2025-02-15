package com.vst.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.vst.myapplication.Room.roomRepository;
import com.vst.myapplication.Services.ProjectRepository;
import com.vst.myapplication.UI.Dashboard.DashboardFragment;
import com.vst.myapplication.UI.Login.Login;
import com.vst.myapplication.UI.Payments.payment;
import com.vst.myapplication.Utils.AppConstants;
import com.vst.myapplication.Utils.BaseFragment;
import com.vst.myapplication.Utils.CustomDialog;
import com.vst.myapplication.Utils.MyApplicationNew;
import com.vst.myapplication.Utils.NTLMAuthenticator;
import com.vst.myapplication.Utils.Preference;
import com.vst.myapplication.dataObject.farmerDO;
import com.vst.myapplication.dataObject.milkDO;
import com.vst.myapplication.dataObject.userDO;
import com.vst.myapplication.databinding.ActivityMainBinding;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    ProjectRepository repository;
    private String verificationId;
    private FirebaseAuth mAuth;

    private FirebaseDatabase database;
    private DatabaseReference usersRef;
    DatabaseReference usersRef2;
    roomRepository roomrepo;
    private CaptureManager capture;
    private DecoratedBarcodeView barcodeScannerView;
    Preference preference;


    farmerDO farmerDo;
    //AIzaSyDvRze1B1XKqZqsYvzuDuAZiCH2x_dtezE       keyfirebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        MyApplicationNew.lifecycleOwner = this;
//        Log.d("String",""+returnstring());
//        getPrintSummaryslip();
        preference = new Preference(getApplicationContext());
        repository = new ProjectRepository();
//        barcodeScannerView = findViewById(R.id.zxing_barcode_scanner);
//        capture = new CaptureManager(this, barcodeScannerView);
//        capture.initializeFromIntent(getIntent(), savedInstanceState);
//        capture.decode();
//        barcodeScannerView.setStatusText("Place a QR code inside the viewfinder rectangle to scan it.");
//        barcodeScannerView.decodeContinuous(new BarcodeCallback() {
//            @Override
//            public void barcodeResult(BarcodeResult result) {
//                Intent resultIntent = new Intent();
//                resultIntent.putExtra("SCAN_RESULT", result.getText());
//                setResult(Activity.RESULT_OK, resultIntent);
//                finish();
//            }
//
//            @Override
//            public void possibleResultPoints(List<ResultPoint> resultPoints) {
//            }
//        });
//        roomrepo = new roomRepository(getApplication());
        database = FirebaseDatabase.getInstance();
        usersRef = database.getReference();
        usersRef2 = usersRef.child("users");
        mAuth = FirebaseAuth.getInstance();
//        preference.saveStorage(getBaseContext(), "RoomDB");
        preference.saveBooleanInPreference("RoomDB", true);
        if (preference.getbooleanFromPreference("RoomDB",true)) {
            MyApplicationNew.RoomDB = true;
        }
//        binding.dashboard.setOnClickListener(view -> {
//
//        });
//        binding.menu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//        testfirebasesms();
//        startSmsRetriever();

//        movetoLogin();//by kush
        movetoDashBoard();
        binding.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, payment.class);
                startActivity(intent);
//                verifyCode(binding.edtotp.getText().toString().trim());
//                test3();
//                tblmilkdata mdata= new tblmilkdata();
//                mdata.date="2024-03-03";
//                mdata.farmercode="1";
//                mdata.farmername="Tables";
//                mdata.mtype ="cow";
//                mdata.quantity = 1.0;
//                mdata.fat = 3.0;
//                mdata.snf =8.0;
//                mdata.rate = 30.0;
//                mdata.amount = 30.0;
//                roomrepo.insertmilkdata(mdata);

                if (MyApplicationNew.RoomDB) {
                    Log.d("PRINTDB", "ROOMDB");
                } else {
                    Log.d("PRINTDB", "API");
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {

//                        saveUser();
//                        test11();
//                        test1();
//                        test2();
//                        test12();
//                        test4();

                    }
                }).start();
            }
        });
//
//
//        connection.setRequestProperty("Authorization",credential);
//        .addHeader("soapAction", "urn:microsoft-dynamics-schemas/codeunit/GetTransList:ExportPurchaseReturnList")
//                .addHeader("Content-Type", "application/xml")


//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
    }

    public static String test1() {
        try {
            String userName = "GSAUser";
            String password = "G$@!ntPdt*&^%";
            String domain = "gagroup.local";

            HttpClient httpclient = new DefaultHttpClient();
            NTCredentials creds = new NTCredentials(userName, password, "", domain);
            Log.d("creds", "" + creds);
            ((AbstractHttpClient) httpclient).getCredentialsProvider().setCredentials(AuthScope.ANY, creds);

            HttpPost httppost = new HttpPost("http://10.10.0.104:7017/Grandiose_UAT00/WS/Grandiose%20Stores/Codeunit/GetTransList");

            String soapRequest = "<Soap:Envelope xmlns:Soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                    "   <Soap:Body>\n" +
                    "      <ExportPurchaseReturnList xmlns=\"urn:microsoft-dynamics-schemas/codeunit/GetTransList\">\n" +
                    "         <docType>PR</docType>\n" +
                    "         <docDT>2024-03-03</docDT>\n" +
                    "         <locCode>DBVHS</locCode>\n" +
                    "         <exportPurchaseReturnlistXML>\n" +
                    "            <PurchaseHeader xmlns=\"urn:microsoft-dynamics-nav/xmlports/x50068\">\n" +
                    "               <Type></Type>\n" +
                    "               <DocNo></DocNo>\n" +
                    "               <HeaderLocation></HeaderLocation>\n" +
                    "               <VendorNo></VendorNo>\n" +
                    "               <PostingDate></PostingDate>\n" +
                    "               <ExtDocNo></ExtDocNo>\n" +
                    "            </PurchaseHeader>\n" +
                    "         </exportPurchaseReturnlistXML>\n" +
                    "      </ExportPurchaseReturnList>\n" +
                    "   </Soap:Body>\n" +
                    "</Soap:Envelope>";
            String SOAP_ACTION = "urn:microsoft-dynamics-schemas/codeunit/GetTransList:ExportPurchaseReturnList";

            StringEntity input = new StringEntity(soapRequest, "UTF-8");
            input.setContentType("text/xml");
            httppost.setEntity(input);
            httppost.setHeader("soapAction", SOAP_ACTION);

            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                String s = EntityUtils.toString(entity);
                Log.d("response", "" + s);
//            return EntityUtils.toString(entity);
            }
        } catch (
                Exception e) {
            Log.e("NTLMAuthTask", "Error during NTLM authentication", e);
        }
        return null;
    }

//    @Override
//    public void onBackPressed() {
//        Fragment f = getSupportFragmentManager().findFragmentById(R.id.frame) ;
//        if(f instanceof DashboardFragment){
//            showCustomDialog(this, getResources().getString(R.string.warning), getResources().getString(R.string.do_you_want_to_logout), getResources().getString(R.string.Yes), getResources().getString(R.string.No), "logout");
//        }else{
//            super.onBackPressed();
//        }
//
//    }
//@Override
//public void onBackPressed() {
//    Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame);
//    if (fragment instanceof BaseFragment) {
//        boolean handled = ((BaseFragment) fragment).onBackPressed();
//        if (!handled) {
//            super.onBackPressed(); // If not handled by fragment, proceed with default back behavior
//        }
//    } else {
//        super.onBackPressed();
//    }
//}

    public void test12() {

        String userName = "GSAUser";
        String password = "G$@!ntPdt*&^%";
        String domain = "gagroup.local";

        // Base API endpoint URL
        String baseUrl = "http://10.10.0.104:7017/Grandiose_UAT00/WS/Grandiose%20Stores/Codeunit/GetTransList";
        try {
            // Create HttpUrl using the base URL
            HttpUrl.Builder urlBuilder = HttpUrl.parse(baseUrl)
                    .newBuilder()
                    .username(userName)
                    .password(password)


//                    .host(domain)
                    ;


            // Build the URL
            String endpointUrl = urlBuilder.build().toString();

            // Create URL object from the constructed URL string
            URL url = null;

            url = new URL(endpointUrl);


            // Open a connection to the URL using HttpURLConnection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set request method to GET
            connection.setRequestMethod("GET");

            // Read the response
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Print result
            System.out.println("Response:");
            System.out.println(response.toString());

            // Disconnect the HttpURLConnection
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String test2() {
        String userName = "GSAUser";
        String password = "G$@!ntPdt*&^%";
        String domain = "gagroup.local";
        NTCredentials creds = new NTCredentials(userName, password, "", domain);
        Log.d("creds", "" + creds);
        OkHttpClient client = new OkHttpClient.Builder()
                .authenticator(new NTLMAuthenticator("", userName, password, domain))
                .build();

        String soapRequest = "<Soap:Envelope xmlns:Soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "   <Soap:Body>\n" +
                "      <ExportPurchaseReturnList xmlns=\"urn:microsoft-dynamics-schemas/codeunit/GetTransList\">\n" +
                "         <docType>PR</docType>\n" +
                "         <docDT>2024-03-03</docDT>\n" +
                "         <locCode>DBVHS</locCode>\n" +
                "         <exportPurchaseReturnlistXML>\n" +
                "            <PurchaseHeader xmlns=\"urn:microsoft-dynamics-nav/xmlports/x50068\">\n" +
                "               <Type></Type>\n" +
                "               <DocNo></DocNo>\n" +
                "               <HeaderLocation></HeaderLocation>\n" +
                "               <VendorNo></VendorNo>\n" +
                "               <PostingDate></PostingDate>\n" +
                "               <ExtDocNo></ExtDocNo>\n" +
                "            </PurchaseHeader>\n" +
                "         </exportPurchaseReturnlistXML>\n" +
                "      </ExportPurchaseReturnList>\n" +
                "   </Soap:Body>\n" +
                "</Soap:Envelope>";
        String SOAP_ACTION = "urn:microsoft-dynamics-schemas/codeunit/GetTransList:ExportPurchaseReturnList";

        RequestBody body = RequestBody.create(soapRequest, MediaType.parse("text/xml; charset=utf-8"));
        Request request = new Request.Builder()
                .url("http://10.10.0.104:7017/Grandiose_UAT00/WS/Grandiose%20Stores/Codeunit/GetTransList")
                .post(body)
                .addHeader("Content-Type", "text/xml; charset=utf-8")
                .addHeader("soapAction", SOAP_ACTION)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // Handle response
            String s = response.body().toString();
            Log.d("response", "" + s);
            return response.body().string();
        } catch (IOException e) {
            Log.e("NTLMAuthTask", "Error during NTLM authentication", e);
        }
        return null;
    }

    public void test3() {
        try {
            String soapRequest = "<Soap:Envelope xmlns:Soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                    "   <Soap:Body>\n" +
                    "      <ExportPurchaseReturnList xmlns=\"urn:microsoft-dynamics-schemas/codeunit/GetTransList\">\n" +
                    "         <docType>PR</docType>\n" +
                    "         <docDT>2024-03-03</docDT>\n" +
                    "         <locCode>DBVHS</locCode>\n" +
                    "         <exportPurchaseReturnlistXML>\n" +
                    "            <PurchaseHeader xmlns=\"urn:microsoft-dynamics-nav/xmlports/x50068\">\n" +
                    "               <Type></Type>\n" +
                    "               <DocNo></DocNo>\n" +
                    "               <HeaderLocation></HeaderLocation>\n" +
                    "               <VendorNo></VendorNo>\n" +
                    "               <PostingDate></PostingDate>\n" +
                    "               <ExtDocNo></ExtDocNo>\n" +
                    "            </PurchaseHeader>\n" +
                    "         </exportPurchaseReturnlistXML>\n" +
                    "      </ExportPurchaseReturnList>\n" +
                    "   </Soap:Body>\n" +
                    "</Soap:Envelope>";
            String SOAP_ACTION = "urn:microsoft-dynamics-schemas/codeunit/GetTransList:ExportPurchaseReturnList";
            RequestBody body = RequestBody.create(soapRequest, MediaType.parse("text/xml; charset=utf-8"));
            repository.getTransList(body).observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    Log.d("reponseretro", "" + s);
                }
            });
        } catch (Exception ex) {
            Log.e("NTLMAuthTask", "Error during NTLM authentication", ex);
        }
    }

    public void testfirebasesms() {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
//                        .setPhoneNumber("+917780293187")
                        .setPhoneNumber("+919705966305")
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
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken token) {
            super.onCodeSent(s, token);
            verificationId = s;
            Toast.makeText(MainActivity.this, "OTP Sent", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(MainActivity.this, "Verification successful", Toast.LENGTH_SHORT).show();
                            // Handle successful verification
                        } else {
                            Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void startSmsRetriever() {
        SmsRetrieverClient client = SmsRetriever.getClient(this);
        Task<Void> task = client.startSmsRetriever();
        task.addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "SMS Retriever started", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Error starting SMS Retriever", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private final BroadcastReceiver smsBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (SmsRetriever.SMS_RETRIEVED_ACTION.equals(intent.getAction())) {
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    int status = extras.getInt(SmsRetriever.EXTRA_STATUS);
                    if (status == CommonStatusCodes.SUCCESS) {
                        String message = (String) extras.get(SmsRetriever.EXTRA_SMS_MESSAGE);
                        if (message != null) {
                            String code = parseOtp(message);
                            binding.edtotp.setText(code);
                            verifyCode(code);
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Error receiving SMS", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    };

    private String parseOtp(String message) {
        // This will depend on the format of the OTP SMS.
        // Here, assuming the OTP is 6 digits long.
        return message.replaceAll("[^0-9]", "");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(smsBroadcastReceiver);
//        capture.onDestroy();
    }

    private void saveUser() {
        String userId = usersRef2.push().getKey();
//        userDO user = new userDO(userId, "VADLURI SAITEJA", "PASSWORD","9705966305",1,1);
        userDO user = new userDO();
        user.userid = userId;
        user.name = "VADLURI SAITEJA";
        user.password = "PASSWORD";
        user.mobileno = "9705966305";
        user.role = 1;
        user.isactive = 1;
        usersRef2.child(userId).setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "User saved successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Failed to save user", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void movetoLogin() {
        Bundle mBundle = new Bundle();
        mBundle.putBoolean("isTitle", true);
        Login loginFragment = new Login();
        loginFragment.setArguments(mBundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .add(R.id.frame, loginFragment, "")
                .addToBackStack("")
                .commitAllowingStateLoss();

    }
    public void movetoDashBoard() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .add(R.id.frame,new DashboardFragment(), "")
                .addToBackStack("")
                .commitAllowingStateLoss();

    }

    @Override
    protected void onResume() {
        super.onResume();
//        capture.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        capture.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        capture.onSaveInstanceState(outState);
    }

    //    public static String test1() {
//        String userName = "GSAUser";
//        String password = "G$@!ntPdt*&^%";
//        String domain = "gagroup.local";
//
//        OkHttpClient client = new OkHttpClient.Builder()
//                .authenticator(new NTLMAuthenticator(userName, password, domain))
//                .build();
////	NTCredentials creds = new NTCredentials(userName, password, "", domain);
////	((AbstractHttpClientConnection) client).getCredentialsProvider().setCredentials(AuthScope.ANY, creds);
////	((AbstractHttpClient) client).getCredentialsProvider().setCredentials(AuthScope.ANY, creds);
//
//        String soapRequest = "<Soap:Envelope xmlns:Soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
//                "   <Soap:Body>\n" +
//                "      <ExportPurchaseReturnList xmlns=\"urn:microsoft-dynamics-schemas/codeunit/GetTransList\">\n" +
//                "         <docType>PR</docType>\n" +
//                "         <docDT>2024-03-03</docDT>\n" +
//                "         <locCode>DBVHS</locCode>\n" +
//                "         <exportPurchaseReturnlistXML>\n" +
//                "            <PurchaseHeader xmlns=\"urn:microsoft-dynamics-nav/xmlports/x50068\">\n" +
//                "               <Type></Type>\n" +
//                "               <DocNo></DocNo>\n" +
//                "               <HeaderLocation></HeaderLocation>\n" +
//                "               <VendorNo></VendorNo>\n" +
//                "               <PostingDate></PostingDate>\n" +
//                "               <ExtDocNo></ExtDocNo>\n" +
//                "            </PurchaseHeader>\n" +
//                "         </exportPurchaseReturnlistXML>\n" +
//                "      </ExportPurchaseReturnList>\n" +
//                "   </Soap:Body>\n" +
//                "</Soap:Envelope>";
//        String SOAP_ACTION = "urn:microsoft-dynamics-schemas/codeunit/GetTransList:ExportPurchaseReturnList";
//
//        RequestBody body = RequestBody.create(soapRequest, MediaType.parse("text/xml; charset=utf-8"));
//        Request request = new Request.Builder()
//                .url("http://10.10.0.104:7017/Grandiose_UAT00/WS/Grandiose%20Stores/Codeunit/GetTransList")
//                .post(body)
//                .addHeader("Content-Type", "text/xml; charset=utf-8")
//                .addHeader("soapAction", SOAP_ACTION)
//                .build();
//
//        try (Response response = client.newCall(request).execute()) {
//            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
//
//            // Handle response
//            Log.d("reponse",""+response.body().toString());
//            return response.body().string();
//
//        } catch (IOException e) {
//            Log.e("NTLMAuthTask", "Error during NTLM authentication", e);
//        }
//        return null;
//    }
//    public void test2(){
//        try {
//            String userName = "GSAUser";
//            String password = "G$@!ntPdt*&^%";
//            String domain = "gagroup.local";
//            String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
//            Log.d("DeviceID", "Device ID: " + deviceId);
//            String request = "\r\n<soapenv:Envelope " +
//                    "xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"" +
//                    " xmlns:get=\"urn:microsoft-dynamics-schemas/codeunit/GetTransList\" " +
//                    "xmlns:x50=\"urn:microsoft-dynamics-nav/xmlports/x50068\">\r\n   " +
//                    "<soapenv:Header/>\r\n   " +
//                    "<soapenv:Body>\r\n  " +
//                    "    <get:ExportPurchaseReturnList>\r\n " +
//                    "        <get:docType>PR</get:docType>\r\n  " +
//                    "       <get:docDT>2024-03-03</get:docDT>\r\n  " +
//                    "       <get:locCode>DBVHS</get:locCode>\r\n " +
//                    "        <get:exportPurchaseReturnlistXML>\r\n   " +
//                    "         <x50:PurchaseHeader>\r\n " +
//                    "              <x50:Type></x50:Type>\r\n  " +
//                    "             <x50:DocNo></x50:DocNo>\r\n   " +
//                    "            <x50:HeaderLocation></x50:HeaderLocation>\r\n  " +
//                    "             <x50:VendorNo></x50:VendorNo>\r\n    " +
//                    "           <x50:PostingDate></x50:PostingDate>\r\n  " +
//                    "             <x50:ExtDocNo></x50:ExtDocNo>\r\n   " +
//                    "         </x50:PurchaseHeader>\r\n     " +
//                    "    </get:exportPurchaseReturnlistXML>\r\n   " +
//                    "   </get:ExportPurchaseReturnList>\r\n " +
//                    "  </soapenv:Body>\r\n" +
//                    "</soapenv:Envelope>";
//
//            String request2 = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:get=\"urn:microsoft-dynamics-schemas/codeunit/GetTransList\" xmlns:x50=\"urn:microsoft-dynamics-nav/xmlports/x50068\">\r\n   <soapenv:Body>\r\n      <get:ExportPurchaseReturnList>\r\n         <get:docType>PR</get:docType>\r\n         <get:docDT>2024-03-03</get:docDT>\r\n         <get:locCode>DBVHS</get:locCode>\r\n         <get:exportPurchaseReturnlistXML>\r\n            <x50:PurchaseHeader>\r\n               <x50:Type></x50:Type>\r\n               <x50:DocNo></x50:DocNo>\r\n               <x50:HeaderLocation></x50:HeaderLocation>\r\n               <x50:VendorNo></x50:VendorNo>\r\n               <x50:PostingDate></x50:PostingDate>\r\n               <x50:ExtDocNo></x50:ExtDocNo>\r\n            </x50:PurchaseHeader>\r\n         </get:exportPurchaseReturnlistXML>\r\n      </get:ExportPurchaseReturnList>\r\n   </soapenv:Body>\r\n</soapenv:Envelope>";
//            RequestBody body = RequestBody.create(request2, MediaType.parse("text/xml; charset=utf-8"));
//            repository.getTransList(body).observe(this, new Observer<String>() {
//                @Override
//                public void onChanged(String s) {
//                    Log.d("PRINTBYSAI_NTLM",""+s);
//                }
//            });
//        } catch (
//                Exception e) {
//            Log.e("NTLMAuthTask", "Error during NTLM authentication", e);
//        }
//    }


    //    public void test4(){
//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(10, TimeUnit.SECONDS) // Set connection timeout
//                .build();
//
//        String url = "http://10.10.0.104:7017"; // Replace with your server URL
//
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//
//        try {
//            Response response = client.newCall(request).execute();
//            if (response.isSuccessful()) {
//                System.out.println("Request successful");
//                System.out.println(response.body().string());
//            } else {
//                System.out.println("Request failed: " + response.code() + " - " + response.message());
//            }
//        } catch (IOException e) {
//            System.err.println("Failed to make request: " + e.getMessage());
//        }
//    }
//    public void test5(){
//        String url = "https://your-server-url";
//        String username = "your-username";
//        String password = "your-password";
//        String domain = "your-domain";
//        String workstation = "your-workstation";
//
//        // Create NT credentials
//        NTCredentials ntCredentials = new NTCredentials(username, password, workstation, domain);
//
//        // Create the HttpClient with NTLM credentials
//        try (CloseableHttpClient httpClient = WinHttpClients.custom()
//                .setDefaultCredentialsProvider(credentialsProvider -> {
////                    credentialsProvider.setCredentials(new AuthScope(null, -1), ntCredentials);
//                    credentialsProvider.setCredentials(AuthScope.ANY, ntCredentials);
//                })
//                .build()) {
//
//            // Create the HTTP GET request
//            HttpGet httpGet = new HttpGet(url);
//
//            // Execute the request
//            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
//                if (response.getCode() == 200) {
//                    String responseBody = EntityUtils.toString(response.getEntity());
//                    System.out.println("Response: " + responseBody);
//                } else {
//                    System.out.println("Error: " + response.get);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    public static Bitmap getPrintSummaryslip(){
        File myDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/PrinterTest");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        int salesHeight = 1, freeGoodsHeight = 1, damageHeight = 1;
//            n = generator.nextInt(n);
        n = generator.nextInt((200 - 100) + 1) + 100;
//        String fname = "Image-" + n + ".jpg";
        String fname = "Image-" + 102 + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        Bitmap bmp = null;
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            int width = 460;
            int height = 900 + ((salesHeight + freeGoodsHeight + damageHeight) *30);
            bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            // NEWLY ADDED CODE STARTS HERE [
            Canvas canvas = new Canvas(bmp);
            Paint paintBG = new Paint();
            paintBG.setColor(Color.WHITE);
            canvas.drawRect(0, 0, width, height, paintBG);
            int het1 = 40;
            String[] as1 = null;
            String[] Header = null;
            int Headerh1 = 20+40;
//            String formateHeader1 	= "%1$-12.12s %2$-15.15s %3$-13.13s %4$-15.15s %5$-16.16s %6$-15.15s %7$-15.15s %8$-10.10s %9$-15.15s \r\n";
            String rightalign = "%1$12.12s";
            Header = new String[]{"Company Name","Branch Name","Collection CenterName","Village Name","Address","Contact","______________________________________________"};
            if (Header != null) {
                for (int i = 0; i < Header.length; i++) {
                    canvas.drawText(Header[i], ((width/2)-Header[i].length()),Headerh1, getPaintObjHeaderNew(22));
                    Headerh1 += 30;
                }
            }
//            as1  = getInner();

            int h1 = 20+Headerh1;
            if (as1 != null) {
                for (int i = 0; i < as1.length; i++) {
                    canvas.drawText(as1[i], 30,h1, getPaintObjHeaderNew(16));
                    h1 += 20;
                }
            }
            String[] Footer = null;
            int Footerf1 = h1+20;
            Footer = new String[]{"______________________________________________"};
            if (Footer != null) {
                for (int i = 0; i < Footer.length; i++) {
                    canvas.drawText(Footer[i], 30,Footerf1, getPaintObjHeaderNew(22));
                    Footerf1 += 30;
                }
            }
//            Paint paint = new Paint();
            String formateHeader1 	= "%1$-12.12s %2$-5.5s %3$-6.6s %4$-6.6s %5$-7.7s %6$-7.7s %7$-5.5s %8$-5.5s %9$-6.6s %10$-7.7s\r\n";
            canvas.drawText(String.format(formateHeader1,"TDATE","M/E","CODE","NAME","B/C","QTY","FAT","SNF","RATE","AMT"), 10,Footerf1+10, getPaintObjHeaderNew(14));
            canvas.drawText(String.format(formateHeader1,"01JUN2024","M","111","NAME123456789012345","BM","111.00","3.5","8.5","32.00","4800.00"), 10,Footerf1+25, getPaintObjHeaderNew(14));
            int footerheight=Footerf1+50;
            for(int i=0 ;i<5;i++){
                canvas.drawText(String.format(formateHeader1,"TDATE","M/E","CODE","NAME","B/C","QTY","FAT","SNF","RATE","AMT"), 10,footerheight, getPaintObjHeaderNew(14));
                footerheight+=20;
                canvas.drawText(String.format(formateHeader1,"01JUN2024","M","111","NAME123456789012345","BM","111.00","3.5","8.5","32.00","4800.00"), 10,footerheight, getPaintObjHeaderNew(14));
                footerheight+=20;
//                canvas.drawText(returnstring(), 10,footerheight, getPaintObjHeaderNew(14));
//                paint.measureText("TDATE") + paint.measureText(" ");
            }
//            canvas.drawText(String.format(rightalign,"Footer Text"),300,Footerf1+10,getPaintObjHeaderNew(22));
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bmp != null)
                bmp.recycle();
        }
        return BitmapFactory.decodeFile(file.getAbsolutePath());
    }
    private static Paint getPaintObjHeaderNew(int textsize) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK); // Text Color
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setStrokeWidth(12); // Text Size
        paint.setTypeface(Typeface.create(Typeface.DEFAULT_BOLD, Typeface.BOLD));
        paint.setTextSize(textsize);
        return paint;
    }
    private static String returnstring(){
        String s ="";
        String formateHeader1 	= "%1$-12.12s %2$-5.5s %3$-6.6s %4$-6.6s %5$-7.7s %6$-7.7s %7$-5.5s %8$-5.5s %9$-6.6s %10$-7.7s \r\n";
        s+=String.format(formateHeader1,"TDATE","M/E","CODE","NAME","B/C","QTY","FAT","SNF","RATE","AMT");
        s+=String.format(formateHeader1,"01JUN2024","M","111","NAME123456789012345","BM","111.00","3.5","8.5","32.00","4800.00");
        s+=String.format(formateHeader1,"TDATE","M/E","CODE","NAME","B/C","QTY","FAT","SNF","RATE","AMT");
        s+=String.format(formateHeader1,"01JUN2024","M","111","NAME123456789012345","BM","111.00","3.5","8.5","32.00","4800.00");
        return s;
    }
}
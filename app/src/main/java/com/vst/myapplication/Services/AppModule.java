package com.vst.myapplication.Services;

import android.util.Log;

import com.sun.jna.platform.win32.Netapi32Util;
import com.vst.myapplication.Utils.AppConstants;
import com.vst.myapplication.Utils.JcifsNtlmAuthenticator;
import com.vst.myapplication.Utils.NTLMAuthenticator;

import java.io.IOException;
import java.util.Base64;

import jcifs.smb.NtlmAuthenticator;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
//import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
//import retrofit2.converter.gson.GsonConverterFactory;

public class AppModule {
    private static Retrofit retrofit = null;
    private static final MediaType MEDIA_TYPE_XML = MediaType.parse("application/xml");
    public static synchronized Retrofit getClient() {

        if (retrofit == null) {

            String Username = "GSAUser";
            String Password = "G$@!ntPdt*&^%";
            String Domain = "gagroup.local";
//            String credentials = Domain + "\\" + Username + ":" + Password;
//            String credential="";
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                credential = "NTLM " + Base64.getEncoder().encodeToString(credentials.getBytes());
//            }
//            NtlmAuthenticator ntlmAuthenticator = new NtlmAuthenticator();
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    Request request = original.newBuilder()
//                            .header("Content-Type", "application/xml; charset=utf-8")
                            .header("Content-Type", "application/xml")
//                            .header("Content-Type", "text/xml; charset=utf-8")
//                            .header("soapAction", "urn:microsoft-dynamics-schemas/codeunit/GetTransList:ExportPurchaseReturnList")
//                            .header("Content-Type", MEDIA_TYPE_XML.toString())
//                            .header("Accept", MEDIA_TYPE_XML.toString())
//                            .header("Authorization", "NTLM Z2Fncm91cC5sb2NhbFxHU0FVc2VyOkckQCFudFBkdComXiU=")
                            .method(original.method(), original.body())
                            .build();
                    Log.e("AppModule."," "+request.method()+" "+request.url());
                    return chain.proceed(request);
                }
            });
            OkHttpClient client = httpClient.build();
//            OkHttpClient client = new OkHttpClient.Builder()
//                    .authenticator(new NTLMAuthenticator("GSAUser","G$@!ntPdt*&^%", "gagroup.local", ""))
//                    .build();
//            OkHttpClient client = new OkHttpClient.Builder()
//                    .authenticator(new JcifsNtlmAuthenticator(Username, Password, Domain))
//                    .build();


            retrofit = new Retrofit.Builder()
                    .baseUrl(AppConstants.BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }
}

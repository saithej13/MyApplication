package com.vst.myapplication.Utils;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.List;

import jcifs.ntlmssp.Type2Message;
import jcifs.ntlmssp.Type3Message;
import jcifs.smb.NtlmPasswordAuthentication;
import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import org.apache.commons.codec.binary.Base64;

public class NTLMAuthenticator implements Authenticator {
    private final String username;
    private final String password;
    private final String domain;
    public NTLMAuthenticator(String username, String password, String domain,String workstation) {
        this.username = username;
        this.password = password;
        this.domain = domain;
    }
    public okhttp3.Request authenticate(Route route, Response response) throws IOException {
//        if (response.request().header("Authorization") != null) {
//            return null; // Give up, we've already attempted to authenticate.
//        }
//
//        String ntlmType1Message = NtlmMessageGenerator.generateType1Message(domain);
//        Response type2Response = getType2Response(response, ntlmType1Message);
//
//        String ntlmType3Message = NtlmMessageGenerator.generateType3Message(type2Response, username, password, domain, workstation);
//
//        return response.request().newBuilder()
//                .header("Authorization", "NTLM " + ntlmType3Message)
//                .build();
        return null;
    }

    private Response getType2Response(Response response, String ntlmType1Message) throws IOException {
        // Your logic to handle the NTLM type 2 message retrieval
        // Implement as per your application's requirement
        // Example:
        // Request newRequest = response.request().newBuilder()
        //         .header("Authorization", "NTLM " + ntlmType1Message)
        //         .build();
        // return client.newCall(newRequest).execute();
        return null; // Placeholder for implementation
    }

}

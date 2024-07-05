package com.vst.myapplication.Utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.apache.commons.codec.binary.Base64;

import java.io.IOException;

import jcifs.CIFSContext;
import jcifs.CIFSException;
import jcifs.ntlmssp.Type1Message;
import jcifs.ntlmssp.Type2Message;
import jcifs.ntlmssp.Type3Message;
import jcifs.smb.NtlmContext;
import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class JcifsNtlmAuthenticator implements Authenticator {
    private String username;
    private String password;
    private String domain;
    public JcifsNtlmAuthenticator(String username, String password, String domain) {
        this.username = username;
        this.password = password;
        this.domain = domain;
    }
    @Nullable
    @Override
    public Request authenticate(@Nullable Route route, @NonNull Response response) throws IOException {
//        if (response.request().header("Authorization") != null) {
//            return null; // Give up, we've already attempted to authenticate.
//        }
//
//        String ntlmType1Message = NtlmMessageGenerator.generateType1Message(domain);
//        Response type2Response = getType2Response(response, ntlmType1Message);
//        if (type2Response == null) {
//            return null; // Handle the case where getType2Response returns null
//        }
//
//        String ntlmType3Message = NtlmMessageGenerator.generateType3Message(type2Response, username, password, domain);
//
//        return response.request().newBuilder()
//                .header("Authorization", "NTLM " + ntlmType3Message)
//                .build();
        return null;
    }
    private Response getType2Response(Response response, String ntlmType1Message) throws IOException {
        if (response.priorResponse() == null) {
            return null; // Handle the case where priorResponse is null
        }

        Request newRequest = response.request().newBuilder()
                .header("Authorization", "NTLM " + ntlmType1Message)
                .build();

        // Create a new response builder based on prior response or the current response
        Response.Builder builder = response.priorResponse() != null
                ? response.priorResponse().newBuilder()
                : response.newBuilder();

        return builder
                .code(401)
                .header("WWW-Authenticate", "NTLM " + ntlmType1Message)
                .build();
    }
}

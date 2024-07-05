package com.vst.myapplication.Utils;

import org.apache.commons.codec.binary.Base64;

import java.io.IOException;
import jcifs.CIFSContext;
import jcifs.ntlmssp.Type1Message;
import jcifs.ntlmssp.Type2Message;
import jcifs.ntlmssp.Type3Message;
import okhttp3.Response;

public class NtlmMessageGenerator {
//    public static String generateType1Message(String domain) {
//        Type1Message type1Message = new Type1Message(Type1Message.getDefaultFlags(), domain, null);
//        return Base64.encodeBase64String(type1Message.toByteArray());
//    }
//
//    public static String generateType3Message(Response response, String username, String password, String domain, String workstation) throws IOException {
//        String ntlmMessage2 = response.header("WWW-Authenticate").substring(5);
//        Type2Message type2Message = new Type2Message(Base64.decodeBase64(ntlmMessage2));
//        Type3Message type3Message = new Type3Message(type2Message, password.toCharArray(), domain, username, workstation);
//        return Base64.encodeBase64String(type3Message.toByteArray());
//    }
}

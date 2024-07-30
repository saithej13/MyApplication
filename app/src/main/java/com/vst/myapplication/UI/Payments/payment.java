package com.vst.myapplication.UI.Payments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;

import com.google.gson.JsonObject;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.vst.myapplication.R;
import com.vst.myapplication.Utils.BaseFragment;
import com.vst.myapplication.databinding.PaymentBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class payment extends AppCompatActivity implements PaymentResultListener {
    PaymentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.payment);
        binding.paybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Checkout checkout = new Checkout();
                //checkout.setKeyID("rzp_live_G8kp9PmzRPBsP6");
                //rzp_live_6wPsYHs1emTgGz
                checkout.setKeyID("rzp_live_ngEAJ3BMLzsRof");
//                checkout.setImage(R.drawable.logo);
                JSONObject object = new JSONObject();
                try {
                    object.put("name", "TEST");
                    object.put("description","Testing");
                    object.put("theme.color", "#528FF0");
                    object.put("currency", "INR");
                    object.put("amount", Math.round(Float.parseFloat("1") * 100));
                    object.put("prefill.contact", "9705966305");
                    object.put("prefill.email", "saithej.13@gmail.com");
                    checkout.open(payment.this, object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    public void onPaymentSuccess(String s) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(payment.this);
        dialog.setMessage(s);
        dialog.setTitle("Success, Payment ID");
        dialog.setPositiveButton("ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        return;
                    }
                });
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(payment.this);
        dialog.setMessage(s);
        dialog.setTitle("Error ,Payment Failed");
        dialog.setPositiveButton("ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        return;
                    }
                });
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }
}

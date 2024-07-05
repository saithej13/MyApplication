package com.vst.myapplication.Utils;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.vst.myapplication.R;

public class CustomDialog extends Dialog {
    boolean isCancellable = true;

    public CustomDialog(@NonNull Context context) {
        super(context);
    }

    public CustomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CustomDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public CustomDialog(Context context, View view, int lpW, int lpH, boolean isCancellable) {
        super(context, R.style.Dialog);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(view, new ViewGroup.LayoutParams(lpW, lpH));
        this.isCancellable = isCancellable;
    }

    public CustomDialog(Context context, View view, int lpW, int lpH, boolean isCancellable, int style) {
        super(context, R.style.Dialog);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(view, new ViewGroup.LayoutParams(lpW, lpH));
        this.isCancellable = isCancellable;
    }

    @Override
    public void onBackPressed() {
        if (isCancellable)
            super.onBackPressed();
    }

    @Override
    public void setCanceledOnTouchOutside(boolean cancel) {
        super.setCanceledOnTouchOutside(cancel);
    }

    public void showCustomDialog() {
        try {
            show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

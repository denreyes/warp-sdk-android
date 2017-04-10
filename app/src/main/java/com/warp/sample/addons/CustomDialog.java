package com.warp.sample.addons;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.StringRes;

public class CustomDialog {

    static ProgressDialog progressDialog;

    public static void showProgress(Context context, String text, Boolean cancelable, Boolean touchOutside) {
//        if (Build.VERSION.SDK_INT >= 23) {
//            progressDialog = new ProgressDialog(context, R.style.AppCompatAlertDialogStyle);
//        } else {
            progressDialog = new ProgressDialog(context);
//        }
        progressDialog.setMessage(text);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(cancelable);
        progressDialog.setCanceledOnTouchOutside(touchOutside);
        progressDialog.show();
    }

    public static void showProgress(Context context, @StringRes int text, Boolean cancelable, Boolean touchOutside) {
//        if (Build.VERSION.SDK_INT >= 23) {
//            progressDialog = new ProgressDialog(context, R.style.AppCompatAlertDialogStyle);
//        } else {
            progressDialog = new ProgressDialog(context);
//        }
        progressDialog.setMessage(context.getResources().getString(text));
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(cancelable);
        progressDialog.setCanceledOnTouchOutside(touchOutside);
        progressDialog.show();
    }

    public static void hideProgress() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

}
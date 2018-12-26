package me.jessyan.mvparms.wanandroid.app.utils;

import android.app.ProgressDialog;
import android.content.Context;

public class DialogUtils {
    private static ProgressDialog progressDialog;

    public static void showLoading(Context context){
        if (progressDialog == null)
            progressDialog = new ProgressDialog(context);
        progressDialog.show();
    }

    public static void hideLoading(){
        if (progressDialog != null)
            progressDialog.dismiss();
    }
}

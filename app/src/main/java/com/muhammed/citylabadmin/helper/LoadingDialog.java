package com.muhammed.citylabadmin.helper;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;

import com.muhammed.citylabadmin.R;

import java.util.zip.Inflater;

public class LoadingDialog {


    private static Dialog progress;

    public static void init(Activity activity) {
        progress = new Dialog(activity);
        progress.setContentView(R.layout.dialog_layout);
        progress.setCancelable(false);
        progress.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progress.getWindow().
                setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public static void showDialog(Activity activity) {
        init(activity);
        if (!(activity).isFinishing()) {
            //show dialog
            try {
                progress.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static Boolean isVisible(){

        try {
            return progress.isShowing();
        }catch (Exception e){
            return false;
        }

    }

    public static void hideDialog() {
        if (progress != null)
            progress.dismiss();
    }
}

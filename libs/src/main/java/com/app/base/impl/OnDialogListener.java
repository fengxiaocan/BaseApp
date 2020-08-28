package com.app.base.impl;

import android.app.Dialog;
import android.view.View;

public class OnDialogListener implements View.OnClickListener{
    private Dialog mDialog;

    public OnDialogListener(Dialog dialog){
        mDialog = dialog;
    }

    @Override
    public void onClick(View v){
        onClick(mDialog,v);
    }

    public void onClick(Dialog dialog, View view){
        try{
            if(dialog != null){
                dialog.dismiss();
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}

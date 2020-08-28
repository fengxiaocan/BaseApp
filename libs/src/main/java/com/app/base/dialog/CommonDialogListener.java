package com.app.base.dialog;

import android.content.DialogInterface;

public class CommonDialogListener implements DialogInterface.OnClickListener {
    protected boolean isDismiss ;

    public CommonDialogListener(boolean isDismiss) {
        this.isDismiss = isDismiss;
    }

    public CommonDialogListener() {
        isDismiss = true;
    }

    @Override
    public void onClick(DialogInterface dialog,int which) {
        if (isDismiss){
            dialog.dismiss();
        }
    }

}

package com.vlad.wdino.view.dialog;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public abstract class CustomDialogBase {

    public CustomDialogBase() {
    }

    protected abstract int getTitle();

    protected abstract int getConfirmMessage();

    protected abstract int getCancelMessage();

    protected abstract String getMessage();

    protected abstract int getIconResId();

    private AlertDialog.Builder dialogBuilder;
    private Dialog dialog;

    public void makeDialog(Context context) {
        makeDialog(context, null);
    }

    public void makeDialog(Context context, DialogInterface.OnClickListener confirmClickListener) {
        makeDialog(context, confirmClickListener, null);
    }

    public void makeDialog(Context context, DialogInterface.OnClickListener confirmClickListener, DialogInterface.OnClickListener cancelClickListener) {
        initDialog(context, confirmClickListener, cancelClickListener);

    }

    private AlertDialog.Builder initDialog(Context context, DialogInterface.OnClickListener confirmListener, DialogInterface.OnClickListener cancelListener) {
        dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle(context.getString(getTitle()));
        dialogBuilder.setMessage(getMessage());
        dialogBuilder.setIcon(getIconResId());
        if (confirmListener != null) {
            dialogBuilder.setPositiveButton(getConfirmMessage(), confirmListener);
        }
        if (cancelListener != null) {
            dialogBuilder.setNegativeButton(getCancelMessage(), cancelListener);
        }
        return dialogBuilder;
    }

    public void showDialog() {
        if (dialogBuilder != null) {
            dialog = dialogBuilder.create();
            dialog.show();
        }
    }

    public void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

}

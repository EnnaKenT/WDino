package com.vlad.wdino.view.dialog;


import android.content.Context;
import android.content.DialogInterface;

import com.vlad.wdino.R;

public class RegisterUserError extends CustomDialogBase {

    private String message;

    public RegisterUserError(Context context, String error, DialogInterface.OnClickListener confirmCallback) {
        super();
        message = error;
        makeDialog(context,confirmCallback);
    }

    @Override
    protected int getTitle() {
        return R.string.confirm_title;
    }

    @Override
    protected int getConfirmMessage() {
        return R.string.ok;
    }

    @Override
    protected int getCancelMessage() {
        return -1;
    }

    @Override
    protected int getMessage() {
        return -1;
    }

    @Override
    protected String getCustomMessage() {
        return message;
    }

    @Override
    protected int getIconResId() {
        return android.R.drawable.ic_dialog_alert;
    }

}

package com.vlad.wdino.view.dialog;

import android.content.Context;
import android.content.DialogInterface;

import com.vlad.wdino.R;

public class NoInternetAccessError extends CustomDialogBase {

    public NoInternetAccessError(Context context) {
        super();
        makeDialog(context);
    }
    @Override
    protected int getTitle() {
        return R.string.notify;
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
        return R.string.confirm_title;
    }

    @Override
    protected String getCustomMessage() {
        return null;
    }

    @Override
    protected int getIconResId() {
        return 0;
    }
}

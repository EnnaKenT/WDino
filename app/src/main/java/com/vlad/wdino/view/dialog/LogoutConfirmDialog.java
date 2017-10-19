package com.vlad.wdino.view.dialog;

import android.content.Context;
import android.content.DialogInterface;

import com.vlad.wdino.R;

public class LogoutConfirmDialog extends CustomDialogBase {

    public LogoutConfirmDialog(Context context, DialogInterface.OnClickListener confirmCallback, DialogInterface.OnClickListener cancelCallback) {
        super();
        makeDialog(context,confirmCallback, cancelCallback);
    }

    @Override
    protected int getTitle() {
        return R.string.confirm_title;
    }

    @Override
    protected int getConfirmMessage() {
        return R.string.yes;
    }

    @Override
    protected int getCancelMessage() {
        return R.string.cancel;
    }

    @Override
    protected int getMessage() {
        return R.string.logout_message;
    }

    @Override
    protected String getCustomMessage() {
        return null;
    }

    @Override
    protected int getIconResId() {
        return R.drawable.ic_question_answer_white_48dp;
    }

}

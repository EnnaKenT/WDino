package com.vlad.wdino.view.dialog;

import com.vlad.wdino.R;

public class NoInternetAccessError extends CustomDialogBase {
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
    protected String getMessage() {
        return null;
    }

    @Override
    protected int getIconResId() {
        return 0;
    }
}

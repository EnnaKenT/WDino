package com.vlad.wdino.background;

import android.util.Log;

import com.vlad.wdino.utils.Constants;

public abstract class DefaultBackgroundCallback<T> implements IBackgroundCallback<T> {
    @Override
    public void doOnError(Exception e) {
        Log.e(Constants.LOG_TAG, "Houston, we've got a problem:\n" + e.getMessage());
    }
}

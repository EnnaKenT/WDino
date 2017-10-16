package com.vlad.wdino.view.loader;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.vlad.wdino.R;

class Loader extends FrameLayout {

    public Loader(Context context) {
        this(context, null);
    }

    public Loader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Loader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflate(context, R.layout.loader, this);
    }







}

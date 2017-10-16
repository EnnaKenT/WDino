package com.vlad.wdino.view.loader;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;

import com.vlad.wdino.WDinoApp;

public class LoaderManager {

    private static Loader currentLoader;

    public static void show() {
        if (currentLoader == null) {
            Activity currentActivity = WDinoApp.getCurrentActivity();
            currentLoader = new Loader(currentActivity);
            ViewGroup rootView = (ViewGroup) currentActivity.findViewById(android.R.id.content);
            rootView.addView(currentLoader);
        }
    }

    public static void hide() {
        if (currentLoader != null) {
            Context context = currentLoader.getContext();
            final ViewGroup rootView = (ViewGroup) ((Activity) context).findViewById(android.R.id.content);
            rootView.removeView(currentLoader);
            currentLoader = null;
        }
    }
}

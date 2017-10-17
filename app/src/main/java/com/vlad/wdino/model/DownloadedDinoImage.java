package com.vlad.wdino.model;

import android.graphics.Bitmap;

abstract class DownloadedDinoImage {
    private Bitmap image;

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}

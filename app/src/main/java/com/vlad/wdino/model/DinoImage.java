package com.vlad.wdino.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DinoImage extends DownloadedDinoImage{

    @SerializedName("src")
    @Expose
    private String src;
    @SerializedName("alt")
    @Expose
    private String alt;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

}
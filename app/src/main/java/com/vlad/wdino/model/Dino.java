package com.vlad.wdino.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dino {

    @SerializedName("dino")
    @Expose
    private Dino_ dino;

    public Dino_ getDino() {
        return dino;
    }

    public void setDino(Dino_ dino) {
        this.dino = dino;
    }

}
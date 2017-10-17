package com.vlad.wdino.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dinos {

    @SerializedName("dinos")
    @Expose
    private List<Dino> dinos = null;

    public List<Dino> getDinos() {
        return dinos;
    }

    public void setDinos(List<Dino> dinos) {
        this.dinos = dinos;
    }

}
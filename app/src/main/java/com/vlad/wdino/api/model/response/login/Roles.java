package com.vlad.wdino.api.model.response.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Roles {

    @SerializedName("2")
    @Expose
    private String _2;

    public String get2() {
        return _2;
    }

    public void set2(String _2) {
        this._2 = _2;
    }

}
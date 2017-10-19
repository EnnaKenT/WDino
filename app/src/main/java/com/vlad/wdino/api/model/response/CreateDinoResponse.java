package com.vlad.wdino.api.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateDinoResponse {

    @SerializedName("nid")
    @Expose
    private String nid;
    @SerializedName("uri")
    @Expose
    private String uri;

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

}
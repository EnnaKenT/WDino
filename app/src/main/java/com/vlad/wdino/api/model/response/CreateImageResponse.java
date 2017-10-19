package com.vlad.wdino.api.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateImageResponse {

    @SerializedName("fid")
    @Expose
    private String fid;
    @SerializedName("uri")
    @Expose
    private String uri;

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

}
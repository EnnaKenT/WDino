package com.vlad.wdino.api.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vlad.wdino.api.model.formErrors.Errors;

public class RegisterUserResponse extends Errors {

    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("uri")
    @Expose
    private String uri;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

}
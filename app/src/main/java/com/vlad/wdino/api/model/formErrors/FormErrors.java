package com.vlad.wdino.api.model.formErrors;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FormErrors {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mail")
    @Expose
    private String mail;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

}

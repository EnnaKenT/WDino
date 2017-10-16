package com.vlad.wdino.api.model.formErrors;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Errors {

    @SerializedName("form_errors")
    @Expose
    private FormErrors formErrors;

    public FormErrors getFormErrors() {
        return formErrors;
    }

    public void setFormErrors(FormErrors formErrors) {
        this.formErrors = formErrors;
    }

}
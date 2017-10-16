package com.vlad.wdino.api.model.response.login;

abstract class Error {
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

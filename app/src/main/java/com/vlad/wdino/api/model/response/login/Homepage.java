package com.vlad.wdino.api.model.response.login;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Homepage {

    @SerializedName("predicates")
    @Expose
    private List<String> predicates = null;
    @SerializedName("type")
    @Expose
    private String type;

    public List<String> getPredicates() {
        return predicates;
    }

    public void setPredicates(List<String> predicates) {
        this.predicates = predicates;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
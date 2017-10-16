package com.vlad.wdino.api.model.response.login;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RdfMapping {

    @SerializedName("rdftype")
    @Expose
    private List<String> rdftype = null;
    @SerializedName("name")
    @Expose
    private Name name;
    @SerializedName("homepage")
    @Expose
    private Homepage homepage;

    public List<String> getRdftype() {
        return rdftype;
    }

    public void setRdftype(List<String> rdftype) {
        this.rdftype = rdftype;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Homepage getHomepage() {
        return homepage;
    }

    public void setHomepage(Homepage homepage) {
        this.homepage = homepage;
    }

}
package com.vlad.wdino.api.model.response.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("mail")
    @Expose
    private String mail;
    @SerializedName("theme")
    @Expose
    private String theme;
    @SerializedName("signature")
    @Expose
    private String signature;
    @SerializedName("signature_format")
    @Expose
    private String signatureFormat;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("access")
    @Expose
    private String access;
    @SerializedName("login")
    @Expose
    private Integer login;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("timezone")
    @Expose
    private Object timezone;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("picture")
    @Expose
    private String picture;
    @SerializedName("data")
    @Expose
    private Boolean data;
    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("roles")
    @Expose
    private Roles roles;
    @SerializedName("rdf_mapping")
    @Expose
    private RdfMapping rdfMapping;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

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

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSignatureFormat() {
        return signatureFormat;
    }

    public void setSignatureFormat(String signatureFormat) {
        this.signatureFormat = signatureFormat;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public Integer getLogin() {
        return login;
    }

    public void setLogin(Integer login) {
        this.login = login;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getTimezone() {
        return timezone;
    }

    public void setTimezone(Object timezone) {
        this.timezone = timezone;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Boolean getData() {
        return data;
    }

    public void setData(Boolean data) {
        this.data = data;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public RdfMapping getRdfMapping() {
        return rdfMapping;
    }

    public void setRdfMapping(RdfMapping rdfMapping) {
        this.rdfMapping = rdfMapping;
    }

}
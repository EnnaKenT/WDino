package com.vlad.wdino.api.model.playload;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateImagePlayload {
    @SerializedName("filename")
    @Expose
    private String filename;
    @SerializedName("target_uri")
    @Expose
    private String targetUri;
    @SerializedName("filemime")
    @Expose
    private String filemime;
    @SerializedName("file")
    @Expose
    private String file;
    @SerializedName("filesize")
    @Expose
    private String filesize;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getTargetUri() {
        return targetUri;
    }

    public void setTargetUri(String targetUri) {
        this.targetUri = targetUri;
    }

    public String getFilemime() {
        return filemime;
    }

    public void setFilemime(String filemime) {
        this.filemime = filemime;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

}

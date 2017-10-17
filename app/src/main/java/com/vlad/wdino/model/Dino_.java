package com.vlad.wdino.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dino_ {

    @SerializedName("dino_title")
    @Expose
    private String dinoTitle;
    @SerializedName("dino_color")
    @Expose
    private String dinoColor;
    @SerializedName("dino_birthdate")
    @Expose
    private String dinoBirthdate;
    @SerializedName("dino_image")
    @Expose
    private DinoImage dinoImage;
    @SerializedName("dino_about")
    @Expose
    private String dinoAbout;

    public String getDinoTitle() {
        return dinoTitle;
    }

    public void setDinoTitle(String dinoTitle) {
        this.dinoTitle = dinoTitle;
    }

    public String getDinoColor() {
        return dinoColor;
    }

    public void setDinoColor(String dinoColor) {
        this.dinoColor = dinoColor;
    }

    public String getDinoBirthdate() {
        return dinoBirthdate;
    }

    public void setDinoBirthdate(String dinoBirthdate) {
        this.dinoBirthdate = dinoBirthdate;
    }

    public DinoImage getDinoImage() {
        return dinoImage;
    }

    public void setDinoImage(DinoImage dinoImage) {
        this.dinoImage = dinoImage;
    }

    public String getDinoAbout() {
        return dinoAbout;
    }

    public void setDinoAbout(String dinoAbout) {
        this.dinoAbout = dinoAbout;
    }

}
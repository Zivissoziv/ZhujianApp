package com.example.ziv.zhujiandemo.Models;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * 菜谱的实体类
 * 为了方便在不同页面进行传输，实现Serializable接口
 */

public class Recipe implements Serializable {
    private String Title;
    private String Description;
    private String Image;



    private String Categorie;
    private boolean isSlide;
    private boolean isRecommend;
    private ArrayList<String> Material;
    private ArrayList<String> StepDescription;
    private ArrayList<String> StepImg;


    public Recipe() {
    }


    @Override
    public String toString() {
        return "Recipe{" +
                "Title='" + Title + '\'' +
                ", Description='" + Description + '\'' +
                ", Image='" + Image + '\'' +
                ", Categorie='" + Categorie + '\'' +
                ", isSlide=" + isSlide +
                ", isRecommend=" + isRecommend +
                ", Material=" + Material +
                ", StepDescription=" + StepDescription +
                ", StepImg=" + StepImg +
                '}';
    }

    public String getImage() {
        return Image;
    }

    public String getTitle() {
        return Title;
    }

    public String getDescription() {
        return Description;
    }

    public void setImage(String image) {
        Image = image;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public boolean isSlide() {
        return isSlide;
    }

    public void setSlide(boolean slide) {
        isSlide = slide;
    }

    public boolean isRecommend() {
        return isRecommend;
    }

    public void setRecommend(boolean recommend) {
        isRecommend = recommend;
    }

    public ArrayList<String> getMaterial() {
        return Material;
    }

    public void setMaterial(ArrayList<String> material) {
        Material = material;
    }

    public String getCategorie() {
        return Categorie;
    }

    public void setCategorie(String categorie) {
        Categorie = categorie;
    }

    public ArrayList<String> getStepDescription() {
        return StepDescription;
    }

    public void setStepDescription(ArrayList<String> stepDescription) {
        StepDescription = stepDescription;
    }

    public ArrayList<String> getStepImg() {
        return StepImg;
    }

    public void setStepImg(ArrayList<String> stepImg) {
        StepImg = stepImg;
    }
    // Add more field depand on whay you wa&nt ...




}

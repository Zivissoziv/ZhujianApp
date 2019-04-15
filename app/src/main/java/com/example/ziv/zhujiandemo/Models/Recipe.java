package com.example.ziv.zhujiandemo.Models;

import java.util.ArrayList;


/**
 * 菜谱的实体类
 */

public class Recipe {
    private int Image ;
    private String Title;
    private String Description;
    private String Photo;



    private String Categorie;
    private boolean isSlide;
    private boolean isRecommend;
    private ArrayList<String> Material;


    // Add more field depand on whay you wa&nt ...


    public Recipe() {
    }

    public Recipe(int image, String title) {
        Image = image;
        Title = title;
    }

    public Recipe(int image, String title,String description) {
        Image = image;
        Title = title;
        Description = description;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                ", Title='" + Title + '\'' +
                ", Description='" + Description + '\'' +
                ", Photo='" + Photo + '\'' +
                ", img='" + Image + '\'' +
                ", isSlide"+isSlide+'\''+
                ", isRecommend"+isRecommend+
                '}';
    }

    public int getImage() {
        return Image;
    }

    public String getTitle() {
        return Title;
    }

    public String getDescription() {
        return Description;
    }

    public void setImage(int image) {
        Image = image;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
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


}

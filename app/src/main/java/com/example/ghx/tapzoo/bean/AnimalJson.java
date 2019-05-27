package com.example.ghx.tapzoo.bean;

/**
 * Created by ghx on 2019/4/1.
 * 动物Json数据
 */

public class AnimalJson {

    private String animalname;
    private String picture_url;
    private String description;

    public AnimalJson(String animalname, String picture_url, String description) {
        this.animalname = animalname;
        this.picture_url = picture_url;
        this.description = description;
    }

    public String getAnimalname() {
        return animalname;
    }

    public void setAnimalname(String animalname) {
        this.animalname = animalname;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

package com.example.ghx.tapzoo.bean;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;
import com.example.ghx.tapzoo.utils.Config;

/**
 * Created by ghx on 2019/5/25.
 * 子类化Animal
 */

@AVClassName("Animal")
public class Animal extends AVObject {

    public static final Creator CREATOR = AVObjectCreator.instance;

    public Animal() {

    }


    public String getUserId() {
        return getString(Config.UserId);
    }

    public void setUserId(String userId) {
        put(Config.UserId, userId);
    }

    public String getAnimalname() {
        return getString(Config.Animalname);
    }

    public void setAnimalname(String animalname) {
        put(Config.Animalname, animalname);
    }

    public String getAnimalpic() {
        return getString(Config.Animalpic);
    }

    public void setAnimalpic(String animalpic) {
        put(Config.Animalpic, animalpic);
    }

    public String getAnimaldesc() {
        return getString(Config.Animaldesc);
    }

    public void setAnimaldesc(String animaldesc) {
        put(Config.Animaldesc, animaldesc);
    }
}

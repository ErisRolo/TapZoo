package com.example.ghx.tapzoo.bean;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVUser;
import com.example.ghx.tapzoo.utils.Config;

import java.util.List;

/**
 * Created by ghx on 2019/3/31.
 * 子类化User
 */

@AVClassName("User")
public class User extends AVUser {

/*    private String avatar;//头像
    private String nickname;//昵称
    private String desc;//简介*/

    public String getAvatar() {
        return this.getString(Config.AVATAR);
    }

    public void setAvatar(String avatar) {
        this.put(Config.AVATAR, avatar);
    }

    public String getNickname() {
        return this.getString(Config.NICKNAME);
    }

    public void setNickname(String nickname) {
        this.put(Config.NICKNAME, nickname);
    }

    public String getDesc() {
        return this.getString(Config.DESC);
    }

    public void setDesc(String desc) {
        this.put(Config.DESC, desc);
    }

    public List<String> getLikeAnimal() {
        return this.getList(Config.LIKEANIMAL);
    }

    public void setLikeAnimal(List<String> likeAlbum) {
        this.put(Config.LIKEANIMAL, likeAlbum);
    }

    public List<String> getBookmarkAnimal() {
        return this.getList(Config.BOOKMARKANIMAL);
    }

    public void setBookmarkAnimal(List<String> bookmarkAlbum) {
        this.put(Config.BOOKMARKANIMAL, bookmarkAlbum);
    }
}

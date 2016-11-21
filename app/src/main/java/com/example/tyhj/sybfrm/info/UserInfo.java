package com.example.tyhj.sybfrm.info;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by _Tyhj on 2016/8/3.
 */
public class UserInfo implements Serializable{
    String id,url, name, email, signature,reputation,blog,github,password;
    String tags[];

    public UserInfo(String id, String url, String name, String email, String signature,String reputation,String blog,String github) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.email = email;
        this.signature = signature;
        this.reputation=reputation;
        this.blog=blog;
        this.github=github;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getReputation() {
        return reputation;
    }

    public void setReputation(String reputation) {
        this.reputation = reputation;
    }

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", signature='" + signature + '\'' +
                ", reputation='" + reputation + '\'' +
                ", blog='" + blog + '\'' +
                ", github='" + github + '\'' +
                ", password='" + password + '\'' +
                ", tags=" + Arrays.toString(tags) +
                '}';
    }
}

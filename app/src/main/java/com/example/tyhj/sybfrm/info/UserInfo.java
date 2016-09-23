package com.example.tyhj.sybfrm.info;

/**
 * Created by _Tyhj on 2016/8/3.
 */
public class UserInfo {
    String id,password,url, name, email, signature;

    @Override
    public String toString() {
        return "UserInfo{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }

    public UserInfo(String id, String password, String url, String name, String email, String signature) {
        this.id = id;
        this.password = password;
        this.url = url;
        this.name = name;
        this.email = email;
        this.signature = signature;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}

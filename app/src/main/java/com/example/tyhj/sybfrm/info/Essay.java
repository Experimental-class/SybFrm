package com.example.tyhj.sybfrm.info;

/**
 * Created by Tyhj on 2016/10/3.
 */

public class Essay {
    private String userHeadImageUrl,userName,essayImageUrl,essayTitle,essayBody,agree,collect,remark,time,id;

    public Essay(String userHeadImageUrl, String userName, String essayImageUrl, String essayTitle, String essayBody, String agree, String collect, String remark, String time, String id) {
        this.userHeadImageUrl = userHeadImageUrl;
        this.userName = userName;
        this.essayImageUrl = essayImageUrl;
        this.essayTitle = essayTitle;
        this.essayBody = essayBody;
        this.agree = agree;
        this.collect = collect;
        this.remark = remark;
        this.time = time;
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserHeadImageUrl() {
        return userHeadImageUrl;
    }

    public void setUserHeadImageUrl(String userHeadImageUrl) {
        this.userHeadImageUrl = userHeadImageUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEssayImageUrl() {
        return essayImageUrl;
    }

    public void setEssayImageUrl(String essayImageUrl) {
        this.essayImageUrl = essayImageUrl;
    }

    public String getEssayTitle() {
        return essayTitle;
    }

    public void setEssayTitle(String essayTitle) {
        this.essayTitle = essayTitle;
    }

    public String getEssayBody() {
        return essayBody;
    }

    public void setEssayBody(String essayBody) {
        this.essayBody = essayBody;
    }

    public String getAgree() {
        return agree;
    }

    public void setAgree(String agree) {
        this.agree = agree;
    }

    public String getCollect() {
        return collect;
    }

    public void setCollect(String collect) {
        this.collect = collect;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

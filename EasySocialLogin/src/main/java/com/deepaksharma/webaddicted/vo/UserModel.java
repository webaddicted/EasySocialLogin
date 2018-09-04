package com.deepaksharma.webaddicted.vo;

/**
 * Created by deepaksharma on 22/8/18.
 */

public class UserModel {
    public String uId;
    public String access_token;
    public String name;
    public String emailId;
    public String image;
    public String other;
    public String birthday;

    public UserModel(String uId, String access_token, String name, String emailId, String image, String other, String birthday) {
        this.uId = uId;
        this.access_token = access_token;
        this.name = name;
        this.emailId = emailId;
        this.image = image;
        this.other = other;
        this.birthday = birthday;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}

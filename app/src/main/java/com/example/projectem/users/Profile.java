package com.example.projectem.users;

import android.widget.ImageView;

public class Profile {
    private String username;
    private String email;
    private String id;
    private String carnumber;
    private String phone;
    private String image;
    private String point;

    public Profile() {
    }
    public Profile(String username, String email, String id, String carnumber, String phone, String image,String point) {
        this.username = username;
        this.email = email;
        this.id = id;
        this.carnumber = carnumber;
        this.phone = phone;
        this.image = image;
        this.point=point;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String name) {
        this.email = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCarnumber() {
        return carnumber;
    }

    public void setCarnumber(String carnumber) {
        this.carnumber = carnumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", id='" + id + '\'' +
                ", carnumber='" + carnumber + '\'' +
                ", phone='" + phone + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}

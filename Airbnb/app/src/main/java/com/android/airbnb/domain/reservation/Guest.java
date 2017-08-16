package com.android.airbnb.domain.reservation;

/**
 * Created by JunHee on 2017. 8. 16..
 */

public class Guest {
    private String birthday;

    private String preference_language;

    private String introduce;

    private String living_site;

    private String last_login;

    private String preference_currency;

    private String first_name;

    private String username;

    private String date_joined;

    private String email;

    private String img_profile;

    private String phone_num;

    private String last_name;

    private String gender;

    private String pk;

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPreference_language() {
        return preference_language;
    }

    public void setPreference_language(String preference_language) {
        this.preference_language = preference_language;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getLiving_site() {
        return living_site;
    }

    public void setLiving_site(String living_site) {
        this.living_site = living_site;
    }

    public String getLast_login() {
        return last_login;
    }

    public void setLast_login(String last_login) {
        this.last_login = last_login;
    }

    public String getPreference_currency() {
        return preference_currency;
    }

    public void setPreference_currency(String preference_currency) {
        this.preference_currency = preference_currency;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate_joined() {
        return date_joined;
    }

    public void setDate_joined(String date_joined) {
        this.date_joined = date_joined;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImg_profile() {
        return img_profile;
    }

    public void setImg_profile(String img_profile) {
        this.img_profile = img_profile;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    @Override
    public String toString() {
        return "ClassPojo [birthday = " + birthday + ", preference_language = " + preference_language + ", introduce = " + introduce + ", living_site = " + living_site + ", last_login = " + last_login + ", preference_currency = " + preference_currency + ", first_name = " + first_name + ", username = " + username + ", date_joined = " + date_joined + ", email = " + email + ", img_profile = " + img_profile + ", phone_num = " + phone_num + ", last_name = " + last_name + ", gender = " + gender + ", pk = " + pk + "]";
    }
}
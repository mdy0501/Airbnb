package com.android.airbnb.domain.airbnb;

/**
 * Created by MDY on 2017-08-07.
 */

public class SignUpData {
    private String email;               // 아이디대신 이메일 주소 사용 (필수)
    private String password1;
    private String passwrod2;           // 확인용 비밀번호
    private String first_name;
    private String last_name;
    private String birthday;            // 1989-05-01
    private Boolean agreement = true;

    public SignUpData(){

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPasswrod2() {
        return passwrod2;
    }

    public void setPasswrod2(String passwrod2) {
        this.passwrod2 = passwrod2;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Boolean getAgreement() {
        return agreement;
    }

    public void setAgreement(Boolean agreement) {
        this.agreement = agreement;
    }
}

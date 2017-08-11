package com.android.airbnb.domain.airbnb;

/**
 * Created by MDY on 2017-08-08.
 */

public class LoginResult {
    private String token;
    private String user_pk;     // primary key
    private String user_name;   // email

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPrimaryKey() {
        return user_pk;
    }

    public void setPrimaryKey(String user_pk) {
        this.user_pk = user_pk;
    }

    public String getEmail() {
        return user_name;
    }

    public void setEmail(String user_name) {
        this.user_name = user_name;
    }

    @Override
    public String toString() {
        return "LoginResult{" +
                "token='" + token + '\'' +
                ", primaryKey='" + user_pk + '\'' +
                ", email='" + user_name + '\'' +
                '}';
    }
}

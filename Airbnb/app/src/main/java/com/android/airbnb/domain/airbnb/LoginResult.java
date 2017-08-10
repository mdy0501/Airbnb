package com.android.airbnb.domain.airbnb;

/**
 * Created by MDY on 2017-08-08.
 */

public class LoginResult {
    public String token;
    public String primaryKey;
    public String email;

    @Override
    public String toString() {
        return "LoginResult{" +
                "token='" + token + '\'' +
                ", primaryKey='" + primaryKey + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

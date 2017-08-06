package com.android.airbnb.papago.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JunHee on 2017. 8. 6..
 */

public class Message {

    // field 값 다른 이름으로 파싱할 때 @SerializedName 어노테이션을 사용한다.
    @SerializedName("@service")
    private String service;

    @SerializedName("@type")
    private String type;

    @SerializedName("@version")
    private String version;

    private Result result;

    public String getService() {
        return service;
    }

    public String getType() {
        return type;
    }

    public String getVersion() {
        return version;
    }

    public Result getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "Message{" +
                "service='" + service + '\'' +
                ", type='" + type + '\'' +
                ", version='" + version + '\'' +
                ", result=" + result +
                '}';
    }
}

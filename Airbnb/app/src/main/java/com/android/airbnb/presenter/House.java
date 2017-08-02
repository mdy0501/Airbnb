package com.android.airbnb.presenter;

import com.android.airbnb.R;
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by JunHee on 2017. 8. 1..
 */

public class House implements Serializable {

    private LatLng houseLatLng;
    private String title = "";
    private int price = 123566;
    private String detail = "설명설명";
    private int evaluation = 0;

    public House(String title, double lat, double lon, int evaluation) {
        houseLatLng = new LatLng(lat, lon);
        this.title = title;
        this.evaluation = evaluation;
    }


    public void setEvaluation(int evaluation) {
        this.evaluation = evaluation;
    }

    public LatLng getHouseLatLng() {
        return houseLatLng;
    }

    public String getTitle() {
        return title;
    }

    public int getImgUri() {
        return imgUri;
    }

    private int imgUri = R.mipmap.dummy_room;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDetail() {
        return detail;
    }

    public int getEvaluation() {
        return evaluation;
    }
}

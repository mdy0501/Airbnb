package com.android.airbnb.domain.airbnb;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JunHee on 2017. 8. 2..
 */


public class House_images implements Parcelable {

    private String image;

    protected House_images(Parcel in) {
        image = in.readString();
    }

    public static final Creator<House_images> CREATOR = new Creator<House_images>() {
        @Override
        public House_images createFromParcel(Parcel in) {
            return new House_images(in);
        }

        @Override
        public House_images[] newArray(int size) {
            return new House_images[size];
        }
    };

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "ClassPojo [image = " + image + "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image);
    }
}


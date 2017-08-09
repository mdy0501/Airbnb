package com.android.airbnb.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MDY on 2017-08-03.
 */

public class Amenities implements Parcelable
{
    private String name;

    protected Amenities(Parcel in) {
        name = in.readString();
    }

    public static final Creator<Amenities> CREATOR = new Creator<Amenities>() {
        @Override
        public Amenities createFromParcel(Parcel in) {
            return new Amenities(in);
        }

        @Override
        public Amenities[] newArray(int size) {
            return new Amenities[size];
        }
    };

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [name = "+name+"]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }
}
package com.android.airbnb.data;

/**
 * Created by MDY on 2017-08-03.
 */

public class House_images
{
    private String image;

    public String getImage ()
    {
        return image;
    }

    public void setImage (String image)
    {
        this.image = image;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [image = "+image+"]";
    }
}
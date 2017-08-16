package com.android.airbnb.domain.reservation;

/**
 * Created by JunHee on 2017. 8. 16..
 */

public class House_images
{
    private String image;

    private String _order;

    public String getImage ()
    {
        return image;
    }

    public void setImage (String image)
    {
        this.image = image;
    }

    public String get_order ()
    {
        return _order;
    }

    public void set_order (String _order)
    {
        this._order = _order;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [image = "+image+", _order = "+_order+"]";
    }
}
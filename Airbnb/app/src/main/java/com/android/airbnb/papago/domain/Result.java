package com.android.airbnb.papago.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JunHee on 2017. 8. 6..
 */

public class Result {

    @SerializedName("srcLangType")
    private String srcLangType;

    @SerializedName("translatedText")
    private String translatedText;

    public String getSrcLangType ()
    {
        return srcLangType;
    }

    public void setSrcLangType (String srcLangType)
    {
        this.srcLangType = srcLangType;
    }

    public String getTranslatedText ()
    {
        return translatedText;
    }

    public void setTranslatedText (String translatedText)
    {
        this.translatedText = translatedText;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [srcLangType = "+srcLangType+", translatedText = "+translatedText+"]";
    }
}

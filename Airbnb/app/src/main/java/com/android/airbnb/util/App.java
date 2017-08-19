package com.android.airbnb.util;

import android.app.Application;

import com.tsengvn.typekit.Typekit;

/**
 * Created by JunHee on 2017. 8. 19..
 */

public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/NanumSquareRegular.ttf"))
                .addBold(Typekit.createFromAsset(this, "fonts/NanumSquareBold.ttf"));
    }
}

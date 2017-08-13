package com.android.airbnb.util;

import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by JunHee on 2017. 8. 13..
 */

public class MapUtil {

    // map 위에 마커를 중심으로 반경을 체크하기 위해 사용되는 메소드
    public static void drawCircle(GoogleMap map, LatLng latLng){

        // Instantiating CircleOptions to draw a circle around the marker
        CircleOptions circleOptions = new CircleOptions();

        // Specifying the center of the circle
        circleOptions.center(latLng);

        // Radius of the circle
        circleOptions.radius(2000);

        // Border color of the circle
        circleOptions.strokeColor(Color.BLACK);

        // Fill color of the circle
        circleOptions.fillColor(0x30ff0000);

        // Border width of the circle
        circleOptions.strokeWidth(5);

        // Adding the circle to the GoogleMap
        map.addCircle(circleOptions);

    }


}

package com.android.airbnb.util;

import com.android.airbnb.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JunHee on 2017. 8. 6..
 */

public class Const {

    public static class Amenities {

        // Map 형태의 Collection Framework를 사용하여 이미지를 쉽게 꺼낼 수 있게 코드를 작성하였다.
        public static Map<String, Integer> amenitiesMap = new HashMap<>();

        // 서버에서 넘겨 받은 amenities의 String을 key로 사용한다.
        public static int getAmenityImg(String key) {
            setAmenities();
            return amenitiesMap.get(key);
        }

        private static void setAmenities() {
            amenitiesMap.put("Pets_allowed", R.drawable.ic_pets_allowed);
            amenitiesMap.put("Elevator", R.drawable.ic_elevator);
            amenitiesMap.put("Gym", R.drawable.ic_gym);
            amenitiesMap.put("Indoor_fireplace", R.drawable.ic_fireplace);
            amenitiesMap.put("Internet", R.drawable.ic_wireless_internet);
            amenitiesMap.put("Doorman", R.drawable.ic_doorman);
            amenitiesMap.put("Kitchen", R.drawable.ic_kitchen);
            amenitiesMap.put("Pool", R.drawable.ic_pool);
            amenitiesMap.put("Smoking_allowed", R.drawable.ic_smoking_allowed);
            amenitiesMap.put("Wheelchair_accessible", R.drawable.ic_wheelchair_accessible);
            amenitiesMap.put("Wireless_Internet", R.drawable.ic_wheelchair_accessible);
            amenitiesMap.put("Free_parking", R.drawable.ic_free_parking);
            amenitiesMap.put("Breakfast", R.drawable.ic_breakfast);
            amenitiesMap.put("Dryer", R.drawable.ic_dryer);
            amenitiesMap.put("Free_parking", R.drawable.ic_free_parking);
            amenitiesMap.put("Cable_TV", R.drawable.ic_cable_tv);
            amenitiesMap.put("Hangers", R.drawable.ic_hanger);
            amenitiesMap.put("Washer", R.drawable.ic_washer);
            amenitiesMap.put("Shampoo", R.drawable.ic_shampoo);
            amenitiesMap.put("Essentials", R.drawable.ic_towel);
            amenitiesMap.put("Heating", R.drawable.ic_heating);
            amenitiesMap.put("TV", R.drawable.ic_tv);
            amenitiesMap.put("Air_conditioning", R.drawable.ic_air_conditioning);
        }
    }
}


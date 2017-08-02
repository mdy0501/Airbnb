package com.android.airbnb.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by JunHee on 2017. 8. 1..
 */

public class LoaderDummy {


    public static List<House> dummyHouseList = new ArrayList<>();

    // 런던 geo 값 기준
    public static List<House> getDummyHouseList() {
        for (int i = 0; i < 100; i++) {
            dummyHouseList.add(new House("[ " + i + " ] 번째 집", 51.500910 + (0.1 * i), -0.124690 + (0.1 * i), getRandomInt()));
        }
        return dummyHouseList;
    }

    public static  int getRandomInt(){
        Random random = new Random();
        return random.nextInt(5);

    }

}

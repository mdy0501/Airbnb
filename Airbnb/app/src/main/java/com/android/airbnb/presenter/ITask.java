package com.android.airbnb.presenter;

import com.android.airbnb.domain.Host;
import com.android.airbnb.domain.House;

import java.util.List;

/**
 * Created by JunHee on 2017. 8. 3..
 */

public interface ITask {

    public void doHostListTask(List<Host> hostList);
    public void doHouseListTask(List<House> houseList);
    public void doMapSync();


}

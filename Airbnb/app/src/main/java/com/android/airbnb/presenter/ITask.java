package com.android.airbnb.presenter;

import com.android.airbnb.domain.airbnb.Host;
import com.android.airbnb.domain.airbnb.House;

import java.util.List;

/**
 * Created by JunHee on 2017. 8. 3..
 */

public interface ITask {

//    public void doHostListTask(List<Host> hostList);
    public void doTaskTotalHostList(List<Host> hostList);
//    public void doHouseListTask(List<House> houseList);
    public void doTaskTotalHouseList(List<House> houseList);
//    public void doOneHouseTask(House house);
    public void doTaskOneHouseList(House house);
//    public void doOneHostTask(Host host);
    public void doTaskOneHostList(Host host);


}



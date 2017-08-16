package com.android.airbnb.util.Remote;

import com.android.airbnb.domain.airbnb.Host;
import com.android.airbnb.domain.airbnb.House;
import com.android.airbnb.domain.reservation.Reservation;

import java.util.List;

/**
 * Created by JunHee on 2017. 8. 3..
 */

public interface ITask {

    public interface totalHouseList{
        public void doTask(List<House> houseList);
    }

    public interface totalHostList{
        public void doTask(List<Host> hostList);
    }

    public interface oneHouseList{
        public void doTask(House house);
    }

    public interface oneHostList{
        public void doTask(Host host);
    }

    public interface oneReservation{
        public void doTask(List<Reservation> reservations);
    }

    /*
    public void doTaskTotalHostList(List<Host> hostList);
    public void doTaskTotalHouseList(List<House> houseList);
    public void doTaskOneHouseList(House house);
    public void doTaskOneHostList(Host host);
    */
}



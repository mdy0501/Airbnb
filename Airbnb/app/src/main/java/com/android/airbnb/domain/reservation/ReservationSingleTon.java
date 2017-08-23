package com.android.airbnb.domain.reservation;

/**
 * Created by JunHee on 2017. 8. 21..
 */

public class ReservationSingleTon {

    public static ReservationSingleTon instance = null;

    public static ReservationSingleTon getInstnace() {
        if (instance != null)
            return instance;
        else
            return instance = new ReservationSingleTon();
    }

    private ReservationSingleTon() {

    }

    private String created_date;

    private String message_to_host;

    private String updated_date;

    private String checkout_date;

    private String adults;

    private Guest guest;

    private String checkin_date;

    private String children;

    private com.android.airbnb.domain.airbnb.House house;

    private String infants;

    private String pk;

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getMessage_to_host() {
        return message_to_host;
    }

    public void setMessage_to_host(String message_to_host) {
        this.message_to_host = message_to_host;
    }

    public String getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(String updated_date) {
        this.updated_date = updated_date;
    }

    public String getCheckout_date() {
        return checkout_date;
    }

    public void setCheckout_date(String checkout_date) {
        this.checkout_date = checkout_date;
    }

    public String getAdults() {
        return adults;
    }

    public void setAdults(String adults) {
        this.adults = adults;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public String getCheckin_date() {
        return checkin_date;
    }

    public void setCheckin_date(String checkin_date) {
        this.checkin_date = checkin_date;
    }

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }

    public com.android.airbnb.domain.airbnb.House getHouse() {
        return house;
    }

    public void setHouse(com.android.airbnb.domain.airbnb.House house) {
        this.house = house;
    }

    public String getInfants() {
        return infants;
    }

    public void setInfants(String infants) {
        this.infants = infants;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    @Override
    public String toString() {
        return "ClassPojo [created_date = " + created_date + ", message_to_host = " + message_to_host + ", updated_date = " + updated_date + ", checkout_date = " + checkout_date + ", adults = " + adults + ", guest = " + guest + ", checkin_date = " + checkin_date + ", children = " + children + ", house = " + house + ", infants = " + infants + ", pk = " + pk + "]";
    }
}

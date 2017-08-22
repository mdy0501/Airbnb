package com.android.airbnb.domain.airbnb;

import android.net.Uri;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MDY on 2017-08-19.
 */

public class HostingHouse {

    /* 싱글톤 적용 */
    // 1. 자기 자신을 담아두는 저장공간 생성
    private static HostingHouse hostingHouse = null;

    // 2. 생성자를 private으로 만들어 외부에서 생성할 수 없도록 제한
    private HostingHouse(){

    }

    // 3. 외부에서 생성할 수 없으므로 내부에서 생성한 후 사용할 수 있도록 인터페이스를 제공해야 한다.
    //    외부에서 new가 안되므로 static으로 구성해야 한다.
    public static HostingHouse getInstance(){
        if(hostingHouse == null){
            hostingHouse = new HostingHouse();
        }
        return hostingHouse;
    }


    private String title;   // 숙소 제목
    private String address; // 숙소 주소
    private String introduce;   // 숙소 소개
    private String space_info;  // 내부 소개
    private String guest_access;    // 편의정보 소개
    private String price_per_day;   // 1박당 요금
    private String extra_people_fee;    // 추가인원 요금
    private String cleaning_fee;    // 청소요금
    private String weekly_discount; // 1주이상 할인률
    private String accommodates;    // 최대 수용인원
    private String bathrooms;   // 화장실 개수
    private String bedrooms;    // 침실 개수
    private String beds;        // 침대 개수
    private String room_type;   // 숙소 유형
    private String amenities;   // 편의시설 현황


    private String latitude;    // 숙소 위도
    private String longitude;   // 숙소 경도
    private byte[] image;       // 숙소 이미지

    public List<Uri> uris = new ArrayList<>();
    public List<String> filePaths = new ArrayList<>();

    private String imagePath;   // 숙소 이미지 경로

    private String kingBeds = "0";    // 킹사이즈 침대
    private String queenBeds = "0";   // 퀸사이즈 침대
    private String doubleBeds = "0";  // 더블사이즈 침대
    private String singleBeds = "0";  // 싱글사이즈 침대



    public Map<String, String> facilitiesMap = new HashMap<>();

    public void addFacilities(String key, String value){
        facilitiesMap.put(key, value);
    }
    public void removeFacilities(String key){
        facilitiesMap.remove(key);
    }




    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getSpace_info() {
        return space_info;
    }

    public void setSpace_info(String space_info) {
        this.space_info = space_info;
    }

    public String getGuest_access() {
        return guest_access;
    }

    public void setGuest_access(String guest_access) {
        this.guest_access = guest_access;
    }

    public String getPrice_per_day() {
        return price_per_day;
    }

    public void setPrice_per_day(String price_per_day) {
        this.price_per_day = price_per_day;
    }

    public String getExtra_people_fee() {
        return extra_people_fee;
    }

    public void setExtra_people_fee(String extra_people_fee) {
        this.extra_people_fee = extra_people_fee;
    }

    public String getCleaning_fee() {
        return cleaning_fee;
    }

    public void setCleaning_fee(String cleaning_fee) {
        this.cleaning_fee = cleaning_fee;
    }

    public String getWeekly_discount() {
        return weekly_discount;
    }

    public void setWeekly_discount(String weekly_discount) {
        this.weekly_discount = weekly_discount;
    }

    public String getAccommodates() {
        return accommodates;
    }

    public void setAccommodates(String accommodates) {
        this.accommodates = accommodates;
    }

    public String getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(String bathrooms) {
        this.bathrooms = bathrooms;
    }

    public String getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(String bedrooms) {
        this.bedrooms = bedrooms;
    }

    public String getBeds() {
        return beds;
    }

    public void setBeds(String beds) {
        this.beds = beds;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }


    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }



    public String getKingBeds() {
        return kingBeds;
    }

    public void setKingBeds(String kingBeds) {
        this.kingBeds = kingBeds;
    }

    public String getQueenBeds() {
        return queenBeds;
    }

    public void setQueenBeds(String queenBeds) {
        this.queenBeds = queenBeds;
    }

    public String getDoubleBeds() {
        return doubleBeds;
    }

    public void setDoubleBeds(String doubleBeds) {
        this.doubleBeds = doubleBeds;
    }

    public String getSingleBeds() {
        return singleBeds;
    }

    public void setSingleBeds(String singleBeds) {
        this.singleBeds = singleBeds;
    }
}

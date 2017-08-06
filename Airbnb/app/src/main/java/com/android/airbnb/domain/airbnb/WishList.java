package com.android.airbnb.domain.airbnb;

import java.util.List;

/**
 * Created by JunHee on 2017. 8. 5..
 */

public class WishList {

    private List<House> wishList;

    private String listName;

    private House_images[] house_images;

    public WishList() {

    }

    public List<House> getWishList() {
        return wishList;
    }

    public WishList(List<House> wishList) {
        this.wishList = wishList;
    }

    public void addList(House house) {
        wishList.add(house);
    }

    public void removeList(House house) {
        wishList.remove(house);
    }

    public House getHouse(int i) {
        return wishList.get(i);
    }

    public String getWishListSize(){
        return wishList.size() + "";
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }
}

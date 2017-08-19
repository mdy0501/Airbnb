package com.android.airbnb.domain.register;

/**
 * Created by MDY on 2017-08-17.
 */

public class Bed {

    // 킹 베드
    private int kingBed = 0;
    public void setKingBed(int count){
        if(count > 0){
            kingBed = count;
        }
    }
    public int getKingBed(){
        return kingBed;
    }

    // 퀸 베드
    private int queenBed = 0;
    public void setQueenBed(int count){
        if(count > 0){
            queenBed = count;
        }
    }
    public int getQueenBed(){
        return queenBed;
    }

    // 더블 베드
    private int doubleBed = 0;
    public void setDoubleBed(int count){
        if(count > 0){
            doubleBed = count;
        }
    }
    public int getDoubleBed(){
        return doubleBed;
    }

    // 싱글 베드
    private int singleBed = 0;
    public void setSingleBed(int count){
        if(count > 0){
            singleBed = count;
        }
    }
    public int getSingleBed(){
        return singleBed;
    }
}

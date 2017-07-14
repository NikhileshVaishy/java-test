package com.sevenre.triastest;

import java.util.List;

/**
 * Created by shah on 6/23/2017.
 */
public class Trip {

    String tripId;
    String currentStop;
    String originStop;
    String destinationStop;
    String time;
    String modeNo;
    String direction;

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getCurrentStop() {
        return currentStop;
    }

    public void setCurrentStop(String currentStop) {
        this.currentStop = currentStop;
    }

    public String getOriginStop() {
        return originStop;
    }

    public void setOriginStop(String originStop) {
        this.originStop = originStop;
    }

    public String getDestinationStop() {
        return destinationStop;
    }

    public void setDestinationStop(String destinationStop) {
        this.destinationStop = destinationStop;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getModeNo() {
        return modeNo;
    }

    public void setModeNo(String modeNo) {
        this.modeNo = modeNo;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}

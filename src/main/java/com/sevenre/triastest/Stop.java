package com.sevenre.triastest;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

import java.io.Serializable;

/**
 * Created by shah on 6/23/2017.
 */
@Entity
@Table(name = "stop", uniqueConstraints = {
        @UniqueConstraint(columnNames = "global_id") })
public class Stop implements Serializable {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "global_id", unique = true)
    private String globalId;

    @Column(name = "stop_name")
    private String stopName;

    @Column(name = "loc_name")
    private String locName;

    private Double lat;
    private Double lon;

    public Stop() {
        // for hibernate
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGlobalId() {
        return globalId;
    }

    public void setGlobalId(String globalId) {
        this.globalId = globalId;
    }

    public String getStopName() {
        return stopName;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }
}

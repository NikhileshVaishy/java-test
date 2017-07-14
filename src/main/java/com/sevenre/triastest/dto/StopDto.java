package com.sevenre.triastest.dto;

/**
 * Created by nikhilesh on 13/07/17.
 */
public class StopDto {
    private int id;
    private String globalId;
    private String stopName;
    private String locName;
    private Double lat;
    private Double lon;

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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof StopDto)) return false;

        StopDto stopDto = (StopDto) object;

        if (getId() != stopDto.getId()) return false;
        if (getGlobalId() != null ? !getGlobalId().equals(stopDto.getGlobalId()) : stopDto.getGlobalId() != null)
            return false;
        if (getStopName() != null ? !getStopName().equals(stopDto.getStopName()) : stopDto.getStopName() != null)
            return false;
        if (getLocName() != null ? !getLocName().equals(stopDto.getLocName()) : stopDto.getLocName() != null)
            return false;
        if (getLat() != null ? !getLat().equals(stopDto.getLat()) : stopDto.getLat() != null) return false;
        return getLon() != null ? getLon().equals(stopDto.getLon()) : stopDto.getLon() == null;
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getGlobalId() != null ? getGlobalId().hashCode() : 0);
        result = 31 * result + (getStopName() != null ? getStopName().hashCode() : 0);
        result = 31 * result + (getLocName() != null ? getLocName().hashCode() : 0);
        result = 31 * result + (getLat() != null ? getLat().hashCode() : 0);
        result = 31 * result + (getLon() != null ? getLon().hashCode() : 0);
        return result;
    }
}

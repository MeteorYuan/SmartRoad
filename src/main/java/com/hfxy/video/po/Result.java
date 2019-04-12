package com.hfxy.video.po;

public class Result {
    private String utc;
    private String longitude;
    private String latitude;
    private String no_turn;
    private String no_stop;
    private String lim_speed;
    private int road_id;

    @Override
    public String toString() {
        return "Result{" +
                "utc='" + utc + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", no_turn=" + no_turn +
                ", no_stop=" + no_stop +
                ", lim_speed=" + lim_speed +
                ", road_id=" + road_id +
                '}';
    }

    public String getUtc() {
        return utc;
    }

    public void setUtc(String utc) {
        this.utc = utc;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getNo_turn() {
        return no_turn;
    }

    public void setNo_turn(String no_turn) {
        this.no_turn = no_turn;
    }

    public String getNo_stop() {
        return no_stop;
    }

    public void setNo_stop(String no_stop) {
        this.no_stop = no_stop;
    }

    public String getLim_speed() {
        return lim_speed;
    }

    public void setLim_speed(String lim_speed) {
        this.lim_speed = lim_speed;
    }

    public int getRoad_id() {
        return road_id;
    }

    public void setRoad_id(int road_id) {
        this.road_id = road_id;
    }
}

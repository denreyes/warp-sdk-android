package com.warp.android.http.models;

public class Location {

    private int id;
    private String address;
    private String city;
    private String province;
    private double latitude;
    private double longitude;

    public Location() {

    }

    public Location(String city) {
        this.city = city;
    }

    public Location(String city, String province) {
        this.city = city;
        this.province = province;
    }

    public Location(String address, String city, String province) {
        this.address = address;
        this.city = city;
        this.province = province;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getFullAddress() {
        return address + ", " + city + ", " + province;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}

package model;

public class Place {

    private float lon;
    private float lat;

    private long sunSet;
    private long sunRise;

    private String Country;
    private String city;

    private long lastUpdate;

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public long getSunSet() {
        return sunSet;
    }

    public void setSunSet(long sunSet) {
        this.sunSet = sunSet;
    }

    public long getSunRise() {
        return sunRise;
    }

    public void setSunRise(long sunRise) {
        this.sunRise = sunRise;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}

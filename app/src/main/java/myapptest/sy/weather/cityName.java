package myapptest.sy.weather;

/**
 * Created by Sy on 2016/3/12.
 */
public class cityName {
    private String name;
    private String cityID;

    public cityName(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public cityName(String name, String cityID) {

        this.name = name;
        this.cityID = cityID;
    }
}

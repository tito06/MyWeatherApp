package data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import model.Place;
import model.Weather;
import util.Utils;

public class JSONWeatherParser {

    public static Weather getWeather(String data){
        Weather weather = new Weather();


        try {
            JSONObject jsonObject = new JSONObject(data);

            Place place = new Place();

            JSONObject coorObj = Utils.getObject("coord", jsonObject);
            place.setLat(Utils.getFloat("lat", coorObj));
            place.setLon(Utils.getFloat("lon", coorObj));

            JSONObject sysObj = Utils.getObject("sys", jsonObject);
            place.setCountry(Utils.getString("country", sysObj));
            place.setLastUpdate(Utils.getInt("dt", jsonObject));
            place.setSunRise(Utils.getInt("sunrise", sysObj));
            place.setSunSet(Utils.getInt("sunset", sysObj));
            place.setCity(Utils.getString("name", jsonObject));
            weather.place = place;


            JSONArray jsonArray = jsonObject.getJSONArray("weather");

            JSONObject jsonWeather = jsonArray.getJSONObject(0);
            weather.currentCondition.setWeatherId(Utils.getInt("id", jsonWeather));
            weather.currentCondition.setDescription(Utils.getString("description", jsonWeather));
            weather.currentCondition.setCondition(Utils.getString("main", jsonWeather));
            weather.currentCondition.setIcon(Utils.getString("icon", jsonWeather));


            JSONObject mainObj = Utils.getObject("main", jsonObject);
            weather.currentCondition.setHumidity(Utils.getInt("humidity", mainObj));
            weather.currentCondition.setPressure(Utils.getInt("pressure", mainObj));
            weather.currentCondition.setMintemp(Utils.getFloat("temp_min", mainObj));
            weather.currentCondition.setMaxtemp(Utils.getFloat("temp_max", mainObj));
            weather.currentCondition.setTemp(Utils.getDouble("temp", mainObj));

            JSONObject windObj = Utils.getObject("wind", jsonObject);
            weather.wind.setSpeed(Utils.getFloat("speed", windObj));
            weather.wind.setDeg(Utils.getFloat("deg", windObj));


            JSONObject cloudObj = Utils.getObject("clouds", jsonObject);
            weather.clouds.setPrecipitation(Utils.getInt("all", cloudObj));

            return weather;










        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}

package data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Util.Utils;
import model.Locations;
import model.Weather;

public class WeatherParser {
    public static Weather getWeather(String data)
    {
        Weather weather = new Weather();
        try
        {
            JSONObject jsonObject = new JSONObject(data);
            Locations locations = new Locations();

            JSONObject coordObj = Utils.getObject("coord", jsonObject);
            locations.setLat(Utils.getFloat("lat", coordObj));
            locations.setLon(Utils.getFloat("lon", coordObj));

            JSONObject sysObj = Utils.getObject("sys", jsonObject);
            locations.setCountry(Utils.getString("country", sysObj));
            locations.setUpdate(Utils.getInt("dt", jsonObject));
            locations.setSunset(Utils.getInt("sunset", sysObj));
            locations.setSunrise(Utils.getInt("sunrise", sysObj));
            locations.getCity(Utils.getString("name", jsonObject));
            weather.locations = locations;

            JSONArray jsonArray = jsonObject.getJSONArray("weather");
            JSONObject jsonWeather = jsonArray.getJSONObject(0);
            weather.currentCondition.setWeatherId(Utils.getInt("Id", jsonWeather));
            weather.currentCondition.setDescriptions(Utils.getString("description", jsonWeather));
            weather.currentCondition.setConditions(Utils.getString("main",jsonWeather));
            weather.currentCondition.setIcon(Utils.getString("icon",jsonWeather));

            JSONObject mainObj = Utils.getObject("main", jsonObject);
            weather.currentCondition.setHumidity(Utils.getInt("id", mainObj));
            weather.currentCondition.setDescriptions(Utils.getString("description", mainObj));
            weather.currentCondition.setMinimum_temp(Utils.getFloat("temp_min", mainObj));
            weather.currentCondition.setMaximum_temp(Utils.getFloat("temp_max", mainObj));
            weather.currentCondition.setTemperature(Utils.getDouble("temp", mainObj));
            weather.currentCondition.setPressure(Utils.getInt("pressure", mainObj));



            JSONObject windObj = Utils.getObject("wind", jsonObject);
            weather.winds.setDegree(Utils.getFloat("deg",windObj));
            weather.winds.setSpeed(Utils.getFloat("speed",windObj));

            JSONObject cloudObj = Utils.getObject("clouds", jsonObject);
            weather.clouds.setPrecipitation(Utils.getInt("all",cloudObj));

            return weather;
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            return null;
        }

    }


}



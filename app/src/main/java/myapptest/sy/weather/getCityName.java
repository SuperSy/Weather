package myapptest.sy.weather;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sy on 2016/3/12.
 */
public class getCityName {
    public static List<cityName> cityname(){
        BufferedReader reader = null;
        String cityname = null;
        StringBuffer sbf = new StringBuffer();
        String httpUrl = "https://api.heweather.com/x3/citylist?search=allchina&key=cf7dbfe11aaf4f5d9f07c7c0f3f50328";
        List<cityName> mList = new ArrayList<cityName>();
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null){
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            cityname = sbf.toString();

            Log.d("TEST4", cityname);

            JSONObject jsonObject = new JSONObject(cityname);
            JSONArray jsonArray = jsonObject.getJSONArray("city_info");
            for (int i = 0;i<jsonArray.length();i++){
                cityName cityName = new cityName();
                cityName.setName(jsonArray.getJSONObject(i).getString("city"));
                cityName.setCityID(jsonArray.getJSONObject(i).getString("id"));
                mList.add(cityName);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mList;
    }
}

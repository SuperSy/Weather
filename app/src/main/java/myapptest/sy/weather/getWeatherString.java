package myapptest.sy.weather;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Sy on 2016/3/5.
 */
public class getWeatherString {
//    String httpUrl = "http://apis.baidu.com/heweather/weather/free";
//    String httpArg = "city=beijing";

    public static String request(String httpUrl, String httpArg,int nameorinfo) {

        BufferedReader reader = null;
        String result = null;
        String strdata = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?" + httpArg;

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            // 填入apikey到HTTP header
            connection.setRequestProperty("apikey",  "91cd97c3271f90f8aca8653ed1fa2c3a");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();

            JSONObject jsonobj = new JSONObject(result);
            JSONArray jsonarray = jsonobj.getJSONArray("HeWeather data service 3.0");
            JSONObject jsonWeather = jsonarray.getJSONObject(0);
            switch (nameorinfo){
                case 1:
                JSONObject jsonbasic = jsonWeather.getJSONObject("basic");
                strdata = jsonbasic.getString("city");
                    break;
                case 2:
                JSONObject jsonnow = jsonWeather.getJSONObject("now");
                JSONObject cond = jsonnow.getJSONObject("cond");
//            JSONObject jsontmp = jsonnow.getJSONObject("tmp");
//            String strnow = "天气状况:" + cond.getString("txt")+"      "+"\n"+"最高温度:"+jsontmp.getString("max")+";最低温度:"+jsontmp.getString("min");
                String strnow = "天气状况:" + cond.getString("txt")+"\n"+"温度:"+jsonnow.getString("tmp")+"摄氏度";
                JSONObject jsondrsg = jsonarray.getJSONObject(0).getJSONObject("suggestion").getJSONObject("drsg");
                String strdrsg = jsondrsg.getString("txt");

                strdata = strnow+"\n"+strdrsg;
                    break;
                case 3:
                    JSONObject now = (JSONObject) jsonWeather.get("now");
                    cond = now.getJSONObject("cond");
                    final int condint = cond.getInt("code");
                    strdata = "http://files.heweather.com/cond_icon/"+condint+".png";
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("TEST5",strdata);
        return strdata;
    }

}

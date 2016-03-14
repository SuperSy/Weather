package myapptest.sy.weather;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    String imgurl,strdata = null;
    ImageView imageView = null;
    TextView tv_cityname, tv_content = null;
    ListView lv = null;

    String httpUrl = "http://apis.baidu.com/heweather/weather/free";
    static String httpArg = "city=北京";

    Handler mHandler = new Handler(){
        @Override
        public boolean sendMessageAtTime(Message msg, long uptimeMillis) {
            switch (msg.what){
                case 0x001:
                loading();
                break;
            }
            return super.sendMessageAtTime(msg, uptimeMillis);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.listview);
        tv_cityname = (TextView) findViewById(R.id.tv_city);
        tv_content = (TextView) findViewById(R.id.tv_content);
        imageView = (ImageView) findViewById(R.id.imageView);
        getAddress();
        loading();

    }

    public void setHttpArg(String str){
        this.httpArg = "city="+str;
        Log.d("SSSSSSSSSSSSS", httpArg + "43154354325431");
    }

    public void loading(){

        new Thread(new Runnable() {
            @Override
            public void run() {
             final String ss = getWeatherString.request(httpUrl, httpArg, 1);
             final String sss = getWeatherString.request(httpUrl, httpArg, 2);
             final String ssss = getWeatherString.request(httpUrl, httpArg, 3);
                final List list = getCityName.cityname();
                Log.d("TTTTTTTTTTTTTTTTT",list.size()+"");
                final Bitmap bitmap = getImage(ssss);
                Log.d("Test", ss +"aaaaaaaaaa");
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        lv.setAdapter(new cityAdapter(list,getApplicationContext(),mHandler));
                        tv_cityname.setText(ss);
                        tv_content.setText(sss);
                        imageView.setImageBitmap(bitmap);
                    }
                });

            }
        }).start();
    }

    private void dialogAddress(){
        final EditText address = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("输入所在地址");
        DialogInterface.OnClickListener dialogOnclicListener=new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case Dialog.BUTTON_POSITIVE:
                        SharedPreferences sp = getSharedPreferences("Weather_address", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("address", address.getText().toString());
                        editor.commit();
                        httpArg = "city="+address.getText().toString();
                        loading();
                        Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        builder.setView(address);
        builder.setPositiveButton("保存", dialogOnclicListener);
        builder.show();
    }

    private void getAddress(){
        String weatherInfo = null;
        SharedPreferences sp = getSharedPreferences("Weather_address", Context.MODE_PRIVATE);
        final String address = sp.getString("address", null);
        if (address != null){
             httpArg = "city="+address;
        }else {
            dialogAddress();
        }
    }

    private Bitmap getImage(String imgurl){
        Bitmap bitmap = null;
        InputStream is = null;

        try {
            URL url = new URL(imgurl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            is = new BufferedInputStream(connection.getInputStream());
            bitmap = BitmapFactory.decodeStream(is);
            connection.disconnect();
            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    EditText et;
    TextView tv;
    String temperatureTEXT, humidityTEXT, windSpeedTEXT, countryTEXT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et=findViewById(R.id.et);
        tv=findViewById(R.id.tv);

    }
    public void get(View v){
        String apikey="1f1cb800d54ad1e692833e3a1270c882";
        String city=et.getText().toString();
        String url="https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="+apikey;
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    //object1 refers to category for temp and humid
                    JSONObject object1 = response.getJSONObject("main");

                    String temperature = object1.getString("temp");
                    Double temp = Double.parseDouble(temperature)-273.15;
                    temperatureTEXT = "<br><b>Temperature:</b> "+temp.toString().substring(0,5)+" °C";

                    String humidity = object1.getString("humidity");
                    humidityTEXT = "<br><b>Humidity:</b> " + humidity;

                    //object2 refers to category of wind
                    JSONObject object2 = response.getJSONObject("wind");

                    String windSpeed = object2.getString("speed");
                    windSpeedTEXT = "<br><b>Wind Speed:</b> " + windSpeed;

                    //object1 refers to category of city information
                    JSONObject object3 = response.getJSONObject("sys");

                    String country = object3.getString("country");
                    countryTEXT = "<br><b>Country:</b> " + country;

                    tv.setText(Html.fromHtml(temperatureTEXT + humidityTEXT + windSpeedTEXT + countryTEXT));

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"Please Try Again.", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
    }
}
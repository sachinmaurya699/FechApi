package com.apksachin.fechapi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.icu.text.LocaleDisplayNames;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    String api1,api2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        api1="http://www.speshfood.com/FoodWellHandler.ashx?RequestType=AutoSuggestSubLocality&SearchText=dwarka";
        api2="http://www.speshfood.com/FoodWellHandler.ashx?RequestType=RestaurantAPI&cityID=1&SubLocalityID=2&CuisinesCode=0&RestaurantName=";
        hitapi1();
    }



    public void hitapi1()
    {
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.POST
                , api1, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String s1=response.getJSONArray("FOODAPP").getJSONObject(1).toString();
                    Toast.makeText(getApplicationContext(),s1, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("Error>>>>>",e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error>>>>>>>>>",error.toString());
                Toast.makeText(MainActivity.this,error.toString(), Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap= new HashMap<String, String>();
                hashMap.put("Content-Type", "application/json; charset=utf-8");
                //hashMap.put("providerName", "");
                return hashMap;
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS*48,
                2,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jsonObjectRequest.setShouldCache (false);
        Volley.newRequestQueue(MainActivity.this).add (jsonObjectRequest);

    }
    public void hitapi2()
    {
        StringRequest stringRequest= new StringRequest(Request.Method.POST, api2, new
                Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,error.toString(), Toast.LENGTH_SHORT).show();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap= new HashMap<String, String>();
                hashMap.put("Content-Type", "Application/json ; charset=utf-8");
                return hashMap;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy (
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS*48,
                2,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setShouldCache (false);
        Volley.newRequestQueue(MainActivity.this).add (stringRequest);
    }

}

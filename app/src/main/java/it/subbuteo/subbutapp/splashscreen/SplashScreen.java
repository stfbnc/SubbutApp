package it.subbuteo.subbutapp.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import it.subbuteo.subbutapp.globals.Globals;
import it.subbuteo.subbutapp.mainactivity.MainActivity;
import it.subbuteo.subbutapp.R;
import it.subbuteo.subbutapp.data.DataRetriever;
import it.subbuteo.subbutapp.data.VolleyResponseListener;

public class SplashScreen extends FragmentActivity {

    RequestQueue rs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final DataRetriever dataRetriever = new DataRetriever(getApplicationContext());
        if (dataRetriever.getJsonString().equals("")){
            setContentView(R.layout.splashscreen);
            //hall of fame data
            rs = Volley.newRequestQueue(this);
            dataRetriever.sendJsonRequest(rs, Globals.url_hof, new VolleyResponseListener() {
                @Override
                public void onErrorResponse(String message) {
                    Log.d("respErrorHof", "responseErrorHof = " + message);
                    finish();
                }

                @Override
                public void onResponse(JSONObject response) {
                    if (response != null) {
                        Log.d("respOKHof", "responseOKHof = " + response);
                        Log.d("respStrHof", "jsonStringHof = " + dataRetriever.getJsonString());
                        if (!response.toString().trim().equals("")) {
                            dataRetriever.unpackJSONHof(response);
                        }
                    } else {
                        Log.d("respNullHof", "responseHof = null");
                        finish();
                    }
                }
            });
            //images data
            rs = Volley.newRequestQueue(this);
            dataRetriever.sendJsonRequest(rs, Globals.url_img, new VolleyResponseListener() {
                @Override
                public void onErrorResponse(String message) {
                    Log.d("respErrorImg", "responseErrorImg = " + message);
                    finish();
                }

                @Override
                public void onResponse(JSONObject response) {
                    if (response != null) {
                        Log.d("respOKImg", "responseOKImg = " + response);
                        Log.d("respStrImg", "jsonStringImg = " + dataRetriever.getJsonString());
                        if (!response.toString().trim().equals("")) {
                            dataRetriever.unpackJSONImg(response);
                        }
                    } else {
                        Log.d("respNullImg", "responseImg = null");
                        finish();
                    }
                }
            });
            //results data
            rs = Volley.newRequestQueue(this);
            dataRetriever.sendJsonRequest(rs, Globals.url, new VolleyResponseListener() {
                @Override
                public void onErrorResponse(String message) {
                    Log.d("respErrorChampionship", "responseErrorChampionship = " + message);
                    finish();
                }

                @Override
                public void onResponse(JSONObject response) {
                    if (response != null) {
                        Log.d("respOKChampionship", "responseOKChampionship = " + response);
                        Log.d("respStrChampionship", "jsonStringChampionship = " + dataRetriever.getJsonString());
                        if (!response.toString().equals(dataRetriever.getJsonString())) {
                            dataRetriever.unpackJSON(response);
                        }
                        startMainActivity();
                    } else {
                        Log.d("respNullChampionship", "responseChampionship = null");
                        finish();
                    }
                }
            });
        }else{
            startMainActivity();
        }
    }

    private void startMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}

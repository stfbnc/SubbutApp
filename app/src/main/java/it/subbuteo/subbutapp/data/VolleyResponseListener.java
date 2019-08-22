package it.subbuteo.subbutapp.data;

import org.json.JSONObject;

public interface VolleyResponseListener {

    void onErrorResponse(String message);
    void onResponse(JSONObject response);

}

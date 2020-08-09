package it.subbuteo.subbutapp.data;

import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Objects;

import it.subbuteo.subbutapp.globals.Globals;

public class UpdatableFragment extends Fragment implements OnDataUpdateListener {

    RequestQueue rs;

    public UpdatableFragment() {}

    protected void setSwipeRefresh(final SwipeRefreshLayout swipeRefresh, final OnDataUpdateListener listener) {
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateData(listener);
                swipeRefresh.setRefreshing(false);
            }
        });
    }

    protected void updateData(final OnDataUpdateListener listener){
        rs = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));
        final DataRetriever dataRet = new DataRetriever(getActivity());
        dataRet.sendJsonRequest(rs, Globals.url, new VolleyResponseListener() {
            @Override
            public void onErrorResponse(String message) {
                Log.d("respError", "responseError = " + message);
                Objects.requireNonNull(getActivity()).finish();
            }

            @Override
            public void onResponse(JSONObject response) {
                if (response != null) {
                    Log.d("respOK", "responseOK = " + response);
                    Log.d("respStr", "jsonString = " + dataRet.getJsonString());
                    if (!response.toString().equals(dataRet.getJsonString())) {
                        dataRet.unpackJSON(response);
                        listener.notifyJsonStringChange();
                    }
                } else {
                    Log.d("respNull", "response = null");
                    getActivity().finish();
                }
            }
        });
    }

    @Override
    public void notifyJsonStringChange(){}

}

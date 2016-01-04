package com.cjq.bejingunion.utils;

import android.annotation.TargetApi;
import android.os.Build;

import org.json.*;

import java.util.Collection;

/**
 * Created by CJQ on 2016/1/4.
 */
public class JSONArray extends org.json.JSONArray {

    @Override
    public JSONObject getJSONObject(int index) {
        try {
            return new JSONObject(super.getJSONObject(index).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    public JSONArray(String json) throws JSONException {
        super(json);
    }

    public JSONArray(Collection copyFrom) {
        super(copyFrom);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public JSONArray(Object array) throws JSONException {
        super(array);
    }

    public JSONArray() {
    }

}

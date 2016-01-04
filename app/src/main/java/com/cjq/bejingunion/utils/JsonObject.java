package com.cjq.bejingunion.utils;

import org.json.*;

import java.util.Map;

/**
 * Created by CJQ on 2016/1/4.
 */
public class JSONObject extends org.json.JSONObject {

    @Override
    public String getString(String name) {
        try {
            String ss = super.getString(name);
            if ("null".equals(ss))
                return null;
            else
                return ss;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public JSONObject getJSONObject(String name) {
        try {
            return new JSONObject(super.getJSONObject(name).toString());
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }

    public JSONObject() {
    }

    @Override
    public JSONArray getJSONArray(String name) {
        try {
            return new JSONArray(super.getJSONArray(name).toString());
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    public JSONObject(String json) throws JSONException {
        super(json);
    }

    public JSONObject(Map copyFrom) {
        super(copyFrom);
    }

    public JSONObject(JSONTokener readFrom) throws JSONException {
        super(readFrom);
    }

    public JSONObject(org.json.JSONObject copyFrom, String[] names) throws JSONException {
        super(copyFrom, names);
    }
}

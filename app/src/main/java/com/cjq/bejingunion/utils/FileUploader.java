package com.cjq.bejingunion.utils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by CJQ on 2015/9/2.
 */
public class FileUploader {

    public interface FileUploadCallBack{
        void callBack(JSONObject object);
    }

    public static void  upload(String url,String name,String path,RequestParams params, final FileUploadCallBack callBack) throws Exception {
            AsyncHttpClient client = new AsyncHttpClient();
            if(params==null){
                throw new Exception("参数不能为空哦！");
            }
            params.put(name,new File(path));
            client.post(url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String response = new String(responseBody);
                    try {
                        JSONObject object = new JSONObject(response);
                        if(callBack!=null)
                        callBack.callBack(object);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    System.out.println("ERROR:===========\n"+statusCode);
                    if(responseBody!=null){
                        String response = new String(responseBody);
                        System.out.println("ERROR:===========\n"+response);
                    }
                }
            });
    }
}

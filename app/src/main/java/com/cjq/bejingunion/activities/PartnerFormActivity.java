package com.cjq.bejingunion.activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.cjq.bejingunion.BaseActivity;
import com.cjq.bejingunion.CommonDataObject;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.utils.FileUploader;
import com.cjq.bejingunion.utils.LoginUtil;
import com.cjq.bejingunion.view.ImageSelectorView;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by CJQ on 2015/8/31.
 */
public class PartnerFormActivity extends BaseActivity implements View.OnClickListener, ImageSelectorView.OnImageChangeListener {

    private AQuery aq;
    private ImageSelectorView imageSelectorView;
    private Map<String,String> images=new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apply_partner_form);

        aq = new AQuery(this);

        imageSelectorView = (ImageSelectorView)aq.id(R.id.apply_form_add_pic).getView();
        imageSelectorView.setOnAddButtonClickListener(this);
        imageSelectorView.setImageChangeListener(this);
        imageSelectorView.setW(120);
        imageSelectorView.setH(80);
        imageSelectorView.setMax(2);

        aq.id(R.id.apply_form_back).clicked(this, "closeUp");
        aq.id(R.id.apply_form_submit).clicked(this,"submit");
    }

    public void submit(){
        Map<String,String> params = new HashMap<>();
        try {
            params.put("key",LoginUtil.getKey(this));
            params.put("member_truename",aq.id(R.id.apply_form_member_truename).getText().toString());
            params.put("member_mobile",aq.id(R.id.apply_form_member_mobile).getText().toString());
            params.put("member_email",aq.id(R.id.apply_form_member_email).getText().toString());
            params.put("member_areainfo",aq.id(R.id.apply_form_member_areainfo).getText().toString());
            params.put("ID_card",aq.id(R.id.apply_form_id_card).getText().toString());

            StringBuilder builder = new StringBuilder();

            for (String s:images.values()){
                builder.append(s).append(",");
            }
            String img = builder.toString().substring(0,builder.length()-1);
//            System.out.println(img);

            params.put("material",img);


            aq.ajax(CommonDataObject.APPLY_PARTNER_URL,params,JSONObject.class,new AjaxCallback<JSONObject>(){
                @Override
                public void callback(String url, JSONObject object, AjaxStatus status) {
                    try {
//                        System.out.println(object.toString());
                        if(200==object.getInt("code")){
                            setResult(RESULT_OK);
                            finish();
                        }else{
                            Toast.makeText(PartnerFormActivity.this,object.getJSONObject("datas").getString("error"),Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    super.callback(url, object, status);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeUp(){
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1){
            if (resultCode == RESULT_OK){
                Uri uri = data.getData();
                if("content".equals(uri.getScheme())){
                    String[] proj = { MediaStore.Images.Media.DATA };
                    Cursor cursor = managedQuery(uri, proj, null, null, null);
                    int column_index = cursor
                            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    String path = cursor.getString(column_index);

                    imageSelectorView.addImage(path);
                }else
                if("file".equals(uri.getScheme()))
                {
                    String path = uri.getPath();
                    imageSelectorView.addImage(path);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    @Override
    public void add(final String path) {
        RequestParams params = new RequestParams();
        try {
            params.put("key", LoginUtil.getKey(this));
            FileUploader.upload(CommonDataObject.UPLOAD_AGENT_URL, "pic", path, params, new FileUploader.FileUploadCallBack() {
                @Override
                public void callBack(JSONObject object) {
                    try {
                        if(200==object.getInt("code")){
                            String image = object.getJSONObject("datas").getString("agent_img");
                            images.put(path,image);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String path) {
        images.remove(path);
    }
}

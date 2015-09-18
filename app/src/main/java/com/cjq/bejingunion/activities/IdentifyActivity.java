package com.cjq.bejingunion.activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by CJQ on 2015/9/11.
 */
public class IdentifyActivity extends BaseActivity implements View.OnClickListener, ImageSelectorView.OnImageChangeListener {

    private AQuery aq;
    private ImageSelectorView imageSelectorView;
    private Map<String, String> images = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.identity_authentication);
        aq = new AQuery(this);

        Intent intent = getIntent();

        aq.id(R.id.identify_set_meal).text(intent.getStringExtra("setMeal"));
        aq.id(R.id.identify_price).text(intent.getStringExtra("price"));
        aq.id(R.id.identify_phone_number).text(intent.getStringExtra("phoneNumber"));
        aq.id(R.id.identify_back).clicked(this, "finish");
        aq.id(R.id.identify_submit).clicked(this, "submit");
        SpannableString spannableString = new SpannableString("根据国家工信部《电话用户真实身份信息登记规定》（工业和信息化部令第25号）文件要求，用户通过网络办理新号码须上传身份证照片并通过实名验证,收货时须再出示本人身份证原并提供身份证复印件以做备案");
        URLSpan urlSpan = new URLSpan(CommonDataObject.IDENTIFY_NOTIFY1);
        spannableString.setSpan(urlSpan, 7, 37, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableString spannableString1 = new SpannableString("我已经同意《入网协议》");
        URLSpan urlSpan1 = new URLSpan(CommonDataObject.IDENTIFY_NOTIFY2);
        spannableString1.setSpan(urlSpan1, 5, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        aq.id(R.id.identify_text1).text(spannableString).getTextView().setMovementMethod(LinkMovementMethod.getInstance());
        aq.id(R.id.identify_check_box).text(spannableString1).getTextView().setMovementMethod(LinkMovementMethod.getInstance());

        imageSelectorView = (ImageSelectorView) aq.id(R.id.identify_add_pic).getView();
        imageSelectorView.setOnAddButtonClickListener(this);
        imageSelectorView.setImageChangeListener(this);
        imageSelectorView.setW(120);
        imageSelectorView.setH(80);
        imageSelectorView.setMax(1);

    }

    public void submit() {
        if(!aq.id(R.id.identify_check_box).isChecked()){
            Toast.makeText(this,"不同意入网协议吗？",Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            Map<String, String> params = new HashMap<>();

            StringBuilder builder = new StringBuilder();
            for (String s : images.values()) {
                builder.append(s).append(",");
            }
            String ids="";
            if( images.size()>0)
            ids = builder.toString().substring(0, builder.length() - 1);

            params.put("username", aq.id(R.id.identify_username).getText().toString());
            params.put("ID_card", aq.id(R.id.identify_ID_card).getText().toString());
            params.put("img_ids", ids);

            aq.ajax(CommonDataObject.IDENTIFY_SUBMIT_URL, params, JSONObject.class, new AjaxCallback<JSONObject>() {
                @Override
                public void callback(String url, JSONObject object, AjaxStatus status) {
                    System.out.println(object.toString());
                    try {
                        if (object.getInt("code") == 200) {
                            String id = object.getJSONObject("datas").getString("phone_additional_id");

                            Intent intent = new Intent();
                            intent.putExtra("id", id);
                            setResult(RESULT_OK, intent);
                            finish();
                        }else{
                            Toast.makeText(IdentifyActivity.this,object.getJSONObject("datas").getString("error"),Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                if ("content".equals(uri.getScheme())) {
                    String[] proj = {MediaStore.Images.Media.DATA};
                    Cursor cursor = managedQuery(uri, proj, null, null, null);
                    int column_index = cursor
                            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    String path = cursor.getString(column_index);

                    imageSelectorView.addImage(path);
                } else if ("file".equals(uri.getScheme())) {
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
            FileUploader.upload(CommonDataObject.COMMON_UPLOAD_IMAGE_URL, "pic", path, params, new FileUploader.FileUploadCallBack() {
                @Override
                public void callBack(JSONObject object) {
                    try {
                        if (200 == object.getInt("code")) {
                            String imageId = object.getJSONObject("datas").getString("img_id");
                            images.put(path, imageId);
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

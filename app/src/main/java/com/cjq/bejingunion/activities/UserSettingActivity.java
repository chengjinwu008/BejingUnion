package com.cjq.bejingunion.activities;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.cjq.bejingunion.BaseActivity;
import com.cjq.bejingunion.CommonDataObject;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.dialog.MyToast;
import com.cjq.bejingunion.event.EventLogout;
import com.cjq.bejingunion.event.EventPortraitChange;
import com.cjq.bejingunion.utils.FileUploader;
import com.cjq.bejingunion.utils.LoginUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ypy.eventbus.EventBus;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.sql.Time;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by CJQ on 2015/8/19.
 */
public class UserSettingActivity extends BaseActivity implements View.OnFocusChangeListener {

    private AQuery aq;
    public String nickName;
    private String avator;
    private int sex;
    private String birthday;
    public String mobile;
    private int is_agent;
    private int is_apply_agent;
    private boolean changed = false;
    private boolean inEditMode = false;
    private EditText e;
    private String field;
    private InputMethodManager inputManager;
    private boolean inChooseMode = false;
    private RadioGroup radioGroup;
    private TextView confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_setting);

        aq = new AQuery(this);

        aq.id(R.id.user_setting_edit_nickname_click).clicked(this, "editNickname");
        aq.id(R.id.user_setting_edit_mobile_click).clicked(this, "editMobile");
        aq.id(R.id.user_setting_sex_click).clicked(this, "editSex");
        aq.id(R.id.user_setting_edit_birthday_click).clicked(this, "editBirthday");
        aq.id(R.id.user_setting_back).clicked(this, "closeUp");
        aq.id(R.id.user_center_setting_portrait).clicked(this, "changePortrait");
        aq.id(R.id.user_setting_edit_nickname).getEditText().setOnFocusChangeListener(this);
        aq.id(R.id.user_setting_edit_mobile).getEditText().setOnFocusChangeListener(this);
        aq.id(R.id.user_setting_commit).clicked(this, "submit");
        Map<String, String> params = new HashMap<>();
        try {
            params.put("key", LoginUtil.getKey(this));
            aq.ajax(CommonDataObject.USER_INFO_SETTING, params, JSONObject.class, new AjaxCallback<JSONObject>() {
                @Override
                public void callback(String url, JSONObject object, AjaxStatus status) {
                    System.out.println(object.toString());
                    try {
                        if (200 == object.getInt("code")) {
                            JSONObject member_info = object.getJSONObject("datas").getJSONObject("member_info");

                            nickName = member_info.getString("member_nickname");
                            avator = member_info.getString("avator");
                            sex = member_info.getInt("member_sex");
                            birthday = member_info.getString("member_birthday");
                            mobile = member_info.getString("member_mobile");
                            is_agent = member_info.getInt("is_agent");
                            is_apply_agent = member_info.getInt("is_apply_agent");

                            aq.id(R.id.user_setting_showing_nick_name).text(nickName.equals("")?mobile:nickName);
                            aq.id(R.id.user_center_setting_portrait).image(avator, true, false);
                            aq.id(R.id.user_setting_edit_nickname).text(nickName);
                            aq.id(R.id.user_setting_username).text(member_info.getString("user_name"));

                            switch (sex) {
                                case 1:
                                    aq.id(R.id.user_setting_sex).text("男");
                                    break;
                                case 2:
                                    aq.id(R.id.user_setting_sex).text("女");
                                    break;
                                default:
                                    aq.id(R.id.user_setting_sex).text("保密");
                                    break;
                            }
                            aq.id(R.id.user_setting_edit_birthday).text(birthday);
                            aq.id(R.id.user_setting_edit_mobile).text(mobile);

                            if (1 != is_agent) {
                                aq.id(R.id.user_setting_vip).background(R.drawable.btn1);
                                aq.id(R.id.user_setting_become_partner).visible();
                                if (is_apply_agent > 0) {
                                    aq.id(R.id.user_setting_become_partner_text).text("代理商申请中……").textColor(Color.parseColor("#BDBDBD"));
                                } else {
                                    aq.id(R.id.user_setting_become_partner).clicked(UserSettingActivity.this, "jumpPartnerFormActivity");
                                }
                            } else {
                                aq.id(R.id.user_setting_vip).background(R.drawable.btn);
                                aq.id(R.id.user_setting_become_partner).gone();
                            }
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

        aq.id(R.id.user_setting_logout).clicked(this, "doLogout");
    }

    public void doLogout() {
        LoginUtil.logout(this);
        finish();
    }

    public void jumpPartnerFormActivity() {
        Intent intent = new Intent(this, PartnerFormActivity.class);
        startActivityForResult(intent, 0);
    }

    public void editNickname() {
        showEditor(R.id.user_setting_edit_nickname, "nickName");
    }

    public void editMobile() {
        showEditor(R.id.user_setting_edit_mobile, "mobile");
    }

    private void showEditor(int id, final String field) {

        if (inEditMode) {
            e.clearFocus();
            e = null;
            inEditMode = false;
            UserSettingActivity.this.field = null;
        }

        if (inChooseMode) {
            final ViewGroup group = (ViewGroup) aq.id(R.id.user_setting_sex_click).getView();
            group.removeView(radioGroup);
            group.removeView(confirm);

            aq.id(R.id.user_setting_sex_icon).visible();
            aq.id(R.id.user_setting_sex).visible();

            inChooseMode = false;
        }

        inEditMode = true;

        e = aq.id(id).getEditText();
        this.field = field;
        e.setFocusable(true);
        e.setFocusableInTouchMode(true);
        e.requestFocus();

        e.setImeOptions(EditorInfo.IME_ACTION_DONE);
        e.setSingleLine();
        e.selectAll();
        if (inputManager == null)
            inputManager =

                    (InputMethodManager) e.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(e, 0);

        e.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String s = e.getText().toString();
                    try {
                        if (!s.equals(UserSettingActivity.this.getClass().getField(UserSettingActivity.this.field).get(UserSettingActivity.this))) {
                            changed = true;
                            if (aq.id(R.id.user_setting_commit).getView().getVisibility() != View.VISIBLE) {
                                aq.id(R.id.user_setting_commit).visible();
                            }
                        }
                        UserSettingActivity.this.getClass().getField(UserSettingActivity.this.field).set(UserSettingActivity.this, s);
                    } catch (IllegalAccessException e1) {
                        e1.printStackTrace();
                    } catch (NoSuchFieldException e1) {
                        e1.printStackTrace();
                    }
                    e.clearFocus();
                    inEditMode = false;
                    inputManager.hideSoftInputFromWindow(e.getWindowToken(), 0);
                    e = null;
                    UserSettingActivity.this.field = null;
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            v.setFocusable(false);
            v.setFocusableInTouchMode(false);
            try {
                ((TextView) v).setText(UserSettingActivity.this.getClass().getField(UserSettingActivity.this.field).get(this).toString());
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            } catch (NoSuchFieldException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK && (inChooseMode || inEditMode)) {
            if (inEditMode) {
                e.clearFocus();
                e = null;
                inEditMode = false;
                UserSettingActivity.this.field = null;
            }

            if (inChooseMode) {
                final ViewGroup group = (ViewGroup) aq.id(R.id.user_setting_sex_click).getView();
                group.removeView(radioGroup);
                group.removeView(confirm);

                aq.id(R.id.user_setting_sex_icon).visible();
                aq.id(R.id.user_setting_sex).visible();

                inChooseMode = false;
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void submit() {
        Map<String,String> params = new HashMap<>();
        try {
            params.put("key",LoginUtil.getKey(this));
            params.put("member_nickname",nickName);
            params.put("member_sex", String.valueOf(sex));
            params.put("member_birthday",birthday);
            params.put("member_mobile", mobile);

            aq.ajax(CommonDataObject.USER_INFO_SETTING_COMMIT,params,JSONObject.class,new AjaxCallback<JSONObject>(){
                @Override
                public void callback(String url, JSONObject object, AjaxStatus status) {
                    try {
                        if(200==object.getInt("code"))
                        {
//                            Toast.makeText(UserSettingActivity.this,object.getJSONObject("datas").getString("msg"),Toast.LENGTH_SHORT).show();
                            MyToast.showText(UserSettingActivity.this,object.getJSONObject("datas").getString("msg"));
                            changed=false;
                            aq.id(R.id.user_setting_commit).gone();
                        }else{
//                            Toast.makeText(UserSettingActivity.this,object.getJSONObject("datas").getString("error"),Toast.LENGTH_SHORT).show();
                            MyToast.showText(UserSettingActivity.this, object.getJSONObject("datas").getString("error"),R.drawable.a2);
                        }
                        super.callback(url, object, status);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }
            });
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public void editSex() {
        if (inEditMode) {
            e.clearFocus();
            e = null;
            inEditMode = false;
            UserSettingActivity.this.field = null;
        }

        if (!inChooseMode) {
            inChooseMode = true;

            radioGroup = new RadioGroup(this);
            radioGroup.setLayoutParams(aq.id(R.id.user_setting_sex).getView().getLayoutParams());

            radioGroup.setOrientation(LinearLayout.HORIZONTAL);

            RadioButton radioButton1 = new RadioButton(this);
            radioButton1.setText("男");

            RadioButton radioButton2 = new RadioButton(this);
            radioButton2.setText("女");

            RadioButton radioButton3 = new RadioButton(this);
            radioButton3.setText("保密");

            radioGroup.addView(radioButton1, 0);
            radioGroup.addView(radioButton2, 1);
            radioGroup.addView(radioButton3, 2);

            final ViewGroup group = (ViewGroup) aq.id(R.id.user_setting_sex_click).getView();
            group.addView(radioGroup);

            confirm = new TextView(this);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 20, 0);
            confirm.setLayoutParams(params);

            confirm.setPadding(10, 5, 10, 5);
            confirm.setText("确定");

            confirm.setBackgroundResource(R.drawable.blue_5dp_corner);
            confirm.setTextColor(Color.WHITE);

            group.addView(confirm);

            ((RadioButton) radioGroup.getChildAt(sex - 1)).setChecked(true);

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < 3; i++) {
                        if (((RadioButton) radioGroup.getChildAt(i)).isChecked()) {
                            if (i + 1 != sex) {
                                changed = true;
                                if (aq.id(R.id.user_setting_commit).getView().getVisibility() != View.VISIBLE) {
                                    aq.id(R.id.user_setting_commit).visible();
                                }
                            }
                            sex = i + 1;
                            break;
                        }
                    }

                    group.removeView(radioGroup);
                    group.removeView(confirm);

                    aq.id(R.id.user_setting_sex_icon).visible();
                    aq.id(R.id.user_setting_sex).visible();

                    switch (sex) {
                        case 1:
                            aq.id(R.id.user_setting_sex).text("男");
                            break;
                        case 2:
                            aq.id(R.id.user_setting_sex).text("女");
                            break;
                        default:
                            aq.id(R.id.user_setting_sex).text("保密");
                            break;
                    }

                    inChooseMode = false;
                }
            });

            aq.id(R.id.user_setting_sex_icon).gone();
            aq.id(R.id.user_setting_sex).gone();
        }
    }

    public void editBirthday() {
        if (inEditMode) {
            e.clearFocus();
            e = null;
            inEditMode = false;
            UserSettingActivity.this.field = null;
        }

        if (inChooseMode) {
            final ViewGroup group = (ViewGroup) aq.id(R.id.user_setting_sex_click).getView();
            group.removeView(radioGroup);
            group.removeView(confirm);

            aq.id(R.id.user_setting_sex_icon).visible();
            aq.id(R.id.user_setting_sex).visible();

            inChooseMode = false;
        }

        String[] times = birthday.split("-");
        if("".equals(birthday)){
            times=new String[]{"1970","01","01"};
        }

        DatePickerDialog dialog = new DatePickerDialog(this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                NumberFormat format = NumberFormat.getNumberInstance();
                format.setMaximumIntegerDigits(4);
                format.setMinimumIntegerDigits(2);
                format.setMaximumFractionDigits(0);
                format.setMinimumFractionDigits(0);

                String x = year+ "-" + format.format(monthOfYear) + "-" + format.format(dayOfMonth);

                if(!x.equals(birthday)){
                    birthday=x;
                    changed=true;
                    if (aq.id(R.id.user_setting_commit).getView().getVisibility() != View.VISIBLE) {
                        aq.id(R.id.user_setting_commit).visible();
                    }
                }

                aq.id(R.id.user_setting_edit_birthday).text(birthday);
            }
        },Integer.parseInt(times[0]),Integer.parseInt(times[1]),Integer.parseInt(times[2]));

        dialog.setTitle("选择出生日期");
        dialog.show();
    }

    public void closeUp(){
        finish();
    }

    public void changePortrait(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(1==requestCode){
            if(resultCode==RESULT_OK){
                Uri uri = data.getData();
                if("content".equals(uri.getScheme())){
                    String[] proj = { MediaStore.Images.Media.DATA };
                    Cursor cursor = managedQuery(uri, proj, null, null, null);
                    int column_index = cursor
                            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    String path = cursor.getString(column_index);

                    uploadImage(path);
                }else
                if("file".equals(uri.getScheme()))
                {
                    String path = uri.getPath();
                    uploadImage(path);
                }
            }
        }else if(0==requestCode){
            if(resultCode==RESULT_OK){
                aq.id(R.id.user_setting_become_partner_text).text("代理商申请中……").textColor(Color.parseColor("#BDBDBD"));
                aq.id(R.id.user_setting_become_partner).clicked(this, null);
//                Toast.makeText(this, "代理商申请已经提交，请耐心等待审核结果", Toast.LENGTH_SHORT).show();
                MyToast.showText(this, "代理商申请已经提交，请耐心等待审核结果");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadImage(final String path) {

        RequestParams params = new RequestParams();
        try {
            params.put("key",LoginUtil.getKey(this));
            FileUploader.upload(CommonDataObject.UPLOAD_PORTRAIT_URL, "pic", path, params, new FileUploader.FileUploadCallBack() {
                @Override
                public void callBack(JSONObject object) {
                    try {
                        if(200==object.getInt("code")){
                            String image = object.getJSONObject("datas").getString("agent_img");
                            avator=image;
                            aq.id(R.id.user_center_setting_portrait).image(avator, true, false);
                            EventBus.getDefault().post(new EventPortraitChange(image));
                        }
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }
            });
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
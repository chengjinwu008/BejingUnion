package com.cjq.bejingunion.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.cjq.bejingunion.BaseActivity;
import com.cjq.bejingunion.CommonDataObject;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.adapter.BannerAdapter;
import com.cjq.bejingunion.adapter.DetailChoiceAdapter;
import com.cjq.bejingunion.dialog.WarningAlertDialog;
import com.cjq.bejingunion.entities.Ad;
import com.cjq.bejingunion.entities.CardNumber;
import com.cjq.bejingunion.entities.DetailChoice;
import com.cjq.bejingunion.entities.DetailItem;
import com.cjq.bejingunion.utils.GoodsUtil;
import com.cjq.bejingunion.utils.LoginUtil;
import com.cjq.bejingunion.view.BannerView;
import com.cjq.bejingunion.view.NumericView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CJQ on 2015/8/20.
 */
public class CardDetailActivity extends BaseActivity {

    private String goods_id;
    private AQuery aq;
    private TextView nameText;
    private ListView detailItemListView;
    private DetailChoiceAdapter adapter;
    private String is_virtual;
    private String is_fcode;
    private String number;
    private String numberId;
    private String price;
    private long identify_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy_phone_number_detail);

        Intent intent = getIntent();
        goods_id = intent.getStringExtra("goods_id");
        aq = new AQuery(this);

        nameText = aq.id(R.id.buy_phone_number_detail_name).getTextView();
        aq.id(R.id.buy_phone_number_detail_back).clicked(this, "closeUp");
        aq.id(R.id.buy_phone_number_detail_buy_immediately).clicked(this, "payImmediately");
        aq.id(R.id.buy_phone_number_detail_phone_number).clicked(this, "choosePhoneNumber");

        detailItemListView = aq.id(R.id.buy_phone_number_detail_detail_item).getListView();

        Map<String, String> params = new HashMap<>();
        params.put("goods_id", goods_id);

        aq.ajax(CommonDataObject.GOODS_DETAIL_URL, params, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                System.out.println(object.toString());
                try {
                    if (200 == object.getInt("code")) {
                        JSONObject goods_info = object.getJSONObject("datas").getJSONObject("goods_info");
                        String name = goods_info.getString("goods_name");

                        JSONArray images = object.getJSONObject("datas").getJSONArray("goods_image");

                        is_virtual = goods_info.getString("is_virtual");
                        is_fcode = goods_info.getString("is_fcode");

                        aq.id(R.id.buy_phone_number_detail_image).image(images.getString(0));
                        price =  goods_info.getString("goods_price");
                        aq.id(R.id.buy_phone_number_detail_price).text(price);

                        aq.id(R.id.buy_phone_number_detail_detail).text(goods_info.getString("mobile_body"));

                        JSONArray spec_info = goods_info.getJSONArray("spec_info");
                        List<DetailItem> detailItems = new ArrayList<DetailItem>();
                        for (int i = 0; i < spec_info.length(); i++) {
                            JSONObject o = spec_info.getJSONObject(i);
                            DetailItem detailItem = new DetailItem(o.getString("attr_type"), o.getString("attr_id"));

                            JSONArray attr_value = o.getJSONArray("attr_value");
                            Map<Integer, DetailChoice> choices = new HashMap<>();
                            for (int j = 0; j < attr_value.length(); j++) {
                                JSONObject item = attr_value.getJSONObject(j);
                                DetailChoice choice = new DetailChoice(item.getString("value"), item.getString("id"), item.getString("src"));
                                choices.put(item.getInt("id"), choice);
                            }
                            detailItem.setDetailChoices(choices);
                            detailItem.setChosenId(o.getString("chosen_id"));
                            detailItems.add(detailItem);
                        }

                        adapter = new DetailChoiceAdapter(detailItems, CardDetailActivity.this);

                        detailItemListView.setAdapter(adapter);
                        nameText.setText(name);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                super.callback(url, object, status);
            }
        });
    }

    public void closeUp() {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    String id = data.getStringExtra("resultId");
                    int position = data.getIntExtra("position", 0);
                    DetailItem detailItem = (DetailItem) adapter.getItem(position);
                    detailItem.setChosenId(id);
                    adapter.notifyDataSetChanged();
                }
                break;
            case 1:
                if(resultCode==RESULT_OK){
                    CardNumber cardNumber =  data.getParcelableExtra("cardNumber");
                    this.number = cardNumber.getNumber();
                    this.numberId = cardNumber.getId();
                    aq.id(R.id.buy_phone_number_detail_phone_number).text(number);
                }
                break;
            case 2:
                if(resultCode==RESULT_OK){
                    identify_id = data.getLongExtra("id",0);
                    //
                    submit();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void submit(){
        // TODO: 2015/9/11 加参数项
        Intent intent = new Intent(this,PhoneNumberConfirmActivity.class);
        intent.putExtra("cart_id",goods_id+"|"+1);
        intent.putExtra("phone_additional_id",identify_id);
        intent.putExtra("phone_id",numberId);
        intent.putExtra("ifcart",is_fcode);
        startActivity(intent);
        finish();
    }

    public void payImmediately() {
        DetailItem detailItem = (DetailItem) adapter.getItem(0);
        DetailChoice choice = detailItem.getDetailChoices().get(new Integer(detailItem.getChosenId()));

        GoodsUtil.showIdentify(this,choice.getValue(),price,number,2);
    }

    public void choosePhoneNumber() {
        Intent intent = new Intent(this,PhoneNumberActivity.class);
        startActivityForResult(intent,1);
    }
}

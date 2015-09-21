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
import com.cjq.bejingunion.dialog.MyToast;
import com.cjq.bejingunion.entities.Ad;
import com.cjq.bejingunion.entities.CardNumber;
import com.cjq.bejingunion.entities.DetailChoice;
import com.cjq.bejingunion.entities.DetailItem;
import com.cjq.bejingunion.utils.GoodsUtil;
import com.cjq.bejingunion.utils.LoginUtil;
import com.cjq.bejingunion.view.BannerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by CJQ on 2015/8/20.
 */
public class ContractMachineDetailActivity extends BaseActivity {

    private String goods_id;
    private AQuery aq;
    private TextView nameText;
    private TextView evaluateCountText;
    private TextView collectCountText;
    private BannerView detail_banner;
    private ListView detailItemListView;
    private DetailChoiceAdapter adapter;
    private int collectionCount;
    private String is_virtual;
    private String is_fcode;
    private String number;
    private String numberId;
    private String price;
    private String identifyxid;
    private Map<String,String> ids=new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contract_machine_detail);

        Intent intent = getIntent();
        goods_id = intent.getStringExtra("goods_id");
        aq = new AQuery(this);

        nameText = aq.id(R.id.contract_machine_detail_name).getTextView();
        aq.id(R.id.contract_machine_detail_back).clicked(this, "closeUp");
        aq.id(R.id.contract_machine_detail_add_to_collection).clicked(this, "addToCollection");
        aq.id(R.id.contract_machine_detail_show_detail_info).clicked(this, "showDetailWap");
        aq.id(R.id.contract_machine_detail_jump_evaluation).clicked(this, "showEvaluations");
        aq.id(R.id.contract_machine_detail_pay_immediately).clicked(this, "payImmediately");
        aq.id(R.id.contract_machine_detail_const_3_detail).clicked(this, "choosePhoneNumber");

        evaluateCountText = aq.id(R.id.contract_machine_detail_evaluation_count).getTextView();
        collectCountText = aq.id(R.id.contract_machine_detail_collect_count).getTextView();
        detail_banner = (BannerView) aq.id(R.id.contract_machine_detail_banner).getView();
        detailItemListView = aq.id(R.id.contract_machine_detail_item).getListView();

        requestData();
    }

    private void requestData() {
        Map<String, String> params = new HashMap<>();
        params.put("goods_id", goods_id);

        aq.ajax(CommonDataObject.GOODS_DETAIL_URL, params, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
//                System.out.println(object.toString());
                try {
                    if (200 == object.getInt("code")) {
                        JSONObject goods_info = object.getJSONObject("datas").getJSONObject("goods_info");
                        String name = goods_info.getString("goods_name");

                        JSONArray images = object.getJSONObject("datas").getJSONArray("goods_image");

                        List<Ad> adList = new ArrayList<Ad>();

                        for (int i = 0; i < images.length(); i++) {
                            String image = images.getString(i);
                            Ad ad = new Ad(ContractMachineDetailActivity.this, image, "");
                            adList.add(ad);
                        }

                        ids.clear();
                        JSONObject aids =  object.getJSONObject("datas").getJSONObject("spec_list");
                        Iterator<String> keys = aids.keys();
                        while (keys.hasNext()){
                            String k =  keys.next();
                            ids.put(k,aids.getString(k));
                        }

                        is_virtual = goods_info.getString("is_virtual");
                        is_fcode = goods_info.getString("is_fcode");

                        detail_banner.setAdapter(new BannerAdapter(ContractMachineDetailActivity.this, adList));

                        price = goods_info.getString("goods_price");
                        aq.id(R.id.contract_machine_detail_const_price).text(price);
                        aq.id(R.id.contract_machine_detail_const_2_detail).text(goods_info.getString("goods_storage"));


                        JSONArray spec_info = goods_info.getJSONArray("spec_info");
                        List<DetailItem> detailItems = new ArrayList<DetailItem>();
                        for (int i = 0; i < spec_info.length(); i++) {
                            JSONObject o = spec_info.getJSONObject(i);
                            DetailItem detailItem = new DetailItem(o.getString("attr_type"), o.getString("attr_id"));

                            JSONArray attr_value = o.getJSONArray("attr_value");
                            Map<Integer, DetailChoice> choices = new HashMap<Integer, DetailChoice>();
                            for (int j = 0; j < attr_value.length(); j++) {
                                JSONObject item = attr_value.getJSONObject(j);
                                DetailChoice choice = new DetailChoice(item.getString("value"), item.getString("id"), item.getString("src"));
                                choices.put(item.getInt("id"), choice);
                            }
                            detailItem.setDetailChoices(choices);
                            detailItem.setChosenId(o.getString("chosen_id"));
                            detailItems.add(detailItem);
                        }

                        adapter = new DetailChoiceAdapter(detailItems, ContractMachineDetailActivity.this);

                        detailItemListView.setAdapter(adapter);
                        collectionCount = goods_info.getInt("goods_collect");
                        nameText.setText(name);
                        evaluateCountText.setText("(" + goods_info.getString("evaluation_count") + ")");
                        collectCountText.setText("(" + collectionCount + ")");
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

                    StringBuilder builder = new StringBuilder();
                    for(int i = 0;i<adapter.getCount();i++){
                        DetailItem item = (DetailItem) adapter.getItem(i);
                        builder.append(item.getDetailChoices().get(Integer.valueOf(item.getChosenId())).getId()).append("|");
                    }
                    String idString = builder.toString().substring(0, builder.length() - 1);

                    goods_id = ids.get(idString);

//                    adapter.notifyDataSetChanged();
                    requestData();
                }
                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    CardNumber cardNumber = data.getParcelableExtra("cardNumber");
                    this.number = cardNumber.getNumber();
                    this.numberId = cardNumber.getId();
                    aq.id(R.id.contract_machine_detail_const_3_detail).text(number);
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    identifyxid = data.getStringExtra("id");
                    //
                    submit();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void submit() {
        Intent intent = new Intent(this, ContractMachineConfirmActivity.class);
        intent.putExtra("cart_id", goods_id + "|" + 1);
        intent.putExtra("phone_additional_id",identifyxid);
        intent.putExtra("phoneNumber",number);
        intent.putExtra("phone_id",numberId);
        intent.putExtra("ifcart", "0");
        startActivity(intent);
        finish();
    }

    public void addToCollection() {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("key", LoginUtil.getKey(this));
            params.put("goods_id", goods_id);

            aq.ajax(CommonDataObject.COLLECTION_ADD_URL, params, JSONObject.class, new AjaxCallback<JSONObject>() {
                @Override
                public void callback(String url, JSONObject object, AjaxStatus status) {
                    try {
                        if (200 == object.getInt("code")) {
                            //添加收藏成功
                            collectionCount++;
                            collectCountText.setText("(" + collectionCount + ")");
//                            Toast.makeText(ContractMachineDetailActivity.this, getString(R.string.add_to_collection_succeed), Toast.LENGTH_SHORT).show();
                            MyToast.showText(ContractMachineDetailActivity.this, getString(R.string.add_to_collection_succeed));
                        } else {
//                            Toast.makeText(ContractMachineDetailActivity.this, object.getJSONObject("datas").getString("error"), Toast.LENGTH_SHORT).show();
                            MyToast.showText(ContractMachineDetailActivity.this, object.getJSONObject("datas").getString("error"), R.drawable.a2);
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

    public void showDetailWap() {
        Intent intent = new Intent(this, CommonWebViewActivity.class);
        intent.putExtra("url", CommonDataObject.DETAIL_WAP + "?goods_id=" + goods_id);
        startActivity(intent);
    }

    public void showEvaluations() {
        GoodsUtil.showEvaluations(this, goods_id);
    }

    public void payImmediately() {
        if (numberId == null || "".equals(numberId)) {
//            Toast.makeText(this, "不选号码怎么帮您购买卡号呢？", Toast.LENGTH_SHORT).show();
            MyToast.showText(ContractMachineDetailActivity.this, "不选号码怎么帮您购买卡号呢？", R.drawable.a2);
            return;
        }
        DetailItem item = null;
        for (int i = 0; i < adapter.getCount(); i++) {
            DetailItem detailItem = (DetailItem) adapter.getItem(i);
            if ("套餐选择".equals(detailItem.getName())) {
                item = detailItem;
                break;
            }
        }
        DetailChoice choice = null;
        if (item != null) {
            choice = item.getDetailChoices().get(Integer.valueOf(item.getChosenId()));
        }

        if (choice != null)
            GoodsUtil.showIdentify(this, choice.getValue(), price, number, 2);
        else
            GoodsUtil.showIdentify(this, null, price, number, 2);

//        Intent intent = new Intent(this, OrderConfirmActivity.class);
//        intent.putExtra("cart_id", goods_id + "|" + 1);
//        intent.putExtra("ifcart", is_fcode);
//        startActivity(intent);
//        finish();
    }

    public void choosePhoneNumber() {
        Intent intent = new Intent(this, PhoneNumberActivity.class);
        startActivityForResult(intent, 1);
    }
}

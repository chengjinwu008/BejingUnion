package com.cjq.bejingunion.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.cjq.bejingunion.BaseActivity;
import com.cjq.bejingunion.CommonDataObject;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.adapter.BannerAdapter;
import com.cjq.bejingunion.entities.Ad;
import com.cjq.bejingunion.view.BannerView;

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
public class DetailActivity extends BaseActivity {

    private String goods_id;
    private AQuery aq;
    private TextView nameText;
    private TextView evaluateCountText;
    private TextView collectCountText;
    private BannerView detail_banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        Intent intent = getIntent();
        goods_id = intent.getStringExtra("goods_id");
        aq = new AQuery(this);

        nameText = aq.id(R.id.detail_name).getTextView();
        aq.id(R.id.detail_back).clicked(this, "closeUp");
        evaluateCountText = aq.id(R.id.detail_evaluation_count).getTextView();
        collectCountText = aq.id(R.id.detail_collect_count).getTextView();
        detail_banner = (BannerView) aq.id(R.id.detail_banner).getView();

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

                        JSONObject images = object.getJSONObject("datas").getJSONObject("spec_image");

                        Iterator<String> iterator = images.keys();

                        List<Ad> adList = new ArrayList<Ad>();

                        while (iterator.hasNext()) {
                            String k = iterator.next();
                            String image = images.getString(k);
                            Ad ad = new Ad(DetailActivity.this, image, k);

                            adList.add(ad);
                        }

                        detail_banner.setAdapter(new BannerAdapter(DetailActivity.this, adList));


                        nameText.setText(name);
                        evaluateCountText.setText("(" + goods_info.getString("evaluation_count") + ")");
                        collectCountText.setText("(" + goods_info.getString("goods_collect") + ")");
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
}

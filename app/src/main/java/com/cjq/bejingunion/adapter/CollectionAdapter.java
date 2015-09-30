package com.cjq.bejingunion.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.cjq.bejingunion.CommonDataObject;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.dialog.WarningAlertDialog;
import com.cjq.bejingunion.entities.CollectionToShow;
import com.cjq.bejingunion.event.EventCollectionChange;
import com.cjq.bejingunion.utils.GoodsUtil;
import com.cjq.bejingunion.utils.LoginUtil;
import com.cjq.bejingunion.view.SwipeListItemView;
import com.ypy.eventbus.EventBus;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CJQ on 2015/8/25.
 */
public class CollectionAdapter extends SwipeListAdapter {

    private List<CollectionToShow> list;

    public CollectionAdapter(Context context, List<CollectionToShow> list) {
        super(context);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.collection_list_item, null, false);
        }
        final CollectionToShow collectionToShow = list.get(position);


        final AQuery aq = new AQuery(convertView);
        aq.id(R.id.collection_image).image(collectionToShow.getGoods_image_url(), false, true);
        aq.id(R.id.collection_title).text(collectionToShow.getGoods_name());
        aq.id(R.id.collection_price).text(collectionToShow.getGoods_price());

        final View finalConvertView = convertView;
        aq.id(R.id.sliding_content).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean drewOut = ((SwipeListItemView) finalConvertView).isDrawOut();
                if (!drewOut) {
                    String goods_id = collectionToShow.getGoods_id();
                    GoodsUtil.showGoodsDetail(context, goods_id);
                }
            }
        });

        aq.id(R.id.sliding_menu).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new WarningAlertDialog(context).changeText("确定要删除这条收藏吗？").onOKClick(new Runnable() {
                    @Override
                    public void run() {
                        String favid = collectionToShow.getFav_id();
                        try {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("key", LoginUtil.getKey(context));
                            params.put("fav_id", favid);

                            aq.ajax(CommonDataObject.COLLECTION_DELETE_URL, params, JSONObject.class, new AjaxCallback<JSONObject>() {
                                @Override
                                public void callback(String url, JSONObject object, AjaxStatus status) {
                                    try {
                                        if (200 == object.getInt("code")) {
                                            ((SwipeListItemView) finalConvertView).mSmoothScrollTo(0);
                                            list.remove(position);
                                            EventBus.getDefault().post(new EventCollectionChange());
                                            notifyDataSetChanged();
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
                });
            }
        });
        return convertView;
    }
}

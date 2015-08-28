package com.cjq.bejingunion.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.cjq.bejingunion.CommonDataObject;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.activities.AddressEditActivity;
import com.cjq.bejingunion.entities.Address4Show;
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
public class AddressAdapter extends SwipeListAdapter {
    List<Address4Show> address4ShowList;

    public AddressAdapter(List<Address4Show> address4ShowList, Context context) {
        super(context);
        this.address4ShowList = address4ShowList;
    }

    @Override
    public int getCount() {
        return address4ShowList.size();
    }

    @Override
    public Object getItem(int position) {
        return address4ShowList.get(position);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final Address4Show address4Show = address4ShowList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.address_swipe_list_item, parent, false);
        }
        final AQuery aq = new AQuery(convertView);
        aq.id(R.id.address_detail).text(address4Show.getAddress());
        aq.id(R.id.address_true_name).text(address4Show.getTrueName());
        aq.id(R.id.address_mobile_number).text(address4Show.getPhoneNumber());

        final View finalConvertView = convertView;
        aq.id(R.id.sliding_content).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean drewOut = ((SwipeListItemView) finalConvertView).isDrawOut();
                if (!drewOut) {
                    String addressId = address4Show.getAddressId();
                    Intent intent = new Intent(context, AddressEditActivity.class);
                    intent.putExtra("position", position);
                    intent.putExtra("addressId", addressId);
                    ((Activity) context).startActivityForResult(intent, 1);
                }
            }
        });

        aq.id(R.id.sliding_menu).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addressId = address4Show.getAddressId();

                try {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("key", LoginUtil.getKey(context));
                    params.put("address_id", addressId);

                    aq.ajax(CommonDataObject.ADDRESS_DELETE_URL, params, JSONObject.class, new AjaxCallback<JSONObject>() {
                        @Override
                        public void callback(String url, JSONObject object, AjaxStatus status) {
                            try {
                                if (200 == object.getInt("code")) {
                                    address4ShowList.remove(position);
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

        return convertView;
    }
}

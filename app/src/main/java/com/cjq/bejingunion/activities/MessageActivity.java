package com.cjq.bejingunion.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.cjq.bejingunion.BaseActivity;
import com.cjq.bejingunion.CommonDataObject;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.adapter.MsgAdapter;
import com.cjq.bejingunion.dialog.WarningAlertDialog;
import com.cjq.bejingunion.entities.Evaluation;
import com.cjq.bejingunion.utils.LoginUtil;
import com.cjq.bejingunion.view.MyRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CJQ on 2015/9/15.
 */
public class MessageActivity extends BaseActivity implements MyRefreshLayout.onLoadListener,MyRefreshLayout.OnRefreshListener, AdapterView.OnItemLongClickListener {

    private AQuery aq;
    private int cp = 1;
    private List<Evaluation> evaluationList;
    private BaseAdapter adapter;
    private MyRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.msg_list);

        aq = new AQuery(this);

        aq.id(R.id.address_title).text("消息中心");

        aq.id(R.id.address_add_new).gone();

//        aq.id(R.id.address_list).adapter()
        evaluationList = new ArrayList<Evaluation>();

        adapter = new MsgAdapter(this,evaluationList);

        aq.id(R.id.msg_list_list).adapter(adapter);
        aq.id(R.id.msg_list_list).getListView().setOnItemLongClickListener(this);

        refreshLayout = (MyRefreshLayout) aq.id(R.id.msg_list_refresh).getView();
        refreshLayout.setOnLoadListener(this);
        refreshLayout.setOnRefreshListener(this);

        requestData();
    }

    private void requestData() {
        try {

            Map<String, String> params = new HashMap<>();
            params.put("key", LoginUtil.getKey(this));
            params.put("curpage", String.valueOf(cp));

            aq.ajax(CommonDataObject.MSG_LIST_URL, params, JSONObject.class, new AjaxCallback<JSONObject>() {
                @Override
                public void callback(String url, JSONObject object, AjaxStatus status) {
                    try {
                        JSONArray datas = object.getJSONArray("datas");
                        if (200 == object.getInt("code") && datas.length()>1) {

                            for(int i = 0;i<datas.length();i++){
                                JSONObject o = datas.getJSONObject(i);
                                Evaluation e = new Evaluation("",o.getString("from_member_name"),o.getLong("message_update_time"),o.getString("message_body"),o.getString("message_id"));
                                evaluationList.add(e);
                                refreshLayout.setLoading(false);
                                refreshLayout.setRefreshing(false);
                            }
                            adapter.notifyDataSetChanged();
                        }else{
                            cp--;
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
    public void onLoad() {
        cp++;
        requestData();
        refreshLayout.setLoading(true);
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        cp=1;
        evaluationList.clear();
        adapter.notifyDataSetChanged();
        requestData();
    }

    public void deleteMSG(final int i){
        Map<String, String> params = new HashMap<String, String>();
        Evaluation e = evaluationList.get(i);
        try {
            params.put("key", LoginUtil.getKey(MessageActivity.this));
            params.put("message_id", e.getId());
            params.put("drop_type", "msg_system");

            aq.ajax(CommonDataObject.MSG_DEL_URL, params, JSONObject.class, new AjaxCallback<JSONObject>() {
                @Override
                public void callback(String url, JSONObject object, AjaxStatus status) {
                    try {
                        if (200 == object.getInt("code")) {
                            Toast.makeText(MessageActivity.this, object.getJSONObject("datas").getString("msg"), Toast.LENGTH_SHORT).show();
                            evaluationList.remove(i);
                        } else {
                            Toast.makeText(MessageActivity.this, object.getJSONObject("datas").getString("error"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }

                    super.callback(url, object, status);
                }
            });
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        new WarningAlertDialog(this).changeText("确定删除该条信息吗？").onOKClick(new Runnable() {
            @Override
            public void run() {
                deleteMSG(position);
            }
        });

        return true;
    }
}

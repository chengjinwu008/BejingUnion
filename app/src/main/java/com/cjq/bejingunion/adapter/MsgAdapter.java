package com.cjq.bejingunion.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.cjq.bejingunion.CommonDataObject;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.entities.Evaluation;
import com.cjq.bejingunion.utils.LoginUtil;
import com.daimajia.swipe.SwipeLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CJQ on 2015/9/15.
 */
public class MsgAdapter extends SwipeListAdapter {

    List<Evaluation> evaluationList;
    Context context;

    public MsgAdapter(Context context, List<Evaluation> evaluationList) {
        super(context);
        this.context = context;
        this.evaluationList = evaluationList;
    }

    @Override
    public int getCount() {
        return evaluationList.size();
    }

    @Override
    public Object getItem(int position) {
        return evaluationList.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.msg_item, parent, false);
        }
        SwipeLayout swipeLayout = (SwipeLayout) convertView;
        swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

        final Evaluation e = evaluationList.get(position);
        final AQuery a = new AQuery(convertView);
        a.id(R.id.msg_item_username).text(e.getUsername());
        a.id(R.id.msg_item_content).text(e.getContent());

        swipeLayout.addDrag(SwipeLayout.DragEdge.Right, a.id(R.id.sliding_menu).getView());

        DateFormat dateFormat = DateFormat.getDateTimeInstance();

        a.id(R.id.msg_item_time).text(dateFormat.format(e.getTime()*1000));

        return convertView;
    }
}

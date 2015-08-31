package com.cjq.bejingunion.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.androidquery.AQuery;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.activities.CommonListActivity;
import com.cjq.bejingunion.entities.DetailChoice;
import com.cjq.bejingunion.entities.DetailItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CJQ on 2015/8/27.
 */
public class DetailChoiceAdapter extends BaseAdapter {
    List<DetailItem> detailItems;
    Context context;

    public DetailChoiceAdapter(List<DetailItem> detailItems, Context context) {
        this.detailItems = detailItems;
        this.context = context;
    }

    @Override
    public int getCount() {
        return detailItems.size();
    }

    @Override
    public Object getItem(int position) {
        return detailItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.detail_item, parent, false);
        }
        DetailItem item = detailItems.get(position);
        AQuery aq = new AQuery(convertView);
        final ArrayList<DetailChoice> choices = new ArrayList<>();
        for (DetailChoice c : item.getDetailChoices().values()) {
            choices.add(c);
        }
        aq.id(R.id.detail_item_type_name).text(item.getName());
        if (item.getDetailChoices().size() > 0)
            aq.id(R.id.detail_item_chosen_name).text(item.getDetailChoices().get(Integer.parseInt(item.getChosenId())).getValue());
        else
            aq.id(R.id.detail_item_chosen_name).text("");
        if (item.getDetailChoices().size() > 1) {
            aq.id(R.id.detail_item_into).visible();
            convertView.setClickable(true);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CommonListActivity.class);
                    intent.putExtra("ListNo", CommonListActivity.CHOICE_LIST);
                    intent.putExtra("position", position);
                    intent.putParcelableArrayListExtra("choices", choices);
                    ((Activity) context).startActivityForResult(intent, 0);
                }
            });
        } else {
            aq.id(R.id.detail_item_into).invisible();
            convertView.setClickable(false);
        }
        return convertView;
    }
}

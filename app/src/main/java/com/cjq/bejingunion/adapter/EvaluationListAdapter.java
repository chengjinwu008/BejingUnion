package com.cjq.bejingunion.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.androidquery.AQuery;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.entities.Goods4IndexList;
import com.cjq.bejingunion.view.PinnedSectionListView;

import java.util.List;

/**
 * Created by CJQ on 2015/9/15.
 */
public class EvaluationListAdapter  extends BaseAdapter implements PinnedSectionListView.PinnedSectionListAdapter{
    private AQuery aq;

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return true;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    List<Goods4IndexList> goods4IndexListList;
    Context context;

    public EvaluationListAdapter(List<Goods4IndexList> goods4IndexListList, Context context) {
        this.goods4IndexListList = goods4IndexListList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return goods4IndexListList.size();
    }

    @Override
    public Object getItem(int position) {
        return goods4IndexListList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Goods4IndexList g=  goods4IndexListList.get(position);

        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.items4evaluation,parent,false);
        }

        aq = new AQuery(convertView);

        aq.id(R.id.evaluate_item_image).image(g.getGoods_image_url(),false,true);
        aq.id(R.id.evaluation_item_title).text(g.getGoods_name());
        aq.id(R.id.evaluate_price).text("￥"+g.getGoods_price());
        aq.id(R.id.evaluation_count).text("已有"+g.getMarket_price()+"人评价");

        aq.id(R.id.evaluation_go_write_evaluation).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 跳转到写评论页面
            }
        });

        return convertView;
    }
}

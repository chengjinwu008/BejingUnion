package com.cjq.bejingunion.adapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.androidquery.AQuery;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.entities.Goods4OrderList;
import com.cjq.bejingunion.utils.GoodsUtil;

import java.util.List;

/**
 * Created by CJQ on 2015/9/15.
 */
public class OrderItemAdapter extends BaseAdapter  {
    List<Goods4OrderList> goods4OrderListList;
    Context context;

    public OrderItemAdapter(List<Goods4OrderList> goods4OrderListList, Context context) {
        this.goods4OrderListList = goods4OrderListList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return goods4OrderListList.size();
    }

    @Override
    public Object getItem(int position) {
        return goods4OrderListList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.order_item_item,parent,false);
        }
        AQuery aq = new AQuery(convertView);
        Goods4OrderList  g = goods4OrderListList.get(position);

        aq.id(R.id.order_item_image).image(g.getPortrait(), false, true, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, context.getResources().getDisplayMetrics()), R.drawable.nopic10);
        aq.id(R.id.order_item_name).text(g.getName());
        aq.id(R.id.order_item_price).text("￥" + g.getPrice4One() + "×" + g.getCount());

        final String goods_id =g.getGoods_id();
        final String type = g.getGc_id();

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (type){
                    case "1":
                        GoodsUtil.showGoodsDetail(context,goods_id, GoodsUtil.TYPE.BROAD_BAND);
                        break;
                    case "2":
                        GoodsUtil.showGoodsDetail(context,goods_id, GoodsUtil.TYPE.CARD);
                        break;
                    case "3":
                        GoodsUtil.showGoodsDetail(context,goods_id, GoodsUtil.TYPE.CONTRACT_MACHINE);
                        break;
                    case "4":
                        GoodsUtil.showGoodsDetail(context,goods_id, GoodsUtil.TYPE.DEFAULT);
                        break;
                }
            }
        });

        return convertView;
    }
}

package com.cjq.bejingunion.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.androidquery.AQuery;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.entities.Evaluation;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by CJQ on 2015/8/29.
 */
public class EvaluationAdapter extends BaseAdapter {
    List<Evaluation> evaluationList;
    Context context;

    public EvaluationAdapter(List<Evaluation> evaluationList, Context context) {
        this.evaluationList = evaluationList;
        this.context = context;
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
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Evaluation evaluation = evaluationList.get(position);
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.evaluate_comments_item,parent,false);
        }

        AQuery aq = new AQuery(convertView);
        aq.id(R.id.evaluation_item_portrait).image(evaluation.getImage(), false, true);
        aq.id(R.id.evaluate_item_user_name).text(evaluation.getUsername());
        aq.id(R.id.evaluate_content).text(evaluation.getContent());
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        Calendar calendar2 = Calendar.getInstance(Locale.CHINA);
        calendar.setTimeInMillis(evaluation.getTime() * 1000);

        int mo1=calendar.get(Calendar.MONTH);
        int mo2=calendar2.get(Calendar.MONTH);

        int y1=calendar.get(Calendar.YEAR);
        int y2=calendar2.get(Calendar.YEAR);

        int date = calendar.get(Calendar.DATE);
        int date2 = calendar2.get(Calendar.DATE);

        String timeString = null;
        if(date!=date2 || mo1!=mo2 || y1!=y2){
            timeString=calendar.get(Calendar.YEAR)+"年"+(calendar.get(Calendar.MONTH)+1)+"月"+calendar.get(Calendar.DAY_OF_MONTH)+"日";
        }else{
            timeString = "今天 "+calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE);
        }
        aq.id(R.id.evaluate_time).text(timeString);

        return convertView;
    }
}

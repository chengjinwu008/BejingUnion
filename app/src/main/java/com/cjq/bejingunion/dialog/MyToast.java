package com.cjq.bejingunion.dialog;

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.cjq.bejingunion.R;

/**
 * Created by CJQ on 2015/9/21.
 */
public class MyToast {
    public static Toast toast = null;

    public static void showText(Context context, String text, String image) {
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            makeImage(context, image);
            toast.setGravity(Gravity.CENTER,0,0);
        } else {
            toast.setText(text);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    private static void makeImage(Context context, String image) {
        if (image != null && !"".equals(image)) {
            LinearLayout toastView = (LinearLayout) toast.getView();
            toastView.setGravity(Gravity.CENTER);
            ImageView imageView = new ImageView(context);
            AQuery aq = new AQuery(imageView);
            aq.image(image, false, true);
            toastView.addView(imageView, 0);
        }
    }

    public static void showText(Context context, int textId, String image) {
        if (toast == null) {
            toast = Toast.makeText(context, textId, Toast.LENGTH_SHORT);
            makeImage(context, image);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(textId);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    public static void showText(Context context, int textId, int image) {
        if (toast == null) {
            toast = Toast.makeText(context, textId, Toast.LENGTH_SHORT);
            makeImage(context, image);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(textId);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    private static void makeImage(Context context, int image) {
        if (image != 0) {
            LinearLayout toastView = (LinearLayout) toast.getView();
            toastView.setGravity(Gravity.CENTER);
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(image);
//                AQuery aq = new AQuery(imageView);
//                aq.image(image,false,true);
            toastView.addView(imageView, 0);
        }
    }

    public static void showText(Context context, String text, int image) {
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            makeImage(context, image);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(text);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    public static void showText(Context context, String text) {
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            makeImage(context, R.drawable.gou);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(text);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    public static void showText(Context context, int textId) {
        if (toast == null) {
            toast = Toast.makeText(context, textId, Toast.LENGTH_SHORT);
            makeImage(context, R.drawable.gou);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(textId);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }
}

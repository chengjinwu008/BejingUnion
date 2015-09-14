package com.cjq.bejingunion.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.cjq.bejingunion.BaseActivity;
import com.cjq.bejingunion.CommonDataObject;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.dialog.WarningAlertDialog;
import com.cjq.bejingunion.utils.LoginUtil;
import com.cjq.bejingunion.utils.PayResult;
import com.cjq.bejingunion.utils.SignUtils;
import com.unionpay.UPPayAssistEx;

import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.helpers.ParserFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by CJQ on 2015/9/6.
 */
public class PayActivity extends BaseActivity {
    // 商户PID
    public static final String PARTNER = "2088021182904922";
    // 商户收款账号
    public static final String SELLER = "1308526928@qq.com";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKDF2q1AkBaeus/4\n" +
            "p6rTfLVBdRIeeNEat57+I4oPk2uGhqRdblsYWb3MU6FWqCo3FwFeEoY67LphNlHz\n" +
            "5WV5xCnHFJtpBwS9eRJ5MnayYK2HCdioFv1nmtoPcKX0kpAQtf62W9WPHee2ddPb\n" +
            "0SywJps2Ua4qYoerVowJCayj585NAgMBAAECgYEAiKzq5FJ4q+Iw6lB3KXA1GloV\n" +
            "Tlv9Vbai11UxnVL0fnqUx/JtuJ+Q2xtzFTt4JrtCoXT5xocdbKzr4uu23neP3KNZ\n" +
            "WtMrhpYAqcKkb6lV7UgKjbGQ/ECZHxhveAjhL9hO8sMbwMxRe6wwSTmJp8IxqErW\n" +
            "m7ndN4NMH80PH6U6RsECQQDOAlCq0lF5+EE0qo9Wxp65q0phebdQDf1SplaJLry6\n" +
            "Ld9zPAcE3nPE7uvIAifDpvZqU8Hls9e9T0wXjJKxysA1AkEAx8lhHiLwAE08ep7/\n" +
            "DNaGGFTMJBXO1CxvaJc/5XYliwLPYmBY3UtyFFaNdSlI2v6nUl68Gjig/B4xD0Jk\n" +
            "y4RIuQJAboTRcAMrEMs9eBq0kXI2/xbE7axVys3mhGuWazw2pY8snG6suVD6PMGM\n" +
            "np2BZbZx5jMPB8NGz1n2UX/pxxYlrQJABVHOXTAO9eMYlic/oUbhASrY2Kkf/bRF\n" +
            "LyK/18tCiqYDgZoRI6tLmVEIqTL1NqeLKv1MwuH5H11qbv6UknquOQJAM5Ecn4Dx\n" +
            "qQ5IHJf1z0WdWGYFJFH+ebRL8ffI1X4g8ndMxz+E1Kawj/0d/alluxWNoBJOY3Ks\n" +
            "cRUqK1IW8d+rsg==";
    // 支付宝公钥
    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
    private static final int SDK_PAY_FLAG = 1;

    private static final int SDK_CHECK_FLAG = 2;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);

                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();

                    String resultStatus = payResult.getResultStatus();

                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(PayActivity.this, "支付成功",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(PayActivity.this, "支付结果确认中",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(PayActivity.this, "支付失败",
                                    Toast.LENGTH_SHORT).show();
            
                        }
                    }
                    break;
                }
                case SDK_CHECK_FLAG: {
                    Toast.makeText(PayActivity.this, "检查结果为：" + msg.obj,
                            Toast.LENGTH_SHORT).show();
                    break;
                }
                default:
                    break;
            }
        }
    };
    //商品名称
    private String name;
    //商品描述
    private String body;
    //商品订单号
    private String orderNumber;
    //商品价格
    private String price;
    private AQuery aq;
    // “00” – 银联正式环境
    // “01” – 银联测试环境，该环境中不发生真实交易
    public String CHINA_BANK_UNION_MODE = "01";
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_choose);

        Intent intent = getIntent();

        name = intent.getStringExtra("name");
        body = intent.getStringExtra("body");
        orderNumber = intent.getStringExtra("orderNumber");
        price = intent.getStringExtra("price");
        type = intent.getStringExtra("type");

        aq = new AQuery(this);

        SpannableString ss = new SpannableString("订单号：" + orderNumber);
        int l = ss.length();
        ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.title_bar)), 4, l, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        aq.id(R.id.pay_order_code).text(ss);

        NumberFormat format = NumberFormat.getInstance();
        format.setMinimumFractionDigits(2);
        format.setMaximumFractionDigits(2);
        format.setMinimumIntegerDigits(1);

        aq.id(R.id.pay_show_money).text(format.format(Double.valueOf(price)));

        aq.id(R.id.pay_by_alipay).clicked(this, "pay");
        aq.id(R.id.pay_by_china_bank).clicked(this, "payByChinaBank");
        aq.id(R.id.pay_by_points).clicked(this, "payByPoints");
        aq.id(R.id.pay_choose_back).clicked(this, "finish");
    }

    public void payByPoints(){

        try {
            Map<String,String> params = new HashMap<>();
            params.put("key",LoginUtil.getKey(this));
            params.put("order_type","r");
            params.put("pay_sn", orderNumber);

            aq.ajax(CommonDataObject.PAY_BY_POINTS_URL,params, JSONObject.class,new AjaxCallback<JSONObject>(){
                @Override
                public void callback(String url, JSONObject object, AjaxStatus status) {
                    try {
                        if(object.getInt("code")==200){
                            Toast.makeText(PayActivity.this,object.getJSONObject("datas").getString("msg"),Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(PayActivity.this,object.getJSONObject("datas").getString("error"),Toast.LENGTH_SHORT).show();
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

    public void payByChinaBank(String tn) {
        UPPayAssistEx.startPayByJAR(this, com.unionpay.uppay.PayActivity.class, null, null, tn,
                CHINA_BANK_UNION_MODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        String str = data.getExtras().getString("pay_result");
        if (str.equalsIgnoreCase("success")) {
            new WarningAlertDialog(this).changeText("支付成功").showCancel(false).show();
        } else if (str.equalsIgnoreCase("fail")) {
            new WarningAlertDialog(this).changeText("支付失败").showCancel(false).show();
        } else if (str.equalsIgnoreCase("cancel")) {
            new WarningAlertDialog(this).changeText("支付被取消了").showCancel(false).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * call alipay sdk pay. 调用SDK支付
     */
    public void pay(View v) {
        if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE)
                || TextUtils.isEmpty(SELLER)) {
            new AlertDialog.Builder(this)
                    .setTitle("警告")
                    .setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
                    .setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialoginterface, int i) {
                                    //
                                    finish();
                                }
                            }).show();
            return;
        }
        // 订单
        String orderInfo = null;
        try {
            orderInfo = getOrderInfo(name, body, price, orderNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 对订单做RSA 签名
        String sign = sign(orderInfo);
        try {
            // 仅需对sign 做URL编码
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 完整的符合支付宝参数规范的订单信息
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
                + getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(PayActivity.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * check whether the device has authentication alipay account.
     * 查询终端设备是否存在支付宝认证账户
     */
    public void check(View v) {
        Runnable checkRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask payTask = new PayTask(PayActivity.this);
                // 调用查询接口，获取查询结果
                boolean isExist = payTask.checkAccountIfExist();

                Message msg = new Message();
                msg.what = SDK_CHECK_FLAG;
                msg.obj = isExist;
                mHandler.sendMessage(msg);
            }
        };

        Thread checkThread = new Thread(checkRunnable);
        checkThread.start();

    }

    /**
     * get the sdk version. 获取SDK版本号
     */
    public void getSDKVersion() {
        PayTask payTask = new PayTask(this);
        String version = payTask.getVersion();
        Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
    }

    /**
     * create the order info. 创建订单信息
     */
    public String getOrderInfo(String subject, String body, String price, String orderNumber) throws Exception {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + orderNumber + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\" " + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + CommonDataObject.PAY_NOTIFY_URL + "&key=" + LoginUtil.getKey(this) + "&payment_code=alipay&order_type=" + type
                + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

//    /**
//     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
//     */
//    public String getOutTradeNo() {
//        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
//                Locale.getDefault());
//        Date date = new Date();
//        String key = format.format(date);
//
//        Random r = new Random();
//        key = key + r.nextInt();
//        key = key.substring(0, 15);
//        return key;
//    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    public String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    public String getSignType() {
        return "sign_type=\"RSA\"";
    }
}

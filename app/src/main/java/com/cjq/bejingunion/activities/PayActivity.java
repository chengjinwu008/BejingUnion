package com.cjq.bejingunion.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.androidquery.AQuery;
import com.cjq.bejingunion.BaseActivity;
import com.cjq.bejingunion.CommonDataObject;
import com.cjq.bejingunion.R;
import com.cjq.bejingunion.dialog.WarningAlertDialog;
import com.cjq.bejingunion.utils.LoginUtil;
import com.cjq.bejingunion.utils.PayResult;
import com.cjq.bejingunion.utils.SignUtils;
import com.unionpay.UPPayAssistEx;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by CJQ on 2015/9/6.
 */
public class PayActivity extends BaseActivity {
    // 商户PID
    public static final String PARTNER = "2088021182904922";
    // 商户收款账号
    public static final String SELLER = "1308526928@qq.com";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALziLdVqTPav9ZKp\n" +
            "5dPrK8qS9G79p8Hv0RxMcyOj9Ni5whZfHehNxv3nzsKI18LACJ/PP0NIhXmqXfo+\n" +
            "VevRknrqkszFZdOoqdK0qZ7yINPHDCU29UbvzjL6cEON2gHp+dA/8gzeJ+ylZh1L\n" +
            "3+gR809ixEJ8opW7IE8lg+2n6xWHAgMBAAECgYAvdB+Ru4gkfeaTd02/ZRj2Zt80\n" +
            "N1P7PFXr5yUSMjHkdR7W4gSwhUHWLnPameijC/3esIGzVLf77hp4MUsC2P8Updbe\n" +
            "u7VTXkeuR0EqBF6JNOQ+evO+maGYtiBC2tjfZG4zVa7GEQ/zrdiy74ryaESYGfkR\n" +
            "82Iojip2K+zPi3uikQJBAOaJHgFxuYSXzZ9l7Hpg0M+xu31DkfdrGdMEQTUn3m9K\n" +
            "9Jdyt3kJ3f03095Id3DTBVFi/saf/w07KSG+1w3t8b8CQQDRv0KqdCPJcIc8y+UB\n" +
            "p0P7W4rPCctGp0GFcR/yzDGLqv1+JIFaI+BTn8qlFa0PyDwI31hx9EkG4HaYPUCj\n" +
            "nD45AkEAhAKQhBMzQJIM1PHwcENwTv5fdmNy+lMB/qu5C09BOEmbtf2iwlS8IXwO\n" +
            "foLQonKz4N7nNaX4zwLJkuFNFFTk0QJAL++oStCURTuEjO6EfiY+MTowtTSl0pXz\n" +
            "MY4zIQht4l/H44ZwUauVX0dLxwL3NH4sylJRImoULpAqSozlMv9IcQJAR+vqTRrt\n" +
            "Z5JPrq0UNyafoTfTr41edJ1/+fNmgdxF15tpf8khV+iPgizuCXTSLOLkzc0e2b4c\n" +
            "mqNd2TuZi0UwEw==";
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

        ;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_choose);

        Intent intent = getIntent();

        name = intent.getStringExtra("name");
        body = intent.getStringExtra("body");
        orderNumber = intent.getStringExtra("orderNumber");
        price = intent.getStringExtra("price");

        aq = new AQuery(this);
        aq.id(R.id.pay_by_alipay).clicked(this, "pay");
    }

    public void payByChinaBank(String tn) {
        UPPayAssistEx.startPayByJAR(this, com.unionpay.uppay.PayActivity.class, null, null, tn,
                CHINA_BANK_UNION_MODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( data == null ){
            return;
        }
        String str = data.getExtras().getString("pay_result");
        if( str.equalsIgnoreCase("success") ){
            new WarningAlertDialog(this).changeText("支付成功").showCancel(false).show();
        }else if( str.equalsIgnoreCase("fail") ){
            new WarningAlertDialog(this).changeText("支付失败").showCancel(false).show();
        }else if( str.equalsIgnoreCase("cancel") ){
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
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + CommonDataObject.PAY_NOTIFY_URL + "&key=\"" + LoginUtil.getKey(this) + "\"&payment_code=\"alipay\""
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

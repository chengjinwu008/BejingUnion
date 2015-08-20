package com.cjq.bejingunion;

/**
 * Created by CJQ on 2015/8/19.
 */
public class CommonDataObject {
    public static final int PAGE_SIZE=10;//每页显示的商品数量

    public static final String MAIN_URL = "http://xm0000009.demo.kh888.cn";
    public static final String BANNER_URL = MAIN_URL +"/app/index.php?act=index";
    public static final String INDEX_NEW_GOODS_LIST =MAIN_URL+ "/app/index.php?act=goods&op=index_goods";
    public static final String INDEX_HOT_GOODS_LIST =MAIN_URL+ "/app/index.php?act=goods&op=index_goods&type=1&sort=2";
    public static final String LOGIN_URL =MAIN_URL+ "/app/index.php?act=login";
    public static final String VERIFY_CODE_URL =MAIN_URL+ "/app/index.php?act=login&op=getsms";
    public static final String REGISTER_URL =MAIN_URL+ "/app/index.php?act=login&op=register";
    public static final String GOODS_LIST_URL =MAIN_URL+ "/app/index.php?act=goods&op=goods_list";
}

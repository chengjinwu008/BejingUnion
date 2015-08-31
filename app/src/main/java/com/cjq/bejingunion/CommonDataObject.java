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
    public static final String GOODS_DETAIL_URL =MAIN_URL+ "/app/index.php?act=goods&op=goods_detail";
    public static final String FIND_PASSWORD_BACK_URL =MAIN_URL+ "/app/index.php?act=login&op=getpwd";
    public static final String CHANGE_PASSWORD_URL =MAIN_URL+ "/app/index.php?act=login&op=updatepwd";
    public static final String CATEGORY_LIST_FOR_LIST_URL =MAIN_URL+ "/app/index.php?act=goods_class&op=index";
    public static final String USER_INFO_URL =MAIN_URL+ "/app/index.php?act=member_index";
    public static final String COLLECTION_LIST_URL =MAIN_URL+ "/app/index.php?act=member_favorites&op=favorites_list";
    public static final String ADDRESS_LIST_URL =MAIN_URL+ "/app/index.php?act=member_address&op=address_list";
    public static final String COLLECTION_DELETE_URL =MAIN_URL+ "/app/index.php?act=member_favorites&op=favorites_del";
    public static final String COLLECTION_ADD_URL =MAIN_URL+ "/app/index.php?act=member_favorites&op=favorites_add";
    public static final String PROVENCE_LIST_URL =MAIN_URL+ "/app/index.php?act=member_address&op=area_list";
    public static final String CITY_LIST_URL =MAIN_URL+ "/app/index.php?act=member_address&op=area_list";
    public static final String ADDRESS_EDIT_URL =MAIN_URL+ "/app/index.php?act=member_address&op=address_info";
    public static final String DO_ADD_ADDRESS =MAIN_URL+ "/app/index.php?act=member_address&op=address_add";
    public static final String ADDRESS_DELETE_URL =MAIN_URL+ "/app/index.php?act=member_address&op=address_del";
    public static final String DO_EDIT_ADDRESS =MAIN_URL+ "/app/index.php?act=member_address&op=address_edit";
    public static final String CATEGORIES_GET_URL =MAIN_URL+ "/app/index.php?act=member_brand&op=brand_list";
    public static final String EVALUATION_LIST_URL =MAIN_URL+ "/app/index.php?act=goods&op=goods_common";
    public static final String USER_INFO_SETTING =MAIN_URL+ "/app/index.php?act=member_index&op=user_info";

    public static final String DETAIL_WAP=MAIN_URL+"/wap/tmpl/product_info.html";
}

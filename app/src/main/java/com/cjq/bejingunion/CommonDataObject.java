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
    public static final String USER_INFO_SETTING_COMMIT =MAIN_URL+ "/app/index.php?act=member_index&op=eidt_user";
    public static final String UPLOAD_AGENT_URL =MAIN_URL+ "/app/index.php?act=uploud_image&op=upload_agent_img";
    public static final String UPLOAD_PORTRAIT_URL =MAIN_URL+ "/app/index.php?act=uploud_image&op=upload_user_img";
    public static final String APPLY_PARTNER_URL =MAIN_URL+ "/app/index.php?act=member_index&op=show_vip";
    public static final String PAY_FOR_POINTS_URL =MAIN_URL+ "/app/index.php?act=member_index&op=recharge_add"; //充值
    public static final String CONFIRM_ORDER_URL =MAIN_URL+ "/app/index.php?act=member_buy&op=buy_step1";
    public static final String ADDRESS_HASH_CODE_URL =MAIN_URL+ "/app/index.php?act=member_buy&op=change_address";
    public static final String SUBMIT_ORDER_URL =MAIN_URL+ "/app/index.php?act=member_buy&op=buy_step2";
    public static final String ADD_TO_CART_URL =MAIN_URL+ "/app/index.php?act=member_cart&op=cart_add";
    public static final String DELETE_FROM_CART_LIST =MAIN_URL+ "/app/index.php?act=member_cart&op=cart_del";
    public static final String CART_LIST_URL =MAIN_URL+ "/app/index.php?act=member_cart&op=cart_list";
    public static final String CARD_LIST_URL =MAIN_URL+ "/app/index.php?act=phone&op=phone_list";
    public static final String COMMON_UPLOAD_IMAGE_URL =MAIN_URL+ "/app/index.php?act=uploud_image&op=upload_img";
    public static final String IDENTIFY_SUBMIT_URL =MAIN_URL+ "/app/index.php?act=phone&op=phone_additional";
    public static final String PAY_BY_POINTS_URL =MAIN_URL+ "/app/index.php?act=member_buy&op=pd_pay"; //积分支付
    public static final String BRAND_LIST =MAIN_URL+ "/app/index.php?act=member_brand&op=brand_list"; //品牌列表
    public static final String IDENTIFY_INFO =MAIN_URL+ "/app/index.php?act=phone&op=phone_additional_info"; //认证信息回显
    public static final String ORDER_COUNT_URL =MAIN_URL+ "/app/index.php?act=member_order&op=order_count"; //订单数量
    public static final String MSG_LIST_URL =MAIN_URL+ "/app/index.php?act=member_information&op=systemmsg";
    public static final String MSG_DEL_URL =MAIN_URL+ "/app/index.php?act=member_information&op=dropbatchmsg";
    public static final String EVALUATION_LIST =MAIN_URL+ "/app/index.php?act=member_order&op=goods_comment_list";
    public static final String EVALUATION_INFO_URL =MAIN_URL+ "/app/index.php?act=member_order&op=goods_comment_info";
    public static final String EVALUATION_ADD_URL =MAIN_URL+ "/app/index.php?act=member_order&op=goods_comment_add";
    public static final String ORDER_LIST =MAIN_URL+ "/app/index.php?act=member_order&op=order_list";
    public static final String ORDER_RECEIVE_CONFIRM =MAIN_URL+ "/app/index.php?act=member_order&op=order_receive";

    public static final String DETAIL_WAP=MAIN_URL+"/wap/tmpl/product_info.html";
    public static final String PAY_NOTIFY_URL=MAIN_URL+"/app/index.php?act=payment&op=notify";
    public static final String IDENTIFY_NOTIFY1=MAIN_URL+"/app/index.php?act=goods&op=news_info&article_id=42";
    public static final String IDENTIFY_NOTIFY2=MAIN_URL+"/app/index.php?act=goods&op=news_info&article_id=43";
}

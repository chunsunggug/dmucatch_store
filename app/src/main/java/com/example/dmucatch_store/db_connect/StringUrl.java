package com.example.dmucatch_store.db_connect;

public class StringUrl {

    private static final String server = "http://ci2019catch.dongyangmirae.kr";
    private static final String user = "u";
    private static final String store = "s";
    private static final String kakaoApi = "kakaoApi";
    private static final String Catch = "catch";
    private static final String storeCatch = "storeCatch";

    public static final String login = server + "/" + store + "/" + "s_login.php";
    public static final String signUp = server + "/" + store + "/" + "s_signup.php";
    public static final String getMenu = server + "/" + store + "/" + "getmenu.php";
    public static final String getAddress = server + "/" + store + "/" + "get_address.php";
    public static final String AddressApi = server + "/" + kakaoApi +"/" + "address.php";
    public static final String setAddress = server + "/" + kakaoApi +"/" + "s_setaddress.php";
    public static final String selectCatch = server + "/" + Catch + "/" + storeCatch + "/" + "select.php";
    public static final String updateCatched = server + "/" + Catch + "/" + storeCatch + "/" + "updateCatched.php";
    public static final String updateCatchCancle = server + "/" + Catch + "/" + storeCatch + "/" + "update_catch_cancle.php";
    public static final String updateMenuYn = server + "/" + Catch + "/" + storeCatch + "/" + "update_menu_yn.php";
    public static final String catchConfirm = server + "/" + Catch + "/" + storeCatch + "/" + "catch_confirm.php";
    public static final String isCatched = server + "/" + Catch + "/" + storeCatch + "/" + "is_catched.php";
    public static final String isFinished = server + "/" + Catch + "/" + storeCatch + "/" + "is_catched.php";
    public static final String delCatchMenu = server + "/" + Catch + "/" + storeCatch + "/" + "del_catch_menu.php";
    public static final String insertCatchMenu = server + "/" + Catch + "/" + storeCatch + "/" + "insert_catch_menu.php";
    public static final String setStoreCatchList = server + "/" + Catch + "/" + storeCatch + "/" + "set_store_catch_list.php";
    public static final String stopCatch = server + "/" + Catch + "/" + storeCatch + "/" + "stop.php";

}

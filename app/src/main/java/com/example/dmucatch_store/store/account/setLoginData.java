package com.example.dmucatch_store.store.account;

import com.example.dmucatch_store.db_connect.ConnectDB;
import com.example.dmucatch_store.db_connect.StringUrl;
import com.example.dmucatch_store.store.addressApi.AddVo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class setLoginData {

    static ConnectDB connectdb = new ConnectDB(); // db connection 모음
    static StringUrl url = new StringUrl(); // url 모음

    protected static void setUser(String mJson){

        System.out.println("================ setUser() Start ================");

        String TAG_JSON="storeMembers"; // Json Tag
        String TAG_STORE_SEQ = "store_seq";
        String TAG_ID = "store_id";
        String TAG_NAME = "store_name";
        String TAG_PWD ="pwd";
        String TAG_PHONE = "phone";
        String TAG_ADDRESS = "address";
        String TAG_CODE = "class_code";
        // Json 구분자

        try {
            System.out.println("================ setUser() Json Start ================");

            JSONObject jsonObject = new JSONObject(mJson);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON); // select data count and insert to array , 로그인이라서 어차피 데이터 1개

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String seq = item.getString(TAG_STORE_SEQ);
                String id = item.getString(TAG_ID);
                String name = item.getString(TAG_NAME);
                String phone = item.getString(TAG_PHONE);
                String address = item.getString(TAG_ADDRESS);
                String class_cd = item.getString(TAG_CODE);

                Userinfo.setStoreSeq(seq);
                Userinfo.setID(id);
                Userinfo.setName(name);
                Userinfo.setPhone(phone);
                Userinfo.setAddress(address);
                Userinfo.setClass_code(class_cd);

                System.out.println("================ setUser() Json : " + item + " ================");

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void getAddress(String mJson){

        System.out.println("================ getAddress() Start ================");

        String TAG_JSON="storeMembers"; // Json Tag
        String TAG_SEQ = "store_seq";
        String TAG_ID = "store_id";
        String TAG_ADDRESS = "address";
        // Json 구분자

        String seq, id = null, address = null;

        try {
            System.out.println("================ getAddress() Start ================");

            JSONObject jsonObject = new JSONObject(mJson);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON); // select data count and insert to array , 로그인이라서 어차피 데이터 1개

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                seq = item.getString(TAG_SEQ);
                id = item.getString(TAG_ID);
                address = item.getString(TAG_ADDRESS);
            }

            String [] phpVar = {"add_seq", "storeId"};
            String [] typeData = {address,id};

            String add_json = connectdb.selectToJson(url.getAddress, phpVar, typeData);
            setAddress(add_json);

            System.out.println("================ getAddress() Json End ================");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void setAddress(String add_json){
        System.out.println("================ setAddress() Start ================");

        String TAG_JSON="storeAddress"; // Json Tag
        String TAG_ADD_DATA = "add_data";
        // Json 구분자

        try {
            System.out.println("================ setAddress() Json Start ================");

            JSONObject jsonObject = new JSONObject(add_json);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON); // select data count and insert to array

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String add_data = item.getString(TAG_ADD_DATA);

                AddVo.newAdd = add_data;
            }
            System.out.println("================ setAddress() Json End | Address : " + AddVo.newAdd + " ================");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

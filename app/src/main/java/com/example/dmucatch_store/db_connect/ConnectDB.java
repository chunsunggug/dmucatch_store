package com.example.dmucatch_store.db_connect;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

//import com.example.dmucatch_store.member.user.account.SignUpEnd_csg;

import com.example.dmucatch_store.store.func_catch.CatchTempInfo;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ConnectDB {
    private static HttpPost httppost;
    private static HttpResponse response;
    private static HttpClient httpclient;
    private static List<NameValuePair> nameValuePairs;

    public static String selectToJson(String url, String[] phpVar, String[] typeData) {
        int i;

        for(i =0; i<phpVar.length;i++){
            System.out.println("커넥트 디비 phpVar["+i+"] "+phpVar[i]+" typeData["+i+"] "+typeData[i]);
        }

        try {

            httpclient = new DefaultHttpClient();
            httppost = new HttpPost(url);
            nameValuePairs = new ArrayList<NameValuePair>(2);

            for (i = 0; i < phpVar.length; i++) {
                nameValuePairs.add(new BasicNameValuePair(phpVar[i].toString(), typeData[i].toString()));
            }

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            response = httpclient.execute(httppost);

            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String responsedata = httpclient.execute(httppost, responseHandler);
            System.out.println(">>>>>>>>ConnectDB.selectToJson return : "+responsedata);
            return responsedata;
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage() + " >>>return fail<<<<");
            return "fail";
        }
    }

    public static void setCatched(String catchSeq) { // set catch confirm temp data

        try {

            httpclient = new DefaultHttpClient();
            httppost = new HttpPost(StringUrl.updateCatched);
            nameValuePairs = new ArrayList<NameValuePair>(2);


            nameValuePairs.add(new BasicNameValuePair("catch_seq", catchSeq));


            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            response = httpclient.execute(httppost);

            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String responsedata = httpclient.execute(httppost, responseHandler);
            System.out.println(">>>>>>>> UPDATE CATCHED : "+responsedata);

        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage() + " >>>return fail<<<<");
        }
    }

    public static boolean isCatched(String catchSeq) {

        try {

            httpclient = new DefaultHttpClient();
            httppost = new HttpPost(StringUrl.isCatched);
            nameValuePairs = new ArrayList<NameValuePair>(2);

            nameValuePairs.add(new BasicNameValuePair("catch_seq", catchSeq));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            response = httpclient.execute(httppost);

            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String responsedata = httpclient.execute(httppost, responseHandler);
            System.out.println(">>>>>>>>isCatched return : "+responsedata);

            if("fail".equals(responsedata) || "fai".equals(responsedata)){
                return true;    // 에러 인 경우 그냥 캐치 된 것 처럼 처리
            }
            else{

                if("N".equalsIgnoreCase(parsJson(responsedata, "catched", new String [] {"catch_yn"})) ){
                    return false;
                }
                else{
                    return true;
                }

            }
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage() + " >>>return fail<<<<");
            return false;
        }
    }

    public static boolean isFinished(String catchSeq) {

        try {

            httpclient = new DefaultHttpClient();
            httppost = new HttpPost(StringUrl.isFinished);
            nameValuePairs = new ArrayList<NameValuePair>(2);

            nameValuePairs.add(new BasicNameValuePair("catch_seq", catchSeq));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            response = httpclient.execute(httppost);

            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String responsedata = httpclient.execute(httppost, responseHandler);
            System.out.println(">>>>>>>>isFinished return : "+responsedata);

            if("fail".equals(responsedata) || "fai".equals(responsedata)){
                return true;    // 에러 인 경우 그냥 캐치 된 것 처럼 처리
            }
            else{
                String [] temp = {"isyn_flag"};
                if("N".equalsIgnoreCase(parsJson(responsedata, "catched", temp )) ){
                    return true;
                }
                else{
                    return false;
                }

            }
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage() + " >>>return fail<<<<");
            return true;
        }
    }

    public static void setCatchCancle(String catchSeq) { // set catch confirm temp data

        try {

            httpclient = new DefaultHttpClient();
            httppost = new HttpPost(StringUrl.updateCatchCancle);
            nameValuePairs = new ArrayList<NameValuePair>(2);


            nameValuePairs.add(new BasicNameValuePair("catch_seq", catchSeq));


            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            response = httpclient.execute(httppost);

            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String responsedata = httpclient.execute(httppost, responseHandler);
            System.out.println(">>>>>>>> UPDATE CATCHED : "+responsedata);

        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage() + " >>>return fail<<<<");
        }
    }

    public static boolean insertToDB(String link, String Id, String Pwd, String Name, String Phone,String ZipCode, String Address,String BuidName) {
        try {
            String data = URLEncoder.encode("Id", "UTF-8") + "=" + URLEncoder.encode(Id, "UTF-8");
            data += "&" + URLEncoder.encode("Pwd", "UTF-8") + "=" + URLEncoder.encode(Pwd, "UTF-8");
            data += "&" + URLEncoder.encode("Name", "UTF-8") + "=" + URLEncoder.encode(Name, "UTF-8");
            data += "&" + URLEncoder.encode("Phone", "UTF-8") + "=" + URLEncoder.encode(Phone, "UTF-8");
            data += "&" + URLEncoder.encode("Address", "UTF-8") + "=" + URLEncoder.encode(Address, "UTF-8");

            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();
            wr.close();
            // data 작성

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream())); // conn.getInputStream (내용 전달)
            String phpEcho = bufferedReader.readLine();
            // php echo 값 받아옴

            System.out.println("이것좀 체크하자:"+phpEcho);

            if(phpEcho.equalsIgnoreCase("succeed")){
                StringUrl stringUrl = new StringUrl();
                System.out.println("주소 추가 php 전 :"+Id+ZipCode+Address+BuidName);
                insertAddress(stringUrl.setAddress, Id, ZipCode, Address, BuidName);
                return true;
            }
            else{
                return false;
            }
        } catch (Exception e) {
            System.out.println("Exception (네트워크 에러) : " + e.getMessage());
            return false;
        }
    }

    public static boolean insertAddress(String link, String Id, String ZipCode, String Address, String BuildName) {
        try {
            String data = URLEncoder.encode("Id", "UTF-8") + "=" + URLEncoder.encode(Id, "UTF-8");
            data += "&" + URLEncoder.encode("ZipCode", "UTF-8") + "=" + URLEncoder.encode(ZipCode, "UTF-8");
            data += "&" + URLEncoder.encode("Address", "UTF-8") + "=" + URLEncoder.encode(Address, "UTF-8");
            data += "&" + URLEncoder.encode("BuildName", "UTF-8") + "=" + URLEncoder.encode(BuildName, "UTF-8");

            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();
            wr.close();
            // data 작성

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream())); // conn.getInputStream (내용 전달)
            String phpEcho = bufferedReader.readLine();
            // php echo 값 받아옴

            System.out.println("이것좀 체크하자"+phpEcho);

            if(phpEcho.equalsIgnoreCase("succeed")){
                return true;
            }
            else{
                return false;
            }
        } catch (Exception e) {
            System.out.println("주소 추가에 실패했습니다. : " + e.getMessage());
            return false;
        }
    }

    public static boolean insertToCatchedMenu(String url, String catchSeq, String [] name, String [] person, String userSeq, String storeSeq) {
        try {

            httpclient = new DefaultHttpClient();
            httppost = new HttpPost(url);
            nameValuePairs = new ArrayList<NameValuePair>(2);

            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject;

            for(int i=0; i<name.length; i++){

                jsonObject = new JSONObject();
                jsonObject.put("menu_name",name[i]);
                jsonObject.put("menu_person",person[i]);
                jsonArray.put(jsonObject);

            }   // 배열 값 json 세팅

            System.out.println("제이슨어레이" + jsonArray);

            nameValuePairs.add(new BasicNameValuePair("catch_seq", catchSeq));
            nameValuePairs.add(new BasicNameValuePair("user_seq", userSeq));
            nameValuePairs.add(new BasicNameValuePair("store_seq", storeSeq));
            nameValuePairs.add(new BasicNameValuePair("json_arr", jsonArray.toString()));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            // json to php

            //response = httpclient.execute(httppost);

            ResponseHandler<String> responseHandler = new BasicResponseHandler();

            final String responsedata = httpclient.execute(httppost, responseHandler);
            System.out.println(">>>>>>>>ConnectDB.selectToJson return : "+responsedata);

            if("succeed".equals(responsedata)){
                return true;
            }
            else{
                return false;
            }

        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage() + " >>>return fail<<<<");
            return false;
        }
    }

    public static boolean insertToCatchedMenu2(String url, String catchSeq, String [] name, String [] person, String userSeq, String storeSeq, String [] price) {
        try {

            httpclient = new DefaultHttpClient();
            httppost = new HttpPost(url);
            int totalPrice = 0;


            for(int i=0; i<name.length; i++){
                nameValuePairs = new ArrayList<NameValuePair>(2);

                nameValuePairs.add(new BasicNameValuePair("catch_seq", catchSeq));
                nameValuePairs.add(new BasicNameValuePair("user_seq", userSeq));
                nameValuePairs.add(new BasicNameValuePair("store_seq", storeSeq));
                nameValuePairs.add(new BasicNameValuePair("menu_name", name[i]));
                nameValuePairs.add(new BasicNameValuePair("menu_price", price[i]));
                nameValuePairs.add(new BasicNameValuePair("menu_person", person[i]));
                totalPrice += Integer.parseInt(price[i]) * Integer.parseInt(person[i]);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

                ResponseHandler<String> responseHandler = new BasicResponseHandler();

                final String responsedata = httpclient.execute(httppost, responseHandler);

                if("failed".equals(responsedata) || "faile".equals(responsedata)){
                    httppost = new HttpPost(StringUrl.delCatchMenu);
                    nameValuePairs = new ArrayList<NameValuePair>(2);
                    nameValuePairs.add(new BasicNameValuePair("catch_seq", catchSeq));
                    responseHandler = new BasicResponseHandler();

                    httpclient.execute(httppost, responseHandler);

                    return false;
                }
                else{


                }

            }

            if(setStoreCatchList(StringUrl.setStoreCatchList, catchSeq, userSeq, storeSeq, Integer.toString(totalPrice))){
                return true;
            }
            else{

                httppost = new HttpPost(StringUrl.delCatchMenu);
                nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("catch_seq", catchSeq));
                ResponseHandler<String> respHandler = new BasicResponseHandler();

                httpclient.execute(httppost, respHandler);

                return false;
            }


        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage() + " >>>return fail<<<<");
            return false;
        }
    }

    public static boolean setStoreCatchList(String url, String catchSeq, String userSeq, String storeSeq, String price) {
        try{
            httpclient = new DefaultHttpClient();
            httppost = new HttpPost(url);

            nameValuePairs = new ArrayList<NameValuePair>(2);

            nameValuePairs.add(new BasicNameValuePair("catch_seq", catchSeq));
            nameValuePairs.add(new BasicNameValuePair("user_seq", userSeq));
            nameValuePairs.add(new BasicNameValuePair("store_seq", storeSeq));
            nameValuePairs.add(new BasicNameValuePair("menu_price", price));
            nameValuePairs.add(new BasicNameValuePair("menu_yn", "Y"));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

            ResponseHandler<String> responseHandler = new BasicResponseHandler();

            final String responsedata = httpclient.execute(httppost, responseHandler);

            if("succeed".equals(responsedata) || "succee".equals(responsedata)){
                return true;
            }
            else{

                return false;
            }

        }catch (Exception e){
            System.out.println("Exception : " + e.getMessage() + " >>>return fail<<<<");
            return false;
        }
    }

    static String parsJson(String catchJsonString, String tagTtl, String[] tagNm) {

        try {

            JSONObject jsonObject = new JSONObject(catchJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(tagTtl); // get Tag Title

            String data = "";

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                for(int j = 0; j<tagNm.length; j++){
                    data += item.getString(tagNm[j]);
                }

            }

            return data;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


}

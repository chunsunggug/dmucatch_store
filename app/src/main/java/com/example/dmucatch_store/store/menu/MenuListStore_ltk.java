package com.example.dmucatch_store.store.menu;

import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TableRow;
import android.widget.Toast;

import com.example.dmucatch_store.R;
import com.example.dmucatch_store.store.AppMainOrderList;
import com.example.dmucatch_store.store.account.Userinfo;
import com.example.dmucatch_store.utility.StringUtil;

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
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MenuListStore_ltk extends AppCompatActivity {
    FrameLayout addMenu;
    LinearLayout Menu;
    HttpPost httppost;
    HttpClient httpclient;
    HttpResponse response;
    List<NameValuePair> nameValuePairs;
    TableRow add;

    ListView listView;              //리스트 뷰를 리스트 뷰 선언
    SimpleAdapter sAdapter;         //심플아답터 s아답터 선언
    ArrayList<HashMap<String,String>> listData = new ArrayList<>();
    Userinfo userinfo;
    StringUtil util = new StringUtil(); // util 모음

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_menu_list_activity_ltk);



        setTitle("사장님 페이지");

        final String store_id = userinfo.getID();
        System.out.println("store_id"+store_id);

        try {
            httpclient = new DefaultHttpClient();
            httppost = new HttpPost("http://ci2019catch.dongyangmirae.kr/s/menu_AddPageList.php");
            nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("store_id",store_id ));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            response = httpclient.execute(httppost);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String response = httpclient.execute(httppost, responseHandler);
            System.out.println("연결 성공 및 리턴 값 확인 : "+response);

            if(util.isNoFoundMenu(response)) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("메뉴 없음 : " + response);
                    }
                });
            } // user not found
            else if(util.isFailed(response)){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(response);
                        Toast.makeText(MenuListStore_ltk.this, "네트워크 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
                    }
                });
            } // network || php error
            else {
                System.out.println("json : "+response);
                listView = findViewById(R.id.listView);     //리스트 뷰 불러오기
                sAdapter = new SimpleAdapter(this,listData,android.R.layout.simple_list_item_activated_2,
                        new String[] {"menu_name","menu_price"},new int[] {android.R.id.text1,android.R.id.text2});

                listView.setAdapter(sAdapter);

                String TAG_JSON="MenuList"; // Json Tag
                String TAG_SEQ = "menu_seq";
                String TAG_NAME = "menu_name";
                String TAG_PRICE = "menu_price";
                String TAG_INFO ="menu_info";
                String TAG_FREE = "menu_freeview";

                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("MenuList");

                final MenuVO mvo = new MenuVO(jsonArray.length());

                for(int i=0;i<jsonArray.length();i++){

                    JSONObject item = jsonArray.getJSONObject(i);

                    int menu_seq = item.getInt(TAG_SEQ);
                    mvo.setMenu_seq(menu_seq,i);
                    String menu_name = item.getString(TAG_NAME);
                    mvo.setMenu_name(menu_name,i);
                    String menu_price = item.getString(TAG_PRICE);
                    mvo.setMenu_price(menu_price,i);
                    String menu_info = item.getString(TAG_INFO);
                    mvo.setMenu_info(menu_info,i);
                    String menu_freeview = item.getString(TAG_FREE);
                    mvo.setMenu_freeview(menu_freeview,i);

                    HashMap<String,String> items = new HashMap<>();
                    items.put("menu_name",menu_name);
                    items.put("menu_price",menu_price);

                    listData.add(items);
                    sAdapter.notifyDataSetChanged();
                }


                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        System.out.println("position : "+ position);
                        System.out.println("id : "+ id);
                        System.out.println("mvo 겟 seq : "+ mvo.getMenu_seq(position));

                        Intent intent=new Intent(getApplicationContext(),MenuModifyStore_hja.class);
                        intent.putExtra("menu_seq",mvo.getMenu_seq(position));
                        intent.putExtra("menu_name",mvo.getMenu_name(position));
                        intent.putExtra("menu_info",mvo.getMenu_info(position));
                        intent.putExtra("menu_price",mvo.getMenu_price(position));
                        intent.putExtra("menu_freeview",mvo.getMenu_freeview(position));
                        startActivity(intent);
                    }
                });
            }

        }catch (Exception e){
            e.printStackTrace();
        }


        addMenu = findViewById(R.id.addmenu);
        addMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMenu.setAlpha(0.5f);
                Intent it = new Intent(getApplicationContext(), MenuCreateStore_ltk.class);
                it.putExtra("store_id",store_id);
                startActivityForResult(it,1);
            }
        });

        Button save = findViewById(R.id.btnSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(),  AppMainOrderList.class);
                startActivity(it);
            }
        });

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode== RESULT_OK)
        {
            if(requestCode ==1)
            {
                LinearLayout menu = findViewById(R.id.menu);

                //menuadd.t.setText("sssss");
                Menu = findViewById(R.id.menu);
                //    Menu.addView(menuadd);
                addMenu = findViewById(R.id.addmenu);

            }

            else if(requestCode == 2)
            {
                // 수정결과
            }

        }

    }




}





package com.example.dmucatch_store.store.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.dmucatch_store.R;
import com.example.dmucatch_store.db_connect.ConnectDB;
import com.example.dmucatch_store.db_connect.StringUrl;
import com.example.dmucatch_store.store.AppMainOrderList;
import com.example.dmucatch_store.store.account.Userinfo;
import com.example.dmucatch_store.store.func_catch.CatchTempInfo;
import com.example.dmucatch_store.store.func_catch.CatchWaitStore;
import com.example.dmucatch_store.store.func_catch.CatchedConfirm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MenuChoose extends AppCompatActivity {
    StringUrl url = new StringUrl();
    String mJsonString;
    private static MenuListInfo menuListInfos;
    ListView menuList, menuSelectedList;              //리스트 뷰를 리스트 뷰 선언
    SimpleAdapter sAdapter,bAdapter;         //심플아답터 s아답터 선언 / b아답터 선언
    ArrayList<HashMap<String,String>> listData = new ArrayList<>();
    ArrayList<HashMap<String,String>> selectedListData = new ArrayList<>();
    String tempMenuNm, tempMenuPerson, tempMenuPrice;
    static int selectedPosition;    // 클릭 position 값 받는 temp int
    String catchSeq, money, person, userSeq;
    String strCheckedMenu ="";
    ArrayList<String> menuPriceList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_menu_choose_activity);

        final String phpVar [] = {"storeId"}; // php로 넘길 post 테그 명
        final String typeData [] = {Userinfo.getID()}; // EditText값 배열
        mJsonString = ConnectDB.selectToJson(url.getMenu, phpVar, typeData); // login url, php 테그 배열, EditText 변수 값 배열
        setMenuInfo(mJsonString);

        System.out.println("mJsonString 이야 : " + mJsonString);

        Intent confirmIntent = getIntent();

        catchSeq = confirmIntent.getStringExtra("catch_seq");
        money = confirmIntent.getStringExtra("money");
        person = confirmIntent.getStringExtra("person");
        userSeq = confirmIntent.getStringExtra("user_seq");
        // 캐치 조건

        Button btnDone = findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkMenulist()){
                    Intent mvCatched = new Intent(getApplicationContext(), CatchedConfirm.class);
                    mvCatched.putExtra("selected_menu", strCheckedMenu);
                    startActivityForResult(mvCatched,104);
                }
            }
        });

    }

    public void setMenuInfo(String mJson){

        String TAG_JSON="MenuList"; // Json Tag
        String TAG_MENU_SEQ = "menu_seq";
        String TAG_MENU_NAME = "menu_name";
        String TAG_MENU_INFO = "menu_info";
        String TAG_MENU_PRICE = "menu_price";
        // Json 구분자

        menuList = findViewById(R.id.storeMenuList);    // 상단 메뉴 리스트

        menuSelectedList = findViewById(R.id.menuChooseList);   // 하단 메뉴 선택 리스트

        sAdapter = new SimpleAdapter(this,listData,android.R.layout.simple_list_item_activated_2,
                new String[] {"menu_name","menu_price"},new int[] {android.R.id.text1,android.R.id.text2});

        bAdapter = new SimpleAdapter(this,selectedListData,android.R.layout.simple_list_item_activated_2,
                new String[] {"menu_name","menu_person"},new int[] {android.R.id.text1,android.R.id.text2});


        menuList.setAdapter(sAdapter);  // 상단 메뉴 리스트
        menuSelectedList.setAdapter(bAdapter);  // 선택 된 리스트

        try {

            JSONObject jsonObject = new JSONObject(mJson);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON); // select menu data
            menuListInfos = new MenuListInfo(jsonArray.length());

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                HashMap<String,String> items = new HashMap<>();
                items.put("menu_name",item.getString(TAG_MENU_NAME));
                items.put("menu_price",item.getString(TAG_MENU_PRICE));

                listData.add(items);
                sAdapter.notifyDataSetChanged();

                menuListInfos.setMenuNm(item.getString(TAG_MENU_NAME), i);
                menuListInfos.setMenuPrice(item.getString(TAG_MENU_PRICE), i);

            }

            // 상단 메뉴 리스트
            menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent=new Intent(getApplicationContext(),MenuConfirm.class);
                    tempMenuNm = menuListInfos.getMenuNm(position);
                    tempMenuPrice = menuListInfos.getMenuPrice(position);
                    intent.putExtra("menu_name",tempMenuNm);
                    intent.putExtra("menu_price",menuListInfos.getMenuPrice(position));

                    startActivityForResult(intent,101);
                    // 시스템쪽으로 인텐트를 보낸다. 인텐트 전달하면서, 화면을 띄워줄것을 요청한다.
                    // 응답을 받아 올때는 startActivityForResult를 사용한다.
                }
            });

            // 하단 선택 된 리스트
            menuSelectedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    selectedPosition = position;

                    Intent intent=new Intent(getApplicationContext(),MenuDelConfirm.class);

                    // Object vo = (Object)parent.getAdapter().getItem(position);  // 리스트 클릭 값 전달
                    //parent.getItemAtPosition(position);

                    HashMap<String, Object> item = (HashMap<String, Object>) parent.getItemAtPosition(position);    // Listview(position) 값 hashmap 으로 받기.

                    System.out.println("포지션 : " + position);
                    System.out.println("이건 뭐야? "+parent.getItemAtPosition(position));

                    String menuNm = item.get("menu_name").toString();
                    String menuPerson = item.get("menu_person").toString();
                    // set string

                    intent.putExtra("menu_name", menuNm);
                    intent.putExtra("menu_person", menuPerson);

                    startActivityForResult(intent,102);
                    // 시스템쪽으로 인텐트를 보낸다. 인텐트 전달하면서, 화면을 띄워줄것을 요청한다.
                    // 응답을 받아 올때는 startActivityForResult를 사용한다.
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean checkMenulist(){
        if(0 == selectedListData.size()){
            Toast.makeText(getApplicationContext(),"메뉴를 선택 해주세요.",Toast.LENGTH_LONG).show();
            return false;
        }else{

            for(int i=0; i<selectedListData.size(); i++){
                strCheckedMenu += selectedListData.get(i).get("menu_name") + "/" + selectedListData.get(i).get("menu_person") + "\r";
            }

            return true;
        }
    }

    private boolean doMenuInsert(){

        int size = selectedListData.size();

        String [] menuNm = new String[size];
        String [] menuPerson = new String[size];

        for(int i=0; i<size; i++){
            menuNm[i] = selectedListData.get(i).get("menu_name");
            menuPerson[i] = selectedListData.get(i).get("menu_person");
        }

        boolean result = ConnectDB.insertToCatchedMenu2(StringUrl.insertCatchMenu, catchSeq, menuNm, menuPerson, userSeq, Userinfo.getStoreSeq(),menuPriceList.toArray(new String[menuPriceList.size()]));

        return result;
    }

    /*
    void show(String strCheckedMenu) {
        final Intent mvCatched = new Intent(getApplicationContext(), CatchedConfirm.class);
        mvCatched.putExtra("selected_menu", strCheckedMenu);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("선택한 목록을 확인해주세요.");
        builder.setMessage(strCheckedMenu);
        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivityForResult(mvCatched,103);
                    }
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();

    }
    */


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        HashMap<String,String> items = new HashMap<>();

        // requestCode 로 각 intent 구분
        if(requestCode == 101){ // Selected list 값 추가
            tempMenuPerson = data.getStringExtra("menu_person");        //  getExtra from MenuConfirm
            System.out.println(tempMenuNm + "||||===" + tempMenuPerson);

            items.put("menu_name",tempMenuNm);
            items.put("menu_person",tempMenuPerson);
            menuPriceList.add(tempMenuPrice);

            selectedListData.add(items);
            bAdapter.notifyDataSetChanged();

        }
        else if(requestCode == 102){    // Selected List 값 삭제

            tempMenuNm = data.getStringExtra("menu_name");        //  getExtra from MenuDelConfirm
            tempMenuPerson = data.getStringExtra("menu_person");

            selectedListData.remove(selectedPosition);
            // MenuDelConfirm 값 삭제

            menuPriceList.remove(selectedPosition);

            Toast.makeText(getApplicationContext(), tempMenuNm + " " + tempMenuPerson +  " 인분을 삭제 했습니다.", Toast.LENGTH_LONG).show();

            bAdapter.notifyDataSetChanged();

        }
        else if(requestCode == 103){
            String yn = data.getStringExtra("yn");        //  getExtra from CatchedConfirm

            if("y".equals(yn)){
                if(doMenuInsert()) {
                    Intent mvHome = new Intent(getApplicationContext(), AppMainOrderList.class);
                    startActivity(mvHome);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "주문 실패", Toast.LENGTH_LONG).show();
                }
            }

        }
        else if(requestCode == 104){

            String yn = data.getStringExtra("yn");

            if("y".equals(yn)){
                if(doMenuInsert()) {
                    CatchWaitStore.checkIsYN.shutdown();
                    Intent mvHome = new Intent(getApplicationContext(), AppMainOrderList.class);
                    startActivity(mvHome);
                    Toast.makeText(getApplicationContext(), "캐치 성공!", Toast.LENGTH_LONG).show();
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "주문 실패", Toast.LENGTH_LONG).show();
                }
            }

        }
    }

    @Override
    public void onBackPressed() {
        ConnectDB.setCatchCancle(CatchTempInfo.catchSeq);
        Intent mvCatchWait = new Intent(getApplicationContext(), CatchWaitStore.class);
        startActivity(mvCatchWait);
        finish();
    }

}

// do catch
package com.example.dmucatch_store.store.func_catch;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.dmucatch_store.R;
import com.example.dmucatch_store.db_connect.ConnectDB;
import com.example.dmucatch_store.db_connect.StringUrl;
import com.example.dmucatch_store.store.addressApi.AddVo;
import com.example.dmucatch_store.store.addressApi.addressParsing;
import com.example.dmucatch_store.store.menu.MenuChoose;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CatchWaitStore extends AppCompatActivity {
    ConnectDB db = new ConnectDB();
    StringUrl strUrl = new StringUrl();
    String [] phpVar; // 주소 값 가져오기
    String [] typeData;    // 주소 데이터
    ScheduledExecutorService doSelectCatch;
    Runnable reloader;
    int countSchedule = 1;
    public static ScheduledExecutorService checkIsYN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catch_wait_user_activity);

        try{
            addressParsing.parseGu(AddVo.newAdd);
            //db.selectToJson(strUrl.selectCatch, phpVar, typeData)

            phpVar = new String[] {"address"};
            typeData = new String[] {AddVo.par_newAdd};

            ProgressBar pb = findViewById(R.id.wait_progressBar);
            pb.setIndeterminate(true);
            pb.getIndeterminateDrawable().setColorFilter(Color.rgb(135,154,255),android.graphics.PorterDuff.Mode.MULTIPLY);

            final Button buttonCancel = findViewById(R.id.buttonCancel);
            buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doSelectCatch.shutdown();
                    finish();
                }
            });

            reloader = new Runnable() {
                @Override
                public void run() {
                    doCatch();
                }
            };

            doSelectCatch = Executors.newSingleThreadScheduledExecutor();
            doSelectCatch.scheduleAtFixedRate(reloader, 0, 1, TimeUnit.SECONDS);    // 0: no delay , retry 1 seconds for reloader

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void doCatch(){
        System.out.println("스케줄러 작동 중" + countSchedule);
        countSchedule++;
        System.out.println("strUrl.selectCatch "+ strUrl.selectCatch + " phpVar " + phpVar + " typeData " + typeData[0]);
        String catchJsonString = db.selectToJson(strUrl.selectCatch, phpVar, typeData);

        if("fail".equals(catchJsonString) || "fai".equals(catchJsonString)){
            // catch failed
        }
        else{

            doSelectCatch.shutdown();   // end scheduler when catcheds
            boolean is = doSelectCatch.isShutdown(); // check shutdown scheduler
            setCatched(catchJsonString);   // catch json parsing

            Intent mvConfirm = new Intent(getApplicationContext(), CatchConfirm.class);
            startActivityForResult(mvConfirm, 101);
        }

/*
        while(NoCatched && NoCancled){
            catchJsonString = db.selectToJson(strUrl.selectCatch, phpVar, typeData); // original catchJsonString was class value;

            if("fail".equals(catchJsonString) || "fai".equals(catchJsonString)){
                // catch failed
            }
            else{
                NoCatched = false;
                setCatched();   // catch json parsing

                Intent mvConfirm = new Intent(getApplicationContext(), CatchConfirm.class);
                startActivity(mvConfirm);

            }
        }
        */
    }

    // check for catch isyn_flag
    void doCheckIsYN(){

        if(db.isFinished(CatchTempInfo.catchSeq)){  // 캐치 종료 체크 isyn_flag : N
            checkIsYN.shutdown();
            Intent mvRetryCatch = new Intent(getApplicationContext(), RetryCatch.class);
            startActivity(mvRetryCatch);
            finish();
        }
        else{

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        HashMap<String, String> items = new HashMap<>();

        // requestCode 로 각 intent 구분
        if (requestCode == 101) { // CatchConfirm
            if(resultCode == RESULT_OK){   // 확인 result = -1
                ConnectDB.setCatched(CatchTempInfo.catchSeq);

                Runnable reloaderCheckIsYN = new Runnable() {
                    @Override
                    public void run() {
                        doCheckIsYN();
                    }
                };

                checkIsYN = Executors.newSingleThreadScheduledExecutor();
                checkIsYN.scheduleAtFixedRate(reloaderCheckIsYN, 0, 1, TimeUnit.SECONDS);    // 0: no delay , retry 1 seconds for reloader

                Intent mvMenuChoose = new Intent(getApplicationContext(), MenuChoose.class);
                mvMenuChoose.putExtra("catch_seq", CatchTempInfo.catchSeq);
                mvMenuChoose.putExtra("money", CatchTempInfo.money);
                mvMenuChoose.putExtra("person", CatchTempInfo.person);
                mvMenuChoose.putExtra("user_seq", CatchTempInfo.userSeq);

                startActivity(mvMenuChoose);

            }else{                  // 취소 result = 0
                doSelectCatch = Executors.newSingleThreadScheduledExecutor();
                doSelectCatch.scheduleAtFixedRate(reloader, 0, 1, TimeUnit.SECONDS);
            }
        }

        /*
        if (requestCode == 102) {
            if(resultCode == RESULT_OK){   // 확인 result = -1

            }else{                  // 취소 result = 0

            }
        }
        */

    }

    void setCatched(String catchJsonString) {
        System.out.println("================ setCatched() Start ================");

        String TAG_JSON = "catched"; // Json Tag
        String TAG_CATCH_SEQ = "catch_seq";
        String TAG_PERSON = "catch_person";
        String TAG_MONEY = "catch_money";
        String TAG_ADDRESS = "add_data";
        String TAG_USER_SEQ = "user_seq";
        // Json 구분자

        try {

            JSONObject jsonObject = new JSONObject(catchJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON); // Catched Menu Json

            String catchSeq = null;
            String person = null;
            String money = null;
            String address = null;
            String userSeq = null;

            for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject item = jsonArray.getJSONObject(i);
            System.out.println("아이이이이이이이이이템" + item);

            catchSeq = item.getString(TAG_CATCH_SEQ);
            person = item.getString(TAG_PERSON);
            money = item.getString(TAG_MONEY);
            address = item.getString(TAG_ADDRESS);
            userSeq = item.getString(TAG_USER_SEQ);

            }

            CatchTempInfo.catchSeq = catchSeq;
            CatchTempInfo.person = person;
            CatchTempInfo.money = money;
            CatchTempInfo.catchedAddress = address;
            CatchTempInfo.userSeq = userSeq;

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

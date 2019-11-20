package com.example.dmucatch_store.store.func_catch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dmucatch_store.R;
import com.example.dmucatch_store.db_connect.ConnectDB;
import com.example.dmucatch_store.store.menu.MenuChoose;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CatchConfirm extends AppCompatActivity {
    TextView strCatchInfo;
    ScheduledExecutorService doSelectCatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catch_confirm_activity);



        Runnable reloader = new Runnable() {
            @Override
            public void run() {
                if(ConnectDB.isCatched(CatchTempInfo.catchSeq)){    // catch 잡은게 catched 됐는지 검사 || 캐치 되면 true , 아직 캐치 되기 전이면 false;
                    doSelectCatch.shutdown();

                    Intent mvCatchWait = new Intent(getApplicationContext(), CatchWaitStore.class);
                    setResult(Activity.RESULT_CANCELED,mvCatchWait); // 이전화면 으로 보낼 세팅
                    finish();
                }
            }
        };

        doSelectCatch = Executors.newSingleThreadScheduledExecutor();
        doSelectCatch.scheduleAtFixedRate(reloader, 0, 1, TimeUnit.SECONDS);    // 0: no delay , retry 1 seconds for reloader

        strCatchInfo = findViewById(R.id.textCatched);
        strCatchInfo.setText(CatchTempInfo.money + "원에 " + CatchTempInfo.person + "인분!! 캐치하시겠습니까? ");


        Button btnDecline = findViewById(R.id.btnDecline);
        btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSelectCatch.shutdown();
                Intent mvCatchWait = new Intent(getApplicationContext(), CatchWaitStore.class);
                setResult(Activity.RESULT_CANCELED,mvCatchWait); // 이전화면 으로 보낼 세팅
                finish();
            }
        });

        Button btnAccept = findViewById(R.id.btnAccept);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSelectCatch.shutdown();
                Intent mvCatchWait = new Intent(getApplicationContext(), CatchWaitStore.class);
                setResult(Activity.RESULT_OK,mvCatchWait); // 이전화면 으로 보낼 세팅
                finish();
            }
        });

    }

}

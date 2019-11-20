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
import com.example.dmucatch_store.store.AppMainOrderList;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RetryCatch extends AppCompatActivity {
    TextView strCatchInfo;
    ScheduledExecutorService doSelectCatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catch_confirm_activity);

        strCatchInfo = findViewById(R.id.textCatched);
        strCatchInfo.setText("유저가 캐치를 취소했습니다.\n 다시 캐치 하시겠습니까?");


        Button btnDecline = findViewById(R.id.btnDecline);
        btnDecline.setText("취소");
        btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mvHome = new Intent(getApplicationContext(), AppMainOrderList.class);
                startActivity(mvHome);
                finish();
            }
        });

        Button btnAccept = findViewById(R.id.btnAccept);
        btnDecline.setText("캐치!");
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mvCatchWait = new Intent(getApplicationContext(), CatchWaitStore.class);
                startActivity(mvCatchWait);
                finish();
            }
        });

    }

}

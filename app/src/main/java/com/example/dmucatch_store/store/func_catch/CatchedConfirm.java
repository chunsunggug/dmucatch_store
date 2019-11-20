package com.example.dmucatch_store.store.func_catch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dmucatch_store.R;
import com.example.dmucatch_store.store.menu.MenuChoose;

public class CatchedConfirm extends AppCompatActivity {
    TextView strCatchInfo;
    String strSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catch_confirm_activity);

        Intent selectedMenu = getIntent();

        strSelected = selectedMenu.getStringExtra("selected_menu");

        strCatchInfo = findViewById(R.id.textCatched);

        strCatchInfo.setText(strSelected + "주문 합니다? ");


        Button btnDecline = findViewById(R.id.btnDecline);
        btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button btnAccept = findViewById(R.id.btnAccept);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mvMenuChoose = new Intent(getApplicationContext(), MenuChoose.class);
                mvMenuChoose.putExtra("yn", "y");

                setResult(Activity.RESULT_OK,mvMenuChoose); // 이전화면 으로 보낼 세팅
                finish();
            }
        });

    }

}

package com.example.dmucatch_store.store.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dmucatch_store.R;

public class MenuDelConfirm extends AppCompatActivity {
    TextView strMenuDel;
    String menuNm, menuPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_del_confirm_activity);

        strMenuDel = findViewById(R.id.textDelMenu);

        Intent menuDelConfirm = getIntent();

        menuNm = menuDelConfirm.getStringExtra("menu_name");
        menuPerson = menuDelConfirm.getStringExtra("menu_person");

        strMenuDel.setText(menuNm + " " + menuPerson + " 인분 선택을 취소 하시겠습니까?");

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
                mvMenuChoose.putExtra("menu_name", menuNm);
                mvMenuChoose.putExtra("menu_person", menuPerson);

                setResult(Activity.RESULT_OK,mvMenuChoose); // 이전화면 으로 보낼 세팅
                finish();
            }
        });


    }

}

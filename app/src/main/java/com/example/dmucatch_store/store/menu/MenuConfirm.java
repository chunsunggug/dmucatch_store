package com.example.dmucatch_store.store.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.dmucatch_store.R;
import com.example.dmucatch_store.utility.NumUtil;

public class MenuConfirm extends AppCompatActivity {
    TextView strCatchInfo;
    EditText person;
    final NumUtil numUtil = new NumUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_confirm_activity);

        Intent menuIntent = getIntent();

        String menuNm = menuIntent.getStringExtra("menu_name");
        String price = menuIntent.getStringExtra("menu_price");

        strCatchInfo = findViewById(R.id.menuSelected);
        strCatchInfo.setText(price + " 원 " + menuNm + " 몇 인분으로 하시겠습니까?");

        person = findViewById(R.id.textPerson);

        ImageButton pUpBtn = findViewById(R.id.btnRight);
        pUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                person.setText(Integer.toString(numUtil.upPerson(Integer.parseInt(person.getText().toString()))));
            }
        }); // 클릭 시 인원 수 증가 || onclick plus person one.

        ImageButton pDownBtn = findViewById(R.id.btnLeft);
        pDownBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                person.setText(Integer.toString(numUtil.dnPerson(Integer.parseInt(person.getText().toString()))));
            }
        }); // 클릭 시 인원 수 감소 || onclick minus person one.

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
                mvMenuChoose.putExtra("menu_person", person.getText().toString());

                setResult(Activity.RESULT_OK,mvMenuChoose); // 이전화면 으로 보낼 세팅

                finish();
            }
        });

    }

}

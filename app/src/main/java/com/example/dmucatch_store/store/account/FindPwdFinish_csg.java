package com.example.dmucatch_store.store.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.dmucatch_store.R;

public class FindPwdFinish_csg extends AppCompatActivity {
    Button  findIdBtn;
    Button loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findpwd_finish_activity_csg);


        findIdBtn = findViewById(R.id.findIdBtn);
        findIdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FindIdPwdUser_jjy.class);
                startActivity(intent);
            }
        });

        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginUser_kgt.class);
                startActivity(intent);
            }
        });


    }
}

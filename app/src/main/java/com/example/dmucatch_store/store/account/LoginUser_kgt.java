package com.example.dmucatch_store.store.account;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.dmucatch_store.R;
import com.example.dmucatch_store.db_connect.ConnectDB;
import com.example.dmucatch_store.db_connect.StringUrl;
import com.example.dmucatch_store.store.AppMainOrderList;
import com.example.dmucatch_store.utility.StringUtil;


public class LoginUser_kgt extends AppCompatActivity {

    Button BtnSignIn, BtnSignUp, BtnFindID;
    EditText inputID, inputPW;
    Userinfo userInfo = new Userinfo();

    private String mJsonString, addJsonString;
    ConnectDB connectdb = new ConnectDB(); // db connection 모음
    StringUrl url = new StringUrl(); // url 모음
    StringUtil util = new StringUtil(); // util 모음

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_user_activity_kgt);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        System.out.println("=========== LoginUser_kgt Start ===========");

        BtnSignUp = (Button)findViewById(R.id.btn_signup);
        BtnSignIn = (Button)findViewById(R.id.btn_signin);
        BtnFindID = (Button)findViewById(R.id.findIdPwd) ;
        inputID = (EditText)findViewById(R.id.user_id);
        inputPW = (EditText)findViewById(R.id.user_pw);


        BtnSignIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                                new Thread(new Runnable() {
                    public void run() {
                        login();
                    }
                }).start();}

        });

        BtnSignUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUp();
            }
        });
        //아이디 비밀번호 찾기 이동
        BtnFindID.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FindIdPwdUser_jjy.class);
                startActivity(intent);
            }
        });
    }

    void login() {
        final String id = inputID.getText().toString();
        final String pwd = inputPW.getText().toString();

        if(util.isNull(id) || util.isNull(pwd)){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "아이디/비밀번호를 입력하여 주십시오.", Toast.LENGTH_LONG).show();
                }
            });
        } // null check
        else {

            final String phpVar [] = {"userid", "password"}; // php로 넘길 post 테그 명
            final String typeData [] = {inputID.getText().toString(), inputPW.getText().toString()}; // EditText값 배열
            mJsonString = connectdb.selectToJson(url.login, phpVar, typeData); // login url, php 테그 배열, EditText 변수 값 배열

            if(util.isNoFoundUser(mJsonString)) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(mJsonString);
                        Toast.makeText(LoginUser_kgt.this, "아이디/비밀번호가 틀립니다.", Toast.LENGTH_SHORT).show();
                    }
                });
            } // user not found
            else if(util.isFailed(mJsonString)){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(mJsonString);
                        Toast.makeText(LoginUser_kgt.this, "네트워크 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
                    }
                });
            } // network || php error
            else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("json : "+mJsonString);
                        setLoginData.setUser(mJsonString); // getJsonData to Userinfo
                        setLoginData.getAddress(mJsonString); // getJsonData to AddVo
                        Toast.makeText(LoginUser_kgt.this, "Login Success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), AppMainOrderList.class);
                        startActivity(intent);
                    }
                });
                // finish();
            } // login successed
        }
    }

    void SignUp()
    {
        Intent intent = new Intent(this, SignUp_kgt.class);
        startActivity(intent);
    }
}
package com.example.dmucatch_store.store;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.dmucatch_store.R;
import com.example.dmucatch_store.db_connect.ConnectDB;
import com.example.dmucatch_store.db_connect.StringUrl;
import com.example.dmucatch_store.store.account.Userinfo;
import com.example.dmucatch_store.store.addressApi.AddVo;
import com.example.dmucatch_store.store.func_catch.CatchWaitStore;
import com.example.dmucatch_store.store.menu.MenuListStore_ltk;
import com.example.dmucatch_store.utility.StringUtil;

public class AppMainOrderList_Temp extends AppCompatActivity {
    Userinfo userInfo = new Userinfo();
    ConnectDB connectDB = new ConnectDB();
    StringUrl strUrl = new StringUrl();
    AddVo addVo = new AddVo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_ordered_list_activity_ljs);

        Button create = findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), MenuListStore_ltk.class);
                startActivity(it);

            }
        });

        Button btnCatch = findViewById(R.id.btnCatch);
        btnCatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!StringUtil.isNull(AddVo.newAdd)){
                    Intent it = new Intent(getApplicationContext(), CatchWaitStore.class);
                    startActivity(it);
                }
            }
        });
    }
}

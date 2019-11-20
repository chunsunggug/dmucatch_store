package com.example.dmucatch_store.store.menu;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dmucatch_store.R;
import com.example.dmucatch_store.store.account.SignUpEnd_csg;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class MenuModifyStore_hja extends AppCompatActivity {

    EditText modiName,modiInfo,modiPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_modify_store_hja);
        Intent intent=getIntent();

        final int menu_seq = intent.getIntExtra("menu_seq",0);
        String menu_name = intent.getStringExtra("menu_name");
        String menu_price = intent.getStringExtra("menu_price");
        String menu_info = intent.getStringExtra("menu_info");
        String menu_freeview = intent.getStringExtra("menu_freeview");

        System.out.println("modify seq : "+menu_seq);
        System.out.println("menu_name : "+menu_name);
        System.out.println("menu_price : "+menu_price);
        System.out.println("menu_info : "+menu_info);
        System.out.println("menu_freeview : "+menu_freeview);

        modiName=findViewById(R.id.modiName);
        modiName.setText(menu_name);
        modiPrice=findViewById(R.id.modiPrice);
        modiPrice.setText(menu_price);
        modiInfo=findViewById(R.id.modiInfo);
        modiInfo.setText(menu_info);


        Button btnsave=findViewById(R.id.btnsave);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getApplicationContext(),MenuListStore_ltk.class);
                startActivity(intent1);
            }
        });

        Button btnModi=findViewById(R.id.btnModify);
        btnModi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String Modiname = modiName.getText().toString();
                String Modiprice = modiPrice.getText().toString();
                String ModiInfo = modiInfo.getText().toString();



                modiMenuToDatabase(menu_seq,Modiname,Modiprice, ModiInfo);

                Intent intent1=new Intent(getApplicationContext(),MenuListStore_ltk.class);
                startActivity(intent1);
                finish();
            }
        });

        Button btnDel=findViewById(R.id.btnDel);
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                delMenuToDatabase(menu_seq);

                Intent intent1=new Intent(getApplicationContext(),MenuListStore_ltk.class);
                startActivity(intent1);
                finish();
            }
        });



    }

    private void delMenuToDatabase(int menu_seq) {

        class DelData extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                try {
                    String menu_seq = (String) params[0];


                    String link = "http://ci2019catch.dongyangmirae.kr/s/s_menudelete.php";
                    String data = URLEncoder.encode("Mseq", "UTF-8") + "=" + URLEncoder.encode(menu_seq, "UTF-8");
                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());


                    wr.write(data);
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    Toast.makeText(getApplicationContext(), "메뉴 삭제에 성공하였습니다.", Toast.LENGTH_LONG).show();
                    return sb.toString();
                } catch (Exception e) {
                    // Toast.makeText(getApplicationContext(), "메뉴 삭제에 실패하였습니다.", Toast.LENGTH_LONG).show();
                    return new String("Exception: " + e.getMessage());
                }
            }
        }
        DelData task = new DelData();
        task.execute(String.valueOf(menu_seq));



    }


    private void modiMenuToDatabase(int menu_seq, String modiname, String modiprice, String modiInfo) {

        class ModiData extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                try {
                    String menu_seq = (String) params[0];
                    String modiname = (String) params[1];
                    String modiprice = (String) params[2];
                    String modiinfo = (String) params[3];


                    String link = "http://ci2019catch.dongyangmirae.kr/s/s_menumodify.php";
                    String data = URLEncoder.encode("Mseq", "UTF-8") + "=" + URLEncoder.encode(menu_seq, "UTF-8");
                    data += "&" + URLEncoder.encode("Mname", "UTF-8") + "=" + URLEncoder.encode(modiname, "UTF-8");
                    data += "&" + URLEncoder.encode("Mprice", "UTF-8") + "=" + URLEncoder.encode(modiprice, "UTF-8");
                    data += "&" + URLEncoder.encode("Minfo", "UTF-8") + "=" + URLEncoder.encode(modiinfo, "UTF-8");
                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());


                    wr.write(data);
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    Toast.makeText(getApplicationContext(), "메뉴 수정에 성공하였습니다.", Toast.LENGTH_LONG).show();
                    return sb.toString();
                } catch (Exception e) {
                    // Toast.makeText(getApplicationContext(), "메뉴 수정에 실패하였습니다.", Toast.LENGTH_LONG).show();
                    return new String("Exception: " + e.getMessage());
                }
            }
        }
        ModiData task = new ModiData();
        task.execute(String.valueOf(menu_seq),modiname,modiprice, modiInfo);



    }
}

package com.example.dmucatch_store.store.account;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dmucatch_store.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import com.example.dmucatch_store.store.account.SignUpEnd_csg;
public class JoinStore_csg extends AppCompatActivity {
    private EditText editTextId;
    private EditText editTextPwd;
    private EditText editTextRePwd;
    private EditText editTextName;
    private EditText editTexttel;
    private EditText editTextAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_store_activity_csg);


        editTextId = (EditText) findViewById(R.id.bizid);
        editTextPwd = (EditText) findViewById(R.id.bizpwd);
        editTextRePwd = (EditText) findViewById(R.id.bizrepwd);
        editTextName = (EditText) findViewById(R.id.bizname);
        editTexttel = (EditText) findViewById(R.id.biztel);
        editTextAddress = (EditText) findViewById(R.id.bizaddress);

        Button back =findViewById(R.id.Backbtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void bizinsert(View view) {
        String Id = editTextId.getText().toString();
        String Pwd = editTextPwd.getText().toString();
        String RePwd = editTextRePwd.getText().toString();
        String Name = editTextName.getText().toString();
        String Phone = editTexttel.getText().toString();
        String Address = editTextAddress.getText().toString();




        if(Id.equals("")||Pwd.equals("")||RePwd.equals("")||Name.equals("")||Phone.equals("")||Address.equals("")){
            Toast.makeText(getApplicationContext(), "양식을 작성하여주십시오.", Toast.LENGTH_LONG).show();
        }else {
            if (Pwd.equals(RePwd)) {
                insertoToDatabase(Id, Pwd, Name, Phone, Address);
            } else {
                Toast.makeText(getApplicationContext(), "비밀번호가 다릅니다.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void insertoToDatabase(String Id, String Pwd, String Name, String Phone, String Address) {

        class InsertData extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                try {
                    String Id = (String) params[0];
                    String Pwd = (String) params[1];
                    String Name = (String) params[2];
                    String Phone = (String) params[3];
                    String Address = (String) params[4];

                    String link = "http://ci2019catch.dongyangmirae.kr/s/s_signup.php";
                    String data = URLEncoder.encode("Id", "UTF-8") + "=" + URLEncoder.encode(Id, "UTF-8");
                    data += "&" + URLEncoder.encode("Pwd", "UTF-8") + "=" + URLEncoder.encode(Pwd, "UTF-8");
                    data += "&" + URLEncoder.encode("Name", "UTF-8") + "=" + URLEncoder.encode(Name, "UTF-8");
                    data += "&" + URLEncoder.encode("Phone", "UTF-8") + "=" + URLEncoder.encode(Phone, "UTF-8");
                    data += "&" + URLEncoder.encode("Address", "UTF-8") + "=" + URLEncoder.encode(Address, "UTF-8");

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
                    Toast.makeText(getApplicationContext(), "회원가입에 성공하였습니다.", Toast.LENGTH_LONG).show();
                    return sb.toString();
                } catch (Exception e) {
                    // Toast.makeText(getApplicationContext(), "회원가입에 실패하였습니다.", Toast.LENGTH_LONG).show();
                    return new String("Exception: " + e.getMessage());
                }
            }
        }
        InsertData task = new InsertData();
        task.execute(Id, Pwd, Name, Phone, Address);
        Intent intent = new Intent(getApplicationContext(), SignUpEnd_csg.class);
        startActivity(intent);
        finish();
    }
}

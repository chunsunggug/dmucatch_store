package com.example.dmucatch_store.store.menu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.dmucatch_store.R;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MenuCreateStore_ltk extends AppCompatActivity {
    ImageButton image;
    TextView name;
    TextView price;
    TextView infor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_menu_create_activity_ltk);
        Intent getit = getIntent();//OrderList -> MenuCreateStore page  (store_id get)
        final String store_id = getit.getExtras().getString("store_id");
        image = findViewById(R.id.imgBtn);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imgit = new Intent();
                imgit.setType("image/*");
                imgit.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(imgit, 3);
            }
        });

        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] data = stream.toByteArray();


                name = findViewById(R.id.Mname);
                price = findViewById(R.id.price);
                infor = findViewById(R.id.infor);


                String mname = name.getText().toString();
                String mprice = price.getText().toString();
                String minfor = infor.getText().toString();
                String mdata = data.toString();


                InsertData task = new InsertData();
                task.execute("http://ci2019catch.dongyangmirae.kr/s/menu_insert.php", mname, mprice,minfor,mdata,store_id);

                Intent it = getIntent();

                setResult(RESULT_OK, it);
                finish();
            }
        });
    }

    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(MenuCreateStore_ltk.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
        }


        @Override
        protected String doInBackground(String... params) {
            try {
                String mname = (String)params[1];
                String mprice = (String)params[2];
                String minfor = (String)params[3];
                String mdata = (String)params[4];
                String store_id = (String)params[5];

                String serverURL = (String)params[0];

                String postParameters = URLEncoder.encode("name", "UTF-8") + "=" +  URLEncoder.encode(mname, "UTF-8") +"&" +  URLEncoder.encode("price", "UTF-8") + "=" +  URLEncoder.encode(mprice, "UTF-8")
                        +"&"+           URLEncoder.encode("info", "UTF-8") + "=" +  URLEncoder.encode(minfor, "UTF-8") +"&" +  URLEncoder.encode("photo", "UTF-8") + "=" +  URLEncoder.encode(mdata, "UTF-8")
                        +"&"+           URLEncoder.encode("store_id", "UTF-8") + "=" +  URLEncoder.encode(store_id, "UTF-8");




                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setDoOutput(true);


                OutputStreamWriter outputStream = new OutputStreamWriter(httpURLConnection.getOutputStream());
                outputStream.write(postParameters);
                outputStream.flush();


                InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);



                StringBuilder sb = new StringBuilder();
                String line = null;

                Log.d("완료",mname);


                return sb.toString();


            } catch (Exception e) {


                return new String("Error: " + e.getMessage());
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 3) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    image.setImageBitmap(img);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}

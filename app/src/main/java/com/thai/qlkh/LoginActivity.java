package com.thai.qlkh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.EventLogTags;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText edtUsername, edtPassword;
    private Button btnLogin;
    private String strURL = "http://192.168.1.69/qlkh/login.php";
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addControls();
        initData();
        checkLoginExist();
        loginEvent();
    }

    private void checkLoginExist() {
        String layDL = "SELECT * FROM tblKh";
        Cursor cursor = db.rawQuery(layDL, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()){
            Intent intent = new Intent(LoginActivity.this, Manager.class);
            intent.putExtra("USER", cursor.getString(1));
            startActivity(intent);
            finish();
        }
    }

    private void initData() {
        db = openOrCreateDatabase("tblKh.db", MODE_PRIVATE, null);
        String thembang = "CREATE TABLE IF NOT EXISTS tblKh (id integer primary key autoincrement, username text )";
        db.execSQL(thembang);
    }

    private void addControls() {
        edtUsername =  findViewById(R.id.edtUsername);
        edtPassword =  findViewById(R.id.edtPassWord);
        btnLogin = findViewById(R.id.btnLogin);
    }

    private void loginEvent() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                loginCheck(username,password);
            }
        });
    }

    private void loginCheck(String username, String password) {
        if (username.length()>0&&password.length()>0){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, strURL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("res", response);
                    if (response.equals("success")) {
                        String sql = "Insert into tblKh values(null,'"+username+"')";
                        db.execSQL(sql);
                        Intent intent = new Intent(LoginActivity.this, Manager.class);
                        intent.putExtra("USER", username);
                        startActivity(intent);
                        finish();
                    } else if (response.equals("failure")) {
                        Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không đúng.", Toast.LENGTH_SHORT).show();
                    }
//                    String res = response;
//                    Toast.makeText(getApplicationContext(),res,Toast.LENGTH_LONG).show();
//
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(LoginActivity.this,error.toString().trim(),Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<>();
                    params.put("username",username);
                    params.put("password",password);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
        else{
            Toast.makeText(LoginActivity.this,"Các trường không được để trống.",Toast.LENGTH_SHORT).show();
        }
    }
}
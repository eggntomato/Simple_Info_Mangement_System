package com.thai.qlkh;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.TransactionTooLargeException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddGuestActivity extends AppCompatActivity {
    private String addURL = "http://192.168.1.69/qlkh/creates.php";
    private String editURL = "http://192.168.1.69/qlkh/edit.php";
    private String TITTLE = "",BUTTON = "";
    private Button btnAction;
    private TextInputEditText edtTen,edtNS,edtDC,edtSDT;
    private int id;
    private CircleImageView imgAvatar;
    private TextView tvTen,tvSDT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_guest);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        TITTLE = intent.getStringExtra("TITTLE");
        if (!TITTLE.equals(""))setTitle(TITTLE);
        BUTTON = intent.getStringExtra("BUTTON");
        addControl();
        loaddata(intent);
        addEvents();
    }

    private void addEvents() {
        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TITTLE.equals("Thêm Khách Hàng")){
                    if (edtTen.getText().length()>0) {
                        String ten = edtTen.getText().toString();
                        String ns = edtNS.getText().toString();
                        String dc = edtDC.getText().toString();
                        String sdt = edtSDT.getText().toString();
                        addAction(ten,ns,dc,sdt);
                    }
                    else Toast.makeText(getApplicationContext(),"Trường Họ tên không được để trống",Toast.LENGTH_SHORT).show();
                }
                else if (TITTLE.equals("SỬA THÔNG TIN")){
                    if (edtTen.getText().length()>0) {
                        String ten = edtTen.getText().toString();
                        String ns = edtNS.getText().toString();
                        String dc = edtDC.getText().toString();
                        String sdt = edtSDT.getText().toString();
                        editAction(id,ten,ns,dc,sdt);
                    }
                    else Toast.makeText(getApplicationContext(),"Trường Họ tên không được để trống",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void editAction(int id, String ten, String ns, String dc, String sdt) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, editURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("res", response);
                if (response.contains("success")) {
                    Toast.makeText(getApplicationContext(),"Lưu thành công!", Toast.LENGTH_SHORT).show();
                } else if (response.equals("failure")) {
                    Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                }
//                    String res = response;
//                    Toast.makeText(getApplicationContext(),res,Toast.LENGTH_LONG).show();
//
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("ten",ten);
                params.put("ns",ns);
                params.put("dc",dc);
                params.put("sdt",sdt);
                params.put("id",String.valueOf(id));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void addAction(String ten, String ns, String dc, String sdt) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, addURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("res", response);
                if (response.contains("success")) {
                    Toast.makeText(getApplicationContext(),"Thêm thành công!", Toast.LENGTH_SHORT).show();
                } else if (response.equals("failure")) {
                    Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                }
//                    String res = response;
//                    Toast.makeText(getApplicationContext(),res,Toast.LENGTH_LONG).show();
//
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("ten",ten);
                params.put("ns",ns);
                params.put("dc",dc);
                params.put("sdt",sdt);
                params.put("avatar","");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void loaddata(Intent intent) {
        if (!BUTTON.equals("")) btnAction.setText(BUTTON);
        if (TITTLE.equals("SỬA THÔNG TIN")){
            id = intent.getIntExtra("KHID",0);
            tvTen.setText(intent.getStringExtra("TEN"));
            tvSDT.setText(intent.getStringExtra("SDT"));
            edtTen.setText(intent.getStringExtra("TEN"));
            edtDC.setText(intent.getStringExtra("DC"));
            edtSDT.setText(intent.getStringExtra("SDT"));
            edtNS.setText(intent.getStringExtra("NS"));
            if (!intent.getStringExtra("AVATAR").equals(""))
                Picasso.get().load(intent.getStringExtra("AVATAR")).into(imgAvatar);
        }
    }

    private void addControl() {
        btnAction = findViewById(R.id.profileButton);
        edtTen = findViewById(R.id.edtProfileName);
        edtNS = findViewById(R.id.edtProfileDate);
        edtDC = findViewById(R.id.edtProfileLocation);
        edtSDT = findViewById(R.id.edtProfilePhone);
        tvTen = findViewById(R.id.profilename);
        tvSDT = findViewById(R.id.profilephone);
        imgAvatar = findViewById(R.id.profileavatar);
    }
}
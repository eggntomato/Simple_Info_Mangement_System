package com.thai.qlkh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Manager extends AppCompatActivity {
    SQLiteDatabase db;
    ListView lvKhachHang;
    KhachHangAdapter adapter;
    String username;
    private ArrayList<KhachHangModel> arr;
    private SearchView searchView;
    private String views_URL = "http://192.168.1.69/qlkh/views.php";
    private String delete_URL = "http://192.168.1.69/qlkh/delete.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        Intent intent  = getIntent();
        if(!TextUtils.isEmpty(intent.getStringExtra("USER"))) username = intent.getStringExtra("USER");
        addUser();
        addControls();
        addListView();
        addEvents();
        getData();
    }

    private void addUser() {
        if(username == null){
            db = openOrCreateDatabase("tblKh.db", MODE_PRIVATE, null);
            String layDL = "SELECT * FROM tblKh";
            Cursor cursor = db.rawQuery(layDL, null);
            cursor.moveToFirst();
            if (!cursor.isAfterLast()){
                username = cursor.getString(1);
            }
        }
    }

    @Override
    protected void onRestart() {
        Intent intent = new Intent(getApplicationContext(),Manager.class);
        intent.putExtra("USER",username);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        super.onRestart();
    }


    private void getData() {
        arr.clear();
        adapter.notifyDataSetChanged();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, views_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i =0;i<array.length();i++){
                        JSONObject object = array.getJSONObject(i);
                        int id = object.getInt("id");
                        String ten = object.getString("ten");
                        String ns = object.getString("ns");
                        String dc = object.getString("dc");
                        String sdt = object.getString("sdt");
                        String avatar = object.getString("avatar");
                        KhachHangModel model = new KhachHangModel(id,ten,ns,dc,sdt,avatar);
                        arr.add(model);
                    }
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menuLogout:
                db = openOrCreateDatabase("tblKh.db", MODE_PRIVATE, null);
                String xoa = "DELETE FROM tblKh";
                db.execSQL(xoa);
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"Đăng xuất thành công!",Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.menuAdd:
                Intent intent1 = new Intent(getApplicationContext(),AddGuestActivity.class);
                intent1.putExtra("TITTLE","Thêm Khách Hàng");
                intent1.putExtra("BUTTON","THÊM");
                startActivity(intent1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addEvents() {

    }

    private void addListView() {
        arr = new ArrayList<>();
        adapter = new KhachHangAdapter(this,arr,this);
        lvKhachHang.setAdapter(adapter);
        registerForContextMenu(lvKhachHang);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_admin,menu);
        MenuItem item = menu.findItem(R.id.menuUserName);
        item.setTitle("Hi! "+username);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.menuSearch).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_context,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.menuDelete:
                KhachHangModel model = arr.get(info.position);
                int id = model.getId();
                deleteData(id);
                arr.remove(info.position);
                adapter.notifyDataSetChanged();
                return true;
            case R.id.menuEdit:
                KhachHangModel model1 = arr.get(info.position);
                Intent intent = new Intent(Manager.this,AddGuestActivity.class);
                intent.putExtra("TITTLE","SỬA THÔNG TIN");
                intent.putExtra("BUTTON","LƯU");
                intent.putExtra("KHID",model1.getId());
                intent.putExtra("TEN",model1.getHT());
                intent.putExtra("NS",model1.getNS());
                intent.putExtra("DC",model1.getDiaChi());
                intent.putExtra("SDT",model1.getSDT());
                intent.putExtra("AVATAR",model1.getAvatar());
                startActivity(intent);
        }
        return super.onContextItemSelected(item);
    }

    private void deleteData(int id) {
        String strid = String.valueOf(id);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, delete_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("res", response);
                if (response.equals("success")) {
                    Toast.makeText(getApplicationContext(),"Xoá thành công!", Toast.LENGTH_SHORT).show();
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
                params.put("id",strid);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void addControls() {
        lvKhachHang = findViewById(R.id.lvKhachHang);
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()){
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }
}
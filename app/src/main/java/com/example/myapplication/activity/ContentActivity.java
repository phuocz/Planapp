package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.adapter.Adapter_Account;
import com.example.myapplication.adapter.Adapter_Comment;
import com.example.myapplication.adapter.Adapter_cook_user;
import com.example.myapplication.model.Comment;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContentActivity extends AppCompatActivity {
    int a;
    TextView tv_name,tv_contentct,tv_nguyenlieu;
    EditText txtcomment;
    Button btncommnet;
    ImageView imageView;
    int check=MainActivity.status_bl;
    static int id_nd;
    private RecyclerView recyclerView;
    private Adapter_Comment adapter_comment;
    List<Comment> list = new ArrayList<Comment>();
    MainActivity mainActivity =new MainActivity();
    String getAllUrl = mainActivity.localhost()+ "/android/getcontent.php";
    String Urlinsert = mainActivity.localhost()+ "/android/insertbinhluan.php";
    String getAllcommnet = mainActivity.localhost()+ "/android/getbinhluan.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_activity);
        tv_name=findViewById(R.id.tv_name);
        tv_contentct=findViewById(R.id.tv_contentct);
        tv_nguyenlieu=findViewById(R.id.txtnguyenlieu);
        imageView=findViewById(R.id.imageView);
        txtcomment=findViewById(R.id.txtcomment);
        btncommnet=findViewById(R.id.btncommnet);

        recyclerView=(RecyclerView) findViewById(R.id.comment_recyclerView);
        Bundle bundle=getIntent().getExtras();
        a=bundle.getInt("id");
        Log.d("tên",String.valueOf(a));
        getAll(a);

        btncommnet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               
                if (check==0){
                    Toast.makeText(ContentActivity.this,"bạn đã bị chặn không thể dùng chức năng này ",Toast.LENGTH_LONG).show();
                }
                else {
                    list.clear();
                    Log.d("textxxxid",""+String.valueOf(id_nd));
                    insertbinhluan(MainActivity.id,id_nd,txtcomment.getText().toString());
                    getAll(a);
                    txtcomment.setText("");
            }

            }
        });
    }

    public void getAll(final int id){



        StringRequest stringrequest = new StringRequest(
                Request.Method.POST,getAllUrl
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.d("aaasasasas",response);
                    JSONObject jsonobject=new JSONObject(response);

                    //doc tat ca du lieu tu json bo vao ArrayList


                    JSONArray sps=jsonobject.getJSONArray("congthuc");
                    Log.d("aaasasasas","1");
                    for(int i=0;i<sps.length();i++) {
                        JSONObject spsJSONObject = sps.getJSONObject(i);
                        id_nd=spsJSONObject.getInt("id");
                        tv_name.setText(spsJSONObject.getString("name"));
                        tv_nguyenlieu.setText(spsJSONObject.getString("nguyenlieu"));
                        tv_contentct.setText(spsJSONObject.getString("noidung"));
                        Glide.with(ContentActivity.this).load(mainActivity.localhost()+"/android/"+spsJSONObject.getString("picture")).into(imageView);
                        Log.d("tên",spsJSONObject.getString("name"));
                        Log.d("tên",spsJSONObject.getString("noidung"));
                        Log.d("tên ảnh",spsJSONObject.getString("picture"));

                    }
                    getAllBinhluan(id_nd);
                } catch (JSONException e) {
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(ContentActivity.this, "Volley error", Toast.LENGTH_SHORT).show();
                Log.d("loi",error.toString());

            }
        }
        ) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param=new HashMap<String,String>();

                param.put("id",String.valueOf(id));


                return param;
            }
        };
        Volley.newRequestQueue(ContentActivity.this).add(stringrequest);
        return ;
    }

    public void insertbinhluan(final int id_user,int id_noidung,String binhluan){



        StringRequest stringrequest = new StringRequest(
                Request.Method.POST,Urlinsert
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.d("aaasasasas",response);

                } catch (Exception e) {
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(ContentActivity.this, "Volley error", Toast.LENGTH_SHORT).show();
                Log.d("loi",error.toString());

            }
        }
        ) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param=new HashMap<String,String>();

                param.put("id_user",String.valueOf(id_user));
                param.put("id_noidung",String.valueOf(id_noidung));
                param.put("binhluan",String.valueOf(binhluan));


                return param;
            }
        };
        Volley.newRequestQueue(ContentActivity.this).add(stringrequest);
        return ;
    }

    public void getAllBinhluan(final int id_nd){


        StringRequest stringrequest = new StringRequest(
                Request.Method.POST,getAllcommnet
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.d("getAllBinhluan",response);
                    JSONObject jsonobject=new JSONObject(response);

                    //doc tat ca du lieu tu json bo vao ArrayList


                    JSONArray sps=jsonobject.getJSONArray("binhluan");

                    for(int i=0;i<sps.length();i++) {
                        JSONObject spsJSONObject = sps.getJSONObject(i);


                        Comment comment = new Comment();
                        comment.user_name = spsJSONObject.getString("user_name");;
                        comment.id_user = spsJSONObject.getInt("id_user");;
                        comment.id = spsJSONObject.getInt("id");;
                        comment.comment=spsJSONObject.getString("noidung");;
                        comment.photo_user =mainActivity.localhost()+"/android/"+spsJSONObject.getString("picture_user");
                        Log.d("tên",String.valueOf(comment.user_name));
                        Log.d("tên",comment.comment);

                        list.add(comment);

                    }
                    Collections.reverse(list);
                    adapter_comment= new Adapter_Comment(ContentActivity.this,list);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ContentActivity.this));
                    recyclerView.setAdapter(adapter_comment);
                } catch (JSONException e) {
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(ContentActivity.this, "Volley error", Toast.LENGTH_SHORT).show();
                Log.d("loi",error.toString());

            }
        }
        ){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param=new HashMap<String,String>();

                param.put("id",String.valueOf(id_nd));


                return param;
            }
        };
        Volley.newRequestQueue(ContentActivity.this).add(stringrequest);
        return ;
    }
}

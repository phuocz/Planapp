package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;
import com.example.myapplication.adapter.Adapter_cook_user;
import com.example.myapplication.adapter.Adapter_favcook;
import com.example.myapplication.model.DatatestAll_Cook;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavActivity extends AppCompatActivity {

    MainActivity mainActivity=new MainActivity();
    String getAllUrl = mainActivity.localhost() + "/android/getcongthucfav.php";
    String getfav = mainActivity.localhost() + "/android/getfavid.php";
    private RecyclerView recyclerView;
    private Adapter_favcook adapter_cook;
    List<DatatestAll_Cook> list = new ArrayList<DatatestAll_Cook>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);
        recyclerView=(RecyclerView) findViewById(R.id.cook_recyclerView);
        getfav(MainActivity.id);

    }public void getfav(final int id){


        StringRequest stringrequest = new StringRequest(
                Request.Method.POST,getfav
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.d("aaasasasas",response);
                    JSONObject jsonobject=new JSONObject(response);

                    //doc tat ca du lieu tu json bo vao ArrayList


                    JSONArray sps=jsonobject.getJSONArray("congthuc");

                    for(int i=0;i<sps.length();i++) {
                        JSONObject spsJSONObject = sps.getJSONObject(i);



                        getAll( spsJSONObject.getInt("id_congthuc"));

                    }
                } catch (JSONException e) {
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(FavActivity.this, "Volley error", Toast.LENGTH_SHORT).show();
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
        Volley.newRequestQueue(FavActivity.this).add(stringrequest);
        return ;
    }


    ////////////////////////////////////
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

                    for(int i=0;i<sps.length();i++) {
                        JSONObject spsJSONObject = sps.getJSONObject(i);


                        DatatestAll_Cook datatestAllCook = new DatatestAll_Cook();
                        datatestAllCook.id = spsJSONObject.getInt("id");;
                        datatestAllCook.name = spsJSONObject.getString("name");;
                        datatestAllCook.mota = spsJSONObject.getString("mota");;
                        datatestAllCook.photo =mainActivity.localhost()+"/android/"+spsJSONObject.getString("picture");
                        Log.d("tên",String.valueOf(datatestAllCook.id));
                        Log.d("tên", datatestAllCook.name);
                        Log.d("tên", datatestAllCook.mota);
                        Log.d("ảnh", datatestAllCook.photo);
                        list.add(datatestAllCook);
                        adapter_cook= new Adapter_favcook(FavActivity.this,list);
                        recyclerView.setLayoutManager(new LinearLayoutManager(FavActivity.this));
                        recyclerView.setAdapter(adapter_cook);
                    }
                } catch (JSONException e) {
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(FavActivity.this, "Volley error", Toast.LENGTH_SHORT).show();
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
        Volley.newRequestQueue(FavActivity.this).add(stringrequest);
        return ;
    }
}
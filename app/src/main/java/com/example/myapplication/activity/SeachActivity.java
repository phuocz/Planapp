package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;
import com.example.myapplication.adapter.Adapter_seach;
import com.example.myapplication.model.DatatestAll_Cook;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SeachActivity extends AppCompatActivity {
    MainActivity mainActivity=new MainActivity();
    String getAllUrl = mainActivity.localhost() + "/android/getallcongthuc.php";

    private RecyclerView recyclerView;
    private Adapter_seach adapter_seach;
    List<DatatestAll_Cook> list = new ArrayList<DatatestAll_Cook>();

    EditText edseach;
    Button btnseach;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seach);
        edseach=findViewById(R.id.edseach);
        btnseach=findViewById(R.id.bntseach);

        recyclerView=(RecyclerView) findViewById(R.id.seach_recyclerView);
        btnseach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                getAll();
            }
        });


    }
    public void getAll(){


        StringRequest stringrequest = new StringRequest(
                getAllUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.d("seachaaaaa",response);
                    JSONObject jsonobject=new JSONObject(response);

                    //doc tat ca du lieu tu json bo vao ArrayList

                    JSONArray sps=jsonobject.getJSONArray("congthuc");

                    for(int i=0;i<sps.length();i++) {
                        JSONObject spsJSONObject = sps.getJSONObject(i);


                        DatatestAll_Cook datatestAllCook = new DatatestAll_Cook();
                        datatestAllCook.name = spsJSONObject.getString("name");;
                        datatestAllCook.id = spsJSONObject.getInt("id");;
                        datatestAllCook.mota = spsJSONObject.getString("mota");;
                        datatestAllCook.photo =mainActivity.localhost()+"/android/"+spsJSONObject.getString("picture");

                        Log.d("tên",String.valueOf(datatestAllCook.id));
                        Log.d("tên", datatestAllCook.name);
                        Log.d("tên", datatestAllCook.mota);
                       boolean a= datatestAllCook.name.contains(edseach.getText().toString());
                       if(a==true){
                           list.add(datatestAllCook);
                           adapter_seach= new Adapter_seach(SeachActivity.this,list);
                           recyclerView.setLayoutManager(new LinearLayoutManager(SeachActivity.this));
                           recyclerView.setAdapter(adapter_seach);
                       }

                    }
                } catch (JSONException e) {
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(SeachActivity.this, "Volley error", Toast.LENGTH_SHORT).show();
                Log.d("loi",error.toString());

            }
        }
        ) ;
        Volley.newRequestQueue(SeachActivity.this).add(stringrequest);
        return ;
    }
}
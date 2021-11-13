package com.example.myapplication.ui.hot;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;
import com.example.myapplication.activity.MainActivity;
import com.example.myapplication.adapter.Adapter_cook_hot;
import com.example.myapplication.model.DatatestAll_Cook;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HotFragment extends Fragment {

    View v;

    MainActivity mainActivity=new MainActivity();
    String getcthot =mainActivity.localhost()+"/android/getcongthuchot.php";
    String getAllid =mainActivity.localhost()+"/android/getallidcongthuc.php";
    private RecyclerView recyclerView;
    private Adapter_cook_hot adapter_cookHot;
    List<DatatestAll_Cook> list = new ArrayList<DatatestAll_Cook>();
    public HotFragment(){
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_cook,container,false);



        recyclerView=(RecyclerView) v.findViewById(R.id.cook_recyclerView);

        getAllid();
        return v;

    }

    public void getAllid(){


        StringRequest stringrequest = new StringRequest(getAllid
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

                        getbycount(spsJSONObject.getInt("id"));

                    }
                } catch (JSONException e) {
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), "Volley error", Toast.LENGTH_SHORT).show();
                Log.d("loi",error.toString());

            }
        }
        ) ;
        Volley.newRequestQueue(getContext()).add(stringrequest);
    }
    public void getbycount(final int id){


        StringRequest stringrequest = new StringRequest( Request.Method.POST, getcthot
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.d("aadxasasas",response);
                    JSONObject jsonobject=new JSONObject(response);

                    //doc tat ca du lieu tu json bo vao ArrayList


                    JSONArray sps=jsonobject.getJSONArray("congthuc");

                    for(int i=0;i<sps.length();i++) {
                        JSONObject spsJSONObject = sps.getJSONObject(i);


                        DatatestAll_Cook datatestAllCook = new DatatestAll_Cook();
                        datatestAllCook.countfav = spsJSONObject.getInt("conunt");;
                        datatestAllCook.id = spsJSONObject.getInt("id");;
                        datatestAllCook.name = spsJSONObject.getString("name");;
                        datatestAllCook.mota = spsJSONObject.getString("mota");;
                        datatestAllCook.photo =mainActivity.localhost()+"/android/"+spsJSONObject.getString("picture");
                        list.add(datatestAllCook);

                        Collections.sort(list,new sapxep());
                        adapter_cookHot = new Adapter_cook_hot(getContext(),list);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setAdapter(adapter_cookHot);
                    }
                } catch (JSONException e) {
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), "Volley error", Toast.LENGTH_SHORT).show();
                Log.d("loi",error.toString());

            }
        }
        ) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param=new HashMap<String,String>();

                param.put("id_congthuc",String.valueOf(id));


                return param;
            }
        };
        Volley.newRequestQueue(getContext()).add(stringrequest);
        return ;
    }

    public class sapxep implements Comparator<DatatestAll_Cook> {


        @Override
        public int compare(DatatestAll_Cook o1, DatatestAll_Cook o2) {
            int count1 = o1.getCountfav();
            int count2 = o2.getCountfav();
            if (count1 < count2) {
                return 1;
            } else if (count1 == count2) {
                return 0;
            } else {
                return -1;
            }
        }
    }
}
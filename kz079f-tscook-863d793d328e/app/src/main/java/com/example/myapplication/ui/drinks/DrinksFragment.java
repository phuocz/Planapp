package com.example.myapplication.ui.drinks;

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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;
import com.example.myapplication.activity.MainActivity;
import com.example.myapplication.adapter.Adapter_home;

import com.example.myapplication.model.DatatestAll_Cook;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DrinksFragment extends Fragment {

    View v;
    MainActivity mainActivity =new MainActivity();
    String getAllUrl = mainActivity.localhost()+"/android/getbuanuoc.php";
    private RecyclerView recyclerView;

    private Adapter_home adapter_home;

    List<DatatestAll_Cook> list = new ArrayList<DatatestAll_Cook>();
    public DrinksFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_breakfast,container,false);
        recyclerView=(RecyclerView) v.findViewById(R.id.home_recyclerView);
        getAll();
        BottomNavigationView navView = v.findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.



        return v;

    }

    public void getAll(){



        StringRequest stringrequest = new StringRequest(getAllUrl
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
                        datatestAllCook.tacgia=spsJSONObject.getString("user_name");;
                        datatestAllCook.id = spsJSONObject.getInt("id");;
                        datatestAllCook.name = spsJSONObject.getString("name");;
                        datatestAllCook.mota = spsJSONObject.getString("mota");;
                        datatestAllCook.photo =mainActivity.localhost()+"/android/"+spsJSONObject.getString("picture");
                        datatestAllCook.phototg =mainActivity.localhost()+"/android/"+spsJSONObject.getString("picture_user");

                        Log.d("tênxxxx", datatestAllCook.name);
                        Log.d("tên", datatestAllCook.name);
                        Log.d("tên", datatestAllCook.mota);
                        Log.d("ảnh", datatestAllCook.photo);
                        list.add(datatestAllCook);
                        adapter_home= new Adapter_home(getActivity(),list);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setAdapter(adapter_home);
                    }
                } catch (JSONException e) {
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(), "Volley error", Toast.LENGTH_SHORT).show();
                Log.d("loi",error.toString());

            }
        }
        ) ;
        Volley.newRequestQueue(getActivity()).add(stringrequest);


        Log.d("size",String.valueOf(list.size()));


    }





}
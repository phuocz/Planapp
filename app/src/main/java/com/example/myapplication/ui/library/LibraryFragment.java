package com.example.myapplication.ui.library;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibraryFragment extends Fragment {

    View v;

    MainActivity mainActivity=new MainActivity();
    String getAllUrl = mainActivity.localhost() + "/android/getcongthucid.php";
    private RecyclerView recyclerView;
    private Adapter_cook_hot adapter_cookHot;
    List<DatatestAll_Cook> list = new ArrayList<DatatestAll_Cook>();

    public LibraryFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_cook, container, false);


        recyclerView = (RecyclerView) v.findViewById(R.id.cook_recyclerView);

        getAll(MainActivity.id);
        return v;

    }

    public void getAll(final int id) {


        StringRequest stringrequest = new StringRequest(
                Request.Method.POST, getAllUrl
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.d("aaasasasas", response);
                    JSONObject jsonobject = new JSONObject(response);

                    //doc tat ca du lieu tu json bo vao ArrayList


                    JSONArray sps = jsonobject.getJSONArray("congthuc");

                    for (int i = 0; i < sps.length(); i++) {
                        JSONObject spsJSONObject = sps.getJSONObject(i);


                        DatatestAll_Cook datatestAllCook = new DatatestAll_Cook();
                        datatestAllCook.name = spsJSONObject.getString("name");
                        ;
                        datatestAllCook.mota = spsJSONObject.getString("mota");
                        ;
                        datatestAllCook.photo = mainActivity.localhost() + "/android/" + spsJSONObject.getString("picture");
                        Log.d("tên", String.valueOf(datatestAllCook.id));
                        Log.d("tên", datatestAllCook.name);
                        Log.d("tên", datatestAllCook.mota);
                        Log.d("ảnh", datatestAllCook.photo);
                        list.add(datatestAllCook);
                        adapter_cookHot = new Adapter_cook_hot(getContext(), list);
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
                Log.d("loi", error.toString());

            }
        }
        ) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();

                param.put("id", String.valueOf(id));


                return param;
            }
        };
        Volley.newRequestQueue(getContext()).add(stringrequest);
        return;
    }
}
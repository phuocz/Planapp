package com.example.myapplication.ui.neww;

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
import com.example.myapplication.adapter.Adapter_cook_new;
import com.example.myapplication.model.DatatestAll_Cook;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class NewFragment extends Fragment {


    View v;


    MainActivity mainActivity=new MainActivity();
        String getAllUrl = mainActivity.localhost() + "/android/getallcongthuc.php";
    private RecyclerView recyclerView;
    private Adapter_cook_new adapter_cook;
    List<DatatestAll_Cook> list = new ArrayList<DatatestAll_Cook>();
    public NewFragment(){
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_new,container,false);



        recyclerView=(RecyclerView) v.findViewById(R.id.cook_recyclerView);

        getAll();
        return v;

    }

    public void getAll(){


        StringRequest stringrequest = new StringRequest(
              getAllUrl
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.d("aaasasasas",response);
                    JSONObject jsonobject=new JSONObject(response);

                    //doc tat ca du lieu tu json bo vao ArrayList
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("YYYY-MM-dd HH:MM:SS");

                    JSONArray sps=jsonobject.getJSONArray("congthuc");

                    for(int i=0;i<sps.length();i++) {
                        JSONObject spsJSONObject = sps.getJSONObject(i);


                        DatatestAll_Cook datatestAllCook = new DatatestAll_Cook();
                        datatestAllCook.name = spsJSONObject.getString("name");;
                        datatestAllCook.id = spsJSONObject.getInt("id");;
                        datatestAllCook.mota = spsJSONObject.getString("mota");;
                        datatestAllCook.photo =mainActivity.localhost()+"/android/"+spsJSONObject.getString("picture");
                        datatestAllCook.date =simpleDateFormat.parse(spsJSONObject.getString("creat_date"));
                        Log.d("tên",String.valueOf(datatestAllCook.id));
                        Log.d("tên", datatestAllCook.name);
                        Log.d("tên", datatestAllCook.mota);

                        list.add(datatestAllCook);
                    }

                    Collections.sort(list, new Comparator<DatatestAll_Cook>() {
                        @Override
                        public int compare(DatatestAll_Cook Cook1, DatatestAll_Cook Cook2) {
                            if (Cook1.id < Cook2.id) {
                                return 1;
                            } else {
                                if (Cook1.id == Cook2.id) {
                                    return 0;
                                } else {
                                    return -1;
                                }
                            }
                        }
                    });
                    adapter_cook= new Adapter_cook_new(getContext(),list);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(adapter_cook);
                } catch (JSONException | ParseException e) {
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
        return ;
    }

    public static class ComparDate implements Comparable<DatatestAll_Cook> {

        private Date dateTime;

        public Date getDateTime() {
            return dateTime;
        }

        public void setDateTime(Date datetime) {
            this.dateTime = datetime;
        }

        @Override
        public int compareTo(DatatestAll_Cook o) {
            if (o.getDate() == null || o.getDate() == null)
                return 0;
            return getDateTime().compareTo(o.getDate());
        }
    }
}
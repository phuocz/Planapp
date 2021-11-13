package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.activity.ContentActivity;
import com.example.myapplication.activity.MainActivity;
import com.example.myapplication.model.DatatestAll_Cook;
import com.google.android.material.imageview.ShapeableImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Adapter_home extends RecyclerView.Adapter<Adapter_home.MyViewHolder> {

    Context mcontext;
    List<DatatestAll_Cook> mdata;
    ImageLoader imageLoader;

    private OnItemClickListener onclick;
    MainActivity mainActivity =new MainActivity();
    String urlstatus=mainActivity.localhost()+"/android/getstatus.php";
    String urlstatusid=mainActivity.localhost()+"/android/getstatusid.php";
    String urleditstatus=mainActivity.localhost()+"/android/editstatus.php";
    String urlcount=mainActivity.localhost()+"/android/getcountfavotive.php";
    String urlinsertstatus=mainActivity.localhost()+"/android/insertstatus.php";
    String urldelete=mainActivity.localhost()+"/android/deletefavotive.php";
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        onclick = listener;
    }
    public Adapter_home(Context mcontext, List<DatatestAll_Cook> mdata) {
        this.mcontext=mcontext;
        this.mdata=mdata;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v= LayoutInflater.from(mcontext).inflate(R.layout.cardviewhome,parent,false);
        MyViewHolder vholder= new MyViewHolder(v,onclick);
        return vholder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final DatatestAll_Cook datatestAllCook =mdata.get(position);

        holder.tvtacgia.setText(mdata.get(position).getTacgia());
        holder.tvname.setText(mdata.get(position).getName());
        holder.tvmota.setText(mdata.get(position).getMota());
       Glide.with(mcontext).load(datatestAllCook.getPhoto()).into(holder.img);
        Glide.with(mcontext).load(datatestAllCook.getPhototg()).into(holder.imgtg);


        ////////////////////////////////////////////////
try {
    StringRequest stringcount = new StringRequest(
            Request.Method.POST,urlcount
            , new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                Log.d("aaasadasdas",response);
                JSONObject jsonobject=new JSONObject(response);
                JSONArray sps=jsonobject.getJSONArray("count");
                for(int i=0;i<sps.length();i++) {
                    JSONObject spsJSONObject = sps.getJSONObject(i);
                    holder.number.setText(spsJSONObject.getString("count"));

                }


                //doc tat ca du lieu tu json bo vao ArrayList
            } catch (JSONException e) {
            }


        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

            Toast.makeText(mcontext, "Volley error", Toast.LENGTH_SHORT).show();
            Log.d("loi",error.toString());

        }
    }
    ) {

        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String,String> param=new HashMap<String,String>();

            param.put("id_ct",String.valueOf(String.valueOf(mdata.get(position).getId())));
            return param;
        }
    };
    Volley.newRequestQueue(mcontext).add(stringcount);



}catch (Exception e){}



////////////////////////////////////////////
        StringRequest stringrequest = new StringRequest(
                Request.Method.POST,urlstatusid
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String status = null;
                int id_ct ;
                int id_user ;

                try {

                    JSONObject jsonobject=new JSONObject(response);

                    //doc tat ca du lieu tu json bo vao ArrayList


                    JSONArray sps=jsonobject.getJSONArray("status");
                    holder.favorite.setBackgroundResource(R.drawable.ic_shadow_favorite_24);
                    for(int i=0;i<sps.length();i++) {
                        JSONObject spsJSONObject = sps.getJSONObject(i);
                        status=spsJSONObject.getString("status");
                        id_ct=spsJSONObject.getInt("id_congthuc");
                        id_user=spsJSONObject.getInt("id_user");
                        if (status.equals("1") && id_ct==mdata.get(position).getId() && id_user ==MainActivity.id ){
                            int a=position;
                            if(a==position){
                                Log.d("lỗi position", String.valueOf(mdata.get(position).name));

                                holder.favorite.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
                            }


                        }else {
                        if (status.equals("0") && id_ct==mdata.get(position).getId() && id_user ==MainActivity.id ){
                            Log.d("lỗi position", String.valueOf(id_ct==mdata.get(position).getId()));
                            holder.favorite.setBackgroundResource(R.drawable.ic_shadow_favorite_24);

                        }
                        }

                    }

                } catch (JSONException e) {
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(mcontext, "Volley error", Toast.LENGTH_SHORT).show();
                Log.d("loi",error.toString());

            }
        }
        ) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param=new HashMap<String,String>();

                param.put("id",String.valueOf(MainActivity.id));


                return param;
            }
        };
        Volley.newRequestQueue(mcontext).add(stringrequest);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                Bundle bundle=new Bundle();
                intent=new Intent(mcontext, ContentActivity.class);
                bundle.putInt("id",mdata.get(position).getId());
                intent.putExtras(bundle);
                mcontext.startActivity(intent);
            }
        });

        holder.favorite.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                StringRequest stringrequest = new StringRequest(
                        Request.Method.POST,urlstatus
                        , new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String status =null;
                        String id_user=null ;
                        String id=null ;

                            Log.d("lamthedeonao",response);
                        try {
                            if(response.equals("{\"thanhcong\":\"0\"}")){

                                holder.favorite.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
                                String a= holder.number.getText().toString();
                                holder.number.setText(String.valueOf(Integer.valueOf(a)+1));
                            }
                            JSONObject jsonobject=new JSONObject(response);
                            JSONArray sps=jsonobject.getJSONArray("status");


                            //doc tat ca du lieu tu json bo vao ArrayList
                            for(int i=0;i<sps.length();i++) {
                                JSONObject spsJSONObject = sps.getJSONObject(i);
                                id= spsJSONObject.getString("id");
                                id_user = spsJSONObject.getString("id_user");
                                status = spsJSONObject.getString("status");

                                Log.d("statusid_user", String.valueOf("aaaaaaaaaa"));

                                Log.d("statusid_user", String.valueOf(MainActivity.id));

                            }
                            if(status.equals("1") && id_user.equals(String.valueOf(MainActivity.id))){
                                changestatus(Integer.valueOf(id),0,MainActivity.id);
                                holder.favorite.setBackgroundResource(R.drawable.ic_shadow_favorite_24);
                                String a= holder.number.getText().toString();
                                holder.number.setText(String.valueOf(Integer.valueOf(a)-1));
                                Log.d("statusid_1", String.valueOf("aaaaaaaaaa"));

                            }else {
                                if (status.equals("0") && id_user.equals(String.valueOf(MainActivity.id))) {
                                    changestatus(Integer.valueOf(id), 1, MainActivity.id);
                                    holder.favorite.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
                                    String a= holder.number.getText().toString();
                                    holder.number.setText(String.valueOf(Integer.valueOf(a)+1));
                                    Log.d("statusid_0", String.valueOf("aaaaaaaaaa"));
                            }
                            }




                        } catch (JSONException e) {
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(mcontext, "Volley error", Toast.LENGTH_SHORT).show();
                        Log.d("loi",error.toString());

                    }
                }
                ) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> param=new HashMap<String,String>();
                        param.put("id_ct",String.valueOf(mdata.get(position).getId()));
                        param.put("id_user",String.valueOf(MainActivity.id));


                        return param;
                    }
                };
                Volley.newRequestQueue(mcontext).add(stringrequest);


                return ;


            }
        });

    }

    @Override
    public int getItemCount() {

        return mdata.size();

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tvname;
        private TextView tvmota;
        private TextView number;
        private ImageView img;
        private ShapeableImageView imgtg;
        private TextView tvtacgia;
        private Button favorite;

        public MyViewHolder(View itemView, OnItemClickListener listener){
            super(itemView);

            tvname=(TextView) itemView.findViewById(R.id.tv_name);
            tvmota=(TextView) itemView.findViewById(R.id.tv_mota);
            img=(ImageView) itemView.findViewById(R.id.img);
            imgtg=(ShapeableImageView)itemView.findViewById(R.id.imgav);
            number=(TextView) itemView.findViewById(R.id.number);
            tvtacgia=(TextView) itemView.findViewById(R.id.tac_gia);
            favorite=(Button) itemView.findViewById(R.id.favorite);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }







    ////////////////////////////////////////////////
    public void changestatus(final int id,int status,int id_user){
        StringRequest stringrequest = new StringRequest(
                Request.Method.POST,urleditstatus
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("thaydoi",response);
                    JSONObject jsonobject=new JSONObject(response);

                    //doc tat ca du lieu tu json bo vao ArrayList
                } catch (JSONException e) {
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(mcontext, "Volley error", Toast.LENGTH_SHORT).show();
                Log.d("loi",error.toString());

            }
        }
        ) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param=new HashMap<String,String>();
                param.put("id_user",String.valueOf(id_user));
                param.put("id",String.valueOf(id));
                param.put("status",String.valueOf(status));



                return param;
            }
        };
        Volley.newRequestQueue(mcontext).add(stringrequest);
        return ;

    }

    ////////////////////////////////////////////////
    public void insertstatus(final int id,int status,int id_user){
        StringRequest stringrequest = new StringRequest(
                Request.Method.POST,urlinsertstatus
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("aaasadasdas",response);
                    JSONObject jsonobject=new JSONObject(response);

                    //doc tat ca du lieu tu json bo vao ArrayList
                } catch (JSONException e) {
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(mcontext, "Volley error", Toast.LENGTH_SHORT).show();
                Log.d("loi",error.toString());

            }
        }
        ) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param=new HashMap<String,String>();
                param.put("id_user",String.valueOf(id_user));
                param.put("id_ct",String.valueOf(id));
                param.put("status",String.valueOf(status));


                return param;
            }
        };
        Volley.newRequestQueue(mcontext).add(stringrequest);
        return ;

    }



    ////////////////////////////////////////////////
    public void delete(final int id_user,int id_congthuc){
        StringRequest stringrequest = new StringRequest(
                Request.Method.POST,urldelete
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("aaasadasdas",response);
                    JSONObject jsonobject=new JSONObject(response);

                    //doc tat ca du lieu tu json bo vao ArrayList
                } catch (JSONException e) {
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(mcontext, "Volley error", Toast.LENGTH_SHORT).show();
                Log.d("loi",error.toString());

            }
        }
        ) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param=new HashMap<String,String>();
                param.put("id_congthuc",String.valueOf(id_congthuc));
                param.put("id_user",String.valueOf(id_user));



                return param;
            }
        };
        Volley.newRequestQueue(mcontext).add(stringrequest);
        return ;

    }

    //////////////////////



}

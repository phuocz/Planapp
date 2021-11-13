package com.example.myapplication.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.activity.EditActivity;
import com.example.myapplication.activity.MainActivity;
import com.example.myapplication.activity.MyTopicActivity;
import com.example.myapplication.model.DatatestAll_Cook;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Adapter_cook_user extends RecyclerView.Adapter<Adapter_cook_user.MyViewHolder> {

    Context mcontext;
    List<DatatestAll_Cook> mdata;
    MainActivity mainActivity =new MainActivity();
    String url=mainActivity.localhost()+"/android/deletecongthuc.php";
    private Adapter_home.OnItemClickListener onclick;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(Adapter_home.OnItemClickListener listener){
        onclick = listener;
    }
    public Adapter_cook_user(Context mcontext, List<DatatestAll_Cook> mdata) {
        this.mcontext=mcontext;
        this.mdata=mdata;

    }


    @NonNull
    @Override
    public Adapter_cook_user.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mytoppic,parent,false);
        Adapter_cook_user.MyViewHolder vholder= new Adapter_cook_user.MyViewHolder(v,onclick);
        return vholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_cook_user.MyViewHolder holder, int position) {
        DatatestAll_Cook data=mdata.get(position);
        holder.tvname.setText(data.getName());
        holder.tvmota.setText(data.getMota());
        if (data.getstatus_ct()==1){
            holder.tvstatus.setText("đã kiểm duyệt");
        }
        if (data.getstatus_ct()==0){
            holder.tvstatus.setText("đang chờ kiểm duyệt");
        }
        if (data.getstatus_ct()==3){
            holder.tvstatus.setText("đã bị chặn vì nội dung không hợp lệ");
        }
        Glide.with(mcontext).load(data.getPhoto()).into(holder.img);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder dialog=new AlertDialog.Builder(mcontext);

                dialog.setTitle("chỉnh sửa công thức");
                dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent;
                        Bundle bundle=new Bundle();
                        intent=new Intent(mcontext, EditActivity.class);
                        bundle.putInt("id",mdata.get(position).getId());
                        intent.putExtras(bundle);
                        mcontext.startActivity(intent);
                    }
                });
                dialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                dialog.show();
                return false;
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog=new AlertDialog.Builder(mcontext);

                dialog.setTitle("xóa công thức");
                dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        delete(mdata.get(position).getId());
                        Intent intent=new Intent(mcontext, MyTopicActivity.class);
                        mcontext.startActivity(intent);

                    }
                });
                dialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                dialog.show();



            }
        });


    }

    @Override
    public int getItemCount() {
        if(mdata!=null){
            return mdata.size();
        }

        return mdata.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tvname;
        private TextView tvmota;
        private TextView tvstatus;
        private ImageView img,delete;

        public MyViewHolder(View itemView, Adapter_home.OnItemClickListener listener){
            super(itemView);

            tvname=(TextView) itemView.findViewById(R.id.tv_name);
            tvstatus=(TextView) itemView.findViewById(R.id.tv_status);
            tvmota=(TextView) itemView.findViewById(R.id.tv_mota);
            img=(ImageView) itemView.findViewById(R.id.img);
            delete=(ImageView) itemView.findViewById(R.id.delete);
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


    public void delete(final int id){



        StringRequest stringrequest = new StringRequest(
                Request.Method.POST,url
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {




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

                param.put("id",String.valueOf(id));


                return param;
            }
        };
        Volley.newRequestQueue(mcontext).add(stringrequest);
        return ;
    }
}

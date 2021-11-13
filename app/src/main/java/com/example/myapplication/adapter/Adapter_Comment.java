package com.example.myapplication.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.activity.ContentActivity;
import com.example.myapplication.activity.MainActivity;
import com.example.myapplication.model.Comment;
import com.example.myapplication.model.DatatestAll_Cook;
import com.example.myapplication.ui.dialog.DialogFragment;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Adapter_Comment extends RecyclerView.Adapter<Adapter_Comment.MyViewHolder> {

    Context mcontext;
    List<Comment> mdata;
    MainActivity mainActivity =new MainActivity();
    String url=mainActivity.localhost()+"/android/editbinhluan.php";
    String reporturl=mainActivity.localhost()+"/android/reportbinhluan.php";
    String urldelete=mainActivity.localhost()+"/android/deletebinhluan.php";


    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public Adapter_Comment(Context mcontext, List<Comment> mdata) {
        this.mcontext=mcontext;
        this.mdata=mdata;

    }


    @NonNull
    @Override
    public Adapter_Comment.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment,parent,false);
        Adapter_Comment.MyViewHolder vholder= new Adapter_Comment.MyViewHolder(v);
        return vholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Comment.MyViewHolder holder, int position) {
        Comment data=mdata.get(position);
        if(data==null){
            return;
        }
        holder.tvname.setText(data.getUse_name());
        Glide.with(mcontext).load(data.getPhoto_user()).into(holder.img);
        holder.tvbinhluan.setText(data.getComment());
        Log.d("tvbinhluan",""+data.getId());
        if (mainActivity.id==data.id_user) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
                    View view = LayoutInflater.from(mcontext).inflate(R.layout.dialog, null);
                    builder.setView(view);
                    EditText tvcomment;
                    tvcomment = view.findViewById(R.id.tvcomment);
                    tvcomment.setText(holder.tvbinhluan.getText().toString());

                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d("getid",String.valueOf(data.getId()));
                            edit(MainActivity.id, data.getId(), tvcomment.getText().toString());
                            holder.tvbinhluan.setText(tvcomment.getText().toString());

                        }
                    });
                    builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();

                        }
                    });
                    builder.setNeutralButton("delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            delete( data.getId());
                            int newPosition = holder.getAdapterPosition();
                            mdata.remove(newPosition);
                            notifyItemRemoved(newPosition);
                            notifyItemRangeChanged(newPosition, mdata.size());
                        }
                    });
                    builder.show();
                    return false;
                }
            });
        }

        //report
        if (mainActivity.id!=data.id_user) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
                    builder.setMessage("bạn muốn tố cáo bình luận không hợp lệ");

                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d("getid",String.valueOf(data.getId()));
                            report( data.getId());

                        }
                    });
                    builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();

                        }
                    });
                    builder.show();
                    return false;
                }
            });
        }
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
        private TextView tvbinhluan;

        private ShapeableImageView img;

        public MyViewHolder(View itemView){
            super(itemView);

            tvname=(TextView) itemView.findViewById(R.id.tv_name);
            img=(ShapeableImageView) itemView.findViewById(R.id.imgav);
            tvbinhluan=(TextView) itemView.findViewById(R.id.tvbinhluan);
        }
    }
    public void edit(final int id_user,final int id_cm,String comment){



        StringRequest stringrequest = new StringRequest(
                Request.Method.POST,url
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("chesscomment",response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(mcontext, "Volley error", Toast.LENGTH_SHORT).show();


            }
        }
        ) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param=new HashMap<String,String>();

                param.put("id_user",String.valueOf(id_user));
                param.put("id",String.valueOf(id_cm));
                Log.d("id_cm",String.valueOf(id_cm));
                param.put("comment",String.valueOf(comment));


                return param;
            }
        };
        Volley.newRequestQueue(mcontext).add(stringrequest);
        return ;
    }
/////////////////////////////////
    public void delete(final int id_cm){



        StringRequest stringrequest = new StringRequest(
                Request.Method.POST,urldelete
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("chesscomment",response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(mcontext, "Volley error", Toast.LENGTH_SHORT).show();


            }
        }
        ) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param=new HashMap<String,String>();

                param.put("id",String.valueOf(id_cm));
                Log.d("id",String.valueOf(id_cm));



                return param;
            }
        };
        Volley.newRequestQueue(mcontext).add(stringrequest);
        return ;
    }
    public void report(final int id_cm){



        StringRequest stringrequest = new StringRequest(
                Request.Method.POST,reporturl
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("chesscomment",response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(mcontext, "Volley error", Toast.LENGTH_SHORT).show();


            }
        }
        ) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param=new HashMap<String,String>();
                param.put("id",String.valueOf(id_cm));


                return param;
            }
        };
        Volley.newRequestQueue(mcontext).add(stringrequest);
        return ;
    }
}

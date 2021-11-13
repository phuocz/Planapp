package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.activity.ContentActivity;
import com.example.myapplication.model.DatatestAll_Cook;

import java.util.List;

public class Adapter_favcook extends RecyclerView.Adapter<Adapter_favcook.MyViewHolder> {

    Context mcontext;
    List<DatatestAll_Cook> mdata;



    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public Adapter_favcook(Context mcontext, List<DatatestAll_Cook> mdata) {
        this.mcontext=mcontext;
        this.mdata=mdata;

    }


    @NonNull
    @Override
    public Adapter_favcook.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favcook,parent,false);
        Adapter_favcook.MyViewHolder vholder= new Adapter_favcook.MyViewHolder(v);
        return vholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_favcook.MyViewHolder holder, int position) {
        DatatestAll_Cook data=mdata.get(position);
        if(data==null){
            return;
        }
        holder.tvname.setText(data.getName());
        Glide.with(mcontext).load(data.getPhoto()).into(holder.img);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                Bundle bundle=new Bundle();
                intent=new Intent(mcontext, ContentActivity.class);
                bundle.putInt("id",mdata.get(position).getId());
                Log.d("tesxt",String.valueOf(mdata.get(position).getId()));
                intent.putExtras(bundle);
                mcontext.startActivity(intent);
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

        private ImageView img;

        public MyViewHolder(View itemView){
            super(itemView);

            tvname=(TextView) itemView.findViewById(R.id.tv_name);
            img=(ImageView) itemView.findViewById(R.id.img);
        }
    }
}

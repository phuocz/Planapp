package com.example.myapplication.ui.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.activity.CongThucActivity;
import com.example.myapplication.activity.Edit_Account_Activity;
import com.example.myapplication.activity.FavActivity;
import com.example.myapplication.activity.LoginActivity;
import com.example.myapplication.activity.MainActivity;
import com.example.myapplication.activity.MyTopicActivity;
import com.example.myapplication.model.Account;
import com.google.android.material.imageview.ShapeableImageView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountFragment extends Fragment {

    View v;
    TextView topic,name,email,following,logout,favcongthuc;
    Account account;
    MainActivity mainActivity;
    ShapeableImageView imgav;
    int check=MainActivity.status_post;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_account,container,false);
        logout=v.findViewById(R.id.logout);
        topic =v.findViewById(R.id.toppic);
        name=v.findViewById(R.id.name);
        imgav=v.findViewById(R.id.imgav);
        favcongthuc=v.findViewById(R.id.favcongthuc);
        mainActivity= new MainActivity();
        email=v.findViewById(R.id.email);
        following=v.findViewById(R.id.following);
        name.setText(MainActivity.name);;
        email.setText(MainActivity.email);

        Glide.with(getContext()).load(mainActivity.localhost()+"/android/"+MainActivity.picture).into(imgav);
        Log.d("checkkk",mainActivity.localhost()+"/android/"+MainActivity.picture);

        topic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


        if (check==0){
            Toast.makeText(getContext(),"bạn đã bị chặn không thể dùng chức năng này ",Toast.LENGTH_LONG).show();
        }
        else {
                Intent intent1 = new Intent(getActivity(), CongThucActivity.class);
                startActivity(intent1);}
            }
        });
        following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(getActivity(), MyTopicActivity.class);
                startActivity(intent1);
            }
        });
        imgav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(), Edit_Account_Activity.class);
                startActivity(intent1);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent1);

            }
        });
        favcongthuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(), FavActivity.class);
                startActivity(intent1);

            }
        });
        return v;

    }



}
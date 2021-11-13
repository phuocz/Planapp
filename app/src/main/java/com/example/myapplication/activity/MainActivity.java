package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.model.Account;
import com.example.myapplication.ui.account.AccountFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavArgument;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView drawac;
    Account account ;
    public static String name,email,picture,pass;
    public static  int id,status_bl,status_post;


    private NavArgument nameArg, mailArg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);

        try {
            Intent intent=getIntent();
            Bundle bundle =intent.getExtras();
            if (bundle.getInt("id")>0){
                this.id=bundle.getInt("id");
            }

            this.picture=bundle.getString("picture_user");
            this.email=bundle.getString("email");
            this.name=bundle.getString("name");
            this.pass=bundle.getString("pass");
            this.status_bl=bundle.getInt("status_bl");
            this.status_post=bundle.getInt("status_post");
            Log.d("checkimg",String.valueOf(this.id));

        }catch (Exception e) {
        }


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home2, R.id.navigation_hot, R.id.navigation_account,R.id.navigation_new)
                .build();
        NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

    }

    public String localhost(){
        String localhost="http://192.168.1.3";

        return localhost ;
    }



}
package com.example.myapplication.ui.home;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.activity.SeachActivity;
import com.example.myapplication.model.Datatest;
import com.example.myapplication.ui.dinner.DinnerFragment;
import com.example.myapplication.ui.drinks.DrinksFragment;
import com.example.myapplication.ui.lunch.LunchFragment;
import com.example.myapplication.ui.neww.NewFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment2 extends Fragment {

   View v;
   private RecyclerView recyclerView;
   private List<Datatest> listdata;

  public HomeFragment2(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_home2,container,false);
            BottomNavigationView bottomNav=v.findViewById(R.id.nav_view);
            bottomNav.setOnNavigationItemSelectedListener(navlistener);


        return v;

    }
        private BottomNavigationView.OnNavigationItemSelectedListener navlistener=
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()){
                            case R.id.navigation_breakfast:
                                selectedFragment=new Homebreakfast();
                                break;
                            case R.id.navigation_lunch:
                                selectedFragment=new LunchFragment();
                                break;
                            case R.id.navigation_dinner:
                                selectedFragment=new DinnerFragment();
                                break;
                            case R.id.navigation_drinks:
                                selectedFragment=new DrinksFragment();
                                break;
                            case R.id.navigation_seach:
                                Intent intent=new Intent(getContext(), SeachActivity.class);
                                getContext().startActivity(intent);
                                break;
                        }
                        if (selectedFragment==null){

                        }
                        else {
                        getChildFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,selectedFragment).commit();}
                        return true;
                    }
                };
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



}
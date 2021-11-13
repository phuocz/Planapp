package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;
import com.example.myapplication.model.Account;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {


    Button btnLogin;
    EditText txtEmail, txtPass;
    TextView tvregis;
    Account account;
    java.util.List<Account> list;
    MainActivity mainActivity = new MainActivity();
    String loginUrl = mainActivity.localhost() + "/android/index.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        btnLogin = findViewById(R.id.btnlogin);
        txtEmail = findViewById(R.id.edemail);
        txtPass = findViewById(R.id.edpass);
        tvregis = findViewById(R.id.tvregis);

        tvregis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent1);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StringRequest stringrequest = new StringRequest(
                        Request.Method.POST, loginUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        xulyLogin(response);


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "Volley error", Toast.LENGTH_SHORT).show();
                        Log.d("loi", error.toString());

                    }
                }
                ) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> param = new HashMap<String, String>();


                        param.put("email", txtEmail.getText().toString());
                        param.put("password", txtPass.getText().toString());
                        param.put("tag", "login");

                        return param;
                    }
                };
                Volley.newRequestQueue(LoginActivity.this).add(stringrequest);
            }
        });
    }

    private void xulyLogin(String response) {
        String thanhcong = "";
        account = new Account();
        list = new ArrayList<>();
        try {
            Log.d("login", response);
            JSONObject jsonobject = new JSONObject(response);
            thanhcong = jsonobject.getString("thanhcong");

            Log.d("login", response);
            ;

            //doc tat ca du lieu tu json bo vao ArrayList
            if (Integer.parseInt(thanhcong) == 1)//thanh cong
            {
                JSONObject user = jsonobject.getJSONObject("user");
                account.setId(user.getInt("id"));
                account.setPhoto(user.getString("picture_user"));
                account.setemail(user.getString("email"));
                account.setName(user.getString("name"));
                account.setpass(user.getString("password"));
                account.setstatus_bl(user.getInt("status_bl"));
                account.setstatus_post(user.getInt("status_post"));
                Toast.makeText(this, "chào bạn "+account.getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("name", account.name);
                Bundle bundle = new Bundle();
                bundle.putInt("id", account.id);
                bundle.putString("picture_user", account.photo);
                bundle.putString("email", account.email);
                bundle.putString("ten", account.name);
                bundle.putString("pass", account.pass);
                bundle.putInt("status_bl", account.status_bl);
                bundle.putInt("status_post", account.status_post);
                intent.putExtras(bundle);
                startActivity(intent);
            } else //that bai
            {
                Log.d("login", "LoginFail");
                Toast.makeText(this, "Login Fail", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
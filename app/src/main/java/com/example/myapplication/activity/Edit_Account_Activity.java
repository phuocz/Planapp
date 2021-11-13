package com.example.myapplication.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
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
import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.model.Account;
import com.google.android.material.imageview.ShapeableImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Edit_Account_Activity extends AppCompatActivity {
    private Bitmap bitmap;
    Bundle bundle;
    int id;
    MainActivity mainActivity =new MainActivity();
    String urlup=mainActivity.localhost()+"/android/editaccount.php";
    private int IMG_REQUEST =999;
    ShapeableImageView imgacc;
    Account account;
    EditText edname,edemail,edpass;
    TextView tvback;
    Button btnchange;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change__picture_);
        edname=findViewById(R.id.edname);
        edemail=findViewById(R.id.edemail);
        edpass=findViewById(R.id.edpass);
        tvback=findViewById(R.id.tvback);
        imgacc=findViewById(R.id.imgacc);
        btnchange=findViewById(R.id.btnchange);

        edname.setText(MainActivity.name);
        edemail.setText(MainActivity.email);
        edpass.setText(MainActivity.pass);
        Glide.with(Edit_Account_Activity.this).load(mainActivity.localhost()+"/android/"+MainActivity.picture).into(imgacc);

        imgacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,IMG_REQUEST);

            }
        });

        btnchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editct();

            }
        });

    }



    public void editct(){

        StringRequest stringrequest = new StringRequest(
                Request.Method.POST,urlup
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                account=new Account();
                Log.d("updateacc",response);
                try {
                    JSONObject jsonobject=new JSONObject(response);



                    //doc tat ca du lieu tu json bo vao ArrayList

                    JSONArray sps = jsonobject.getJSONArray("user");

                    for(int i=0;i<sps.length();i++) {
                        JSONObject spsJSONObject = sps.getJSONObject(i);
                        account.setId(spsJSONObject.getInt("id"));
                        account.setPhoto(spsJSONObject.getString("picture_user"));
                        account.setemail(spsJSONObject.getString("email"));
                        account.setName(spsJSONObject.getString("name"));
                        account.setpass(spsJSONObject.getString("password"));
                        if(MainActivity.id==account.id) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("name",account.name);
                            Bundle bundle=new Bundle();
                            bundle.putInt("id", account.id);
                            if (account.photo != null) {
                                bundle.putString("picture_user", account.photo);

                            }
                            bundle.putString("email", account.email);
                            bundle.putString("ten", account.name);
                            bundle.putString("pass", account.pass);


                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    }
                        Toast.makeText(Edit_Account_Activity.this,"đã thay đổi thành công", Toast.LENGTH_SHORT).show();



                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"lỗi: "+error.toString(),Toast.LENGTH_LONG).show();
                Log.d("loi",error.toString());
            }
        }
        ) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param=new HashMap<String,String>();

                String imgdata=imgtostring(bitmap);
                param.put("id",String.valueOf(MainActivity.id));
                param.put("name",edname.getText().toString());
                param.put("email",edemail.getText().toString());
                param.put("pass",edpass.getText().toString());

                param.put("imgct",String.valueOf(imgdata));

                return param;
            }
        };
        Volley.newRequestQueue(Edit_Account_Activity.this).add(stringrequest);

    }
    // imgset
    private String imgtostring(Bitmap bitmap){

        if (bitmap!=null)
        {
            ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,10,outputStream);

            byte[] imagebyte=outputStream.toByteArray();
            String encodedImage= Base64.encodeToString(imagebyte,Base64.DEFAULT);
            return encodedImage ;
        }

        else {
            return null;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK
                && null != data) {
            Uri path=data.getData();
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                imgacc.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}
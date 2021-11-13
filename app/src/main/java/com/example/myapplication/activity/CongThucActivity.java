package com.example.myapplication.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Magnifier;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.model.Account;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

import static android.graphics.BitmapFactory.decodeFile;

public class CongThucActivity extends AppCompatActivity {
    Spinner spinner;
    EditText name,mota;
    ImageView imageView;
    Button  btnupdateimg;
    private static final int PICK_IMAGE = 1;
    private int IMG_REQUEST =999;
    private Bitmap bitmap;
    Account account;
    String a,b,c;
    MainActivity mainActivity =new MainActivity();
    String urlct=mainActivity.localhost()+"/android/taocongthuc.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cong_thuc);
        spinner = findViewById(R.id.spinner);
        imageView = findViewById(R.id.imagev);
        name = findViewById(R.id.name);
        mota = findViewById(R.id.mota);
        btnupdateimg=findViewById(R.id.btnupdateimg);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(CongThucActivity.this, R.array.list_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,IMG_REQUEST);

            }
        });
        btnupdateimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bitmap==null){
                    Toast.makeText(CongThucActivity.this,"vui lòng chọn ảnh đại diện",Toast.LENGTH_LONG).show();}
                else {
                if (name.getText().toString().equals("") && mota.getText().toString().equals("")){
                    Toast.makeText(CongThucActivity.this,"không được để trống các thông tin",Toast.LENGTH_LONG).show();
                }else {
                    ByteArrayOutputStream outputStream=new ByteArrayOutputStream();


                    Intent intent=new Intent(CongThucActivity.this, Create_CongThuc.class);

                    insert();

                    startActivity(intent);
                }}



            }
        });
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK
                && null != data) {
            Uri path=data.getData();
            try {
                bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    private String imgtostring(Bitmap bitmap){
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        byte[] imagebyte=outputStream.toByteArray();
        String encodedImage= Base64.encodeToString(imagebyte,Base64.DEFAULT);
        return encodedImage;
    }

    public  void insert(){
        StringRequest stringrequest = new StringRequest(
                Request.Method.POST,urlct
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),"đã tạo thành công",Toast.LENGTH_LONG).show();

                Log.d("okcoloigi",response);


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
                param.put("id_user",String.valueOf(MainActivity.id));
                param.put("name", name.getText().toString());
                param.put("loai",spinner.getSelectedItem().toString());
                param.put("mota",mota.getText().toString());
                param.put("img",imgdata);


                return param;
            }
        };
        Volley.newRequestQueue(CongThucActivity.this).add(stringrequest);


    }

}
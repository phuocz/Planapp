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
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Create_CongThuc extends AppCompatActivity {

    EditText nguyenlieu,noidung;
    String name,loai,mota,img;
    ImageView imageView;
    byte[] bytes;
    Button  btnupdate;
    private static final int PICK_IMAGE = 1;
    private int IMG_REQUEST =999;
    private Bitmap bitmap;
    int id;

    MainActivity mainActivity =new MainActivity();

    String url=mainActivity.localhost()+"/android/taonoidung.php";
    String urlid=mainActivity.localhost()+"/android/getidmoitao.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__cong_thuc);
        nguyenlieu=findViewById(R.id.txtnguyenlieu);
        noidung=findViewById(R.id.txtcontent);
        imageView=findViewById(R.id.imagend);

        btnupdate=findViewById(R.id.btnupdate);



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,IMG_REQUEST);

            }
        });
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitmap==null){
                    Toast.makeText(Create_CongThuc.this,"vui lòng chọn ảnh nội dung",Toast.LENGTH_LONG).show();}
                else {
                    if (noidung.getText().toString().equals("") && nguyenlieu.getText().toString().equals("")){
                        Toast.makeText(Create_CongThuc.this,"không được để trống các thông tin",Toast.LENGTH_LONG).show();
                    }else {
                    id =getid();
                        finish();
                }}


            }
        });
    }

    public int getid(){

        StringRequest stringrequest = new StringRequest(urlid
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    Log.d("idizzzdd",String.valueOf(response));
                    taond();
                    JSONObject jsonobject=new JSONObject(response);
                    ;
                    //doc tat ca du lieu tu json bo vao ArrayList


                    JSONArray sps=jsonobject.getJSONArray("congthuc");

                    for(int i=0;i<sps.length();i++) {
                        JSONObject spsJSONObject = sps.getJSONObject(i);



                        id=spsJSONObject.getInt("id");;



                    }
                } catch (JSONException e) {
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Create_CongThuc.this, "Volley error", Toast.LENGTH_SHORT).show();
                Log.d("loi",error.toString());

            }
        }
        ) ;

        Volley.newRequestQueue(Create_CongThuc.this).add(stringrequest);
        Log.d("loi","error.toString()");
        return id;
    }

    public void taond(){

        StringRequest stringrequest = new StringRequest(
                Request.Method.POST,url
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),"đã tạo thành công",Toast.LENGTH_LONG).show();

                Log.d("ok",response);
                Intent intent=new Intent(Create_CongThuc.this,MyTopicActivity.class);
                startActivity(intent);
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
                param.put("idcongthuc",String.valueOf(id));
                param.put("nguyenlieu",nguyenlieu.getText().toString());
                param.put("noidung",noidung.getText().toString());
                param.put("img",String.valueOf(imgdata));


                return param;
            }
        };
        Volley.newRequestQueue(Create_CongThuc.this).add(stringrequest);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK
                && null != data) {
            Uri path=data.getData();
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),path);
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

/////////////////////////////////


}
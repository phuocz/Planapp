package com.example.myapplication.activity;

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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;
import com.google.android.material.imageview.ShapeableImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    Button btnOK;
    TextView tvback;
    ShapeableImageView imgacc;
    EditText txtName, txtEmail, txtPass,retxtPass;
    private int IMG_REQUEST2 =99;
    Bitmap bitmap;
    MainActivity mainActivity =new MainActivity();
    String registerUrl =mainActivity.localhost()+ "/android/index.php";
    String thanhcong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        btnOK = findViewById(R.id.btnregis);
        tvback=findViewById(R.id.tvback);
        txtName = findViewById(R.id.edname);
        txtEmail = findViewById(R.id.edemail);
        txtPass = findViewById(R.id.edpass);
        retxtPass = findViewById(R.id.reedpass);
        imgacc=findViewById(R.id.imgacc);

        imgacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,IMG_REQUEST2);
            }
        });


        tvback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent1);
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (bitmap==null){
                    Toast.makeText(RegistrationActivity.this,"vui lòng chọn ảnh đại diện",Toast.LENGTH_LONG).show();}
                else {
                    if (txtName.getText().toString().equals("") && txtEmail.getText().toString().equals("") &&txtPass.getText().toString().equals("")){
                        Toast.makeText(RegistrationActivity.this,"không được để trống các thông tin",Toast.LENGTH_LONG).show();
                    }else {
                        if (txtPass.getText().toString().equals(retxtPass.getText().toString())){

                            StringRequest stringrequest = new StringRequest(
                                    Request.Method.POST, registerUrl, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                try {
                                    JSONObject jsonobject=new JSONObject(response);
                                    thanhcong=jsonobject.getString("thanhcong");
                                    if (Integer.parseInt(thanhcong)==1) {
                                        Toast.makeText(RegistrationActivity.this, "đăng kí thành công", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                    if (Integer.parseInt(thanhcong)==0) {
                                        Log.d("xulyRegister", response);

                                        Toast.makeText(RegistrationActivity.this, "email đã bị trùng hãy đăng kí bằng email khác", Toast.LENGTH_SHORT).show();
                                    }
                                }catch (JSONException e) {
                                    e.printStackTrace();
                                }


                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    Toast.makeText(RegistrationActivity.this, "Volley error", Toast.LENGTH_SHORT).show();
                                    Log.d("loi",error.toString());

                                }
                            }
                            ) {

                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String,String> param=new HashMap<String,String>();
                                    String imgdata=imgtostring(bitmap);
                                    param.put("picture_user",String.valueOf(imgdata));
                                    param.put("name",txtName.getText().toString());
                                    param.put("email",txtEmail.getText().toString());
                                    param.put("password",txtPass.getText().toString());
                                    param.put("tag","register");

                                    return param;
                                }
                            };
                            Volley.newRequestQueue(RegistrationActivity.this).add(stringrequest);

                        }else {
                            Toast.makeText(RegistrationActivity.this,"nhập mật khẩu không giống nhau",Toast.LENGTH_LONG).show();

                        } }
                    }
                    }


        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMG_REQUEST2 && resultCode == RESULT_OK
                && null != data) {
            Uri path=data.getData();
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),path);

                imgacc.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }}
    private String imgtostring(Bitmap bitmap){

        if (bitmap!=null)
        {
            ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);

            byte[] imagebyte=outputStream.toByteArray();
            String encodedImage= Base64.encodeToString(imagebyte,Base64.DEFAULT);
            return encodedImage ;
        }

        else {
            return null;
        }
    }
}
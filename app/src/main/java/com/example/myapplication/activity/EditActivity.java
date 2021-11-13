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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EditActivity extends AppCompatActivity {

    EditText txtnguyenlieu,txtcontent,txtname,txtmota;
    Spinner spinner;
    Button  btnupdate;
    ImageView imagend,imagect;
    byte[] bytes;
    private static final int PICK_IMAGE = 1;
    private int IMG_REQUEST =999;
    private int IMG_REQUEST2 =99;
    private Bitmap bitmap,bitmap2;
    Bundle bundle;
     int id;
    MainActivity mainActivity =new MainActivity();
    String urlget=mainActivity.localhost()+"/android/geteditid.php";
    String urlup=mainActivity.localhost()+"/android/editcongthuc.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        txtnguyenlieu=findViewById(R.id.txtnguyenlieu);
        txtcontent=findViewById(R.id.txtcontent);
        txtname=findViewById(R.id.txtname);
        txtmota=findViewById(R.id.txtmota);
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(EditActivity.this, R.array.list_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        imagend=findViewById(R.id.imagend);
        imagect=findViewById(R.id.imagect);
        btnupdate=findViewById(R.id.btnupdate);

        try {
            Intent intent=getIntent();
            Bundle bundle =intent.getExtras();
            this.id=bundle.getInt("id");


            getAll(id);
            Log.d("adasdasdasd",String.valueOf(imagect.getBackground()));
        }catch (Exception e) {
        }
        imagect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,IMG_REQUEST);

            }
        });
        imagend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,IMG_REQUEST2);

            }
        });

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           editct();
           Toast.makeText(EditActivity.this,"đã cập nhập",Toast.LENGTH_LONG).show();
                Intent intent1 = new Intent(EditActivity.this, MyTopicActivity.class);
                startActivity(intent1);


            }
        });
    }


    public void getAll(final int id){

        StringRequest stringrequest = new StringRequest(Request.Method.POST,urlget
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    Log.d("idizzzdd",String.valueOf(response));

                    JSONObject jsonobject=new JSONObject(response);
                    ;
                    //doc tat ca du lieu tu json bo vao ArrayList


                    JSONArray sps=jsonobject.getJSONArray("congthuc");

                    for(int i=0;i<sps.length();i++) {
                        JSONObject spsJSONObject = sps.getJSONObject(i);
                        txtname.setText(spsJSONObject.getString("name"));
                        txtmota.setText(spsJSONObject.getString("mota"));
                        txtnguyenlieu.setText(spsJSONObject.getString("nguyenlieu"));
                        txtcontent.setText(spsJSONObject.getString("noidung"));
                         Glide.with(EditActivity.this).load(mainActivity.localhost()+"/android/"+spsJSONObject.getString("picturect")).into(imagect);
                        Glide.with(EditActivity.this).load(mainActivity.localhost()+"/android/"+spsJSONObject.getString("picturend")).into(imagend);
                        Log.d("tdsad",mainActivity.localhost()+"/android/"+spsJSONObject.getString("picturect"));
                        Log.d("tdsad",mainActivity.localhost()+"/android/"+spsJSONObject.getString("picturend"));

                    }
                } catch (JSONException e) {
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(EditActivity.this, "Volley error", Toast.LENGTH_SHORT).show();
                Log.d("loi",error.toString());

            }
        }
        ) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param=new HashMap<String,String>();

                param.put("id",String.valueOf(id));


                return param;
            }
        };
        Volley.newRequestQueue(EditActivity.this).add(stringrequest);
        return ;
    }

    public void editct(){

        StringRequest stringrequest = new StringRequest(
                Request.Method.POST,urlup
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {





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
                String imgdata2=imgtostring2(bitmap2);
                param.put("id",String.valueOf(id));
                param.put("name",txtname.getText().toString());
                param.put("mota",txtmota.getText().toString());
                param.put("loai",spinner.getSelectedItem().toString());
                param.put("nguyenlieu",txtnguyenlieu.getText().toString());
                param.put("noidung",txtcontent.getText().toString());

                    param.put("imgct",String.valueOf(imgdata));

                    param.put("imgnd",String.valueOf(imgdata2));

                return param;
            }
        };
        Volley.newRequestQueue(EditActivity.this).add(stringrequest);

    }
    // imgset
    private String imgtostring(Bitmap bitmap){

        if (bitmap!=null)
        {ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);

            byte[] imagebyte=outputStream.toByteArray();
            String encodedImage= Base64.encodeToString(imagebyte,Base64.DEFAULT);
            return encodedImage ;
        }

        else {
            return null;
        }
    }
    private String imgtostring2(Bitmap bitmap2){

if (bitmap2!=null)
{ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
    bitmap2.compress(Bitmap.CompressFormat.JPEG,100,outputStream);

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

        if (requestCode == IMG_REQUEST2 && resultCode == RESULT_OK
                && null != data) {
            Uri path=data.getData();
            try {
                bitmap2= MediaStore.Images.Media.getBitmap(getContentResolver(),path);

                imagend.setImageBitmap(bitmap2);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK
                && null != data) {
            Uri path=data.getData();
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                imagect.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}
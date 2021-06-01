package com.example.project.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.project.DetailActivity;
import com.example.project.MainActivity;
import com.example.project.MapRequest;
import com.example.project.PlaceActivity;
import com.example.project.PlaceRequest;
import com.example.project.PopRequest;
import com.example.project.R;
import com.example.project.SurveyActivity;
import com.example.project.map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {

    private EditText et_id, et_pwd;
    private Button btn_login,btn_signup, btn_skip, btn_goto_place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        getHashKey();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_id = findViewById(R.id.et_id);
        et_pwd = findViewById(R.id.et_pwd);
        btn_login = findViewById(R.id.btn_login);
        btn_signup = findViewById(R.id.btn_signup);
//        btn_skip = findViewById(R.id.btn_skip);
//        btn_goto_place =findViewById(R.id.btn_goto_p);

        //SignUp 버튼 -> 해당 화면으로 이동
        btn_signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent Intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(Intent);
            }
        });
//
//        btn_goto_place.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
////                Intent Intent = new Intent(LoginActivity.this, PlaceActivity.class);
////                startActivity(Intent);
////                String autocamp_name= "기러기 공원";
//                String autocamp_name ="삼탄유원지 다리밑";//필례약수터, 매전교 주변
//                Response.Listener<String> responseListener = new Response.Listener<String>(){
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            Log.d("::::::::::::::::::::::::::::::::",response);
//                            boolean success = jsonObject.getBoolean("success");
//                            if(success){
//                                String autocamp_name = jsonObject.getString("autocamp_name");
//                                Intent intent = new Intent(LoginActivity.this, PlaceActivity.class);
//                                intent.putExtra("autocamp_id",1);
//                                intent.putExtra("autocamp_name",autocamp_name);
//                                intent.putExtra("autocamp_location", jsonObject.getString("autocamp_location"));
//                                intent.putExtra("autocamp_address",jsonObject.getString("autocamp_address"));
//                                intent.putExtra("autocamp_add_info", jsonObject.getString("autocamp_add_info"));
//                                intent.putExtra("autocamp_img",jsonObject.getString("autocamp_img"));
//                                intent.putExtra("autocamp_view",jsonObject.getString("autocamp_view"));
//                                intent.putExtra("tag",jsonObject.getString("tag"));
//                                intent.putExtra("subname",jsonObject.getString("subname"));
//                                startActivity(intent);
//                            }else {
//                                return;
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                };
//                PlaceRequest placeRequest = new PlaceRequest(autocamp_name, responseListener);
//                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
//                queue.add(placeRequest);
//            }
//        });

        btn_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String id = et_id.getText().toString();
                String password = et_pwd.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String[] responses=response.split("user end");

                            JSONObject jsonObject = new JSONObject( responses[0] );
                            boolean success = jsonObject.getBoolean("success");
                            if(success){

                                JSONObject jsonObjects = new JSONObject(responses[1]);
                                JSONArray jsonArray= jsonObjects.getJSONArray("autocamp");
                                if(jsonArray.length()==0) return;

                                Toast.makeText(getApplicationContext(),"로그인에 성공하셨습니다.",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("user_id", jsonObject.getString("user_id"));
                                intent.putExtra("user_name", jsonObject.getString("user_name"));
                                intent.putExtra("birthday", jsonObject.getString("birthday"));
                                intent.putExtra("gender", jsonObject.getString("gender"));
                                intent.putExtra("camped", jsonObject.getString("camped"));
                                intent.putExtra("taste", jsonObject.getString("taste"));
                                intent.putExtra("response",responses[1]);

                                startActivity(intent);
                            }else{
                                Toast.makeText(getApplicationContext(),"로그인에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(id, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });

//        //개발단계에서 로그인 skip 할 수 있도록 임시로 설정
//        btn_skip.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                String id = "a";
//                String password = "a";
//
//                Response.Listener<String> responseListener = new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            String[] responses=response.split("user end");
//
//                            JSONObject jsonObject = new JSONObject( responses[0] );
//                            boolean success = jsonObject.getBoolean("success");
//                            if(success){
//
//                                JSONObject jsonObjects = new JSONObject(responses[1]);
//                                JSONArray jsonArray= jsonObjects.getJSONArray("autocamp");
//                                if(jsonArray.length()==0) return;
//
//                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                                intent.putExtra("user_id", jsonObject.getString("user_id"));
//                                intent.putExtra("user_name", jsonObject.getString("user_name"));
//                                intent.putExtra("birthday", jsonObject.getString("birthday"));
//                                intent.putExtra("gender", jsonObject.getString("gender"));
//                                intent.putExtra("camped", jsonObject.getString("camped"));
//                                intent.putExtra("taste", jsonObject.getString("taste"));
//                                intent.putExtra("response",responses[1]);
//
//                                startActivity(intent);
//                            }else{
//                                Toast.makeText(getApplicationContext(),"로그인에 실패하였습니다.",Toast.LENGTH_SHORT).show();
//                                return;
//                            }
//                        }catch(JSONException e){
//                            e.printStackTrace();
//                        }
//                    }
//                };
//                LoginRequest loginRequest = new LoginRequest(id, password, responseListener);
//                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
//                queue.add(loginRequest);
//            }
//        });

    }
    private void getHashKey(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash::::", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash::::", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash::::", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }
}
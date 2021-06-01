package com.example.project.login;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.project.DetailActivity;
import com.example.project.R;
import com.example.project.SurveyActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class SignupActivity extends AppCompatActivity {
    private EditText et_id, et_pwd,et_name,et_birth,et_pwd_check;
    private RadioGroup rg;
    private Button btn_signup, btn_id_check;
    private String gender="";
    private boolean validate = false;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        et_id = findViewById(R.id.et_id);
        et_pwd = findViewById(R.id.et_pwd);
        et_name = findViewById(R.id.et_name);
        et_birth = findViewById(R.id.et_birth);
        et_pwd_check = findViewById(R.id.et_pwd_check);
        rg = findViewById(R.id.rg);


        rg.setOnCheckedChangeListener(radioGroupButtonChangeListener);

        btn_id_check = findViewById(R.id.btn_id_check);
        btn_id_check.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String id = et_id.getText().toString();
                if(validate){
                    return;
                }
                if(id.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                    dialog = builder.setMessage("아이디를 입력하세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                                dialog = builder.setMessage("사용할 수 있는 아이디입니다.").setPositiveButton("확인", null).create();
                                dialog.show();
                                et_id.setEnabled(false); //아이디값 고정
                                validate = true; //검증 완료
                                btn_id_check.setBackgroundColor(getResources().getColor(R.color.colorGray));
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                                dialog = builder.setMessage("이미 존재하는 아이디입니다.").setNegativeButton("확인", null).create();
                                dialog.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                ValidateRequest validateRequest = new ValidateRequest(id, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SignupActivity.this);
                queue.add(validateRequest);
            }
        });

        //SignUp 끝나면 Login으로 이동
        btn_signup = findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String id = et_id.getText().toString();
                final String password = et_pwd.getText().toString();
                final String password_c = et_pwd_check.getText().toString();
                String user_name = et_name.getText().toString();
                String birthday = et_birth.getText().toString();

                if (!validate) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                    dialog = builder.setMessage("중복된 아이디가 있는지 확인하세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }
                if (id.equals("") || password.equals("") || user_name.equals("")||birthday.equals("")|| gender=="" ) {//날짜판별함수
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                    dialog = builder.setMessage("모두 입력해주세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("JSONMY-----",response);

                            JSONObject jsonObject = new JSONObject(response);

                            boolean success = jsonObject.getBoolean("success");
                            if(password.equals(password_c)){
                                if(success){
                                    Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignupActivity.this, DetailActivity.class);
                                    intent.putExtra("user_id",jsonObject.getString("user_id"));
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(getApplicationContext(),"fail",Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                                dialog = builder.setMessage("비밀번호가 동일하지 않습니다.").setNegativeButton("확인", null).create();
                                dialog.show();
                                return;
                            }
                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                };

                SignupRequest registerRequest = new SignupRequest(id,password,user_name,"19980202", gender,responseListener);
                RequestQueue queue = Volley.newRequestQueue(SignupActivity.this);
                queue.add(registerRequest);
            }
        });

    }
    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            if(i == R.id.rb_m){ gender = "M";
            }
            else if(i == R.id.rb_f){ gender = "F";
            }
        }
    };
}
package com.example.project.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.project.R;

import org.json.JSONException;
import org.json.JSONObject;

public class SignupActivity extends AppCompatActivity {
    private EditText et_id, et_pwd,et_name,et_birth;
    private CheckBox cb_m,cb_f;
    private Button btn_signup, btn_id_check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        et_id = findViewById(R.id.et_id);
        et_pwd = findViewById(R.id.et_pwd);
        et_name = findViewById(R.id.et_name);
        et_birth = findViewById(R.id.et_birth);

        //SignUp 끝나면 Login으로 이동
        btn_signup = findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String id = et_id.getText().toString();
                String password = et_pwd.getText().toString();
                String user_name = et_name.getText().toString();
                String birthday = et_birth.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success){
                                Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(getApplicationContext(),"fail",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                };


                SignupRequest registerRequest = new SignupRequest(id,password,user_name,birthday, "M",responseListener);
                RequestQueue queue = Volley.newRequestQueue(SignupActivity.this);
                queue.add(registerRequest);
            }
        });

    }
}
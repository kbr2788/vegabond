package com.example.project.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.project.MainActivity;
import com.example.project.R;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private EditText et_id, et_pwd;
    private Button btn_login,btn_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_id = findViewById(R.id.et_id);
        et_pwd = findViewById(R.id.et_pwd);
        btn_login = findViewById(R.id.btn_login);
        btn_signup = findViewById(R.id.btn_signup);

        //SignUp 버튼 -> 해당 화면으로 이동
        btn_signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent Intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(Intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String user_id = et_id.getText().toString();
                String user_pwd = et_pwd.getText().toString();

                Response.Listener<String> responseListner = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success){
                                String user_id = jsonObject.getString("id");
                                String user_pwd = jsonObject.getString("password");

                                Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("user_id", user_id);
                                intent.putExtra("user_pwd", user_pwd);
                                startActivity(intent);
                            }else{
                                Toast.makeText(getApplicationContext(),"-------fail-------",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(user_id, user_pwd, responseListner);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });

    }
}
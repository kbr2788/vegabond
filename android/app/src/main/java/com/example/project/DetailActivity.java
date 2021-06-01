package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.project.login.LoginActivity;
import com.example.project.login.LoginRequest;
import com.example.project.login.SignupActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailActivity extends AppCompatActivity {
    String[] tag_array={"강", "바다", "산", "저수지_호수_계곡","낚시","운동","산책","편의시설","가족","친구","연인","혼자","반려동물","노지","숲","물놀이","들판","캠핑장","수상레저","대형차"};
    CheckBox[] cB;
    Button btn;
    String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        user_id = getIntent().getStringExtra("user_id");

        cB = new CheckBox[20];
        for(int i = 0 ; i < 20; i++){
            int k = getResources().getIdentifier("checkBox" + i+1, "id", getPackageName());
            cB[i] = findViewById(k);
        }

        btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String check="";
                Intent Intent = new Intent(DetailActivity.this, LoginActivity.class);
                cB = new CheckBox[20];
                for(int i = 1 ; i<=20;i++){
                    int k = getResources().getIdentifier("checkBox" + i, "id", getPackageName());
                    cB[i-1] = findViewById(k);
                    if(cB[i-1].isChecked()){
                        if(check.equals(""))
                            check+=i+"";
                        else check+=","+i;
                    }
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject( response );
                            boolean success = jsonObject.getBoolean("success");
                            if(success){
                                Intent intent = new Intent(DetailActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }else{
                                return;
                            }
                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                };
                DetailRequest detailRequest = new DetailRequest(user_id, check, responseListener);
                RequestQueue queue = Volley.newRequestQueue(DetailActivity.this);
                queue.add(detailRequest);
            }
        });

    }
}
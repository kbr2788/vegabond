package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class PlaceActivity extends AppCompatActivity {
    String autocamp_name= getIntent().getStringExtra("autocamp_name");
    String autocamp_location= getIntent().getStringExtra("autocamp_location");
    String autocamp_address= getIntent().getStringExtra("autocamp_address");
    String autocamp_img= getIntent().getStringExtra("autocamp_img");
    String autocamp_view= getIntent().getStringExtra("autocamp_view");
    String tag= getIntent().getStringExtra("tag");
    String subname= getIntent().getStringExtra("subname");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
    }
}
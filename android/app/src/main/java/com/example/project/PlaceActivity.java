package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.time.Instant;
import java.time.temporal.TemporalAdjuster;

public class PlaceActivity extends AppCompatActivity {

    private ImageView[] iv_campimg= new ImageView[3];
    private TextView tv_name, tv_address, tv_info,tv_weather,tv_cource,tv_co1,tv_co2,tv_co3,tv_review_tit,tv_review_write,tv_review_con,tv_review;
    private ImageButton ib_weather,ib_co1,ib_co2,ib_co3;
    private Button btn_co,btn_rev;
    private RatingBar ratingBar,ratingBar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        String autocamp_name= getIntent().getStringExtra("autocamp_name");
    String autocamp_location= getIntent().getStringExtra("autocamp_location");
    String autocamp_address= getIntent().getStringExtra("autocamp_address");
    String autocamp_add_info = getIntent().getStringExtra("autocamp_add_info");
    String autocamp_img= getIntent().getStringExtra("autocamp_img");
    String[] autocamps = autocamp_img.split(", ");

    for(int i = 1 ; i <= autocamps.length;i++){
//        int i = 1;
        int k = getResources().getIdentifier("iv_campimg"+i,"id",getPackageName());
//        iv_campimg=findViewById(k);
//        Glide.with(this).load(autocamps[i-1]).into(iv_campimg);
        iv_campimg[i-1]=findViewById(k);
        Glide.with(this).load(autocamps[i-1]).override(500).into(iv_campimg[i-1]);

    }
    Log.d("::::::::::::::::::::::",autocamp_img);
//    String autocamp_view= getIntent().getStringExtra("autocamp_view");
//    String tag= getIntent().getStringExtra("tag");
//    String subname= getIntent().getStringExtra("subname");

        tv_name = findViewById(R.id.tv_name);
        tv_address= findViewById(R.id.tv_address);
        tv_info = findViewById(R.id.tv_info);

        tv_name.setText(autocamp_name);
        tv_address.setText(autocamp_address);
        tv_info.setText(autocamp_add_info);
    }
}
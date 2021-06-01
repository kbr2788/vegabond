package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.project.login.LoginActivity;
import com.example.project.login.SignupActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Instant;
import java.time.temporal.TemporalAdjuster;

public class PlaceActivity extends AppCompatActivity {

    private LinearLayout lL_sns1,lL_no;
    private LinearLayout[] lL_review,lL_sns;
    private LinearLayout.LayoutParams params_layout;
    private ImageView[] iv_campimg= new ImageView[3];
    private TextView tv_name, tv_address, tv_info,tv_weather,tv_cource,tv_co1,tv_co2,tv_co3;
    private TextView[] tv_review_tit,tv_review_con,tv_sns,tv_sns_con;
    private Button btn_co,btn_rev,btn_review_write,btn_review_more;
    private RatingBar ratingBar;
    private RatingBar[] rB;
    String[] tag_array={"강", "바다", "산", "저수지_호수_계곡","낚시","운동","산책","편의시설","가족","친구","연인","혼자","반려동물","노지","숲","물놀이","들판","캠핑장","수상레저","대형차"};
    int autocamp_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        autocamp_id = getIntent().getExtras().getInt("autocamp_id");
        String autocamp_name= getIntent().getStringExtra("autocamp_name");
    String autocamp_location= getIntent().getStringExtra("autocamp_location");
    String autocamp_address= getIntent().getStringExtra("autocamp_address");
    String autocamp_add_info = getIntent().getStringExtra("autocamp_add_info");
    String autocamp_img= getIntent().getStringExtra("autocamp_img");
    String[] autocamps = autocamp_img.split(", ");
        String tag= getIntent().getStringExtra("tag");

        String tag_con="";
        if(!autocamps[0].equals(" ")|| !autocamps[0].equals("")){
        for(int i = 1 ; i <= autocamps.length;i++) {
            int k = getResources().getIdentifier("iv_campimg" + i, "id", getPackageName());
            iv_campimg[i - 1] = findViewById(k);
            Glide.with(this).load(autocamps[i - 1]).override(500).into(iv_campimg[i - 1]);
        }
    }



        tv_name = findViewById(R.id.tv_name);
        tv_address= findViewById(R.id.tv_address);
        tv_info = findViewById(R.id.tv_info);

        tv_name.setText(autocamp_name);
        tv_address.setText(autocamp_address);

        String[] add_info = autocamp_add_info.split(", ");
        for(int j = 0 ; j < 3;j++){
            String[] useful= add_info[j].split(" ");
            if(useful[1].equals("사용가능")){
                tag_con+="#"+useful[0]+"_가능 ";
            }
        }
        String[] tag_split = tag.split(", ");
        if(tag_split.length>1){
            for(int j = 0 ; j < tag_split.length;j++){
                tag_con+="#"+tag_array[Integer.parseInt(tag_split[j])-1]+" ";
            }
        }
        tv_info.setText(tag_con);

        btn_review_write=findViewById(R.id.btn_review_write);

        btn_review_more=findViewById(R.id.btn_review_more);
        btn_review_more.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                            Intent intent = new Intent(PlaceActivity.this, ReviewActivity.class);
                            intent.putExtra("autocamp_id",autocamp_id);
                            intent.putExtra("response",response);
                            startActivity(intent);
                    }
                };
                ReviewsRequest reviewsRequest = new ReviewsRequest(autocamp_id+"", responseListener);
                RequestQueue queue = Volley.newRequestQueue(PlaceActivity.this);
                queue.add(reviewsRequest);


            }
        });
        review_load();
        sns_review_load();
    }
    public void review_load() {
        Response.Listener<String> responseListener = new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray= jsonObject.getJSONArray("autocamp");
                    if(jsonArray.length()==0) return;
                    else {
                        int size=4;
                        if(jsonArray.length()<4) size = jsonArray.length();

                        lL_review=new LinearLayout[4];
                        lL_review[0]= findViewById(R.id.lL_review1);
                        lL_review[1]= findViewById(R.id.lL_review2);
                        lL_review[2]= findViewById(R.id.lL_review3);
                        lL_review[3]= findViewById(R.id.lL_review4);

                        tv_review_tit=new TextView[4];
                        tv_review_tit[0]= findViewById(R.id.tv_review_tit1);
                        tv_review_tit[1]= findViewById(R.id.tv_review_tit2);
                        tv_review_tit[2]= findViewById(R.id.tv_review_tit3);
                        tv_review_tit[3]= findViewById(R.id.tv_review_tit4);

                        tv_review_con=new TextView[4];
                        tv_review_con[0]= findViewById(R.id.tv_review_con1);
                        tv_review_con[1]= findViewById(R.id.tv_review_con2);
                        tv_review_con[2]= findViewById(R.id.tv_review_con3);
                        tv_review_con[3]= findViewById(R.id.tv_review_con4);

                        rB=new RatingBar[4];
                        rB[0]= findViewById(R.id.rB1);
                        rB[1]= findViewById(R.id.rB2);
                        rB[2]= findViewById(R.id.rB3);
                        rB[3]= findViewById(R.id.rB4);

                        for (int i = 0; i < size; i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            float rating = Float.parseFloat(obj.getString("rating"));
                            tv_review_tit[i].setText(obj.getString("user_name"));
                            tv_review_con[i].setText(obj.getString("contents"));
                            rB[i].setRating(rating);
                            params_layout = (LinearLayout.LayoutParams) lL_review[i].getLayoutParams();
                            params_layout.weight = 1;
                            lL_review[i].setLayoutParams(params_layout);
                        }
                        lL_no = findViewById(R.id.lL_no);
                        params_layout = (LinearLayout.LayoutParams) lL_no.getLayoutParams();
                        params_layout.weight = 0;
                        lL_no.setLayoutParams(params_layout);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ReviewRequest reviewRequest = new ReviewRequest(autocamp_id+"", responseListener);
        RequestQueue queue = Volley.newRequestQueue(PlaceActivity.this);
        queue.add(reviewRequest);
    }


    public void sns_review_load() {
        Response.Listener<String> responseListener = new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray= jsonObject.getJSONArray("autocamp");
                    if(jsonArray.length()==0) return;
                    else {
                        int size=4;
                        if(jsonArray.length()<4) size = jsonArray.length();

                        lL_sns=new LinearLayout[4];
                        lL_sns[0]= findViewById(R.id.lL_sns1);
                        lL_sns[1]= findViewById(R.id.lL_sns2);
                        lL_sns[2]= findViewById(R.id.lL_sns3);
                        lL_sns[3]= findViewById(R.id.lL_sns4);

                        tv_sns=new TextView[4];
                        tv_sns[0]= findViewById(R.id.tv_sns1);
                        tv_sns[1]= findViewById(R.id.tv_sns2);
                        tv_sns[2]= findViewById(R.id.tv_sns3);
                        tv_sns[3]= findViewById(R.id.tv_sns4);

                        tv_sns_con=new TextView[4];
                        tv_sns_con[0]= findViewById(R.id.tv_sns_con1);
                        tv_sns_con[1]= findViewById(R.id.tv_sns_con2);
                        tv_sns_con[2]= findViewById(R.id.tv_sns_con3);
                        tv_sns_con[3]= findViewById(R.id.tv_sns_con4);

                        for (int i = 0; i < size; i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            String per = obj.getString("review_p");
                            String po=obj.getString("positive");
                            if(po.equals("1")) per+="\n긍정";
                            else per+="\n부정";
                            tv_sns[i].setText(per);
                            tv_sns_con[i].setText(obj.getString("sentence"));
                            params_layout = (LinearLayout.LayoutParams) lL_sns[i].getLayoutParams();
                            params_layout.weight = 1;
                            lL_sns[i].setLayoutParams(params_layout);

                        }
                        lL_sns1 = findViewById(R.id.lL_sns);
                        params_layout = (LinearLayout.LayoutParams) lL_sns1.getLayoutParams();
                        params_layout.weight = size;
                        lL_sns1.setLayoutParams(params_layout);

                        lL_no = findViewById(R.id.lL_no);
                        params_layout = (LinearLayout.LayoutParams) lL_no.getLayoutParams();
                        params_layout.weight = 0;
                        lL_no.setLayoutParams(params_layout);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        SnsRequest snsRequest = new SnsRequest(autocamp_id+"", responseListener);
        RequestQueue queue = Volley.newRequestQueue(PlaceActivity.this);
        queue.add(snsRequest);
    }
}
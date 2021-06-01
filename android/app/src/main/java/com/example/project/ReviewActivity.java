package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {
    LinearLayout lL_sns1,lL_no,lL_sns;
    TextView tv_review,tv_sns;
    private RatingBar[] rB;
    int review_size=0,sns_size=0;
    int autocamp_id;
    String review,sns;
    String review_response;
    private LinearLayout.LayoutParams params_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        autocamp_id = getIntent().getExtras().getInt("autocamp_id");
        review_response= getIntent().getStringExtra("response");


        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> list_sns = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(review_response);
            JSONArray jsonArray= jsonObject.getJSONArray("autocamp");
            if(jsonArray.length()>0){
                for(int i = 0 ; i < jsonArray.length();i++){
                    JSONObject obj = jsonArray.getJSONObject(i);
                    String no = obj.getString("no");
                    String obs = obj.toString();
                    if(no.equals("0")){
                        list.add(String.format(obs));
                    }
                    else{
                        list_sns.add(String.format(obj.toString())) ;
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        RecyclerView recyclerView = findViewById(R.id.recycler1) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this)) ;

//        params_layout = (LinearLayout.LayoutParams) recyclerView.getLayoutParams();
//        params_layout.weight = list.size();
//        recyclerView.setLayoutParams(params_layout);

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        review_recycle adapter = new review_recycle(list);
        recyclerView.setAdapter(adapter);

        /////////////////////////////////////////

        RecyclerView recyclerView2 = findViewById(R.id.recycler2) ;
        recyclerView2.setLayoutManager(new LinearLayoutManager(this)) ;

//        params_layout = (LinearLayout.LayoutParams) recyclerView2.getLayoutParams();
//        params_layout.weight = list_sns.size();
//        recyclerView2.setLayoutParams(params_layout);

        sns_recycle adapter_sns = new sns_recycle(list_sns);
        recyclerView2.setAdapter(adapter_sns);


        lL_no = findViewById(R.id.lL_no);
        params_layout = (LinearLayout.LayoutParams) lL_no.getLayoutParams();
        params_layout.weight = 0;
        lL_no.setLayoutParams(params_layout);

//        lL_sns = findViewById(R.id.lL_sns);
//        params_layout = (LinearLayout.LayoutParams) lL_sns.getLayoutParams();
//        params_layout.weight = 1;
//        lL_sns.setLayoutParams(params_layout);

    }

}
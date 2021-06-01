package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.project.login.LoginActivity;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Comparator;
import java.util.ResourceBundle;

public class FragmentPage1 extends Fragment {
    MainActivity act;
    String response,taste;
    String[][] place,user;
    ImageView[] iv_p,iv_t;
    Button[] btn_t,btn_p;
    SearchView searchView;
    String autocamp_name;
    int[] taste_arr=new int[3];

        public static FragmentPage1 newInstance(){
            return new FragmentPage1();
        }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_page_1,container,false);

        Bundle bundle = getArguments();
        if(bundle != null) {
            response = bundle.getString("response");
            taste = bundle.getString("taste");
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray= jsonObject.getJSONArray("autocamp");
            place= new String[180][6];
            user= new String[jsonArray.length()-180][3];
            for(int i = 0 ; i < jsonArray.length();i++){
                JSONObject obj= jsonArray.getJSONObject(i);
                String no = obj.getString("no");
                if(no.equals("0")) {
                    place[i][0] = obj.getString("autocamp_id");
                    place[i][1] = obj.getString("autocamp_name");
                    place[i][2] = obj.getString("autocamp_img");
                    place[i][3] = obj.getString("tag");
                    place[i][4] = obj.getString("subname");
                    place[i][5] = obj.getString("autocamp_view");
                }
                else{
                    user[i-180][0]=obj.getString("birthday");
                    user[i-180][1]=obj.getString("gender");
                    user[i-180][2]=obj.getString("taste");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        iv_p = new ImageView[3];

        iv_p[0]=rootView.findViewById(R.id.iv_p1);
        iv_p[1]=rootView.findViewById(R.id.iv_p2);
        iv_p[2]=rootView.findViewById(R.id.iv_p3);

        btn_p = new Button[3];
        btn_p[0]=rootView.findViewById(R.id.btn_p1);
        btn_p[1]=rootView.findViewById(R.id.btn_p2);
        btn_p[2]=rootView.findViewById(R.id.btn_p3);
        for (int i = 0; i < 3; i++) {
            String[] autocamps = place[i][2].split(", ");
            if (!autocamps[0].equals(" ") || !autocamps[0].equals("")) {
                Glide.with(this).load(autocamps[0]).override(150).into(iv_p[i]);
            }
            btn_p[i].setText(place[i][1]);
            autocamp_name = place[i][1];
            btn_p[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Response.Listener<String> responseListener = new Response.Listener<String>(){
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);

                                boolean success = jsonObject.getBoolean("success");
                                if(success){
                                    String autocamp_name = jsonObject.getString("autocamp_name");
                                    Intent intent = new Intent(getActivity(), PlaceActivity.class);
                                    intent.putExtra("autocamp_id",jsonObject.getString("autocamp_id"));
                                    intent.putExtra("autocamp_name",autocamp_name);
                                    intent.putExtra("autocamp_location", jsonObject.getString("autocamp_location"));
                                    intent.putExtra("autocamp_address",jsonObject.getString("autocamp_address"));
                                    intent.putExtra("autocamp_add_info", jsonObject.getString("autocamp_add_info"));
                                    intent.putExtra("autocamp_img",jsonObject.getString("autocamp_img"));
                                    intent.putExtra("autocamp_view",jsonObject.getString("autocamp_view"));
                                    intent.putExtra("tag",jsonObject.getString("tag"));
                                    intent.putExtra("subname",jsonObject.getString("subname"));
                                    startActivity(intent);
                                }else {
                                    return;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    PlaceRequest placeRequest = new PlaceRequest(autocamp_name, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(getActivity());
                    queue.add(placeRequest);

                }
            });
        }

        taste();

        iv_t = new ImageView[3];

        iv_t[0]=rootView.findViewById(R.id.iv_t1);
        iv_t[1]=rootView.findViewById(R.id.iv_t2);
        iv_t[2]=rootView.findViewById(R.id.iv_t3);

        btn_t = new Button[3];
        btn_t[0]=rootView.findViewById(R.id.btn_t1);
        btn_t[1]=rootView.findViewById(R.id.btn_t2);
        btn_t[2]=rootView.findViewById(R.id.btn_t3);

        for (int i = 0; i < 3; i++) {
            String[] autocamps = place[taste_arr[i]][2].split(", ");
            if (!autocamps[0].equals(" ") || !autocamps[0].equals("")) {
                Glide.with(this).load(autocamps[0]).override(150).into(iv_t[i]);
            }
            btn_p[i].setText(place[taste_arr[i]][1]);
            autocamp_name = place[taste_arr[i]][1];
            btn_p[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Response.Listener<String> responseListener = new Response.Listener<String>(){
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);

                                boolean success = jsonObject.getBoolean("success");
                                if(success){
                                    String autocamp_name = jsonObject.getString("autocamp_name");
                                    Intent intent = new Intent(getActivity(), PlaceActivity.class);
                                    intent.putExtra("autocamp_id",jsonObject.getString("autocamp_id"));
                                    intent.putExtra("autocamp_name",autocamp_name);
                                    intent.putExtra("autocamp_location", jsonObject.getString("autocamp_location"));
                                    intent.putExtra("autocamp_address",jsonObject.getString("autocamp_address"));
                                    intent.putExtra("autocamp_add_info", jsonObject.getString("autocamp_add_info"));
                                    intent.putExtra("autocamp_img",jsonObject.getString("autocamp_img"));
                                    intent.putExtra("autocamp_view",jsonObject.getString("autocamp_view"));
                                    intent.putExtra("tag",jsonObject.getString("tag"));
                                    intent.putExtra("subname",jsonObject.getString("subname"));
                                    startActivity(intent);
                                }else {
                                    return;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    PlaceRequest placeRequest = new PlaceRequest(autocamp_name, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(getActivity());
                    queue.add(placeRequest);

                }
            });
        }



        return rootView;

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void taste(){
        int[][] arr =new int[180][2];
        String[] taste_split=taste.split(",");
        if(taste_split[0].equals("")) return;
        for(int i = 0 ; i < 180;i++){
            arr[i][0]=0;
            String[] tag_split=place[i][3].split(", ");
            arr[i][1]=i;
            if(tag_split[0].equals("")) continue;

            for(int j = 0 ; j < taste_split.length;j++){
                for(int k=0;k<tag_split.length;k++){
                    if(taste_split[j].equals(tag_split[k])){
                        arr[i][0]++;
                        continue;
                    }
                }
            }
        }
        Arrays.sort(arr, new Comparator<int[]>() {
            @Override
            public int compare(int[] ints, int[] t1) {
                return t1[0]-ints[0];
            }
        });
        taste_arr= new int[]{arr[0][1], arr[1][1], arr[2][1]};
    }

}


package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.project.login.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FragmentPage2 extends Fragment {

    public Button[] do_btn=new Button[10];

    private String[] do_name={"경기","강원","충북","충남","경북","경남","전북","전남","제주"};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_page_2,container,false);

        do_btn[0] = rootView.findViewById(R.id.btn_0);
        do_btn[1] = rootView.findViewById(R.id.btn_1);
        do_btn[2] = rootView.findViewById(R.id.btn_2);
        do_btn[3] = rootView.findViewById(R.id.btn_3);
        do_btn[4] = rootView.findViewById(R.id.btn_4);
        do_btn[5] = rootView.findViewById(R.id.btn_5);
        do_btn[6] = rootView.findViewById(R.id.btn_6);
        do_btn[7] = rootView.findViewById(R.id.btn_7);
        do_btn[8] = rootView.findViewById(R.id.btn_8);

        for(int i = 0 ; i < 9 ; i ++){
            final int do_num = i;
            do_btn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Response.Listener<String> responseListener = new Response.Listener<String>(){
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);

                                JSONArray jsonArray= jsonObject.getJSONArray("autocamp");
                                if(jsonArray.length()==0) return;
                                else{
                                    String[] id_array= new String[jsonArray.length()];
                                    for(int i = 0 ; i < jsonArray.length();i++){
                                        JSONObject obj= jsonArray.getJSONObject(i);
                                        id_array[i]=obj.getString("autocamp_id");
                                    }
                                    Intent intent = new Intent(getActivity(), map.class);
                                    intent.putExtra("do_num",do_num);
                                    intent.putExtra("id_array",id_array);
                                    startActivity(intent);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    MapRequest mapRequest = new MapRequest(do_name[do_num], responseListener);
                    RequestQueue queue = Volley.newRequestQueue(getActivity());
                    queue.add(mapRequest);
                }
            });
        }

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
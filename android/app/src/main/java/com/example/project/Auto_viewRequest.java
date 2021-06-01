package com.example.project;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Auto_viewRequest extends  StringRequest{

    final static private String URL = "http://holycrab.dothome.co.kr/viewincre.php";
    private Map<String, String> map;

    public Auto_viewRequest(String autocamp_id, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        map = new HashMap<>();
        map.put("autocamp_id",autocamp_id);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError{
        return map;
    }
}

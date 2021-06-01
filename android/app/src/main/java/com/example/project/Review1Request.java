package com.example.project;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Review1Request extends  StringRequest{

    final static private String URL = "http://holycrab.dothome.co.kr/register.php";
    private Map<String, String> map;

    public Review1Request(String autocamp_id,String contents, String user_id,String rating, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        map = new HashMap<>();
        map.put("autocamp_id",autocamp_id);
        map.put("contents",contents);
        map.put("user_id",user_id);
        map.put("rating",rating);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError{
        return map;
    }
}

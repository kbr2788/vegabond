package com.example.project;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DetailRequest extends  StringRequest{

    final static private String URL = "http://holycrab.dothome.co.kr/detail.php";
    private Map<String, String> map;

    public DetailRequest(String user_id, String detail, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        map = new HashMap<>();
        map.put("user_id",user_id);
        map.put("detail",detail);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError{
        return map;
    }
}

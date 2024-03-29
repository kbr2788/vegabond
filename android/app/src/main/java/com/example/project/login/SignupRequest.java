package com.example.project.login;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SignupRequest extends  StringRequest{

    final static private String URL = "http://holycrab.dothome.co.kr/register.php";
    private Map<String, String> map;

    public SignupRequest(String id, String password, String user_name, String birthday, String gender, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        map = new HashMap<>();
        map.put("id",id);
        map.put("password",password);
        map.put("user_name",user_name);
        map.put("birthday",birthday);
        map.put("gender",gender);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError{
        return map;
    }
}

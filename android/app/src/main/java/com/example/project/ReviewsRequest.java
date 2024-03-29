package com.example.project;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ReviewsRequest extends StringRequest {
    final static private String URL = "http://holycrab.dothome.co.kr/review_sns.php";
    private Map<String, String> map;

    public ReviewsRequest(String autocamp_id, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("autocamp_id",autocamp_id);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}

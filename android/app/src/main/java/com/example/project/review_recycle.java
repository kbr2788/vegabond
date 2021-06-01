package com.example.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class review_recycle  extends RecyclerView.Adapter<review_recycle.ViewHolder> {

    private ArrayList<String> mData = null ;
    LinearLayout lL_review;
    TextView tv_review_tit,tv_review_con;
    private RatingBar rB;
    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {


        ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            lL_review = itemView.findViewById(R.id.lL_review1) ;
            tv_review_tit = itemView.findViewById(R.id.tv_review_tit1) ;
            tv_review_con = itemView.findViewById(R.id.tv_review_con1) ;
            rB = itemView.findViewById(R.id.rB1);

        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    review_recycle(ArrayList<String> list) {
        mData = list ;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public review_recycle.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recycle_review, parent, false) ;
        review_recycle.ViewHolder vh = new review_recycle.ViewHolder(view) ;

        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(review_recycle.ViewHolder holder, int position) {
        String text = mData.get(position) ;
        try {
            JSONObject obj=new JSONObject(text);
            float rating = Float.parseFloat(obj.getString("rating"));
            tv_review_tit.setText(obj.getString("user_name"));
            tv_review_con.setText(obj.getString("contents"));
            rB.setRating(rating);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size() ;
    }
}
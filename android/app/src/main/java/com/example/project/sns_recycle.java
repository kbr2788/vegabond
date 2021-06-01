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

public class sns_recycle  extends RecyclerView.Adapter<sns_recycle.ViewHolder> {

    private ArrayList<String> mData = null ;
    TextView tv_sns,tv_sns_con;
    LinearLayout lL_sns;

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {


        ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            lL_sns = itemView.findViewById(R.id.lL_sns1) ;
            tv_sns = itemView.findViewById(R.id.tv_sns1) ;
            tv_sns_con = itemView.findViewById(R.id.tv_sns_con1) ;

        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    sns_recycle(ArrayList<String> list) {
        mData = list ;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public sns_recycle.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recycle_sns, parent, false) ;
        sns_recycle.ViewHolder vh = new sns_recycle.ViewHolder(view) ;

        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(sns_recycle.ViewHolder holder, int position) {
        String text = mData.get(position) ;
        try {
            JSONObject obj = new JSONObject(text);
            String per = obj.getString("review_p");
            String po=obj.getString("positive");
            if(po.equals("1")) per+="\n긍정";
            else per+="\n부정";
            tv_sns.setText(per);
            tv_sns_con.setText(obj.getString("sentence"));
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
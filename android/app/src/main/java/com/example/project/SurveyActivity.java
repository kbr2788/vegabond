package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.example.project.MainActivity;
import com.example.project.databinding.ActivitySurveyBinding;

public class SurveyActivity extends AppCompatActivity {

    ActivitySurveyBinding binding;
    String q1, q2, q3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_survey);
        binding.setActivity(this);

        binding.firstBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox option1 = (CheckBox) binding.q1A; // option1체크박스
                CheckBox option2 = (CheckBox) binding.q1B; // option1체크박스

                if (option1.isChecked()) {
                    q1 = "sunset";
                }
                if (option2.isChecked()) {
                    q1 = "mountain";
                }
                binding.firstPage.setVisibility(View.GONE);
                binding.secondPage.setVisibility(View.VISIBLE);
            }
        });

        binding.secondBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox option1 = (CheckBox) binding.q2A; // option1체크박스
                CheckBox option2 = (CheckBox) binding.q2B; // option1체크박스

                if (option1.isChecked()) {
                    q2 = "family";
                }
                if (option2.isChecked()) {
                    q2 = "friends";
                }

                binding.secondPage.setVisibility(View.GONE);
                binding.thirdPage.setVisibility(View.VISIBLE);


            }
        });

        binding.thirdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox option1 = (CheckBox) binding.q3A; // option1체크박스
                CheckBox option2 = (CheckBox) binding.q3B; // option1체크박스

                if (option1.isChecked()) {
                    q3 = "comfortable life";
                }
                if (option2.isChecked()) {
                    q3 = "beautiful landscape";
                }

                binding.thirdPage.setVisibility(View.GONE);
                binding.results.setText("이 사용자가 선호하는 곳은 " + q1 + " 이며, \n 주로 함께다니는 이들은 " + q2 + " 입니다.\n " +
                        "가장 중요하게 생각하는 것은 " +
                        q3 + "입니다.");
                binding.resultPage.setVisibility(View.VISIBLE);

                binding.ToNextPage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SurveyActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });


    }
}
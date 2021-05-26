package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentPage2 extends Fragment {

    public Button btnKangwon;
    public Button btnSudogwon;
    /*public Button btnChungnam;
    private Button btnChungbuk;
    private Button btnGyeongbuk;
    private Button btnGyeongnam;
    private Button btnJeonbuk;
    private Button btnJeonnam;
    private Button btnJeju;
    private Button btnDokdo;*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_page_2,container,false);

        btnKangwon = rootView.findViewById(R.id.btn_kangwon);
        btnKangwon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), map.class);
                startActivity(intent);
            }
        });

        btnSudogwon = rootView.findViewById(R.id.btn_sudogwon);
        btnSudogwon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), map.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
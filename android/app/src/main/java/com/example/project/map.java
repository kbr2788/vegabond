package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import net.daum.mf.map.api.CameraUpdate;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class map extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);


        MapView mapView = new MapView(this);
        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);

        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.829084, 128.148701), true);
        mapView.setZoomLevel(10, true);
        mapViewContainer.addView(mapView);
    }
<<<<<<< Updated upstream

}
=======
} 
>>>>>>> Stashed changes

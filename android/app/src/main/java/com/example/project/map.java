package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.project.login.LoginActivity;
import com.example.project.login.SignupActivity;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.CameraUpdate;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static net.daum.mf.map.api.MapPOIItem.CalloutBalloonButtonType.RightSideButton;





public class map extends AppCompatActivity implements MapView.POIItemEventListener,MapView.MapViewEventListener{
    double[][] do_location = {{35.95,128.25},{37.791755968876636, 128.24722324156525}, {36.69556926413025, 127.86239146820238},
            {36.53796260347895, 126.87773228381113},{36.275004297801644, 128.64070750071508}, {35.307449175634865, 128.29337971447893},
            {35.727219754168885, 127.05141972315377},{34.86246273456089, 126.96020231556639},{33.37544690228852, 126.53919893650081},
            {37.50680384275685, 130.87134128422144}}; //경기 강원 충북 충남 경북 경남 전북 전남 제주 울릉.
    String[] tag_array={"강", "바다", "산", "저수지_호수_계곡","낚시","운동","산책","편의시설","가족","친구","연인","혼자","반려동물","노지","숲","물놀이","들판","캠핑장","수상레저","대형차"};

    String[][] place;

    private LinearLayout info_Layout;
    private LinearLayout.LayoutParams params_layout;
    private TextView tv_name, tv_address, tv_info, tv_tag;
    private ImageButton btn_exit;
    private Button btn_go;
    private MapView mapView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        info_Layout=  findViewById(R.id.info);

        btn_exit =findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                params_layout = (LinearLayout.LayoutParams) info_Layout.getLayoutParams();
                params_layout.weight = 0;
                info_Layout.setLayoutParams(params_layout);

            }
        });

        String autoload = autoload();
        try {
            JSONObject jsonObject = new JSONObject(autoload);
            JSONArray jsonArray= jsonObject.getJSONArray("autocamp");
            place= new String[jsonArray.length()][6];
            for(int i = 0 ; i < jsonArray.length();i++){
                JSONObject obj= jsonArray.getJSONObject(i);
                place[i][0]=obj.getString("autocamp_id");
                place[i][1]=obj.getString("autocamp_name");
                place[i][2]=obj.getString("autocamp_location");
                place[i][3]=obj.getString("tag");
                place[i][4]=obj.getString("subname");
                place[i][5]=obj.getString("autocamp_add_info");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String id_array[]= getIntent().getExtras().getStringArray("id_array");
        int do_num = getIntent().getExtras().getInt("do_num");


        mapView = new MapView(this);
        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapView.setPOIItemEventListener(this);
        mapView.setMapViewEventListener(this);


        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(do_location[do_num][0], do_location[do_num][1]), true);
        mapView.setZoomLevel(10, true);

        MapPoint[] mapPoint = new MapPoint[id_array.length];
        MapPOIItem[] marker = new MapPOIItem[id_array.length];

        for(int i = 0;i<id_array.length;i++){
            final int id = Integer.parseInt(id_array[i]);
            Double[] location = new Double[2];
            String[] s= place[id-1][2].split(",");
            location[0]=Double.parseDouble(s[0]);
            location[1]=Double.parseDouble(s[1]);
            mapPoint[i] = MapPoint.mapPointWithGeoCoord(location[0],location[1]);

            marker[i] = new MapPOIItem();
            marker[i].setItemName(place[id-1][1]);
            marker[i].setTag(id);
            marker[i].setMapPoint(mapPoint[i]);
            marker[i].setMarkerType(MapPOIItem.MarkerType.CustomImage); // 기본으로 제공하는 BluePin 마커 모양.
            marker[i].setCustomImageResourceId(R.drawable.custom_marker_red);
            marker[i].setCustomImageAutoscale(false);
            marker[i].setCustomImageAnchor(0.5f, 1.0f);

            mapView.addPOIItem(marker[i]);
        }

        mapViewContainer.addView(mapView);
    }

    public String autoload(){
        AssetManager assetManager = getAssets();
        String jsonData = new String();
        try{
            InputStream is = assetManager.open("jsons/autocamp.json");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);

            StringBuffer buffer = new StringBuffer();
            String line = reader.readLine();
            while(line!=null){
                buffer.append(line+"\n");
                line=reader.readLine();
            }
            jsonData = buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonData;
    }


    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
        final int id = mapPOIItem.getTag();
        String tag_con="";

        info_Layout=  findViewById(R.id.info);
        params_layout = (LinearLayout.LayoutParams) info_Layout.getLayoutParams();
        params_layout.weight = 2;
        info_Layout.setLayoutParams(params_layout);

        tv_name = findViewById(R.id.tv_name);
        tv_info = findViewById(R.id.tv_info);
        tv_tag = findViewById(R.id.tv_tag);
        tv_name.setText(place[id-1][1]);
        String[] add_info = place[id-1][5].split(", ");
        for(int j = 0 ; j < 3;j++){

            String[] useful= add_info[j].split(" ");
            if(useful[1].equals("사용가능")){
                tag_con+="#"+useful[0]+"_가능 ";
            }
        }

        String[] tag_split = place[id-1][3].split(", ");
        if(tag_split.length>1){
            for(int j = 0 ; j < tag_split.length;j++){
                tag_con+="#"+tag_array[Integer.parseInt(tag_split[j])-1]+" ";
            }
        }

        tv_tag.setText(tag_con);
        btn_go = findViewById(R.id.btn_go);
        btn_go.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String autocamp_name= place[id-1][1];
                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            boolean success = jsonObject.getBoolean("success");
                            if(success){
                                String autocamp_name = jsonObject.getString("autocamp_name");
                                Intent intent = new Intent(map.this, PlaceActivity.class);
                                intent.putExtra("autocamp_id",id);
                                intent.putExtra("autocamp_name",autocamp_name);
                                intent.putExtra("autocamp_location", jsonObject.getString("autocamp_location"));
                                intent.putExtra("autocamp_address",jsonObject.getString("autocamp_address"));
                                intent.putExtra("autocamp_add_info", jsonObject.getString("autocamp_add_info"));
                                intent.putExtra("autocamp_img",jsonObject.getString("autocamp_img"));
                                intent.putExtra("autocamp_view",jsonObject.getString("autocamp_view"));
                                intent.putExtra("tag",jsonObject.getString("tag"));
                                intent.putExtra("subname",jsonObject.getString("subname"));
                                startActivity(intent);
                            }else {
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                PlaceRequest placeRequest = new PlaceRequest(autocamp_name, responseListener);
                RequestQueue queue = Volley.newRequestQueue(map.this);
                queue.add(placeRequest);
            }
        });
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewInitialized(MapView mapView) {

    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }
}


package cl.citiaps.coordinaciondevoluntarios.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;


import org.w3c.dom.Text;

import java.util.ArrayList;

import cl.citiaps.coordinaciondevoluntarios.R;
import cl.citiaps.coordinaciondevoluntarios.data.ApiInterface;
import cl.citiaps.coordinaciondevoluntarios.data.EmergencyData;
import cl.citiaps.coordinaciondevoluntarios.data.LoginData;
import cl.citiaps.coordinaciondevoluntarios.data.EmergencyInterestData;
import cl.citiaps.coordinaciondevoluntarios.data.MissionData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EmergencyDetailActivity extends AppCompatActivity {

    private MapFragment mapFragment;
    private GoogleMap map;
    Float emergencyLatitude;
    Float emergencyLongitude;
    Float emergencyRadius;
    TextView nameText;
    TextView typeText;
    TextView adressText;
    TextView description;

    public LatLngBounds toBounds(LatLng center, Float radius) {
        LatLng southwest = SphericalUtil.computeOffset(center, radius * Math.sqrt(2.0), 225);
        LatLng northeast = SphericalUtil.computeOffset(center, radius * Math.sqrt(2.0), 45);
        return new LatLngBounds(southwest, northeast);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_detail);
        setTitle("Emergencia");

        EmergencyData emergency = (EmergencyData)getIntent().getSerializableExtra("emergency");

        nameText = (TextView) findViewById(R.id.emergencyDetailName);
        typeText = (TextView) findViewById(R.id.emergencyDetailType);
        adressText = (TextView) findViewById(R.id.emergencyDetailAdress);
        description = (TextView) findViewById(R.id.description);

        nameText.setText(emergency.getTitle());
        typeText.setText(emergency.getEmergency_type().getType());
        adressText.setText(emergency.getMeeting_point_address());
        description.setText(emergency.getDescription());

        emergencyLatitude = emergency.getPlace_latitude();
        emergencyLongitude = emergency.getPlace_longitude();
        emergencyRadius = emergency.getPlace_radius();


        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.emergencyDetailMapFragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap map) {
                    loadMap(map);
                }
            });
        }

    }

    void loadMap(GoogleMap googleMap){

        map = googleMap;
        if (map != null) {
            Log.d("MAPA", "yey se inicializó el mapa");
            /*LatLngBounds emergencyAreaBounds = toBounds(emergencyCenter, emergencyRadius);*/
            /*map.moveCamera(CameraUpdateFactory.newLatLngBounds(emergencyAreaBounds,0));*/


            map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                @Override
                public void onMapLoaded() {
                    LatLng emergencyCenter = new LatLng(emergencyLatitude, emergencyLongitude);
                    Circle emergencyArea = map.addCircle(new CircleOptions()
                            .center(emergencyCenter)
                            .radius(emergencyRadius)
                            .strokeColor(Color.BLUE)
                            .fillColor(0x5500ffff));

                    LatLngBounds emergencyAreaBounds = toBounds(emergencyCenter, emergencyRadius);
                    map.moveCamera(CameraUpdateFactory.newLatLngBounds(emergencyAreaBounds, 0));
                }
            });

        } else {
            Log.d("MAPA", "Error inicializando mapa :c");
        }

    }





}

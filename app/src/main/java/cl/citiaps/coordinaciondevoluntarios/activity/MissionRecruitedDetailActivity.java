package cl.citiaps.coordinaciondevoluntarios.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.text.method.LinkMovementMethod;
import android.text.Html;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cl.citiaps.coordinaciondevoluntarios.R;
import cl.citiaps.coordinaciondevoluntarios.data.ApiInterface;
import cl.citiaps.coordinaciondevoluntarios.data.MissionData;
import cl.citiaps.coordinaciondevoluntarios.data.MissionRecruitData;
import cl.citiaps.coordinaciondevoluntarios.data.MissionResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MissionRecruitedDetailActivity extends AppCompatActivity {

    private MapFragment mapFragment;
    private GoogleMap map;
    ArrayList<String> values = new ArrayList<String>();
    TextView adressText;
    TextView descriptionText;
    Boolean userResponse;
    float missionLatitude;
    float missionLongitude;
    float missionRadius;
    int missionID;
    MissionData missionData;
    final int aceptarMission = 2;
    final int rechazarMision = 5;

    public LatLngBounds toBounds(LatLng center, Float radius) {
        LatLng southwest = SphericalUtil.computeOffset(center, radius * Math.sqrt(2.0), 225);
        LatLng northeast = SphericalUtil.computeOffset(center, radius * Math.sqrt(2.0), 45);
        return new LatLngBounds(southwest, northeast);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruited_mission_detail);
        adressText = (TextView) findViewById(R.id.missionRecruitedDetailAdress);
        descriptionText = (TextView) findViewById(R.id.missionRecruitedDetailDescription);

        missionData = (MissionData) getIntent().getSerializableExtra("mision");

        setTitle(missionData.getTitle());
        adressText.setText(missionData.getMeeting_point_address());
        descriptionText.setText(missionData.getDescription());
        missionLatitude = missionData.getMeeting_point_latitude();
        missionLongitude = missionData.getMeeting_point_longitude();
        missionID = missionData.getId();


        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.missionRecruitedMapFragment);
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
            map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                @Override
                public void onMapLoaded() {
                    LatLng missionCenter = new LatLng(missionLatitude, missionLongitude);
                    Marker marker = map.addMarker(new MarkerOptions()
                                    .position(new LatLng(missionLatitude, missionLongitude)));

                    LatLngBounds missionAreaBounds = toBounds(missionCenter, missionRadius);
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(missionLatitude, missionLongitude),14.0f));
                    TextView mapLink =(TextView)findViewById(R.id.open_map);
                    String mapLinkText= mapLink.getText().toString();
                    mapLink.setClickable(true);
                    mapLink.setMovementMethod(LinkMovementMethod.getInstance());
                    mapLinkText = "<a href='http://maps.google.com/maps?&z=12&q="+missionLatitude+"+"+missionLongitude+"'>"+ mapLinkText +"</a>";
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        mapLink.setText(Html.fromHtml(mapLinkText,Html.FROM_HTML_MODE_LEGACY)); }
                    else {
                        mapLink.setText(Html.fromHtml(mapLinkText));
                    }
                }
            });

        } else {
            Log.d("MAPA", "Error inicializando mapa :c");
        }

    }

    public void missionInterestResponse(View view){

        switch(view.getId()) {
            case R.id.missionRecruitedDetailInterestYes:
                userResponse = true;
                break;
            case R.id.missionRecruitedDetailInterestNo:
                userResponse = false;
                break;
        }

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final String token = prefs.getString("token","");

        try {
            JSONObject jsonBody = new JSONObject();
            if(userResponse) {
                jsonBody.put("history_mission_state_id", aceptarMission);
            }else{
                jsonBody.put("history_mission_state_id", rechazarMision);
            }

            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.PUT,
                    ApiInterface.API_URL2 + "historia/"+missionData.getId(),
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Intent intent;
                            if(userResponse){
                                Toast.makeText(getApplicationContext(), "Misión tomada",
                                        Toast.LENGTH_LONG).show();
                                intent = new Intent(getApplicationContext(), MissionTakeActivity.class);
                                intent.putExtra("mission",missionData);
                                startActivity(intent);
                            }else{
                                Toast.makeText(getApplicationContext(), "Misión rechazada",
                                        Toast.LENGTH_LONG).show();
                                intent = new Intent(getApplicationContext(),MissionsActivity.class);
                                startActivity(intent);
                                finish();
                            }

                        }
                    },
                    new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("response", error.toString());
                        }
                    }) {
                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Bearer " + token);
                    return headers;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);


        } catch (JSONException json) {
            Log.d("Json", json.toString());
        }

    }

    public void showMissions() {

        // Pedir listado de misiones al servidor
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.API_URL2)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final String token = prefs.getString("token","");

        ApiInterface api = retrofit.create(ApiInterface.class);
        Call<List<MissionResponse>> call = api.getActiveMission("Bearer "+token);
        call.enqueue(new Callback<List<MissionResponse>>(){
            @Override
            public void onResponse(Call<List<MissionResponse>> call, Response<List<MissionResponse>> response){
                Intent intent;
                if(response.body()==null){
                    Log.d("(1) ***RESPONSE", "body null");
                    intent = new Intent(MissionRecruitedDetailActivity.this, MissionsActivity.class);

                }else{
                    Log.d("(1) ***RESPONSE", response.body().get(0).getMission().getDescription());
                    intent = new Intent(MissionRecruitedDetailActivity.this, MissionTakeActivity.class);
                    intent.putExtra("missionTitle", response.body().get(0).getMission().getTitle());
                    intent.putExtra("missionDescription", response.body().get(0).getMission().getDescription());
                    intent.putExtra("missionDireccion", response.body().get(0).getMission().getMeeting_point_address());
                    intent.putExtra("missionId", response.body().get(0).getMission().getId());
                }
                startActivity(intent);

            }

            @Override
            public void onFailure(Call<List<MissionResponse>> call, Throwable t) {
                Log.d("(1) ***RESPUESTA", "Falló :c");
                Intent intent = new Intent(MissionRecruitedDetailActivity.this, MissionsActivity.class);
                startActivity(intent);
            }

        });

    }
}

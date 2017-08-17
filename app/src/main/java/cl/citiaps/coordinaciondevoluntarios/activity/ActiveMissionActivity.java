package cl.citiaps.coordinaciondevoluntarios.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cl.citiaps.coordinaciondevoluntarios.R;
import cl.citiaps.coordinaciondevoluntarios.data.ApiInterface;
import cl.citiaps.coordinaciondevoluntarios.data.MissionData;
import cl.citiaps.coordinaciondevoluntarios.data.MissionRecruitData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ActiveMissionActivity extends AppCompatActivity {
    Integer missionId;
    String missionTitle;
    String missionDescription;
    MissionData missionData;
    ProgressBar spinner;
    TextView title;
    TextView missionDescView;
    TextView missionDir;
    final int finishMission = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_mission);

        title = (TextView)findViewById(R.id.activeMissionTitle);
        missionDescView = (TextView) findViewById(R.id.description);
        missionDir = (TextView) findViewById(R.id.direction);

        missionData = (MissionData)getIntent().getSerializableExtra("mission");

        title.setText(missionData.getTitle());
        missionDescView.setText(missionData.getDescription());
        missionDir.setText(missionData.getMeeting_point_address());
    }

    public void markAsCompleted(View view){

        Resources res = getResources();

        spinner = (ProgressBar)findViewById(R.id.progressBarActiveMission);
        spinner.setVisibility(View.VISIBLE);
        MissionRecruitData missionRecruitData = new MissionRecruitData();
        int volunteerID = getSharedPreferences(
                getString(R.string.user_data_preference_file_key), Context.MODE_PRIVATE).
                getInt(getString(R.string.volunteer_id_preference_key), 0);
        int userID = getSharedPreferences(
                getString(R.string.user_data_preference_file_key), Context.MODE_PRIVATE).
                getInt(getString(R.string.user_id_preference_key), 0);
        Date currentTime = new Date();
        missionRecruitData.setEnd_date(currentTime);
        missionRecruitData.setRecruiting_status(res.getInteger(R.integer.recruited_status_finished));
        missionRecruitData.setUser_id(userID);
        missionRecruitData.setVolunteer_id(volunteerID);
        missionRecruitData.setMission_id(missionData.getId());
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final String token = prefs.getString("token","");

        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("history_mission_state_id", finishMission);

            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.PUT,
                    ApiInterface.API_URL2 + "historia/"+missionData.getId(),
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getApplicationContext(), "Mision finalizada",
                                    Toast.LENGTH_LONG).show();
                            finish();
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



    public void viewProblems(View view){
        Intent intent = new Intent(this, ProblemsActivity.class);
        intent.putExtra("mission",missionData);
        startActivity(intent);
    }



    public void viewGuides(View view){
        Intent intent = new Intent(this, GuidesActivity.class);
        intent.putExtra("missionId", missionData.getId());
        startActivity(intent);
    }

}

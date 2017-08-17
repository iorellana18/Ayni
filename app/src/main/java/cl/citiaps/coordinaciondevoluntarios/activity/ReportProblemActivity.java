package cl.citiaps.coordinaciondevoluntarios.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import cl.citiaps.coordinaciondevoluntarios.R;
import cl.citiaps.coordinaciondevoluntarios.data.ApiInterface;
import cl.citiaps.coordinaciondevoluntarios.data.MissionData;

public class ReportProblemActivity extends AppCompatActivity {
    MissionData missionData;
    ProgressBar spinner;
    int missionId;
    EditText titulo;
    EditText descripcion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_problem);
        spinner = (ProgressBar) findViewById(R.id.progressBarProblem);

        missionData = (MissionData)getIntent().getSerializableExtra("mission");
        missionId = missionData.getId();

        titulo = (EditText)findViewById(R.id.titulo);
        descripcion = (EditText)findViewById(R.id.descripcion);

    }

    public void sendProblem(View view) {
        spinner.setVisibility(View.VISIBLE);

        //Validacion básica
        if (titulo == null || descripcion == null ||
                titulo.getText().toString().equals("") || descripcion.getText().toString().equals("")) {
            String formError = getString(R.string.form_problem);
            Toast.makeText(this, formError,
                    Toast.LENGTH_LONG).show();
            spinner.setVisibility(View.GONE);
            return;

        } else {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            final String token = prefs.getString("token","");

            try {
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("title", titulo.getText().toString());
                jsonBody.put("description",descripcion.getText().toString());
                jsonBody.put("mission_id",missionData.getId());
                final String requestBody = jsonBody.toString();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiInterface.API_URL2+"problema/",
                        new com.android.volley.Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(getApplicationContext(), "Problema reportado",
                                        Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(),
                                        ProblemsActivity.class);
                                intent.putExtra("mission",missionData);
                                startActivity(intent);
                                finish();
                            }
                        },
                        new com.android.volley.Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("response",error.toString());
                            }
                        }) {
                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        try {
                            Log.d("body",requestBody);
                            return requestBody == null ? null : requestBody.getBytes("utf-8");
                        } catch (UnsupportedEncodingException uee) {
                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                            return null;
                        }
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("Authorization", "Bearer "+token);
                        return headers;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(stringRequest);


            }catch (JSONException json){Log.d("Json",json.toString());}

        }


    }

    public void cancelProblem(View view) {
        finish();
    }


}
package cl.citiaps.coordinaciondevoluntarios.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cl.citiaps.coordinaciondevoluntarios.R;
import cl.citiaps.coordinaciondevoluntarios.data.ApiInterface;
import cl.citiaps.coordinaciondevoluntarios.data.AppTokenData;
import cl.citiaps.coordinaciondevoluntarios.data.LoginData;
import cl.citiaps.coordinaciondevoluntarios.data.MissionData;
import cl.citiaps.coordinaciondevoluntarios.data.MissionResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IndexNoEmergencyActivity extends AppCompatActivity {
    private static final String TAG = "IndexNoEmergency";
    ProgressBar spinner;
    boolean hasAlert = false;
    boolean hasActiveMission = false;
    MenuItem alertMnu;
    MissionData missionData;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        alertMnu = (MenuItem) menu.findItem(R.id.mnu_alerts);
        if (hasAlert){ alertMnu.setIcon(getResources().getDrawable(R.drawable.ic_alert)); }
        else{ alertMnu.setIcon(getResources().getDrawable(R.drawable.ic_no_alert));}

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.logout:
                // Eliminar Valores y  enviar al login
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(
                        IndexNoEmergencyActivity.this);
                prefs.edit().putBoolean("isLogin",false).apply();

                intent = new Intent(IndexNoEmergencyActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.mnu_profile:
                intent = new Intent(this, PerfilActivity.class);
                startActivity(intent);
                return true;
            case R.id.mnu_alerts:
                String text="";
                if(hasAlert){
                    text = getString(R.string.alerts);
                }else{
                     text = getString(R.string.no_alert);
                }

                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.alert_toast,
                        (ViewGroup) findViewById(R.id.alert_toast_container));

                TextView textView = (TextView) layout.findViewById(R.id.toast_text);
                textView.setText(text);


                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.TOP|Gravity.RIGHT, 0, 120);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(layout);
                toast.show();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, " *** onCreate() ***");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_no_emergency);

    }

    public void showEmergencies(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, EmergenciesActivity.class);
        startActivity(intent);

    }

    public void showMissions(View view) {

        Intent intent = new Intent(IndexNoEmergencyActivity.this, MissionsActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d("onResume","*** onResume() ****");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.API_URL2)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final String token = prefs.getString("token","");
        Log.d("oldToken",token);
        final ApiInterface api = retrofit.create(ApiInterface.class);
        Call<LoginData> call = api.getToken("Bearer "+token);

        call.enqueue(new Callback<LoginData>() {
            @Override
            public void onResponse(Call<LoginData> call, Response<LoginData> response) {
                if(response.body() != null) {
                    prefs.edit().putString("token", response.body().getToken()).apply();
                }else{
                    final String email = prefs.getString("email","");
                    final String password = prefs.getString("password","");
                    LoginData login = new LoginData(email,password);
                    Call<LoginData> logCall = api.login(login);

                    logCall.enqueue(new Callback<LoginData>() {
                        @Override
                        public void onResponse(Call<LoginData> call, Response<LoginData> response) {
                            Log.d("newToken", response.body().getToken());
                            prefs.edit().putString("token", response.body().getToken()).apply();
                        }

                        @Override
                        public void onFailure(Call<LoginData> call, Throwable t) {
                            Log.d(TAG, "Error en update");
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<LoginData> call, Throwable t) {
                Log.d(TAG, "Error en update");
            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"*** onStart() ****");
        updateAlerts();
        Button currentMissionBtn = (Button) findViewById(R.id.buttonCurrentMission);
        currentMissionBtn.setEnabled(false);
        spinner = (ProgressBar)findViewById(R.id.progressBarMain);
        spinner.setVisibility(View.VISIBLE);
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

                if(response.body()==null){
                    Log.d("(1) ***RESPONSE", "body null");
                    hasActiveMission = false;

                }else if(response.body()!=null && response.body().size()>0){
                    hasActiveMission = true;
                    missionData = response.body().get(0).getMission();
                    Button currentMissionBtn = (Button) findViewById(R.id.buttonCurrentMission);
                    currentMissionBtn.setEnabled(true);
                }


                spinner.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<List<MissionResponse>> call, Throwable t) {
                Log.d("(1) ***RESPUESTA", "Falló :c");
                hasActiveMission = false;
                spinner.setVisibility(View.GONE);

            }

        });


    }

    private void updateAlerts(){
        //alertas en caso de mensajes no leidos
        SharedPreferences prefs = getSharedPreferences(getString(R.string.user_data_preference_file_key), Context.MODE_PRIVATE);
        hasAlert = false;
        int missionNmb = prefs.getInt(getString(R.string.mission_number_preference_key), 0);
        int emergencyNmb = prefs.getInt(getString(R.string.emergencies_number_preference_key),0);

        TextView numMissionsTxt = (TextView) findViewById(R.id.numMissionsTxt);
        numMissionsTxt.setVisibility(View.GONE);

        TextView numEmergencyTxt = (TextView) findViewById(R.id.numEmergencyTxt);
        numEmergencyTxt.setVisibility(View.GONE);

        ImageView numEmergencyBkg = (ImageView) findViewById(R.id.numEmergencyBkg);
        numEmergencyBkg.setVisibility(View.GONE);

        ImageView numMissionsBkg = (ImageView) findViewById(R.id.numMissionsBkg);
        numMissionsBkg.setVisibility(View.GONE);



        Log.d(TAG, "Datos guardados --> Misiones: " + String.valueOf(missionNmb) + " Emergencias: " + String.valueOf(emergencyNmb) );


        if (missionNmb>0){ //muestra misiones
            numMissionsTxt.setText(String.valueOf(missionNmb));
            numMissionsTxt.setVisibility(View.VISIBLE);
            numMissionsBkg.setVisibility(View.VISIBLE);
        }

        if(emergencyNmb>0){ //muestra amergencias
            numEmergencyTxt.setText(String.valueOf(emergencyNmb));
            numEmergencyTxt.setVisibility(View.VISIBLE);
            numEmergencyBkg.setVisibility(View.VISIBLE);
        }
        if(emergencyNmb > 0 || missionNmb > 0){
            hasAlert = true;
        }
    }

    public void currentMission(View view){
        Log.d(TAG, "currentMission");
        if(hasActiveMission){
            Intent intent = new Intent(IndexNoEmergencyActivity.this, ActiveMissionActivity.class);
            intent.putExtra("mission",missionData);
            startActivity(intent);
        }else{
            String text = getString(R.string.no_active_missions);
            Toast.makeText(this, text,
                    Toast.LENGTH_LONG).show();
        }
    }
}

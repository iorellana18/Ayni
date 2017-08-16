package cl.citiaps.coordinaciondevoluntarios.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import cl.citiaps.coordinaciondevoluntarios.R;
import cl.citiaps.coordinaciondevoluntarios.adapter.EmergencyListAdapter;
import cl.citiaps.coordinaciondevoluntarios.data.ApiInterface;
import cl.citiaps.coordinaciondevoluntarios.data.EmergencyData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EmergenciesActivity extends AppCompatActivity  {
    private static final String TAG = "EmergenciesActivity";
    ListView listView;
    ProgressBar spinner;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_base, menu);
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergencies);

        setTitle(getResources().getString(R.string.emergency_list));

        //se marcan emergencias como leidas
        SharedPreferences prefs = getSharedPreferences(getString(R.string.user_data_preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(getString(R.string.emergencies_number_preference_key), 0);
        editor.commit();

        spinner = (ProgressBar) findViewById(R.id.emergenciesProgress);
        loadData();

    }
    private void loadData(){
        spinner.setVisibility(View.VISIBLE);

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.emergenciesListView);

        // Pedir listado de emergencias al servidor
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.API_URL2)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);
        Call<List<EmergencyData>> call = api.emergencies();
        call.enqueue(new Callback<List<EmergencyData>>() {
            @Override
            public void onResponse(Call<List<EmergencyData>> call, Response<List<EmergencyData>> response) {
                Log.d("RESPUESTA", "Funcionó :D");
                EmergencyListAdapter emergencyListAdapter = new EmergencyListAdapter(response.body(), getBaseContext());
                listView.setAdapter(emergencyListAdapter);
                spinner.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<EmergencyData>> call, Throwable t) {
                Log.d("RESPUESTA", "Falló :c");
                spinner.setVisibility(View.GONE);
            }
        });

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                EmergencyData emergency = (EmergencyData) listView.getAdapter().getItem(position);
                Intent intent = new Intent(EmergenciesActivity.this,EmergencyDetailActivity.class);
                intent.putExtra("emergency",emergency);
                startActivity(intent);
            }

        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                loadData();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

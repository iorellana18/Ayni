package cl.citiaps.coordinaciondevoluntarios.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import cl.citiaps.coordinaciondevoluntarios.R;
import cl.citiaps.coordinaciondevoluntarios.adapter.MissionListAdapter;
import cl.citiaps.coordinaciondevoluntarios.data.ApiInterface;
import cl.citiaps.coordinaciondevoluntarios.data.MissionResponse;
import cl.citiaps.coordinaciondevoluntarios.data.MissionResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MissionsActivity extends AppCompatActivity {

    ListView listView;
    ProgressBar spinner;
    TextView emptyMission;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_base, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missions);

        setTitle(getResources().getString(R.string.mission_list));

        //se marcan misiones como leidas
        SharedPreferences prefs = getSharedPreferences(getString(R.string.user_data_preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(getString(R.string.mission_number_preference_key), 0);
        editor.apply();

        listView = (ListView)findViewById(R.id.recruitMissionListView);
        spinner = (ProgressBar) findViewById(R.id.progressMissions);
        emptyMission = (TextView) findViewById(R.id.emptyMissionText);
        emptyMission.setVisibility(View.GONE);
        loadData();

    }

    private void loadData(){
        spinner.setVisibility(View.VISIBLE);

        int volunteerID = getSharedPreferences(
                getString(R.string.user_data_preference_file_key), Context.MODE_PRIVATE).
                getInt(getString(R.string.volunteer_id_preference_key), 0);

        // Pedir listado de misiones al servidor
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.API_URL2)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final String token = prefs.getString("token","");


        ApiInterface api = retrofit.create(ApiInterface.class);
        Call<List<MissionResponse>> call = api.misionList("Bearer "+token);

        call.enqueue(new Callback<List<MissionResponse>>() {
            @Override
            public void onResponse(Call<List<MissionResponse>> call, Response<List<MissionResponse>> response) {

                if (response.body() == null){
                    emptyMission.setVisibility(View.GONE);
                }else{
                    emptyMission.setVisibility(View.GONE);
                    MissionListAdapter missionListAdapter = new MissionListAdapter(response.body(), getBaseContext());
                    listView.setAdapter(missionListAdapter);
                    spinner.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<List<MissionResponse>> call, Throwable t) {
                Log.d("RESPUESTA", "Falló :c");
                emptyMission.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.GONE);
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                MissionResponse mision = (MissionResponse) listView.getAdapter().getItem(position);
                Intent intent = new Intent(getBaseContext(), MissionRecruitedDetailActivity.class);
                intent.putExtra("mision",mision.getMission());
                startActivity(intent);
            }

        });
    }

    @Override
    public void onResume(){
        super.onResume();
        loadData();
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

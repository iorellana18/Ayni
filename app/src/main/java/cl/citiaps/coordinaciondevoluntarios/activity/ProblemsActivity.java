package cl.citiaps.coordinaciondevoluntarios.activity;

/**
 * Created by Joaco on 01-06-17.
 */

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import cl.citiaps.coordinaciondevoluntarios.R;
import cl.citiaps.coordinaciondevoluntarios.adapter.ProblemListAdapter;
import cl.citiaps.coordinaciondevoluntarios.data.ApiInterface;
import cl.citiaps.coordinaciondevoluntarios.data.MissionData;
import cl.citiaps.coordinaciondevoluntarios.data.ProblemData;
import cl.citiaps.coordinaciondevoluntarios.data.UserData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProblemsActivity extends AppCompatActivity {
    ListView listView;
    ProgressBar spinner;
    TextView emptyMission;
    Integer missionId;
    String missionTitle;
    String missionDescription;
    MissionData missionData;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.problem_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problems);

        missionData = (MissionData)getIntent().getSerializableExtra("mission");
        missionId = missionData.getId();
        missionTitle = missionData.getTitle();
        missionDescription = missionData.getDescription();

        Toast.makeText(getApplicationContext(),String.valueOf(missionData.getId()),Toast.LENGTH_LONG).show();
        setTitle("Problemas");
        listView = (ListView)findViewById(R.id.recruitMissionListView);
        spinner = (ProgressBar) findViewById(R.id.progressMissions);
        emptyMission = (TextView) findViewById(R.id.emptyMissionText);
        emptyMission.setVisibility(View.GONE);
        loadData();
    }

    private void loadData(){
        spinner.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.API_URL2)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);
        final int mission;
        if(missionId == 0){
            mission = 1;
        }else{
            mission = missionId;
        }

        Call<MissionData> call = api.getMissionData(mission);

        call.enqueue(new Callback<MissionData>() {
            @Override
            public void onResponse(Call<MissionData> call, Response<MissionData> response) {
                ListAdapter problemsListAdapter = new ProblemListAdapter(response.body().getProblems(), getBaseContext());
                listView.setAdapter(problemsListAdapter);
                spinner.setVisibility(View.GONE);
                if (response.body().getProblems().size() == 0){
                    emptyMission.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<MissionData> call, Throwable t) {
                Log.d("RESPUESTA", "Falló :c");
                emptyMission.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.GONE);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(ApiInterface.API_URL2)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                final ProblemData obj = (ProblemData) listView.getAdapter().getItem(position);
                ApiInterface api = retrofit.create(ApiInterface.class);
                Call<UserData> call = api.getUser(obj.getUser_id());
                call.enqueue(new Callback<UserData>() {
                    @Override
                    public void onResponse(Call<UserData> call, Response<UserData> response) {
                        Intent intentDetailProblem = new Intent(getBaseContext(), ProblemDetailActivity.class);
                        intentDetailProblem.putExtra("mission",missionData);
                        intentDetailProblem.putExtra("problemTitle", obj.getTitle());
                        intentDetailProblem.putExtra("problemID", missionData.getProblems().get(position).getId());
                        intentDetailProblem.putExtra("problemDesc", obj.getDescription());
                        intentDetailProblem.putExtra("problemAutor", response.body().getFirst_name()
                                + " " + response.body().getLast_name());
                        startActivity(intentDetailProblem);
                    }

                    @Override
                    public void onFailure(Call<UserData> call, Throwable t) {
                        Log.d("RESPUESTA", "Falló :c");
                        emptyMission.setVisibility(View.VISIBLE);
                        spinner.setVisibility(View.GONE);
                    }
                });
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
            case R.id.ReportProblemTtl:
                reportProblem();
        }
        return super.onOptionsItemSelected(item);
    }

    public void reportProblem(){
        Intent intent = new Intent(this, ReportProblemActivity.class);
        intent.putExtra("mission",missionData);
        startActivity(intent);
    }
}

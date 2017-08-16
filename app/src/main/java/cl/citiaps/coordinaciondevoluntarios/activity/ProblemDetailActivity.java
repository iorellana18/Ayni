package cl.citiaps.coordinaciondevoluntarios.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import cl.citiaps.coordinaciondevoluntarios.R;
import cl.citiaps.coordinaciondevoluntarios.adapter.AnswerListAdapter;
import cl.citiaps.coordinaciondevoluntarios.adapter.ProblemListAdapter;
import cl.citiaps.coordinaciondevoluntarios.data.ApiInterface;
import cl.citiaps.coordinaciondevoluntarios.data.CommentProblemData;
import cl.citiaps.coordinaciondevoluntarios.data.MissionData;
import cl.citiaps.coordinaciondevoluntarios.data.MissionRecruitData;
import cl.citiaps.coordinaciondevoluntarios.data.ProblemData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Joaco on 09-06-17.
 */

public class ProblemDetailActivity extends AppCompatActivity {
    ListView listView;
    ProgressBar spinner;
    TextView emptyMission;
    Integer problemID;
    String problemTitle;
    String problemDesc;
    String problemAutor;
    MissionData missionData;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail_problem, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_problem);

        setTitle("Detalle Problema");

        Intent intent = getIntent();
        problemID = intent.getIntExtra("problemID", 0);
        problemTitle = intent.getStringExtra("problemTitle");
        problemDesc = intent.getStringExtra("problemDesc");
        problemAutor = intent.getStringExtra("problemAutor");
        missionData = (MissionData)getIntent().getSerializableExtra("mission");

        listView = (ListView)findViewById(R.id.answerListView);
        spinner = (ProgressBar) findViewById(R.id.emergenciesProgress);

        TextView tituloProblem = (TextView) findViewById(R.id.problemTitle);
        tituloProblem.setText(problemTitle);
        TextView descripcionProblem = (TextView) findViewById(R.id.problemDesc);
        descripcionProblem.setText(problemDesc);
        TextView autorProblem = (TextView) findViewById(R.id.autorProblem);
        autorProblem.setText(problemAutor);

        loadData();

    }

    private void loadData(){
        spinner.setVisibility(View.VISIBLE);

        int volunteerID = getSharedPreferences(
                getString(R.string.user_data_preference_file_key), Context.MODE_PRIVATE).
                getInt(getString(R.string.volunteer_id_preference_key), 0);

        // Pedir listado de problemas al servidor
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.API_URL2)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);
        Call<ProblemData> call = api.getProblem(problemID);
        call.enqueue(new Callback<ProblemData>() {
            @Override
            public void onResponse(Call<ProblemData> call, Response<ProblemData> response) {
                ListAdapter answerListAdapter = new AnswerListAdapter(
                        response.body().getAnswer(), getBaseContext());
                listView.setAdapter(answerListAdapter);
                spinner.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ProblemData> call, Throwable t) {
                Log.d("RESPUESTA", "Falló :c");
                spinner.setVisibility(View.GONE);
            }
        });

        /*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                ProblemData obj = (ProblemData) listView.getAdapter().getItem(position);

                Intent intentDetailProblem = new Intent(getBaseContext(), ProblemDetailActivity.class);
                intentDetailProblem.putExtra("problemTitle", obj.getTitle());
                intentDetailProblem.putExtra("problemID", obj.getId());
                intentDetailProblem.putExtra("problemDesc", obj.getDescription());
                intentDetailProblem.putExtra("problemAutor", obj.getUser_firstname() + " " + obj.getUser_lastname());
                startActivity(intentDetailProblem);
            }

        });
        */
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

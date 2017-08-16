package cl.citiaps.coordinaciondevoluntarios.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import cl.citiaps.coordinaciondevoluntarios.R;
import cl.citiaps.coordinaciondevoluntarios.adapter.GuideListAdapter;
import cl.citiaps.coordinaciondevoluntarios.data.ApiInterface;
import cl.citiaps.coordinaciondevoluntarios.data.Files;
import cl.citiaps.coordinaciondevoluntarios.data.MissionData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GuidesActivity extends AppCompatActivity {
    private static final String TAG = "GuidesActivity";
    ListView listView;
    ProgressBar guidesSpinner;
    Integer missionId;
    TextView emptyGuides;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_base, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guides);
        setTitle("Guías");

        Intent intent = getIntent();
        missionId = intent.getIntExtra("missionId", 0);
        emptyGuides = (TextView) findViewById(R.id.empty_guides);

        listView = (ListView)findViewById(R.id.guide_list);
        guidesSpinner = (ProgressBar) findViewById(R.id.guidesSpinner);
        loadData(missionId);
    }

    private void loadData(final int missionId){
        Log.d(TAG, "loadData");
        guidesSpinner.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.API_URL2)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final ApiInterface api = retrofit.create(ApiInterface.class);

        final int mission;
        if(missionId==0){
            mission = 1;
        }else {
            mission = missionId;
        }
        Call <MissionData> call = api.getMissionData(mission);
        call.enqueue(new Callback<MissionData>() {
            @Override
            public void onResponse(Call<MissionData> call, Response<MissionData> response) {
                Log.d(TAG, "onResponse");
                emptyGuides.setVisibility(View.GONE);
                GuideListAdapter guideListAdapter = new GuideListAdapter(response.body().getFiles(), getBaseContext());
                listView.setAdapter(guideListAdapter);
                guidesSpinner.setVisibility(View.GONE);
                if (response.body() == null || response.body().getFiles().size() == 0){
                    emptyGuides.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onFailure(Call<MissionData> call, Throwable t) {
                Log.d(TAG, "onFailure");
                emptyGuides.setVisibility(View.VISIBLE);
                guidesSpinner.setVisibility(View.GONE);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Files obj = (Files) listView.getAdapter().getItem(i);
                Intent browserIntent;
                if(missionId==0){
                    browserIntent = new Intent(Intent.ACTION_VIEW,Uri.parse(
                            "http://158.170.140.28:3000/files/1/" + obj.getFile()));
                }else {
                    browserIntent = new Intent(Intent.ACTION_VIEW,Uri.parse(
                            "http://158.170.140.28:3000/files/" + missionId + "/" + obj.getFile()));
                }
                startActivity(browserIntent);

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                loadData(missionId);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

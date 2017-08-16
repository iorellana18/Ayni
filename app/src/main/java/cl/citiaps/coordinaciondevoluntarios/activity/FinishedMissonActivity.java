package cl.citiaps.coordinaciondevoluntarios.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import cl.citiaps.coordinaciondevoluntarios.R;

public class FinishedMissonActivity extends AppCompatActivity {
    String missionTitleTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished_misson);
        Intent receivedIntent = getIntent();

        missionTitleTxt = getResources().getString(R.string.finished_mission_desc);

        TextView missionTitle = (TextView) findViewById(R.id.finishedMissionDesc);
        missionTitle.setText(Html.fromHtml(missionTitleTxt + " <b>" + receivedIntent.getStringExtra("missionTitle")+"</b>. "+ getResources().getString(R.string.finished_mission_desc2)));

    }
    public void toMain(View view){
        Intent intent = new Intent(this, IndexNoEmergencyActivity.class);
        startActivity(intent);
    }
}

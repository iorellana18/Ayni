package cl.citiaps.coordinaciondevoluntarios.activity;

import android.app.ActivityOptions;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import cl.citiaps.coordinaciondevoluntarios.R;
import cl.citiaps.coordinaciondevoluntarios.data.RegisterData;

/**
 * Created by Ian on 17-08-2017.
 */

public class Register2Activity extends AppCompatActivity {

    Button fecha;
    Button siguiente;
    EditText cell;
    EditText emergency;
    CheckBox seguro_vida;
    Boolean seguro_state;
    TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        fecha = (Button)findViewById(R.id.fecha);
        siguiente = (Button)findViewById(R.id.siguiente);
        cell = (EditText)findViewById(R.id.cell);
        emergency = (EditText)findViewById(R.id.emergency);
        seguro_vida =  (CheckBox) findViewById(R.id.seguro);
        seguro_vida.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                   @Override
                                                   public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {

                                                       seguro_state = isChecked;

                                                   }
                                               }
        );

        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });

        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });
    }

    public void next(){
        RegisterData registerData = (RegisterData) getIntent().getSerializableExtra("data");
        RegisterData nuevo = new RegisterData(registerData,Integer.valueOf(cell.getText().toString()),
                Integer.valueOf(emergency.getText().toString()),"1994-06-23"+"T00:00:00Z",seguro_state,3,true);

        Intent intent = new Intent(this,Register3Activity.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(),
                R.transition.animation,R.transition.animation2).toBundle();
        intent.putExtra("data",nuevo);
        startActivity(intent, bndlanimation);
    }
}

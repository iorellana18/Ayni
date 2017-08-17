package cl.citiaps.coordinaciondevoluntarios.activity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import cl.citiaps.coordinaciondevoluntarios.R;
import cl.citiaps.coordinaciondevoluntarios.data.ApiInterface;
import cl.citiaps.coordinaciondevoluntarios.data.AppTokenData;
import cl.citiaps.coordinaciondevoluntarios.data.LoginData;
import cl.citiaps.coordinaciondevoluntarios.data.RegisterData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Joaco on 11-04-17.
 */

public class RegisterActivity extends  AppCompatActivity {

    Button siguiente;
    EditText firstName;
    EditText lastName;
    EditText email;
    EditText pass;
    EditText confirmPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        siguiente = (Button)findViewById(R.id.siguienteButton);
        firstName = (EditText)findViewById(R.id.firstName);
        lastName = (EditText)findViewById(R.id.lastName);
        email = (EditText)findViewById(R.id.email);
        pass = (EditText)findViewById(R.id.pass);
        confirmPass = (EditText)findViewById(R.id.confirmPass);
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siguiente();
            }
        });
    }

    public void siguiente(){
        RegisterData registerData = new RegisterData(firstName.getText().toString(), lastName.getText().toString(),
                email.getText().toString(), pass.getText().toString(), confirmPass.getText().toString());
        Intent intent = new Intent(this,Register2Activity.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(),
                R.transition.animation,R.transition.animation2).toBundle();
        intent.putExtra("data",registerData);
        startActivity(intent, bndlanimation);
    }

    private Boolean isValidEmail(String email){

        if (email != null & email.isEmpty()){
            return false;
        }

        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


}

package cl.citiaps.coordinaciondevoluntarios.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import cl.citiaps.coordinaciondevoluntarios.R;
import cl.citiaps.coordinaciondevoluntarios.data.ApiInterface;
import cl.citiaps.coordinaciondevoluntarios.data.AppTokenData;
import cl.citiaps.coordinaciondevoluntarios.data.LoginData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Joaco on 11-04-17.
 */

public class RegisterActivity extends  AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void register(View view){
        spinner = (ProgressBar) findViewById(R.id.progressRegister);
        spinner.setVisibility(View.VISIBLE);

        EditText emailText = (EditText) findViewById(R.id.emailText);
        EditText passwordText = (EditText) findViewById(R.id.passwordText);
        TextView textLoginError = (TextView) findViewById(R.id.textLoginError);
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if(!isValidEmail(email)){
            emailText.setError(getResources().getString(R.string.error_invalid_email));
            textLoginError.setText("");
        }
        else{

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ApiInterface.API_URL2)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ApiInterface api = retrofit.create(ApiInterface.class);
            LoginData loginData = new LoginData(email, password);
            Call<LoginData> call = api.postRegister(loginData);
            call.enqueue(new Callback<LoginData>() {
                @Override
                public void onResponse(Call<LoginData> call, Response<LoginData> response) {
                    Log.d("Respuesta", response.raw().toString());
                    if(response.body().getUsername() == null){
                        ((TextView) findViewById(R.id.textLoginError)).setText("Correo o contraseña incorrecta");
                    }

                    else{
                        ((TextView) findViewById(R.id.textLoginError)).setText("");
                        SharedPreferences sharedPref = getSharedPreferences(
                                getString(R.string.user_data_preference_file_key), Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();

                        FirebaseMessaging.getInstance().subscribeToTopic("emergencies");

                        Intent intent = new Intent(RegisterActivity.this, IndexNoEmergencyActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    spinner.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<LoginData> call, Throwable t) {
                    Log.d("RESPUESTA", "Falló :c");

                    spinner.setVisibility(View.GONE);
                    ((TextView) findViewById(R.id.textLoginError)).setText("Nuestro servidor no está disponible");
                }
            });
        }
    }

    private Boolean isValidEmail(String email){

        if (email != null & email.isEmpty()){
            return false;
        }

        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void sendAppToken(int userID){
        Log.d(TAG, "sendAppToken()");
        String token = FirebaseInstanceId.getInstance().getToken();
        AppTokenData tokenData = new AppTokenData();
        tokenData.setApp_token(token);
        tokenData.setUserID(userID);

        //llamada a API
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);
        Call<AppTokenData> call = api.updateToken(tokenData);
        call.enqueue(new Callback<AppTokenData>() {
            @Override
            public void onResponse(Call<AppTokenData> call, Response<AppTokenData> response) {
                Log.d(TAG, "Response: " + response.body().toString());
            }

            @Override
            public void onFailure(Call<AppTokenData> call, Throwable t) {
                Log.d(TAG, "Error en update");
            }
        });
    }


}

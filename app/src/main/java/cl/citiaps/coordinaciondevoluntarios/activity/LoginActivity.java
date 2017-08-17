package cl.citiaps.coordinaciondevoluntarios.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.messaging.FirebaseMessaging;
import cl.citiaps.coordinaciondevoluntarios.data.LoginData;
import cl.citiaps.coordinaciondevoluntarios.R;
import cl.citiaps.coordinaciondevoluntarios.data.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private static int userError = 401;
    ProgressBar spinner;
    Button login;
    Button registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        login = (Button)findViewById(R.id.loginButton);
        registro = (Button)findViewById(R.id.registerButton);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toRegistro();
            }
        });


    }

    public void login(){
        spinner = (ProgressBar) findViewById(R.id.progressLogin);
        spinner.setVisibility(View.VISIBLE);

        EditText emailText = (EditText) findViewById(R.id.emailText);
        EditText passwordText = (EditText) findViewById(R.id.passwordText);
        TextView textLoginError = (TextView) findViewById(R.id.textLoginError);
        final String email = emailText.getText().toString();
        final String password = passwordText.getText().toString();

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
            Call<LoginData> call = api.login(loginData);
            call.enqueue(new Callback<LoginData>() {
                @Override
                public void onResponse(Call<LoginData> call, Response<LoginData> response) {

                    if(response.code() == userError){
                        ((TextView) findViewById(R.id.textLoginError)).setText(R.string.bad_password);
                    }else{

                        ((TextView) findViewById(R.id.textLoginError)).setText("");
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(
                                LoginActivity.this);
                        prefs.edit().putString("token",response.body().getToken()).apply();
                        prefs.edit().putString("email",email).apply();
                        prefs.edit().putString("password",password).apply();
                        prefs.edit().putString("expire",response.body().getExpire()).apply();
                        prefs.edit().putBoolean("isLogin",true).apply();
                        FirebaseMessaging.getInstance().subscribeToTopic("emergencies");

                        Intent intent = new Intent(LoginActivity.this, IndexNoEmergencyActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    spinner.setVisibility(View.GONE);

                }

                @Override
                public void onFailure(Call<LoginData> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();

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

    public void toRegistro(){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
        finish();
    }

/*
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
*/
/*
    public void registerView(View view){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
*/
}

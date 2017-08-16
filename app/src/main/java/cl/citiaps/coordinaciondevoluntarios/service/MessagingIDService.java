package cl.citiaps.coordinaciondevoluntarios.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import cl.citiaps.coordinaciondevoluntarios.R;
import cl.citiaps.coordinaciondevoluntarios.data.ApiInterface;
import cl.citiaps.coordinaciondevoluntarios.data.AppTokenData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MessagingIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MessagingIDService";

    @Override
    public void onTokenRefresh() {
        //Get hold of the registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //Log the token
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        sendRegistrationToServer(refreshedToken);
    }
    private void sendRegistrationToServer(String token) {
        Log.d(TAG, "token: " + token);
        //Implement this method if you want to store the token on your server
        int userID = getSharedPreferences(
                getString(R.string.user_data_preference_file_key), Context.MODE_PRIVATE).
                getInt(getString(R.string.user_id_preference_key), 0);
        AppTokenData tokenData = new AppTokenData();
        tokenData.setApp_token(token);
        tokenData.setUserID(userID);

        //TODO: implementar validacion correctamente
        if (userID != 0){
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

}

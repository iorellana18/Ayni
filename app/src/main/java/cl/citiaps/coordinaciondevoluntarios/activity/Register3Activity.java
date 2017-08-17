package cl.citiaps.coordinaciondevoluntarios.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cl.citiaps.coordinaciondevoluntarios.R;
import cl.citiaps.coordinaciondevoluntarios.adapter.PerfilListAdapter;
import cl.citiaps.coordinaciondevoluntarios.data.Abilities;
import cl.citiaps.coordinaciondevoluntarios.data.ApiInterface;
import cl.citiaps.coordinaciondevoluntarios.data.RegisterData;
import cl.citiaps.coordinaciondevoluntarios.data.RegistroHabilidad;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ian on 17-08-2017.
 */

public class Register3Activity extends AppCompatActivity {


    List<Abilities> lista = new ArrayList<>();
    PerfilListAdapter adapterPerfil;
    RegisterData registerData;
    Button terminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);
        terminar = (Button)findViewById(R.id.terminar);

        registerData = (RegisterData)getIntent().getSerializableExtra("data");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.API_URL2)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);
        Call<List<Abilities>> call_lista = api.getAbilities();
        call_lista.enqueue(new Callback<List<Abilities>>() {
            @Override
            public void onResponse(Call<List<Abilities>> call, Response<List<Abilities>> response) {
                System.out.println("" + response.body().size());
                ListView lista_habilidades = (ListView) findViewById(R.id.lista_habilidades);

                for (int i = 0; i < response.body().size(); i++) {
                    lista.add(new Abilities(response.body().get(i).getId(),
                            response.body().get(i).getAbility(), false));
                }

                adapterPerfil = new PerfilListAdapter(lista, getBaseContext());
                lista_habilidades.setAdapter(adapterPerfil);
                adapterPerfil.setListAbility(lista);
            }

            @Override
            public void onFailure(Call<List<Abilities>> call, Throwable t) {

            }

        });


        terminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUsuario();
            }
        });

    }


    public void sendUsuario(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.API_URL2)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final ApiInterface api = retrofit.create(ApiInterface.class);
        Call<RegisterData> call_lista = api.postRegister(registerData);
        call_lista.enqueue(new Callback<RegisterData>() {
            @Override
            public void onResponse(Call<RegisterData> call, Response<RegisterData> response) {
                final int userId = response.body().getId();

                Log.d("ID",String.valueOf(userId));
                List<Abilities> Abilities = new ArrayList<Abilities>();
                
                Abilities.add(new Abilities(1,"Primeros Auxilios"));
                Abilities.add(new Abilities(3, "Artes Marciales"));

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(
                        Register3Activity.this);

                RegistroHabilidad registroHabilidad = new RegistroHabilidad(userId,Abilities ,prefs.getString("tokenFire","hola"));

                Call<RegistroHabilidad> callsito = api.postAbilities(registroHabilidad);

                callsito.enqueue(new Callback<RegistroHabilidad>() {
                    @Override
                    public void onResponse(Call<RegistroHabilidad> call, Response<RegistroHabilidad> response) {
                        Toast.makeText(getApplicationContext(),"Registrado",Toast.LENGTH_LONG).show();



                    }

                    @Override
                    public void onFailure(Call<RegistroHabilidad> call, Throwable t) {

                    }

                });
            }

            @Override
            public void onFailure(Call<RegisterData> call, Throwable t) {

            }

        });

    }
}

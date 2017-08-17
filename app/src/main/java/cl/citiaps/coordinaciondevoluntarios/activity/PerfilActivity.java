package cl.citiaps.coordinaciondevoluntarios.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cl.citiaps.coordinaciondevoluntarios.R;
import cl.citiaps.coordinaciondevoluntarios.adapter.PerfilListAdapter;
import cl.citiaps.coordinaciondevoluntarios.data.Abilities;
import cl.citiaps.coordinaciondevoluntarios.data.ApiInterface;
import cl.citiaps.coordinaciondevoluntarios.data.Habilidad;
import cl.citiaps.coordinaciondevoluntarios.data.UserData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PerfilActivity extends AppCompatActivity {

    PerfilListAdapter adapterPerfil;
    List<Abilities> lista = new ArrayList<>();
    EditText nombre;
    EditText apellido;
    EditText celular;
    EditText celular_emergencia;
    CheckBox seguro_vida;
    Boolean seguro_state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        nombre = (EditText) findViewById(R.id.name);
        apellido = (EditText) findViewById(R.id.apellido);
        celular = (EditText) findViewById(R.id.celular);
        celular_emergencia = (EditText) findViewById(R.id.celular_emergencia);
        seguro_vida =  (CheckBox) findViewById(R.id.has_life_insurance);
        seguro_vida.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

               @Override
               public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {

                    seguro_state = isChecked;

               }
           }
        );

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

        final int userID = getSharedPreferences(
                getString(R.string.user_data_preference_file_key), Context.MODE_PRIVATE).
                getInt(getString(R.string.user_id_preference_key), 0);


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final String token = prefs.getString("token","");

        Call<UserData> call = api.getUserLoged("Bearer "+token);

        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                if (response.body().getFirst_name() == null) {
                    nombre.setText("Voluntario Anónimo");
                    apellido.setText("Voluntario Anónimo");
                    celular.setText("no registrado");
                    celular_emergencia.setText("no registrado");

                } else {
                    nombre.setText(response.body().getFirst_name());
                    apellido.setText(response.body().getLast_name());
                    celular.setText(response.body().getContact_phone_number());
                    celular_emergencia.setText(response.body().getEmergency_phone_number());
                    seguro_vida.setChecked(response.body().getLife_insurance());
                    if(response.body().getVolunteer().getAbilities() == null){

                    }else if (response.body().getVolunteer().getAbilities().size() != 0) {
                        int j = 0;
                        while (j < lista.size()) {
                            for (int i = 0; i < response.body().getVolunteer().getAbilities().size(); i++) {
                                if (lista.get(j).getId() == response.body().getVolunteer().getAbilities().get(i).getId()) {
                                    lista.get(j).setChecked(true);
                                    i = response.body().getVolunteer().getAbilities().size();
                                }
                            }
                            j++;
                        }
                    }
                    if (adapterPerfil == null){
                        adapterPerfil = new PerfilListAdapter(lista, getBaseContext());
                    }
                    adapterPerfil.setListAbility(lista);

                }

            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                Log.d("RESPUESTA", "Falló :c");
            }
        });

        Button saveAbilitiesBtn = (Button) findViewById(R.id.save_profile);
        saveAbilitiesBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                saveAbilities(v);
            }
        });
    }

    public void saveAbilities(View view) {

        List<Abilities> lista_usr = new ArrayList<>();
        for (int i=0; i<lista.size(); i++){
            if(lista.get(i).getChecked()){
                lista_usr.add(lista.get(i));
                Toast.makeText(getApplicationContext(),lista.get(i).getAbility(),Toast.LENGTH_LONG).show();
            }
        }



        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final String token = prefs.getString("token","");

        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("first_name", nombre.getText().toString());
            jsonBody.put("last_name",apellido.getText().toString());
            jsonBody.put("contact_phone_number",Integer.valueOf(celular.getText().toString()));
            jsonBody.put("emergency_phone_number", Integer.valueOf(celular_emergencia.getText().toString()));
            jsonBody.put("life_insurance",seguro_state);
            final String requestBody = jsonBody.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, ApiInterface.API_URL2+"usuario/informacion/",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Datos actualizados exitosamente",
                                Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(PerfilActivity.this, IndexNoEmergencyActivity.class);
                        startActivity(intent);
                        finish();
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("response",error.toString());
                    }
                }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer "+token);
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


        }catch (JSONException json){Log.d("Json",json.toString());}
    }
}



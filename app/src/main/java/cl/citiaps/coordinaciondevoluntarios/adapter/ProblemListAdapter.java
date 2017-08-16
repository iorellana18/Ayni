package cl.citiaps.coordinaciondevoluntarios.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import cl.citiaps.coordinaciondevoluntarios.R;
import cl.citiaps.coordinaciondevoluntarios.data.ApiInterface;
import cl.citiaps.coordinaciondevoluntarios.data.MissionData;
import cl.citiaps.coordinaciondevoluntarios.data.ProblemData;
import cl.citiaps.coordinaciondevoluntarios.data.UserData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Joaco on 09-06-17.
 */

public class ProblemListAdapter  extends BaseAdapter {

    private List<ProblemData> problemArray;
    private Context context;
    private LayoutInflater layoutInflater;

    public ProblemListAdapter(List<ProblemData> problems, Context context) {
        super();
        this.problemArray = problems;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {

        return this.problemArray.size();
    }

    @Override
    public Object getItem(int position) {

        return this.problemArray.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {


        convertView= layoutInflater.inflate(R.layout.problem_item, null);
        TextView titleText =(TextView)convertView.findViewById(R.id.titleText);
        titleText.setText(this.problemArray.get(position).getTitle());

        TextView description =(TextView)convertView.findViewById(R.id.disasterTypeText);
        description.setText(this.problemArray.get(position).getDescription());

        setNombres(convertView,position);
        return convertView;
    }

    private void setNombres(final View convertView, int position){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.API_URL2)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);
        Call<UserData> call = api.getUser(problemArray.get(position).getUser_id());

        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                TextView autor =(TextView)convertView.findViewById(R.id.autor);
                String nombre = response.body().getFirst_name()+ " " + response.body().getLast_name();
                autor.setText(nombre);

            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                Log.d("RESPUESTA", "Falló :c");
            }
        });

    }

    private String setDate(String fechaLarga){
        return fechaLarga.substring(5, 10);
    }

}

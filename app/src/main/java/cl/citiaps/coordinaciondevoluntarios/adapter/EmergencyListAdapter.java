package cl.citiaps.coordinaciondevoluntarios.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cl.citiaps.coordinaciondevoluntarios.data.EmergencyData;
import cl.citiaps.coordinaciondevoluntarios.R;

/**
 * Created by Mok on 22-09-16.
 */
public class EmergencyListAdapter extends BaseAdapter {

    private List<EmergencyData> emergencyArray;
    private Context context;
    private LayoutInflater layoutInflater;

    public EmergencyListAdapter(List<EmergencyData> emergencyArray, Context context) {
        super();
        this.emergencyArray = emergencyArray;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {

        return this.emergencyArray.size();
    }

    @Override
    public EmergencyData getItem(int position) {

        return this.emergencyArray.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        convertView= layoutInflater.inflate(R.layout.list_emergencies, null);

        TextView titleText =(TextView)convertView.findViewById(R.id.emergencyNameText);
        titleText.setText(this.emergencyArray.get(position).getTitle());

        TextView typeText =(TextView)convertView.findViewById(R.id.emergencyTypeText);
        typeText.setText(this.emergencyArray.get(position).getTitle());
        typeText.setText(this.emergencyArray.get(position).getEmergency_type().getType());

        TextView date = (TextView)convertView.findViewById(R.id.fecha);
        date.setText(emergencyArray.get(position).getFormatedDate());
        // TextView distanceText =(TextView)convertView.findViewById(R.id.emergencyDistanceText);
        // distanceText.setText(this.emergencyArray.get(position));

        TextView adressText =(TextView)convertView.findViewById(R.id.emergencyAdressText);
        String adress = this.emergencyArray.get(position).getFullAdress();
        adressText.setText(adress);

        return convertView;
    }
}

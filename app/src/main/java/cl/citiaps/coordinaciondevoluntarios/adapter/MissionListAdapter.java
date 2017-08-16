package cl.citiaps.coordinaciondevoluntarios.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cl.citiaps.coordinaciondevoluntarios.data.MissionData;
import cl.citiaps.coordinaciondevoluntarios.R;
import cl.citiaps.coordinaciondevoluntarios.data.MissionResponse;

/**
 * Created by kayjt on 21-09-2016.
 */

public class MissionListAdapter extends BaseAdapter {

    private List<MissionResponse> missionArray;
    private Context context;
    private LayoutInflater layoutInflater;

    public MissionListAdapter(List<MissionResponse> missionArray, Context context) {
        super();
        this.missionArray = missionArray;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {

        return this.missionArray.size();
    }

    @Override
    public Object getItem(int position) {

        return this.missionArray.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        convertView= layoutInflater.inflate(R.layout.mission_recruited_item, null);
        TextView titleText =(TextView)convertView.findViewById(R.id.titleText);
        titleText.setText(this.missionArray.get(position).getMission().getTitle());

        TextView addressText =(TextView)convertView.findViewById(R.id.meetingPointAdressText);
        addressText.setText(this.missionArray.get(position).getMission().getMeeting_point_address());

        TextView dateText = (TextView)convertView.findViewById(R.id.dateText);
        String dateString = this.missionArray.get(position).getMission().getStart_date();
        dateText.setText(dateString);

        TextView disasterText = (TextView)convertView.findViewById(R.id.disasterTypeText);
        String disasterTitle = this.missionArray.get(position).getMission().getEmergency().getEmergency_type().getType();
        disasterText.setText(disasterTitle);

        return convertView;
    }

}

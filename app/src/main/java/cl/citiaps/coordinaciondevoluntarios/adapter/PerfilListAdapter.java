package cl.citiaps.coordinaciondevoluntarios.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cl.citiaps.coordinaciondevoluntarios.R;
import cl.citiaps.coordinaciondevoluntarios.data.Abilities;
import cl.citiaps.coordinaciondevoluntarios.data.GuideData;
import cl.citiaps.coordinaciondevoluntarios.data.Habilidad;
import retrofit2.Callback;

/**
 * Created by Joaco on 09-05-17.
 */

public class PerfilListAdapter extends BaseAdapter {

    private List<Abilities> listAbility;
    private Context context;
    private LayoutInflater layoutInflater;

    public PerfilListAdapter(List<Abilities> listAbility, Context context){
        super();
        this.listAbility = listAbility;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return this.listAbility.size();
    }

    @Override
    public Object getItem(int position) {
        return this.listAbility.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.perfil_list_abilities, null);
        CheckBox check = (CheckBox) view.findViewById(R.id.stringHabilidad);
        check.setId(this.listAbility.get(i).getId());
        check.setText(this.listAbility.get(i).getAbility());
        check.setChecked(this.listAbility.get(i).getChecked());
        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listAbility.get(i).setChecked(isChecked);
            }
        });
        return view;
    }

    public List<Abilities> getListAbility() {
        return listAbility;
    }

    public void setListAbility(List<Abilities> lista) {
        this.listAbility = lista;
        notifyDataSetChanged();
    }
}
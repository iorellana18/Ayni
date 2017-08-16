package cl.citiaps.coordinaciondevoluntarios.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cl.citiaps.coordinaciondevoluntarios.R;
import cl.citiaps.coordinaciondevoluntarios.data.Files;
import cl.citiaps.coordinaciondevoluntarios.data.GuideData;

/**
 * Created by diego on 30-04-17.
 */

public class GuideListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<Files> guideArray;

    public GuideListAdapter(List<Files> guideArray, Context context){
        super();
        if (guideArray== null){
            this.guideArray = new ArrayList<>();
        }else {
            this.guideArray = guideArray;
        }
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return this.guideArray.size();
    }

    @Override
    public Object getItem(int position) {
        return this.guideArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
/*
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.guide_item, viewGroup);
        }
*/
        view = layoutInflater.inflate(R.layout.guide_item, null);
        TextView fileName =(TextView)view.findViewById(R.id.guide_file_name);
        fileName.setText(this.guideArray.get(i).getFile());


        return view;
    }
}

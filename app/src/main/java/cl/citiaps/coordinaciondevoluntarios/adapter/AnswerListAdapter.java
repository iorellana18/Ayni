package cl.citiaps.coordinaciondevoluntarios.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cl.citiaps.coordinaciondevoluntarios.R;
import cl.citiaps.coordinaciondevoluntarios.data.Answer;
import cl.citiaps.coordinaciondevoluntarios.data.CommentProblemData;
import cl.citiaps.coordinaciondevoluntarios.data.ProblemData;

/**
 * Created by Joaco on 12-06-17.
 */

public class AnswerListAdapter extends BaseAdapter {

    private List<Answer> answerArray;
    private Context context;
    private LayoutInflater layoutInflater;

    public AnswerListAdapter(List<Answer> problems, Context context) {
        super();
        this.answerArray = problems;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {

        return this.answerArray.size();
    }

    @Override
    public Object getItem(int position) {

        return this.answerArray.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        convertView= layoutInflater.inflate(R.layout.answer_list_item, null);
        TextView descriptionText =(TextView)convertView.findViewById(R.id.answerText);
        descriptionText.setText(this.answerArray.get(position).getAnswer());

        TextView autorText = (TextView)convertView.findViewById(R.id.autorAnswer);
        autorText.setText(this.answerArray.get(position).getUser().getFirst_name()+" "+
        answerArray.get(position).getUser().getLast_name());

        return convertView;
    }

}


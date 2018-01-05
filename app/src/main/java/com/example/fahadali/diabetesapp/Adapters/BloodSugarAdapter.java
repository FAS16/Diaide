package com.example.fahadali.diabetesapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.fahadali.diabetesapp.Model.BloodSugar;
import com.example.fahadali.diabetesapp.R;

import java.util.ArrayList;


public class BloodSugarAdapter extends ArrayAdapter<BloodSugar>{
    public BloodSugarAdapter(Context context, ArrayList<BloodSugar> users) {
        super(context,0,users);
    }

    /**
     * Method ??????
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent)   {
        BloodSugar bs = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bloodsugar_listview,parent,false);
        }

        TextView date = convertView.findViewById(R.id.Date);
        TextView comment = convertView.findViewById(R.id.Comment);
        TextView bloodSugar = convertView.findViewById(R.id.Bloodsugar_lvl);
        date.setText(bs.getTime());
        comment.setText(bs.getComment());
        bloodSugar.setText(String.valueOf(bs.getBloodSugar()));

        return convertView;
    }
}

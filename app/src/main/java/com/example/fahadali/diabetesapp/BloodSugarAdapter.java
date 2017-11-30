package com.example.fahadali.diabetesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.fahadali.diabetesapp.Model.BloodSugar;

import java.util.ArrayList;

/**
 * Created by emiljorgensen on 21/11/2017.
 */

public class BloodSugarAdapter extends ArrayAdapter<BloodSugar>{
    public BloodSugarAdapter(Context context, ArrayList<BloodSugar> users) {super(context,0,users);}


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

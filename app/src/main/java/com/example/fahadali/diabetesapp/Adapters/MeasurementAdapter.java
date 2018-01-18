package com.example.fahadali.diabetesapp.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fahadali.diabetesapp.Model.Measurement;
import com.example.fahadali.diabetesapp.R;

import java.util.ArrayList;


public class MeasurementAdapter extends ArrayAdapter<Measurement>{
    private TextView date;
    private TextView comment;
    private TextView bloodSugar;
    private ImageView tag;
    private Measurement bs;


    public MeasurementAdapter(Context context, ArrayList<Measurement> users) {
        super(context,0,users);

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)   {
        bs = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.measurement_list_item,parent,false);
        }


         date = convertView.findViewById(R.id.date);
         comment = convertView.findViewById(R.id.comment);
         bloodSugar = convertView.findViewById(R.id.Bloodsugar_lvl);
         tag = convertView.findViewById(R.id.measurement_tag);

         date.setText(bs.getTime());
         bloodSugar.setText(String.valueOf(bs.getBloodSugar()));
         setComment();
         setImage();

        return convertView;
    }



    private void setImage(){



        if(bs.getTag() == null) tag.setImageResource(0);

        else if(bs.getTag().toString().equals("")) tag.setImageResource(0);

        else if(bs.getTag().equals("Før måltid")) tag.setImageResource(R.drawable.ic_apple_black);

        else if(bs.getTag().equals("Efter måltid")) tag.setImageResource(R.drawable.ic_eaten_apple_green);

        else if(bs.getTag().equals("Sengetid")) tag.setImageResource(R.drawable.ic_bed_blue);


    }


    private void setComment(){

        comment.setTextColor(ContextCompat.getColor(getContext(), R.color.textOnWhite));
        int comLength = bs.getComment().length();

        if(comLength < 20){
            comment.setText(bs.getComment().toString());
        }
        else comment.setText(bs.getComment().substring(0, 20)+"...");

        if(bs.getComment().equals("N/A")) {
             comment.setTextColor(Color.LTGRAY);
             comment.setText(" —");
        }

    }
}

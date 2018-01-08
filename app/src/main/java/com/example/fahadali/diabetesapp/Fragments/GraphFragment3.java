package com.example.fahadali.diabetesapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.example.fahadali.diabetesapp.Model.BloodSugar;
import com.example.fahadali.diabetesapp.Model.User;
import com.example.fahadali.diabetesapp.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class GraphFragment3 extends Fragment {


    private GraphView graph;
    private ArrayList<Date> dates;
    private LineGraphSeries<DataPoint> series;
    private DataPoint[] dataPoints;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_graph_3, container, false);
        graph = view.findViewById(R.id.graph);
        dates = new ArrayList<>();

        fillDatapoints();
        series = new LineGraphSeries<>(dataPoints);
        series.setAnimated(true);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(15);

        graph.addSeries(series);
        graph.setTitle("Seneste målinger");
//        graph.setTitleTextSize(80f);
        // set date label formatter
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        graph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space
//         set manual x bounds to have nice steps
//        graph.getViewport().setMinX(User.getUserInstance().getBloodList().get(0).getBloodSugar());
//        graph.getViewport().setMaxX(User.getUserInstance().getBloodList().get(User.getUserInstance().getBloodList().size()-1).getBloodSugar());
        graph.getViewport().setXAxisBoundsManual(true);

        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(20);
        graph.getViewport().setYAxisBoundsManual(true);
        // as we use dates as labels, the human rounding to nice readable numbers
        // is not necessary
        graph.getGridLabelRenderer().setHumanRounding(false);

        return view;
    }


    public void fillDatapoints(){

        User tempUser = User.getUserInstance();
        int size = tempUser.getBloodList().size();
        dataPoints = new DataPoint[size];

        for(int i = 0; i < size; i++){

            dataPoints[i] = new DataPoint(parseDate(tempUser.getBloodList().get(i).getTime()) ,tempUser.getBloodList().get(i).getBloodSugar());
            dates.add(parseDate(tempUser.getBloodList().get(i).getTime()));

        }

    }


    public Date parseDate(String dateString){

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy HH:mm");
        Date date = null;

        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

}

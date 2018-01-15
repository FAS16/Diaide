package com.example.fahadali.diabetesapp.Fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.fahadali.diabetesapp.Model.Measurement;
import com.example.fahadali.diabetesapp.Model.ObserverPattern.Observer;
import com.example.fahadali.diabetesapp.Model.User;
import com.example.fahadali.diabetesapp.Other.App;
import com.example.fahadali.diabetesapp.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class BloodSugarFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener, Observer {


    private final Handler mHandler = new Handler();
    private Runnable mTimer1;
    private Runnable mTimer2;

    private View view;
    private GraphView graph;
    private LineGraphSeries<DataPoint> series;
    private DataPoint[] dataPoints;

    private Spinner filterSpinner;
    private ArrayAdapter <CharSequence> filterAdapter;
    private TextView lowestBloodSugar, highestBloodSugar, latestBloodSugar;
    private Button go_BTN;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_blood_sugar_2, container, false);
        User.getUserInstance().registerObserver(this);

        filterSpinner = view.findViewById(R.id.filter_SPNR);
        filterAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.blood_sugar_filters, android.R.layout.simple_spinner_dropdown_item);
        highestBloodSugar = view.findViewById(R.id.highestBS_TV);
        lowestBloodSugar = view.findViewById(R.id.lowestBS_TV);
        latestBloodSugar = view.findViewById(R.id.latestBS_TV);

        go_BTN = view.findViewById(R.id.go_BTN);
        go_BTN.setOnClickListener(this);

        filterSpinner.setAdapter(filterAdapter);
//        filterSpinner.setSelection(0);
        filterSpinner.setOnItemSelectedListener(this);




        if(User.getUserInstance().getId() != null){

            updateUI(dataPointsOfTheDay());

        }


        return view;
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        User.getUserInstance().removeObserver(this);
    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        String itemSelected = adapterView.getItemAtPosition(i).toString();

        if(itemSelected.equals("I dag")){

            App.shortToast(getActivity(),"I dag");


//            series.resetData(dataPointsOfTheDay());
            graph.removeAllSeries();
            updateUI(dataPointsOfTheDay());


        }

        else if(itemSelected.equals("Seneste 7 dage")){

            App.shortToast(getActivity(),"Seneste 7 dage");



        }

        else if(itemSelected.equals("Seneste 30 dage")){

            App.shortToast(getActivity(),"Seneste 30 dage");

        }

        else if(itemSelected.equals("Intet filter")){

            graph.removeAllSeries();
            updateUI(allDataPoints());


        }

    }



    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }



    @Override
    public void onClick(View v) {


        if (v == go_BTN) {

            Intent i = new Intent(getContext(), BloodOverviewFragment.class);
            startActivity(i);



        }

    }


    public void createGraph( DataPoint[] points) {


//                allDataPoints();
                graph = view.findViewById(R.id.graph);
                series = new LineGraphSeries<>(points);
                series.setAnimated(true);
                series.setDrawDataPoints(true);
                series.setDataPointsRadius(15);
                series.setColor(Color.BLACK);


                graph.addSeries(series);
                graph.setTitle("Målinger");
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

    }




//    public void createGraph() {
//
//        graph = view.findViewById(R.id.graph);
//        allDataPoints();
//        series = new LineGraphSeries<>(dataPoints);
//        series.setAnimated(true);
//        series.setDrawDataPoints(true);
//        series.setDataPointsRadius(15);
//
//        graph.addSeries(series);
//        graph.setTitle("Målinger");    //        graph.setTitleTextSize(80f);
//
//        // set date label formatter
//        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
//        graph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space
////         set manual x bounds to have nice steps
////        graph.getViewport().setMinX(User.getUserInstance().getBloodList().get(0).getBloodSugar());
////        graph.getViewport().setMaxX(User.getUserInstance().getBloodList().get(User.getUserInstance().getBloodList().size()-1).getBloodSugar());
//        graph.getViewport().setXAxisBoundsManual(true);
//
//        graph.getViewport().setMinY(0);
//        graph.getViewport().setMaxY(20);
//        graph.getViewport().setYAxisBoundsManual(true);
//        // as we use dates as labels, the human rounding to nice readable numbers
//        // is not necessary
//        graph.getGridLabelRenderer().setHumanRounding(false);
//
//
//    }

    public DataPoint[] allDataPoints() {

        graph.removeAllSeries();
        User user = User.getUserInstance();
        int size = user.getBloodList().size();
        dataPoints = new DataPoint[size];

        for (int i = 0; i < size; i++) {

            dataPoints[i] = new DataPoint(parseDate(user.getBloodList().get(i).getTime()), user.getBloodList().get(i).getBloodSugar());

        }

        return dataPoints;

    }


    public DataPoint[] dataPointsOfTheDay() {



        ArrayList<Measurement> tempList = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        String today = sdf.format(new Date(System.currentTimeMillis()));

        User user = User.getUserInstance();
        int size = user.getBloodList().size();
        int specificSize = 0;

        for (int i = 0; i < size; i++) {

            String subject = sdf.format(parseDate(User.getUserInstance().getBloodList().get(i).getTime()));

            if (subject.equals(today)) {

                tempList.add(User.getUserInstance().getBloodList().get(i));

            }

        }

        specificSize = tempList.size();
        dataPoints = null;
        dataPoints = new DataPoint[specificSize];

        for (int i = 0; i < specificSize; i++) {

//            String subject = sdf.format(parseDate(tempList.get(i).getTime()));

//            if (subject.equals(today)) {

                dataPoints[i] = new DataPoint(parseDate(tempList.get(i).getTime()), user.getBloodList().get(i).getBloodSugar());


//            }

        }

        return dataPoints;
    }


    public void showSevenDaysDataPoints(){

    }

    public void showThirtyDaysDataPoints(){

    }


    public Date parseDate(String dateString) {

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy HH:mm");
        Date date = null;

        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }


    public void updateUI(DataPoint[] points) {



        createGraph(points);

        if(!User.getUserInstance().getBloodList().isEmpty()){

            highestBloodSugar.setText("" + series.getHighestValueY());
            lowestBloodSugar.setText("" + series.getLowestValueY());
            int size = User.getUserInstance().getSizeOfList();
            latestBloodSugar.setText("" + User.getUserInstance().getBloodList().get(size - 1).getBloodSugar());

        }

    }


    @Override
    public void update() {

                updateUI(dataPointsOfTheDay());

        System.out.println("User changed - Running update in  BloodSugarFragment - USER: "+User.getUserInstance());

    }
}

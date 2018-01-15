package com.example.fahadali.diabetesapp.Fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
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
import com.example.fahadali.diabetesapp.Model.XYvalue;
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
public class BloodSugarFragment2 extends Fragment implements View.OnClickListener, Observer, AdapterView.OnItemSelectedListener {


    private final String TAG = "BloodSugarFragment2";
    private LineGraphSeries<DataPoint> series;
    private GraphView graph;
    private ArrayList<XYvalue> xYvalues;
    private Spinner filterSpinner;
    private ArrayAdapter<CharSequence> filterAdapter;
    private TextView lowestBloodSugar, highestBloodSugar, latestBloodSugar;
    private Button go_BTN;
    private View view;
    private CardView card;
    private boolean showMeasurementsOfToday;


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
        card = view.findViewById(R.id.graph_containerNew);

        go_BTN.setOnClickListener(this);
        filterSpinner.setAdapter(filterAdapter);
        filterSpinner.setOnItemSelectedListener(this);

        xYvalues = new ArrayList<>();
        updateUI();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        User.getUserInstance().removeObserver(this);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void initGraph(){
        graph = view.findViewById(R.id.graph);
        graph.removeAllSeries();
        card.refreshDrawableState();
        series = new LineGraphSeries<>();
//        initAllXyValues();

        if(xYvalues.size() != 0){

            createGraph();

        }

    }

    private void createGraph() {


        sortXyArrayByY();

        for(int i = 0; i < xYvalues.size(); i++){

            Date x = xYvalues.get(i).getX();
            double y = xYvalues.get(i).getY();
            series.appendData(new DataPoint(x,y), true, 100);
            Log.d(TAG, xYvalues.get(i).getX().toString()+" - "+ xYvalues.get(i).getY());
        }

            series.setAnimated(true);
            series.setDrawDataPoints(true);
            series.setDataPointsRadius(15);
            series.setColor(Color.BLACK);

            graph.setTitle("Målinger");
            graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
            graph.getGridLabelRenderer().setNumHorizontalLabels(3);
            graph.getViewport().setXAxisBoundsManual(true);
            graph.getViewport().setMinX(xYvalues.get(0).getX().getTime());
            graph.getViewport().setMaxX(xYvalues.get(xYvalues.size()-1).getX().getTime());
            graph.getGridLabelRenderer().setNumVerticalLabels(4);
            graph.getViewport().setYAxisBoundsManual(true);
            graph.getViewport().setMinY(0);
            graph.getViewport().setMaxY(15);
            graph.getGridLabelRenderer().setHumanRounding(false);

        graph.addSeries(series);

    }


    public void initAllXyValues(){

        xYvalues.clear();
        for(Measurement m: User.getUserInstance().getBloodList()){

            xYvalues.add(new XYvalue(parseDate(m.getTime()), m.getBloodSugar()));

        }

    }

    public void initXyValuesOfToday(){

        xYvalues.clear();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        String today = sdf.format(new Date(System.currentTimeMillis()));

        for(Measurement m: User.getUserInstance().getBloodList()){

            String subject = sdf.format(parseDate(m.getTime()));

            if(subject.equals(today)){

                xYvalues.add(new XYvalue(parseDate(m.getTime()), m.getBloodSugar()));
            }



        }

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

    public void sortXyArrayByX(){

        if(xYvalues.size() > 1){

            XYvalue temp;
            for(int j = 0; j < xYvalues.size() -1; j++){

                for(int i = 0; i < xYvalues.size() -1; i++){
                    if( xYvalues.get(i).getY() > xYvalues.get(i+1).getY()){

                        temp = xYvalues.get(i);
                        xYvalues.set(i, xYvalues.get(i+1));
                        xYvalues.set(i+1, temp);

                    }
                }
            }
        }
    }

    public void sortXyArrayByY(){

        if(xYvalues.size() > 1){

            XYvalue temp;
            for(int j = 0; j < xYvalues.size() -1; j++){

                for(int i = 0; i < xYvalues.size() -1; i++){
                    if( xYvalues.get(i).getX().after(xYvalues.get(i+1).getX())){

                        temp = xYvalues.get(i);
                        xYvalues.set(i, xYvalues.get(i+1));
                        xYvalues.set(i+1, temp);

                    }
                }
            }
        }
    }

    @Override
    public void onClick(View view) {

        if (view == go_BTN) {

            Intent i = new Intent(getContext(), BloodOverviewFragment.class);
            startActivity(i);

        }

    }

    public void updateUI() {

        if(filterSpinner.getSelectedItemPosition() == 0) initAllXyValues();
        else initXyValuesOfToday();

        initGraph();

        if (!User.getUserInstance().getBloodList().isEmpty()) {

            highestBloodSugar.setText("" + series.getHighestValueY());
            lowestBloodSugar.setText("" + series.getLowestValueY());
            int size = User.getUserInstance().getSizeOfList();
            latestBloodSugar.setText("" + User.getUserInstance().getBloodList().get(size - 1).getBloodSugar());

        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {


        if(pos == 0){

            showMeasurementsOfToday = false;
            updateUI();
            App.shortToast(getActivity(),"Alle målinger");

        }

        else if(pos == 1){

            showMeasurementsOfToday = true;
            updateUI();
            App.shortToast(getActivity(),"Dagens målinger");

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void update() {

        Log.i(TAG, "Observer update running in BloodSugarFragment");
        updateUI();


    }
}



package com.example.fahadali.diabetesapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fahadali.diabetesapp.Model.Measurement;
import com.example.fahadali.diabetesapp.Model.ObserverPattern.Observer;
import com.example.fahadali.diabetesapp.Model.User;
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
public class GraphFragment extends Fragment implements Observer {


    private GraphView graph;
    private LineGraphSeries<DataPoint> series;
    private DataPoint[] dataPoints;
    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        //HUSK AT LAVE OBSERVAR; DA DA FORSIDEN IKKE OPDATERE NÅR DER ER TILFØJET NOGET TIL GRAFEN


        view = inflater.inflate(R.layout.fragment_graph_3, container, false);


        User.getUserInstance().registerObserver(this);


        System.out.println("SE NU HER MAN: " + User.getUserInstance());


//        if(User.getUserInstance() == null){
//
//            App.longToast(getActivity(), "BRUGER VAR NULL");
//
//
//        }
//
//        else{
//            App.longToast(getActivity(), "BRUGER VAR IKKE NULL: "+ User.getUserInstance().getEmail());
//        }


//                        showDataPointsOfTheDay();
        createGraph();


        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        User.getUserInstance().removeObserver(this);

    }

    public void createGraph() {

        graph = view.findViewById(R.id.graph);
        fillDatapoints();
//        showDataPointsOfTheDay();
        series = new LineGraphSeries<>(dataPoints);
        series.setAnimated(true);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(15);

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


    public void fillDatapoints() {

        User user = User.getUserInstance();
        int size = user.getBloodList().size();
        dataPoints = new DataPoint[size];

        for (int i = 0; i < size; i++) {

            dataPoints[i] = new DataPoint(parseDate(user.getBloodList().get(i).getTime()), user.getBloodList().get(i).getBloodSugar());

        }

        System.out.println("OG HER2: " + dataPoints.toString());

    }


    public void showGraphOfToday() {

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

            String subject = sdf.format(parseDate(tempList.get(i).getTime()));

            if (subject.equals(today)) {

                dataPoints[i] = new DataPoint(parseDate(user.getBloodList().get(i).getTime()), user.getBloodList().get(i).getBloodSugar());


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


    @Override
    public void update() {
        createGraph();
        
     }
}







//        App.longToast(getActivity(),"IDAG: "+"("+today+")");
//        App.longToast(getActivity(),"SUBJEKT: "+"("+subject+") ");

package com.example.fahadali.diabetesapp.Model.ObserverPattern;


/**
 * Created by fahadali on 05/12/2017..
 */

public interface Subject {

    void registerObserver(Observer observer);


    void removeObserver(Observer observer);


    void notifyAllObservers();



}



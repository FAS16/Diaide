package com.example.fahadali.diabetesapp.Model;

import android.widget.Button;

import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Created by aleks on 11-01-2018.
 */

public class MedicineCard {

    private String MedicineName;
    private String MedicineEffect;
    private String MedicineSideEffect;
    private String other;

    public MedicineCard(String medicineName, String medicineEffect, String medicineSideEffect, String other){
        this.MedicineName = medicineName;
        this.MedicineEffect = medicineEffect;
        this.MedicineSideEffect = medicineSideEffect;
        this.other = other;;
    }

    public MedicineCard(){

    }

    public String getMedicineName() {
        return MedicineName;
    }

    public void setMedicineName(String medicineName) {
        this.MedicineName = medicineName;
    }

    public String getMedicineEffect() {
        return MedicineEffect;
    }

    public void setMedicineEffect(String medicineEffect) {
        this.MedicineEffect = medicineEffect;
    }

    public String getMedicineSideEffect() {
        return MedicineSideEffect;
    }

    public void setMedicineSideEffect(String medicineSideEffect) {
        this.MedicineSideEffect = medicineSideEffect;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    @Override
    public String toString() {
        return  "["+MedicineName + ", " + MedicineEffect + ", " + MedicineSideEffect + ", " + other + "]" ;
    }
}


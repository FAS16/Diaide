package com.example.fahadali.diabetesapp.Other;

import android.widget.Button;

import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Created by aleks on 11-01-2018.
 */

public class MedicineCard extends ArrayList<MedicineCard> {
    public String MedicineName;
    public String MedicineEffect;
    public String MedicineSideEffect;
    public String other;

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
        MedicineName = medicineName;
    }

    public String getMedicineEffect() {
        return MedicineEffect;
    }

    public void setMedicineEffect(String medicineEffect) {
        MedicineEffect = medicineEffect;
    }

    public String getMedicineSideEffect() {
        return MedicineSideEffect;
    }

    public void setMedicineSideEffect(String medicineSideEffect) {
        MedicineSideEffect = medicineSideEffect;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

}


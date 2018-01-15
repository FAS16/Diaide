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

    @Override
    public Stream<MedicineCard> stream() {
        return null;
    }
}


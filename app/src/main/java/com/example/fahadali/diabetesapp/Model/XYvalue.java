package com.example.fahadali.diabetesapp.Model;

import java.util.Date;

/**
 * Created by fahadali on 14/01/2018.
 */

public class XYvalue {

    private Date x;
    private double y;

    public XYvalue(Date x, double y){

        this.x = x;
         this.y = y;
    }

    public Date getX() {
        return x;
    }

    public void setX(Date x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}

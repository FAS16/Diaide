package com.example.fahadali.diabetesapp.Model;


public class Reminder {

    private String type;
    private String date;
    private String time;
    private String note;
    private String repeat;
    private String priority;


    public Reminder(String type, String date, String time, String note, String repeat, String priority){

        this.type = type;
        this.date = date;
        this.time = time;
        this.note = note;
        this.repeat = repeat;
        this.priority = priority;

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}




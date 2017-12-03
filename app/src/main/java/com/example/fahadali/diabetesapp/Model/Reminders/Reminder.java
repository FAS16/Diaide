package com.example.fahadali.diabetesapp.Model.Reminders;


public class Reminder {

    String time;
    String reminderMessage;
    String reminderHeader;
    boolean repeateable;

    public Reminder(String tiime, String reminderMessage, String reminderHeader, boolean repeateable){

        this.time = tiime;
        this.reminderMessage = reminderMessage;
        this.reminderHeader = reminderHeader;
        this.repeateable = repeateable;

    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getReminderMessage() {
        return reminderMessage;
    }

    public void setReminderMessage(String reminderMessage) {
        this.reminderMessage = reminderMessage;
    }

    public String getReminderHeader() {
        return reminderHeader;
    }

    public void setReminderHeader(String reminderHeader) {
        this.reminderHeader = reminderHeader;
    }

    public boolean isRepeateable() {
        return repeateable;
    }

    public void setRepeateable(boolean repeateable) {
        this.repeateable = repeateable;
    }


}
package com.example.fahadali.diabetesapp.Model.Reminders;

/**
 * Created by fahadali on 02/12/2017.
 */

public class DoctorAppointment extends Reminder {

    public DoctorAppointment(String time, String reminderMessage, String reminderHeader, boolean repeateable) {
        super(time, reminderMessage, reminderHeader, repeateable);
    }
}

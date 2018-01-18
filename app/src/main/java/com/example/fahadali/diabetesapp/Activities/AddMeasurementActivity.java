
package com.example.fahadali.diabetesapp.Activities;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.fahadali.diabetesapp.Model.Measurement;
import com.example.fahadali.diabetesapp.Model.User;
import com.example.fahadali.diabetesapp.Other.App;
import com.example.fahadali.diabetesapp.R;
import com.jesusm.holocircleseekbar.lib.HoloCircleSeekBar;

public class AddMeasurementActivity extends AppCompatActivity implements View.OnClickListener, HoloCircleSeekBar.OnCircleSeekBarChangeListener {

    private ImageView done_BTN;
    private TextView bloodSugarValue;
    private ToggleButton bedTime_BTN;
    private ToggleButton beforeMeal_BTN;
    private ToggleButton afterMeal_BTN;

    private HoloCircleSeekBar seekBar;
    private TextInputLayout comment_TextInput;
    private Intent intent;
    private boolean editMode;

    /**
     * Method for letting the activity know what to do when created.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bsugar_notation2);
        setTitle("Tilføj måling");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        done_BTN = findViewById(R.id.done_BTN);
        bloodSugarValue = findViewById(R.id.value_bs);
        bedTime_BTN = findViewById(R.id.bedtime_ICON);
        beforeMeal_BTN = findViewById(R.id.beforeMeal_ICON);
        afterMeal_BTN = findViewById(R.id.afterMeal_ICON);
        seekBar = findViewById(R.id.picker);
        comment_TextInput = findViewById(R.id.comment_TextInput);

        done_BTN.setOnClickListener(this);
        bedTime_BTN.setOnClickListener(this);
        beforeMeal_BTN.setOnClickListener(this);
        afterMeal_BTN.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(this);
        seekBar.setMax(150);

        intent = getIntent();

        if(intent.hasExtra("editMode")){
            editMode = getIntent().getExtras().getBoolean("editMode", false);
            if(editMode) initializeEditMode();
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        editMode = false;
    }


    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        bloodSugarValue.setText(savedInstanceState.getString("bloodSugarValue"));
        beforeMeal_BTN.setChecked(savedInstanceState.getBoolean("beforeMeal"));
        afterMeal_BTN.setChecked(savedInstanceState.getBoolean("afterMeal"));
        bedTime_BTN.setChecked(savedInstanceState.getBoolean("bedTime"));

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("bloodSugarValue", bloodSugarValue.getText().toString());
        outState.putBoolean("beforeMeal", beforeMeal_BTN.isChecked());
        outState.putBoolean("afterMeal", afterMeal_BTN.isChecked());
        outState.putBoolean("bedTime", bedTime_BTN.isChecked());

        }

    private void initializeEditMode() {

        setTitle("Rediger måling");

        double value = intent.getExtras().getDouble("seekbarValue");
        String comment = intent.getExtras().getString("comment");
        String tag = intent.getExtras().getString("chosenTag");

        seekBar.setValue((int)value*10);
        bloodSugarValue.setText(String.valueOf(value));
        seekBar.refreshDrawableState();
        if(!comment.equals("N/A")) comment_TextInput.getEditText().setText(comment);

        if(tag == null){
            return;
        }

        else if(tag.equals(beforeMeal_BTN.getText().toString())){
            beforeMeal_BTN.setChecked(true);

        }

        else if(tag.equals(afterMeal_BTN.getText().toString())){
            afterMeal_BTN.setChecked(true);

        }

        else if(tag.equals(bedTime_BTN.getText().toString())){
            bedTime_BTN.setChecked(true);
        }

    }

    @Override
    public void onClick(View v) {
        if(v == done_BTN){

            if(editMode){

                editMeasurement();

            }

            else addMeasurement();


        }

        else if(v == beforeMeal_BTN){
            afterMeal_BTN.setChecked(false);
            bedTime_BTN.setChecked(false);
        }

        else if(v == afterMeal_BTN){
            beforeMeal_BTN.setChecked(false);
            bedTime_BTN.setChecked(false);
        }

        else if(v == bedTime_BTN){
            afterMeal_BTN.setChecked(false);
            beforeMeal_BTN.setChecked(false);


        }

    }


    private void editMeasurement() {

        double tempBloodSugar = Double.parseDouble(bloodSugarValue.getText().toString());
        String tempComment = comment_TextInput.getEditText().getText().toString();
        String tempTag = getChosenTag();

        if(tempBloodSugar == 0) App.shortToast(this, "Fejl: Blodsukker ikke angivet.");

        else{

            if(tempComment.trim().isEmpty())tempComment = "N/A";

                int index = intent.getExtras().getInt("index");
                User.getUserInstance().getMeasurements().get(index).setBloodSugar(tempBloodSugar);
                User.getUserInstance().getMeasurements().get(index).setComment(tempComment);
                User.getUserInstance().getMeasurements().get(index).setTag(tempTag);
                App.shortToast(this, "Ændring oprettet.");
                finish();


            }

        }


    public void addMeasurement(){

        double tempBloodSugar = Double.parseDouble(bloodSugarValue.getText().toString());
        String tempComment = comment_TextInput.getEditText().getText().toString();
        String tempTag = getChosenTag();

        if(tempBloodSugar == 0) App.shortToast(this, "Fejl: Blodsukker ikke angivet.");

        else{

            if(tempComment.trim().isEmpty()) tempComment = "N/A";

                User.getUserInstance().addBloodSugarNotation(new Measurement(tempBloodSugar, tempComment, tempTag));
                App.shortToast(this, "Måling på "+ tempBloodSugar +" mmol/l er tilføjet.");
                finish();

        }


    }


    public String getChosenTag(){

        String chosenTag = null;

        if(beforeMeal_BTN.isChecked()) chosenTag = beforeMeal_BTN.getText().toString();
        else if (afterMeal_BTN.isChecked()) chosenTag = afterMeal_BTN.getText().toString();
        else if (bedTime_BTN.isChecked()) chosenTag = bedTime_BTN.getText().toString();

        return chosenTag;

    }


        @Override
        public boolean onSupportNavigateUp() {
            finish();
            return super.onSupportNavigateUp();
        }




    @Override
    public void onProgressChanged(HoloCircleSeekBar holoCircleSeekBar, int i, boolean b) {

        float value = ((float)i / 10);

        bloodSugarValue.setText(""+value);

        if(value > 10 || value < 4){

        }

    }

    @Override
    public void onStartTrackingTouch(HoloCircleSeekBar holoCircleSeekBar) {

    }

    @Override
    public void onStopTrackingTouch(HoloCircleSeekBar holoCircleSeekBar) {

    }


}

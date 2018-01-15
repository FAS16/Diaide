
package com.example.fahadali.diabetesapp.Activities;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fahadali.diabetesapp.Model.Measurement;
import com.example.fahadali.diabetesapp.Model.User;
import com.example.fahadali.diabetesapp.Other.App;
import com.example.fahadali.diabetesapp.R;
import com.jesusm.holocircleseekbar.lib.HoloCircleSeekBar;

public class NotationActivity extends AppCompatActivity implements View.OnClickListener, HoloCircleSeekBar.OnCircleSeekBarChangeListener {

    private Button cancel_BTN, add_BTN;
    private EditText firstNum_ET, secondNum_ET, thirdNum_ET, comment_ET, comma_ET;
    private TextView bloodSugarValue;
    private TextView bedtime_ICON;
    private TextView beforeMeal_ICON;
    private TextView afterMeal_ICON;
    private boolean bedTimeOn = false;
    private boolean beforeMealOn = false;
    private boolean afterMealOn = false;
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

        add_BTN = findViewById(R.id.done_BTN);
        cancel_BTN = findViewById(R.id.cancel_BTN);
        bloodSugarValue = findViewById(R.id.value_bs);
        bedtime_ICON = findViewById(R.id.bedtime_ICON);
        beforeMeal_ICON = findViewById(R.id.beforeMeal_ICON);
        afterMeal_ICON = findViewById(R.id.afterMeal_ICON);
        seekBar = findViewById(R.id.picker);
        comment_TextInput = findViewById(R.id.comment_TextInput);

        add_BTN.setText("Tilføj");
        add_BTN.setOnClickListener(this);
        cancel_BTN.setText("Annuller");
        cancel_BTN.setOnClickListener(this);
        bedtime_ICON.setOnClickListener(this);
        beforeMeal_ICON.setOnClickListener(this);
        afterMeal_ICON.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(this);
        seekBar.setMax(150);

        intent = getIntent();

        if(intent.hasExtra("editMode")){
            editMode = getIntent().getExtras().getBoolean("editMode", false);
            if(editMode) initializeEditMode();
        }

        if (savedInstanceState != null) {



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

        this.bloodSugarValue.setText(savedInstanceState.getString("bloodSugarValue"));


        String chosenTag = savedInstanceState.getString("chosenTag");
        if(chosenTag.equals(beforeMeal_ICON.getText().toString())) {
            beforeMealOn = true;
            beforeMeal_ICON.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
        else if(chosenTag.equals(afterMeal_ICON.getText().toString())) {
            afterMeal_ICON.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            afterMealOn = true;
        }
        else if(chosenTag.equals(bedtime_ICON.getText().toString())) {
            bedtime_ICON.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            bedTimeOn = true;
        }




    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("bloodSugarValue", bloodSugarValue.getText().toString());

        if(beforeMealOn) outState.putString("chosenTag",beforeMeal_ICON.getText().toString());
        else if(afterMealOn) outState.putString("chosenTag",afterMeal_ICON.getText().toString());
        else if(bedTimeOn) outState.putString("chosenTag", bedtime_ICON.getText().toString());



    }

    private void initializeEditMode() {

        setTitle("Rediger måling");
        add_BTN.setText("Gem");

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

        else if(tag.equals(beforeMeal_ICON.getText().toString())){
            beforeMeal_ICON.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            beforeMealOn = true;

        }

        else if(tag.equals(afterMeal_ICON.getText().toString())){
            afterMeal_ICON.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            afterMealOn = true;

        }

        else if(tag.equals(bedtime_ICON.getText().toString())){
            bedtime_ICON.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            bedTimeOn = true;
        }




    }

    @Override
    public void onClick(View v) {
        if(v == add_BTN){

            if(editMode){

                editMeasurement();

            }

            else addMeasurement();


        }
        if(v == cancel_BTN){

            finish();

        }

        else if(v == beforeMeal_ICON){

            if(!beforeMealOn){
                beforeMeal_ICON.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                beforeMealOn = true;
                afterMeal_ICON.setBackgroundColor(getResources().getColor(R.color.greyHighlighted));
                afterMealOn = false;
                bedtime_ICON.setBackgroundColor(getResources().getColor(R.color.greyHighlighted));
                bedTimeOn = false;
            }

            else if(beforeMealOn){
                beforeMeal_ICON.setBackgroundColor(getResources().getColor(R.color.greyHighlighted));
                beforeMealOn = false;

            }

        }

        else if(v == afterMeal_ICON){

            if(!afterMealOn){
                afterMeal_ICON.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                afterMealOn = true;
                beforeMeal_ICON.setBackgroundColor(getResources().getColor(R.color.greyHighlighted));
                beforeMealOn = false;
                bedtime_ICON.setBackgroundColor(getResources().getColor(R.color.greyHighlighted));
                bedTimeOn = false;
            }

            else if(afterMealOn){
                afterMeal_ICON.setBackgroundColor(getResources().getColor(R.color.greyHighlighted));
                afterMealOn = false;
            }

        }

        else if(v == bedtime_ICON){

            if(!bedTimeOn){
                bedtime_ICON.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                bedTimeOn = true;
                beforeMeal_ICON.setBackgroundColor(getResources().getColor(R.color.greyHighlighted));
                beforeMealOn = false;
                afterMeal_ICON.setBackgroundColor(getResources().getColor(R.color.greyHighlighted));
                afterMealOn = false;
            }

            else if(bedTimeOn){
                bedtime_ICON.setBackgroundColor(getResources().getColor(R.color.greyHighlighted));
                bedTimeOn = false;
            }

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
                User.getUserInstance().getBloodList().get(index).setBloodSugar(tempBloodSugar);
                User.getUserInstance().getBloodList().get(index).setComment(tempComment);
                User.getUserInstance().getBloodList().get(index).setTag(tempTag);
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

        if(beforeMealOn) chosenTag = beforeMeal_ICON.getText().toString();
        else if (afterMealOn) chosenTag = afterMeal_ICON.getText().toString();
        else if (bedTimeOn) chosenTag = bedtime_ICON.getText().toString();

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

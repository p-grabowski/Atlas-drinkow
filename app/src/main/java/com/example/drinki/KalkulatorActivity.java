package com.example.drinki;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class KalkulatorActivity extends AppCompatActivity {

    public static TextView waga;
    public static Switch plec;
    private static RadioGroup drinkSizeGroup;
    private static RadioButton drinkRozmiar;
    private static SeekBar procentAlkocholu;
    private static TextView alcPerDisplay;
    private static TextView bacPoziom;
    private static double bacLevelDisplay = 0.00;
    private static double finalBACPoziom = 0.00;
    private static TextView finalowyWynik;
    private static int minAlkochol = 5;
    private static int obecnyAlkochol = 5;
    private static int maxAlkochol = 25;
    private static int stepAlkochol = 5;
    private static Double r = 0.0;
    private static int W = 0;
    private static RadioButton radio30ml ;
    private static RadioButton radio60ml;
    private static RadioButton radio266ml;
    private static String stringWaga;
    private static ArrayList<Integer> PochlonientyAlkochol = new ArrayList<>();
    private ProgressBar progressBar;
    private static int progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activirt_kalkulatorv2);
        Intent intent = getIntent();


        progressBar = findViewById(R.id.progressBarBACLevel);
        bacPoziom = findViewById(R.id.textViewBACLevel);
        drinkSizeGroup = findViewById(R.id.radioGroupDrinkSize);
        radio30ml = findViewById(R.id.radioButton30ml);
        radio60ml = findViewById(R.id.radioButton60ml);
        radio266ml = findViewById(R.id.radioButton266ml);
        waga = findViewById(R.id.editTextWaga);
        plec = findViewById(R.id.switchPlec);
        finalowyWynik = findViewById(R.id.textViewResult);
        finalBACPoziom = Math.round(bacLevelDisplay * 100.0) / 100.0;
        bacPoziom.setText(Double.toString(finalBACPoziom));
        progress = (int) (finalBACPoziom * 100);
        progressBar.setMax(25);
        progressBar.setProgress(progress);
        seekbar();
        setResultColor();

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveButtonClick();
            }
        });
        drinkSizeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                drinkRozmiar = findViewById(checkedId);
            }
        });

        findViewById(R.id.buttonDodajDrink).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(waga.getText().toString().length() <=0) {
                    waga.setError("Wprowadz wage");
                } else {
                    if (W == 0 && r == 0.0) {
                        saveButtonClick();
                    }
                    String stringDrinkSize;
                    if (drinkRozmiar == null) {
                        stringDrinkSize = "30 ml";
                    } else {
                        stringDrinkSize = (String) drinkRozmiar.getText();
                    }
                    stringDrinkSize = stringDrinkSize.replace(" ml", "");
                    PochlonientyAlkochol.add(Integer.parseInt(stringDrinkSize) * obecnyAlkochol);
                    bacLevelDisplay = bacLevelDisplay + ((PochlonientyAlkochol.get(PochlonientyAlkochol.size() - 1) * 6.24) / (W * r * 100));
                    finalBACPoziom = Math.round(bacLevelDisplay * 100.0) / 100.0;
                    progress = (int) (finalBACPoziom * 100);
                    progressBar.setProgress(progress);
                    bacPoziom.setText(Double.toString(finalBACPoziom));
                    setResultColor();
                    enableDisable();
                }
            }
        });
        findViewById(R.id.buttonReset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bacLevelDisplay = 0.00;
                finalBACPoziom = Math.round(bacLevelDisplay * 100.0) / 100.0;
                bacPoziom.setText(Double.toString(finalBACPoziom));
                waga.setText("");
                W = 0;
                r = 0.0;
                plec.setChecked(false);
                setResultColor();
                enableDisable();

                //reset to 0
                progressBar.setProgress(0);
                radio30ml.setChecked(true);
                radio60ml.setChecked(true);
                radio266ml.setChecked(true);
                //reset to 0
                procentAlkocholu = findViewById(R.id.seekBarAlcohol);
                procentAlkocholu.setProgress(0);
            }
        });



    }

    public void saveButtonClick() {
        if(waga.getText().toString().length() <=0) {
            waga.setError("Wprowadz wage");
        }else {
            stringWaga = "" + waga.getText();
            W = Integer.parseInt(stringWaga);
            if (plec.isChecked()) {
                r = 0.68;
            } else {

                r = 0.55;
            }
            if (bacLevelDisplay != 0.00) {
                bacLevelDisplay = 0.00;
                calculateBACLevel();
                setResultColor();
            }
            enableDisable();
        }
    }
    public void enableDisable() {
        if(finalBACPoziom >= 0.25){
            findViewById(R.id.buttonDodajDrink).setEnabled(false);
            Toast.makeText(KalkulatorActivity.this, "Tobie juz wystarczy", Toast.LENGTH_LONG).show();
        }else{
            findViewById(R.id.buttonDodajDrink).setEnabled(true);
        }
    }
    public void setResultColor() {
        if(finalBACPoziom <= 0.08) {
            finalowyWynik.setText("Bezpieczny");
          //  finalResult.setBackgroundColor(getResources().getColor(R.color.green));
           // finalResult.setTextColor(getResources().getColor(R.color.white));
        } else if(finalBACPoziom >= 0.08 && finalBACPoziom <= 0.20) {
            finalowyWynik.setText("Uwazaj");
            //finalResult.setBackgroundColor(getResources().getColor(R.color.yellow));
            //finalResult.setTextColor(getResources().getColor(R.color.white));
        }else if(finalBACPoziom >= 0.20) {
            finalowyWynik.setText("Przekroczyles limit");
            //finalResult.setBackgroundColor(getResources().getColor(R.color.red));
            //finalResult.setTextColor(getResources().getColor(R.color.white));
        }
    }
    public void calculateBACLevel(){
        String stringDrinkSize;
        if (drinkRozmiar == null) {
            stringDrinkSize = "30 ml";
        } else {
            stringDrinkSize = (String) drinkRozmiar.getText();
        }
        stringDrinkSize = stringDrinkSize.replace(" ml", "");
        for (int i = 0; i < PochlonientyAlkochol.size(); i++) {
            bacLevelDisplay = bacLevelDisplay + ((PochlonientyAlkochol.get(i) * 6.24) / (W * r * 100));
        }
        finalBACPoziom = Math.round(bacLevelDisplay * 100.0) / 100.0;
        progress = (int) (finalBACPoziom * 100);
        progressBar.setProgress(progress);
        bacPoziom.setText(Double.toString(finalBACPoziom));
        enableDisable();
    }
    public void seekbar (){
        procentAlkocholu = findViewById(R.id.seekBarAlcohol);
        alcPerDisplay = findViewById(R.id.textViewSeekbarProgress);

        procentAlkocholu.setMax(maxAlkochol - minAlkochol);
        procentAlkocholu.incrementProgressBy(stepAlkochol);
        procentAlkocholu.setProgress(0);
        alcPerDisplay.setText(obecnyAlkochol + "%");
        procentAlkocholu.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = progress / 5;
                progress = progress * 5;
                obecnyAlkochol = progress + minAlkochol;
                alcPerDisplay.setText(obecnyAlkochol + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                alcPerDisplay.setText(obecnyAlkochol + "%");
            }
        });
    }

}
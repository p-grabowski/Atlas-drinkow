package com.example.drinki;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class OpisDrinka extends AppCompatActivity implements View.OnClickListener {

    private TextView mNazwa;
    private TextView mOpis;
    private TextView mSkladniki;
    private TextView mInstrukcja;
    private TextView mLink;
    private ImageView mObraz;
    private EditText mSzukaj;
    Button dodawanie_button;
    Button kalklulator_button;
    Button szukaj_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opis_drinka);

        //Odebranie wszystkich informacji o drinku
        Intent intent = getIntent();
        String nazwa = intent.getExtras().getString("nazwa");
        String opis = intent.getExtras().getString("opis");
        String skladniki = intent.getExtras().getString("skladniki");
        String instrukcja = intent.getExtras().getString("instrukcja");
        String linkPhoto = intent.getExtras().getString("linkPhoto");

        mNazwa = (TextView) findViewById(R.id.text_recipe);
        mOpis = (TextView) findViewById(R.id.desc);
        mSkladniki = (TextView) findViewById(R.id.ingredients);
        mInstrukcja = (TextView) findViewById(R.id.recipe);

        mObraz = (ImageView) findViewById(R.id.imageView2);
        dodawanie_button = (Button) findViewById(R.id.dodawanie_but);
        dodawanie_button.setOnClickListener(this);
        kalklulator_button = (Button) findViewById(R.id.kalkulator_but);
        kalklulator_button.setOnClickListener(this);

        mNazwa.setText(nazwa);
        mOpis.setText(opis);
        mSkladniki.setText(skladniki);
        mInstrukcja.setText(instrukcja);

        Picasso.get().load(linkPhoto).into(mObraz);


        //System.out.println("Odebrano nazwę drinka: ");
    }

    public void onClick(View v) {
        if (v == kalklulator_button) {
            Intent intent = new Intent(OpisDrinka.this, KalkulatorActivity.class);
            startActivity(intent);
            //  Intent intent = new Intent(CzynnoscActivity.this, DrinkiListView.class);
            // startActivity(intent);
            //Intent intent = new Intent(CzynnoscActivity.this, DrinkiListView2.class);
            //startActivity(intent);
        }
        else if (v == dodawanie_button) {
            Intent intent = new Intent(OpisDrinka.this, DodawanieActivity.class);
            startActivity(intent);
        }


    }
}
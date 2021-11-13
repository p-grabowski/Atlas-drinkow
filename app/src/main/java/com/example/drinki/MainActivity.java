package com.example.drinki;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity{

    ListView listadrinkow;
    EditText Szukaj;
    ArrayAdapter<String> nazwyAdapter;

    //public String nazwy[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listadrinkow = findViewById(R.id.drinki_list);
        Szukaj = findViewById(R.id.searchFilter);

        Szukaj.setText(null);
        //Odebranie bazy danych z drinkami
        Intent intent = getIntent();
        String[] nazwy = intent.getStringArrayExtra("nazwy");
        String[] opis = intent.getStringArrayExtra("opis");
        String[] skladniki = intent.getStringArrayExtra("skladniki");
        String[] instrukcja = intent.getStringArrayExtra("instrukcja");
        String[] linkiPhoto = intent.getStringArrayExtra("linki");

        //Wrzucenie nazw drinków do listview, a ona jest interaktywna
         nazwyAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, nazwy);
        listadrinkow.setAdapter(nazwyAdapter);


        Szukaj.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.this.nazwyAdapter.getFilter().filter(s);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        listadrinkow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nazwa = parent.getItemAtPosition(position).toString();
                //System.out.println("Kliknięto ID: " + id);
                //String abc = nazwy[(int) id];
                //System.out.println(abc);
                Intent przegladDrinka = new Intent(MainActivity.this, OpisDrinka.class);
                //Wysłanie wszystkich informacji o wybranym drinku
                przegladDrinka.putExtra("nazwa", nazwy[(int)id]);
                przegladDrinka.putExtra("opis", opis[(int)id]);
                przegladDrinka.putExtra("skladniki", skladniki[(int)id]);
                przegladDrinka.putExtra("instrukcja", instrukcja[(int)id]);
                przegladDrinka.putExtra("linkPhoto", linkiPhoto[(int)id]);
                startActivity(przegladDrinka);
                //Toast.makeText(getApplicationContext(), "Clicked: " + nazwa, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
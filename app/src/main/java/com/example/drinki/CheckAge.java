package com.example.drinki;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class CheckAge extends AppCompatActivity implements View.OnClickListener {

    Button tak_buttoon;
    Button nie_button;
    public String[] nazwy, opis, skladniki, metoda_Przyzadzenia, linkiPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_age);

        tak_buttoon =(Button)findViewById(R.id.tak_but);
        tak_buttoon.setOnClickListener(this);
        nie_button=(Button) findViewById(R.id.nie_but);
        nie_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == tak_buttoon){
           // Intent lista = new Intent(CheckAge.this, MainActivity.class);


            Thread thread= new Thread(new Runnable() {
                @Override
                public void run() {
                    //System.out.println("Odpalony run !!!!!!!!!!!!!!");
                    //try{
                    URL koktajle = null;
                    try {
                    //    koktajle = new URL("https://atlas-drinkow.vercel.app/");
                        koktajle = new URL("https://atlasdrinkow-9348.restdb.io/rest/drinki");
                    } catch (MalformedURLException e) {
                        System.out.println("Blad jest tutaj: " + e.toString() + "    !!!!!!!!!!!!!");
                        e.printStackTrace();
                    }
                    System.out.println("URL przyjety !!!!!!!!!!!");
                    HttpsURLConnection myConnection = null;
/*                    try {


                        System.out.println("Kod odpowiedzi: " + myConnection.getResponseCode() + "     !!!!!!!!!!!!!!");

                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("EXCEPTION CONNECTION     !!!!!!!!!!!!!!");
                    }

                    //System.out.println("DANE DO BAZY PRZYJETE !!!!!!!!!");
*/
                    try {
                        myConnection = (HttpsURLConnection) koktajle.openConnection();
                        myConnection.setRequestProperty("User-Agent", "my-restdb-app");
                        myConnection.setRequestProperty("Accept", "application/json");
                        myConnection.setRequestProperty("x-apikey", "6171abab8597142da17458d7");
                        if(myConnection.getResponseCode() == 200)
                        {
//                                            //Toast.makeText(MainActivity.this, "Succesful connection !!!", Toast.LENGTH_SHORT);
//                                            InputStream responseBOdy = myConnection.getInputStream();
//                                            InputStreamReader odpowiedzBazy = new InputStreamReader(responseBOdy, "UTF-8");
//                                            JsonReader jsonZBazy = new JsonReader(odpowiedzBazy);
//                                            //System.out.println(jsonZBazy);
//                                            //System.out.println(jsonZBazy.toString());
//                                            jsonZBazy.beginArray();
//                                            jsonZBazy.beginObject();
//
//                                            while(jsonZBazy.hasNext()){
//                                                Object key = jsonZBazy.nextName();
//                                                //System.out.println(jsonZBazy.nextString().toString());
//                                            }
//                                            jsonZBazy.close();
//                                            myConnection.disconnect();
                            InputStream in = new BufferedInputStream(myConnection.getInputStream());
                            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                            String line="";
                            StringBuffer buffer = new StringBuffer();
                            while ((line = reader.readLine())!=null){
                                buffer.append(line);
                            }

                            //System.out.println("Baza odeslala: " + buffer.toString());
                            JSONArray jsonArray = new JSONArray(buffer.toString());
                            nazwy = new String[jsonArray.length()];
                            opis = new String[jsonArray.length()];
                            skladniki = new String[jsonArray.length()];
                            metoda_Przyzadzenia = new String[jsonArray.length()];
                            linkiPhoto = new String[jsonArray.length()];
                            DrinksContener drinksContener;
                            for(int i = 0; i< jsonArray.length(); i++)
                            {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String name = jsonObject.getString("name");
                                nazwy[i] = name;
                                String description = jsonObject.getString("description");
                                opis[i] = description;
                                String ingredients = jsonObject.getString("ingredients");
                                skladniki[i] = ingredients;
                                String instrukcja = jsonObject.getString("instructions");
                                metoda_Przyzadzenia[i] = instrukcja;
                                String adresZdjecia = jsonObject.getString("imageUrl");
                                linkiPhoto[i] = adresZdjecia;


                                //System.out.println("Nazwa: " + name + "\nOpis: " + description + "\nSkladniki: " + ingredients + "\nInstrukcja: \n" + instrukcja + "\n\n");
                            }
                            //System.out.println(nazwy[0] + "     " + nazwy[1]);
                            Intent lista = new Intent(CheckAge.this,MainActivity.class);
                            lista.putExtra("nazwy", nazwy);
                            lista.putExtra("opis", opis);
                            lista.putExtra("skladniki", skladniki);
                            lista.putExtra("instrukcja", metoda_Przyzadzenia);
                            lista.putExtra("linki",linkiPhoto);
                            startActivity(lista);

                        }
                        else
                        {
                            //Toast.makeText(MainActivity.this, "Something wrong !!!", Toast.LENGTH_SHORT);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }
        else
            Toast.makeText(CheckAge.this, "Yoe are too young", Toast.LENGTH_SHORT).show();
    }
}
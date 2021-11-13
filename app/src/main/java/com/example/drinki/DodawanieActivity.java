package com.example.drinki;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

public class DodawanieActivity extends AppCompatActivity {

    Button dodaj_button;
    EditText przepis_view;
    Button usun_button;
    EditText jak_zrobic_view;
    EditText Nazwa_view;
    EditText Opis_view;
    EditText Link_view;
   public String nazwy, opis, skladniki, metoda, linkiPhoto;
  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodawanie);
        Intent intent = getIntent();

        dodaj_button =(Button)findViewById(R.id.dodaj_but);
        //dodaj_button.setOnClickListener(this);
        usun_button =(Button)findViewById(R.id.usun_but);
        //usun_button.setOnClickListener(this);
        przepis_view = (EditText)findViewById(R.id.przepis_textview);
        jak_zrobic_view = (EditText)findViewById(R.id.jak_zrobic_textview);
        Nazwa_view = (EditText)findViewById(R.id.Nazwa_textView);
        Opis_view = (EditText)findViewById(R.id.Opis_textView);
       Link_view = (EditText)findViewById(R.id.link_przepisView);

      StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();       //dodałem
      StrictMode.setThreadPolicy(policy);

    //@Override
    //public void onClick(View v){
     //     if (v == dodaj_button) {

      dodaj_button.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Thread thread2 = new Thread(new Runnable() {
                  @Override
                  public void run() {
                      System.out.println("Odpalony run !!!!!!!!!!!!!!");
                      try {

                          URL koktajle = null;
                          try {
                              koktajle = new URL("https://atlasdrinkow-9348.restdb.io/rest/drinki");
                          } catch (MalformedURLException e) {
                              System.out.println("Blad jest tutaj: " + e.toString() + "    !!!!!!!!!!!!!");
                              e.printStackTrace();
                          }
                          //System.out.println("URL przyjety !!!!!!!!!!!");
                          HttpsURLConnection myConnection = null;
                          try {
                              myConnection = (HttpsURLConnection) koktajle.openConnection();
                              myConnection.setRequestProperty("User-Agent", "my-restdb-app");
                              myConnection.setRequestProperty("Accept", "application/json");
                              myConnection.setRequestProperty("x-apikey", "6171abab8597142da17458d7");
                              myConnection.setRequestMethod("POST");

                              nazwy = Nazwa_view.getText().toString();
                              opis = Opis_view.getText().toString();
                              skladniki = przepis_view.getText().toString();
                              metoda = jak_zrobic_view.getText().toString();
                              linkiPhoto = Link_view.getText().toString();
                              String data = URLEncoder.encode("name", "UTF-8")
                                      + "=" + URLEncoder.encode(nazwy, "UTF-8");

                              data += "&" + URLEncoder.encode("description", "UTF-8") + "="
                                      + URLEncoder.encode(opis, "UTF-8");

                              data += "&" + URLEncoder.encode("ingredients", "UTF-8")
                                      + "=" + URLEncoder.encode(skladniki, "UTF-8");

                              data += "&" + URLEncoder.encode("instructions", "UTF-8")
                                      + "=" + URLEncoder.encode(metoda, "UTF-8");

                              data += "&" + URLEncoder.encode("imageUrl", "UTF-8")
                                      + "=" + URLEncoder.encode(linkiPhoto, "UTF-8");

                              String text = "";
                              Log.d("data", data + "");
                              BufferedReader reader = null;
                                  //if (myConnection.getResponseCode() == 200) {                // z jakiegoś powodu bez tego sprawdzenia dziala prawidlowo, prawdopodobnie baza odsyla kod z zakresu 200-299
                                      myConnection.setDoOutput(true);
                                      myConnection.setChunkedStreamingMode(0);
                                      //       OutputStream out = new BufferedOutputStream(myConnection.getOutputStream());
                                      //  writeStream(out);
                                      OutputStreamWriter wr = new OutputStreamWriter(myConnection.getOutputStream());
                                      wr.write(data);
                                      wr.flush();

                                      //Toast.makeText(DodawanieActivity.this, "Wyslano !!!", Toast.LENGTH_SHORT);
                                  Log.d("msg", "Wysłane");
                                  InputStream in = new BufferedInputStream(myConnection.getInputStream());
                                      reader = new BufferedReader(new InputStreamReader(myConnection.getInputStream()));
                                      String line = "";
                                      StringBuffer buffer = new StringBuffer();
                                      while ((line = reader.readLine()) != null) {
                                          buffer.append(line);
                                      }

                                  Log.d("msg","Baza odeslala: " + buffer.toString());
                           /* JSONArray jsonArray = new JSONArray(buffer.toString());
                            nazwy = new String[jsonArray.length()];
                            opis = new String[jsonArray.length()];
                            skladniki = new String[jsonArray.length()];
                            metoda_Przyzadzenia = new String[jsonArray.length()];
                            linkiPhoto = new String[jsonArray.length()];*/
                                      //  DrinksContener drinksContener;
                        /*    for(int i = 0; i< jsonArray.length(); i++)
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

                            }*/


                                  //} else {
                                   //   Log.d("msg", "Something wrong !!!");
                                  //}

                          } catch (IOException e) {
                              Log.d("data", e + "");

                              e.printStackTrace();
                          }
                      } catch (Exception e) {
                          e.printStackTrace();
                          Log.d("data", e + "");

                      }
                  }
              });
              thread2.start();

          }
      });

         // } else {
          //    Toast.makeText(DodawanieActivity.this, "Nie wyslano !!!", Toast.LENGTH_SHORT);

          //}
    }
}

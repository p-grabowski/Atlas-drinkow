package com.example.drinki;

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

public class ConnectDB {

        public void run() {
            System.out.println("Odpalony run !!!!!!!!!!!!!!");

            URL koktajle = null;
            try {
                koktajle = new URL("https://atlasdrinkow-9348.restdb.io/rest/drinki");
            } catch (MalformedURLException e) {
                System.out.println("Blad jest tutaj: " + e.toString() + "    !!!!!!!!!!!!!");
                e.printStackTrace();
            }
            System.out.println("URL przyjety !!!!!!!!!!!");
            HttpsURLConnection myConnection = null;
            try {
                myConnection = (HttpsURLConnection) koktajle.openConnection();
                myConnection.setRequestProperty("User-Agent", "my-restdb-app");
                myConnection.setRequestProperty("Accept", "application/json");
                myConnection.setRequestProperty("x-apikey", "6171abab8597142da17458d7");


                System.out.println("Kod odpowiedzi: " + myConnection.getResponseCode() + "     !!!!!!!!!!!!!!");

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("EXCEPTION CONNECTION     !!!!!!!!!!!!!!");
            }

            //System.out.println("DANE DO BAZY PRZYJETE !!!!!!!!!");

            try {
                if(myConnection.getResponseCode() == 200)
                {
                    InputStream in = new BufferedInputStream(myConnection.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                    String line="";
                    StringBuffer buffer = new StringBuffer();
                    while ((line = reader.readLine())!=null){
                        buffer.append(line);
                    }
                    //System.out.println("Baza odeslala: " + buffer.toString());
                    JSONArray jsonArray = new JSONArray(buffer.toString());
                    for(int i = 0; i< jsonArray.length(); i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        String opis = jsonObject.getString("description");
                        String skladniki = jsonObject.getString("ingredients");
                        String instrukcja = jsonObject.getString("instructions");
                        String adresZdjecia = jsonObject.getString("imageUrl");

                        System.out.println("Nazwa: " + name + "\nOpis: " + opis + "\nSkladniki: " + skladniki + "\nInstrukcja: \n" + instrukcja + "\n\n");
                    }

                    //

                    //

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
            // }catch (Exception e){
            //    e.printStackTrace();
            // }

        }
}

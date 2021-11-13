package com.example.drinki;

import android.content.Context;
import android.widget.ArrayAdapter;

public class DrinksContener{
    private Context context;
    private String[] mNazwa;
    private String[] mOpis;

    public DrinksContener(Context context, int resource, String[] name, String[] opis){
        this.context = context;
        this.mNazwa = name;
       this. mOpis = opis;
    }


    public String[] getmNazwa() {
        return mNazwa;
    }
    public String[] getmOpis(){
        return mOpis;
    }

    public Object getItem(int position){
        String drinki = mNazwa[position];
        String skladniki = mOpis[position];

        return String.format("Drinki: "+drinki+ "\n" + skladniki );
    }
}

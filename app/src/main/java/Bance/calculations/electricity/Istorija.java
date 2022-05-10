package Bance.calculations.electricity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class Istorija extends AppCompatActivity {

    ListView l;
    ArrayList<String> forListView = new ArrayList<String>();
//    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_istorija);

        //Nuskaitymas is Preferences
        SharedPreferences preflist = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        Set<String> forListView2 = preflist.getStringSet("tut", null);
        if(forListView2 == null){
            forListView.add("empty");
        }

        //konvertuojam kad ListView nuskaitytu
        try {
            FileSaver fileSaver = new FileSaver( this);
            forListView = new ArrayList(fileSaver.readFile());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("Kairp atrodo----------", forListView.toString());

        //pasiemam kintamuosius
        Intent intent = getIntent();
        String intentSuma = intent.getStringExtra("suma");
        String intentKaina = intent.getStringExtra("kaina");
        String intentSuvartotaKW = intent.getStringExtra("suvartotaKW");
        String data = intent.getStringExtra("datenow");
        boolean tikIstorija = intent.getBooleanExtra("tikIstorija", false);

        //sortinam pries atvaizuodami ir reversinam kad nuo naujausiu rodytu
        Collections.sort(forListView, new Comparator<String>() {
            @Override
            public int compare(String object1, String object2) {
                return object1.compareTo(object2);
            }
        });
        Collections.reverse(forListView);

        if(tikIstorija){

            l = findViewById(R.id.list);
            ArrayAdapter<String> arr;
            arr = new ArrayAdapter<String>(
                    this,
                    R.layout.support_simple_spinner_dropdown_item,
                    forListView);
            l.setAdapter(arr);
        } else {
            //forListView.add(data+" SuvartotaKW: "+intentSuvartotaKW+" Kaina: "+intentKaina+" "+intentSuma);

            TextView antraste = findViewById(R.id.istorija);
            antraste.setText("Si menesi "+intentSuma);

            l = findViewById(R.id.list);
            ArrayAdapter<String> arr;
            arr = new ArrayAdapter<String>(
                    this,
                    R.layout.support_simple_spinner_dropdown_item,
                    forListView);
            l.setAdapter(arr);

//            //Irasymas
//            SharedPreferences.Editor editor = preflist.edit();
//            Set<String> hashSet = new HashSet<>(forListView);
//            hashSet.addAll(forListView);
//            //= new Set<String>(forListView);
//            editor.putStringSet("tut",hashSet);
//            editor.commit();
        }


    }
}
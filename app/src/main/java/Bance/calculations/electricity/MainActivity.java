package Bance.calculations.electricity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.biometrics.BiometricManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Nuskaitymas is Preferences
        SharedPreferences pref = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        float suma=pref.getFloat("suma",0);
        float kaina=pref.getFloat("kaina",0);
        int dabartiniaiRodmenys= pref.getInt("dabartiniaiRodmenys",0);

        //
        if(suma>=0){
            ((TextView)findViewById(R.id.suma)).setText(suma+"");
            ((TextView)findViewById(R.id.kaina)).setText(kaina+"");
            ((EditText)findViewById(R.id.praejusioMenRodmenys)).setText(dabartiniaiRodmenys+"");
        }

        Button skaiciuoti = (Button) findViewById(R.id.skaiciuoti);
        skaiciuoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText buveLaukas = findViewById(R.id.praejusioMenRodmenys);
                int praejusioMenRodmenys = 0;
                try {
                    praejusioMenRodmenys = Integer.parseInt(buveLaukas.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(view.getContext(), " Neivestas skaicius " +e, Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

                EditText dabartiniaiLauklas = findViewById(R.id.dabartiniaiRodmenys);
                int dabartiniaiRodmenys = 0;
                try {
                    dabartiniaiRodmenys = Integer.parseInt(dabartiniaiLauklas.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(view.getContext(), " Neivestas skaicius " +e, Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

                TextView skyrtumasLaukas = findViewById(R.id.suvartotaKW);

                EditText kainaLaukas = findViewById(R.id.kaina);
                //int kaina = Integer.parseInt(kainaLaukas.getText().toString());
                float kaina =0;
                try {
                    kaina = Float.parseFloat(kainaLaukas.getText().toString());
                } catch(NumberFormatException e){
                    Toast.makeText(view.getContext(), " Neteisingas skaicius " +e, Toast.LENGTH_LONG).show();
                };

                TextView sumaLaukas = findViewById(R.id.suma);

                //double kanaPerKableli = kaina;
                float suma = 0;
                int skyrtumas =0;

                try {
                    skyrtumas = dabartiniaiRodmenys-praejusioMenRodmenys;
                    suma = skyrtumas*kaina;
                    } catch (Exception e){
                    Toast.makeText(view.getContext(), "Nepaejo: "+e, Toast.LENGTH_LONG).show();
                };

                skyrtumasLaukas.setText(""+skyrtumas);

                sumaLaukas.setText("Suma viso: "+suma+" nok");

                //Irasymas
                SharedPreferences pref = getSharedPreferences("MyPrefs", MODE_PRIVATE);

                SharedPreferences.Editor editor = pref.edit();
                editor.putFloat("suma", suma);
                editor.putFloat("kaina", kaina);
                editor.putInt("dabartiniaiRodmenys",dabartiniaiRodmenys);
                editor.commit();


            }
        });

        //----- darau su ne depricated funkcija
        //issaugojimo langas

        Button issaugoti = (Button) findViewById(R.id.issaugoti);
        issaugoti.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //issaugojimo data
                EditText buveLaukas = findViewById(R.id.praejusioMenRodmenys);
                int praejusioMenRodmenys = 0;
                try {
                    praejusioMenRodmenys = Integer.parseInt(buveLaukas.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(v.getContext(), " Neivestas skaicius " +e, Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

                EditText dabartiniaiLauklas = findViewById(R.id.dabartiniaiRodmenys);
                int dabartiniaiRodmenys = 0;
                try {
                    dabartiniaiRodmenys = Integer.parseInt(dabartiniaiLauklas.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(v.getContext(), " Neivestas skaicius " +e, Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
                LocalDateTime datenow = LocalDateTime.now();

                TextView suvartotaKWLaukas = findViewById(R.id.suvartotaKW);
                String suvartotaKW = suvartotaKWLaukas.getText().toString();

                EditText kainaLaukas = findViewById(R.id.kaina);
                //double kaina = Double.parseDouble(kainaLaukas.getText().toString());
                String kaina = kainaLaukas.getText().toString();

                TextView sumaLaukas = findViewById(R.id.suma);
                String suma = sumaLaukas.getText().toString();

                boolean tikIstorija = false;

                Intent intent = new Intent(v.getContext(), Istorija.class);
                //perduodam parametrus
                intent.putExtra("suvartotaKW",suvartotaKW);
                intent.putExtra("kaina", kaina);
                intent.putExtra("suma",suma);
                intent.putExtra("datenow", dtf.format(datenow).toString());
                intent.putExtra("tikIstorija", tikIstorija);
                startActivity(intent);

                //Irasimas i faila

                ArrayList<String> arrList = new ArrayList();
                arrList.add(dtf.format(datenow).toString());
                arrList.add(String.valueOf(suma));
                arrList.add(String.valueOf(kaina));
                arrList.add(String.valueOf(suvartotaKW));
                arrList.add(String.valueOf(praejusioMenRodmenys));
                arrList.add(String.valueOf(dabartiniaiRodmenys));

                File file = new File(getFilesDir(),"record_of_calculations.json");
                try {
                    FileSaver fileSaver = new FileSaver("irasas", arrList, file);
                    fileSaver.updateHistory();
                    //fileSaver.clearFile();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        //----- darau su ne depricated funkcija
        Button istorija =(Button) findViewById(R.id.istorija);
        istorija.setOnClickListener(new View.OnClickListener(){
//tik istorija
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), Istorija.class);
                intent.putExtra("tikIstorija", true);

                File file = new File(getFilesDir(),"record_of_calculations.json");
                try {
                    FileSaver fileSaver = new FileSaver(file);
                    fileSaver.readFromFile();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                startActivity(intent);
            }
        });




    }
}

/*
praejusioMenRodmenys
dabartiniaiRodmenys
suvartotaKW
kaina
skaiciuoti
suma
* */
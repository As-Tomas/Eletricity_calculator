package Bance.calculations.electricity;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileSaver {
    private String nameObjects;
    private JSONObject jasonObjToSave;
    private ArrayList arrToSave;
    private JSONArray arrfromFile;
    private Context context;

    public FileSaver( Context context) {
        this.arrfromFile = new JSONArray();
        this.context = context;
    }

    public FileSaver(String nameObjects, ArrayList arrToSave, Context context) throws JSONException {
        this.nameObjects = nameObjects;
        this.arrToSave = arrToSave;
        this.jasonObjToSave = convertToJasonObj(nameObjects,arrToSave);
        this.arrfromFile = new JSONArray();
        this.context = context;

    }

    public JSONObject convertToJasonObj(String nameObject, ArrayList arr) throws JSONException {

        JSONObject jObj = new JSONObject();

        jObj.put("data",arr.get(0));
        jObj.put("suma",arr.get(1));
        jObj.put("kaina", arr.get(2));
        jObj.put("suvartotaKW", arr.get(3));
        jObj.put("praejusioMenRodmenys", arr.get(4));
        jObj.put("dabartinioMenRodmenys", arr.get(5));

        JSONObject irasas = new JSONObject();

        irasas.put("irasas", jObj);

//        JSONArray jArr = new JSONArray();
//        jArr.put(irasas);
//        Log.i("jArr", jArr.toString());


//
//        JSONArray jArr = new JSONArray();
//
//        for(Object str : arr){
//            JSONObject jObj = new JSONObject();
//
//            JSONObject values = new JSONObject();
//            values.put("irasas", str);
//            //Log.i("values", str );
//
//            jObj.put(nameObject, values);
//
//            jArr.put(jObj);
//            Log.i("jArr", jArr.toString());
//        }
        return irasas;
    }

    public void readFromFile() throws JSONException {

//        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString();
//        //String root = Environment.getExternalStorageDirectory().toString();
//        File myDirs = new File(root ); // + "/Elektros_Skaiciavimai"
//        System.err.println("Permision is give******************************** "+myDirs);
//        if (!myDirs.exists()) {
//            myDirs.mkdirs();
//        }
//        neveike nes neduoda leidimo File files = new File(root  + "/Elektros_Skaiciavimai","records_of_calculations.txt");

        //sitas veikeFile files = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).toString() + "/Elektros_Skaiciavimai/","records_of_calculations.txt");

        String path = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).toString() + "/Elektros_Skaiciavimai"; //nepamirs patikrint katalogus jai veiks

        File files = new File(path ,"records_of_calculations.txt");
        String content=null;
        if(files.exists())
        {
            FileReader reader = null;
            try {
                reader = new FileReader(files);
                char[] chars = new char[(int) files.length()];
                reader.read(chars);
                content = new String(chars);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            System.out.println("CANT READ FILE or no file ****************************************************************");
            //create new one for first time
            try {
                if (!files.exists()) {
                    files.createNewFile();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i("SaveToFile","New file created");
        }
        System.out.println("----------------------------------------------------Failas nuskaitytas--------------------------------------------------------------------------");
        System.out.println(content);

        if(content != null){
            JSONArray temp = new JSONArray(content);

            for (int i=0; i< temp.length(); i++) {
                JSONObject jObj = temp.getJSONObject(i);
                //JSONObject irasasJObj = jObj.getJSONObject("irasas");
                arrfromFile.put(jObj);
                Log.i("Read From File","Success !");

            }
            System.out.println("kaip atrodo "+arrfromFile );
        }
    };

    public void saveToFile(JSONArray artosave) throws JSONException {

//        failas saugomas /storage/emulated/0/Android/data/Bance.calculations.electricity/files/Documents/Elektros_Skaiciavimai

        System.out.println("*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/ saveToFile*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/ " );
        if(isExternalStorageWritable()){
            try {

                //first option
//            File file = new File(getFilesDir(), "Studentu_sarasas.json");
//            file.createNewFile();
//            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
//            writer.write(jArr.toString());
//            writer.close();
//            Log.i("Save","Success");


                String state = Environment.getExternalStorageState();
                if (Environment.MEDIA_MOUNTED.equals(state)) {
                    System.err.println("Permision is give to read******************************** "+state);
                }


//neveikia sitas stulpelis nes pirma skaito, bet is main activity manau veiktu
//                String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString();
//                //String root = Environment.getExternalStorageDirectory().toString();
//                File myDirs = new File(root ); // + "/Elektros_Skaiciavimai"
//                System.err.println("Permision is give******************************** "+myDirs);
//                if (!myDirs.exists()) {
//                    myDirs.mkdirs();
//                }
//                sitas neveike nes nera leidimo File files = new File(root  + "/Elektros_Skaiciavimai","records_of_calculations.txt");

                //sitas veike File files = new File(context.getFilesDir()  + "/Elektros_Skaiciavimai","records_of_calculations.txt");

                String path = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).toString() + "/Elektros_Skaiciavimai";
                File myDir = new File(path);
                System.out.println("Creating file here******************************** "+myDir);
                if (!myDir.exists()) {
                    myDir.mkdirs();
                    System.err.println("Sukure kataloga ******************************** "+myDir);
                }

                File files = new File(path,"records_of_calculations.txt");
                //second option
                //File file = new File(getFilesDir(), fileName);
                FileWriter fw;
                fw = new FileWriter(files);
                fw.write(String.valueOf(artosave));
                //fw.write(adapteris);
                fw.close();
                Log.i("SaveToFile","Success 2");

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println(" No Permision to write ************************************************* isExternalStorageWritable()");
        }
    }

    /* Check do we can read/write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public void updateHistory() throws JSONException {
        try {
            readFromFile();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        arrfromFile.put(jasonObjToSave);
        saveToFile(arrfromFile);

    }

    public ArrayList readFile() throws JSONException {
        readFromFile();
        ArrayList<String> listOfStrings = new ArrayList<>();

        for(int i=0; i<arrfromFile.length(); i++){
            JSONObject objektas = arrfromFile.getJSONObject(i);

            JSONObject irasas = objektas.getJSONObject("irasas");

            listOfStrings.add(irasas.getString("data")+ "   " +irasas.getString("suma")+
                    ", kaina: "+irasas.getString("kaina")+", suvartotaKW: "+irasas.getString("suvartotaKW")+
                    " praejusio menesio rodmenys: "+irasas.getString("praejusioMenRodmenys"));
        }

        return listOfStrings;
    }

    public void clearFile() throws JSONException {
        JSONArray empty = new JSONArray();
        saveToFile(empty);
    }


}

package Bance.calculations.electricity;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
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
    private File file;

    public FileSaver(File file) {
        this.file = file;
        this.arrfromFile = new JSONArray();
    }

    public FileSaver(String nameObjects, ArrayList arrToSave, File file) throws JSONException {
        this.nameObjects = nameObjects;
        this.arrToSave = arrToSave;
        this.jasonObjToSave = convertToJasonObj(nameObjects,arrToSave);
        this.arrfromFile = new JSONArray();
        this.file = file;
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

        String content=null;
        if(file.exists())
        {
            FileReader reader = null;
            try {
                reader = new FileReader(file);
                char[] chars = new char[(int) file.length()];
                reader.read(chars);
                content = new String(chars);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("----------------------------------------------------Failas nuskaitytas--------------------------------------------------------------------------");
        System.out.println(content);


        if(content != null){
            JSONArray temp = new JSONArray(content);

            for (int i=0; i< temp.length(); i++) {
                JSONObject jObj = temp.getJSONObject(i);
                //JSONObject irasasJObj = jObj.getJSONObject("irasas");
                arrfromFile.put(jObj);
                Log.i("readFromFile","Success !");

            }
            System.out.println("kaip atrodo "+arrfromFile );
        }
    };



    public void saveToFile(JSONArray artosave) throws JSONException {

        if(isExternalStorageWritable()){
            try {
                //first option
//            File file = new File(getFilesDir(), "Studentu_sarasas.json");
//            file.createNewFile();
//            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
//            writer.write(jArr.toString());
//            writer.close();
//            Log.i("Save","Success");

                //second option
                //File file = new File(getFilesDir(), fileName);
                FileWriter fw;
                fw = new FileWriter(file);
                fw.write(String.valueOf(artosave));
                //fw.write(adapteris);
                fw.close();
                Log.i("SaveToFile","Success 2");

            } catch (IOException e) {
                e.printStackTrace();
            }
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
        readFromFile();
        arrfromFile.put(jasonObjToSave);
        saveToFile(arrfromFile);

    }

    public void clearFile() throws JSONException {
        JSONArray empty = new JSONArray();
        saveToFile(empty);
    }


}

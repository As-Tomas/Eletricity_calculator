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
    private String nameObject;
    private JSONObject jsonObj;
    private JSONArray jasonArr;
    private ArrayList arrayList;

    public FileSaver() {
    }

    public FileSaver(String nameObject, ArrayList arrToSave) throws JSONException {
        this.nameObject = nameObject;
        this.arrayList = arrToSave;
        this.jasonArr = convertToJasonArr(nameObject,arrToSave);
    }

    public JSONArray convertToJasonArr(String nameObject, ArrayList arr) throws JSONException {
        JSONArray jArr = new JSONArray();

        for(Object str : arr){
            JSONObject jObj = new JSONObject();

            JSONObject values = new JSONObject();
            values.put("irasas", str);
            //Log.i("values", str );

            jObj.put(nameObject, values);

            jArr.put(jObj);
            Log.i("jArr", jArr.toString());
        }
        return jArr;
    }



    public void saveToFile(JSONArray arr, String fileName) throws JSONException {

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
                File file = new File(getFilesDir(), fileName);
                FileWriter fw;
                fw = new FileWriter(file);
                fw.write(String.valueOf(arr));
                //fw.write(adapteris);
                fw.close();
                Log.i("Save","Success 2");

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


}

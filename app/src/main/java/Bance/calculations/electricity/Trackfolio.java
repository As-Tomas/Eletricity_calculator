package Bance.calculations.electricity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Trackfolio extends Activity implements View.OnClickListener {
    /**
     * Called when the activity is first created.
     * Norejau kad isvalytu EditText laukus kai pasirenki ivedineti bet taip ir nebaigiau, nesuveike sitas
     */
    public EditText editText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.praejusioMenRodmenys);
        editText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        editText.getText().clear(); //or you can use editText.setText("");
    }
}

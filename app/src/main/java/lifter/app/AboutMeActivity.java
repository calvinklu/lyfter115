package lifter.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class AboutMeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        setTitle("About Me");

        //Spinner for Fitness Level
        Spinner spinner1 = (Spinner)findViewById(R.id.Fitnessspinner);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.fitnesslevel_array, android.R.layout.simple_spinner_item); //Creates ArrayAdapter using string array and default spinner layout
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //Specify layout to use when the list of choices appear
        spinner1.setAdapter(adapter1); //Apply adapter to spinner

        //Spinner for Experience
        Spinner spinner2 = (Spinner)findViewById(R.id.Experiencespinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.experience_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
    }
}

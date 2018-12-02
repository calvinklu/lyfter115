package lifter.app;

import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.content.res.Resources;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;


public class UpperBody extends AppCompatActivity {

    ListView upperListView;
    String[] upperexercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upper_body);

        Resources res = getResources();
        upperListView = (ListView) findViewById(R.id.upperlistview);
        upperexercises = res.getStringArray(R.array.upperexercises);

        UpperAdapter adapter = new UpperAdapter(this, upperexercises);
        upperListView.setAdapter(adapter);

        TextView exerciseTitle = (TextView) findViewById(R.id.upperexer);
        exerciseTitle.setPaintFlags(exerciseTitle.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        ImageButton infobtn = (ImageButton) findViewById(R.id.upperinfobtn);

        infobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpperBody.this, UpperPop.class));
            }
        });
    }
}

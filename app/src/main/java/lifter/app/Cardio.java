package lifter.app;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class Cardio extends AppCompatActivity {

    ListView cardioListView;
    String[] cardio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardio);

        Resources res = getResources();
        cardioListView = (ListView) findViewById(R.id.cardiolistview);
        cardio = res.getStringArray(R.array.cardio);

        CardioAdapter adapter = new CardioAdapter(this, cardio);
        cardioListView.setAdapter(adapter);

        TextView exerciseTitle = (TextView) findViewById(R.id.upperexer);
        exerciseTitle.setPaintFlags(exerciseTitle.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        ImageButton infobtn = (ImageButton) findViewById(R.id.cardioinfobtn);

        infobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Cardio.this, CardioPop.class));
            }
        });
    }
}
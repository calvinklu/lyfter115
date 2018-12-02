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

public class LowerBody extends AppCompatActivity {

    ListView lowerListView;
    String[] lowerexercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lower_body);

        Resources res = getResources();
        lowerListView = (ListView) findViewById(R.id.lowerlistview);
        lowerexercises = res.getStringArray(R.array.lowerexercises);

        LowerAdapter adapter = new LowerAdapter(this, lowerexercises);
        lowerListView.setAdapter(adapter);

        TextView exerciseTitle = (TextView) findViewById(R.id.upperexer);
        exerciseTitle.setPaintFlags(exerciseTitle.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        ImageButton infobtn = (ImageButton) findViewById(R.id.lowerinfobtn);

        infobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LowerBody.this, LowerPop.class));
            }
        });
    }
}
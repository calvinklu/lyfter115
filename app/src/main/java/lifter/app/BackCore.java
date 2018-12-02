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

public class BackCore extends AppCompatActivity {

    ListView backcoreListView;
    String[] backcore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_core);

        Resources res = getResources();
        backcoreListView = (ListView) findViewById(R.id.backcorelistview);
        backcore = res.getStringArray(R.array.backcore);

        BackCoreAdapter adapter = new BackCoreAdapter(this, backcore);
        backcoreListView.setAdapter(adapter);

        TextView exerciseTitle = (TextView) findViewById(R.id.upperexer);
        exerciseTitle.setPaintFlags(exerciseTitle.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        ImageButton infobtn = (ImageButton) findViewById(R.id.backcoreinfobtn);

        infobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BackCore.this, BackCorePop.class));
            }
        });
    }
}

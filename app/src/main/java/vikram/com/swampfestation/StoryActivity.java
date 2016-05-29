package vikram.com.swampfestation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class StoryActivity extends AppCompatActivity {
    TextView txtView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        txtView = (TextView) findViewById(R.id.story);
        txtView.setText("First day as a "+ Data.person);
    }
}

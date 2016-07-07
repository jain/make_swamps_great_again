package vikram.com.swampfestation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by vikram on 7/6/16.
 */
public class PlayerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player);
        TextView name = (TextView) findViewById(R.id.name);
        name.setText(Constants.pl.name);
        TextView desc = (TextView) findViewById(R.id.description);
        desc.setText(Constants.pl.desc);
        TextView happ = (TextView) findViewById(R.id.happiness);
        happ.setText("Happiness: " + Constants.pl.hap);
        ImageView photo = (ImageView) findViewById(R.id.img);
        try {
            String url = Constants.pl.imgUrl;
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(url, photo);
        } catch (Exception e){
            //Toast.makeText(this, "invalid url", Toast.LENGTH_LONG).show();
        }
        ListView listView = (ListView) findViewById(R.id.messages);
        ArrayAdapter linksAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Constants.pl.msgs);
        listView.setAdapter(linksAdapter);
    }
}

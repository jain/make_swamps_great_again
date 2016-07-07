package vikram.com.swampfestation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by vikram on 7/6/16.
 */
public class VarActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.var);
        ArrayList<String> lst = new ArrayList<String>();
        for (Map.Entry<String, String> entry: Constants.stringVar.entrySet()){
            lst.add(entry.getKey() + " : " + entry.getValue());
        }
        for (Map.Entry<String, Integer> entry: Constants.intVar.entrySet()){
            lst.add(entry.getKey() + " : " + entry.getValue());
        }
        ListView listView = (ListView) findViewById(R.id.messages);
        ArrayAdapter linksAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lst);
        listView.setAdapter(linksAdapter);
    }
}

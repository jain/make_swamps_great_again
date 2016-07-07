package vikram.com.swampfestation;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by vikram on 7/6/16.
 */
public class ButClickListener implements AdapterView.OnItemClickListener {
    private GeneralActivity act;
    public ButClickListener(GeneralActivity act){
        super();
        this.act = act;
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
        Toast.makeText(act, ((AppCompatTextView) view).getText().toString() +" pressed", Toast.LENGTH_SHORT).show();
        Screen.IGButton but = act.buts.get(pos);
        act.fr.onEnd(but);

        String sc = but.next;

        try {
            Constants.sc = Constants.screens.get(sc);
            act.setupListView();
            act.setupFragment();
        } catch (Exception e){
            Toast.makeText(act, "no such screen "+ sc, Toast.LENGTH_LONG).show();
        }
    }
}

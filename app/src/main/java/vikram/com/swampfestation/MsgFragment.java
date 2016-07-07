package vikram.com.swampfestation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by vikram on 7/6/16.
 */
public class MsgFragment extends FragmentInterface {
    private View inflatedView = null;
    private GeneralActivity act;

    public void setup(GeneralActivity act) {
        this.act = act;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //Inflate the msg_layout for this fragment
        this.inflatedView = inflater.inflate(R.layout.msg_layout, container, false);
        RecyclerView messages = (RecyclerView) inflatedView.findViewById(R.id.messages);
        messages.setLayoutManager(new LinearLayoutManager(getContext()));
        ArrayList<Screen.Msg> list = new ArrayList<Screen.Msg>();
        for (int i = 0; i < Constants.sc.parsedMsgs.size(); i++) {
            Screen.Msg but = Constants.sc.parsedMsgs.get(i);
            if (!Constants.condition(act, but.conditions)) {
                list.add(but);
            }
        }
        MessageAdapter adapter = new MessageAdapter(list);
        messages.setAdapter(adapter);
        return this.inflatedView;


    }


    @Override
    public void onEnd(Screen.IGButton but) {
        for (String extra : but.extra) {
            if (extra.contains("(")) {
                String key = extra.substring(0, extra.indexOf("(")).trim();
                String value = extra.substring(1 + extra.indexOf("("), extra.lastIndexOf(")")).trim();
                if (Constants.intVar.containsKey(key)) {
                    try {
                        int increment = Integer.parseInt(value);
                        Constants.intVar.put(key, Constants.intVar.get(key) + increment);
                    } catch (Exception e) {
                        Toast.makeText(act, "invalid increment of int var", Toast.LENGTH_LONG).show();
                    }
                } else if (Constants.ch.hapMap.containsKey(key)) {
                    try {
                        int increment = Integer.parseInt(value);
                        Characters.Player pl = Constants.ch.hapMap.get(key);
                        pl.hap += increment;
                    } catch (Exception e) {
                        Toast.makeText(act, "invalid increment of int var", Toast.LENGTH_LONG).show();
                    }
                } else if (Constants.ch.varMap.containsKey(key)) {
                    Characters.Player pl = Constants.ch.varMap.get(key);
                    pl.msgs.add(value);
                }
            }
        }
    }
}

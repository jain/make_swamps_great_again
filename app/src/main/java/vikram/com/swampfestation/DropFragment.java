package vikram.com.swampfestation;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import vikram.com.swampfestation.helper.OnStartDragListener;
import vikram.com.swampfestation.helper.SimpleItemTouchHelperCallback;

/**
 * Created by vikram on 7/6/16.
 */
public class DropFragment extends FragmentInterface implements OnStartDragListener {
    private View inflatedView = null;
    private ItemTouchHelper mItemTouchHelper;
    private GeneralActivity act;
    //private HashMap<String, String> info;
    private DragAdapter adapter;
    private RecyclerView dragDrop;

    public void setup(GeneralActivity act) {
        this.act = act;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //Inflate the msg_layout for this fragment
        //info = new HashMap<String, String>();
        this.inflatedView = inflater.inflate(R.layout.view2, container, false);

        LinearLayout layout = (LinearLayout) inflatedView.findViewById(R.id.view2_layout);

        dragDrop = new RecyclerView(layout.getContext());
        layout.addView(dragDrop);
        dragDrop.setLayoutManager(new LinearLayoutManager(inflatedView.getContext()));
        /*ArrayList<String> list = new ArrayList<>();

        String[] vals = Constants.sc.msgs.get(0).split("[\\|]");
        for (int j = 0; j < vals.length; j++) {
            String extra = vals[j].trim();
            if (extra.contains("(") && extra.contains(")")) {
                String key = extra.substring(0, extra.indexOf("("));
                String value = extra.substring(1 + extra.indexOf("("), extra.lastIndexOf(")"));
                list.add(key);
                info.put(key, value);
            }
        }*/
        ArrayList<Screen.Msg> list = new ArrayList<Screen.Msg>();
        for (int i = 0; i < Constants.sc.parsedMsgs.size(); i++) {
            Screen.Msg but = Constants.sc.parsedMsgs.get(i);
            if (!Constants.condition(act, but.conditions)) {
                list.add(but);
            }
        }
        adapter = new DragAdapter(list, this);
        dragDrop.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(dragDrop);

        return this.inflatedView;


    }

    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onEnd(Screen.IGButton but) {
        ArrayList<Screen.Msg> data = adapter.data;
        for (int i = 0; i < data.size(); i++) {
            int pos = i+1;
            for (String extra : data.get(i).extra) {
                if (extra.contains("(")) {
                    String key = extra.substring(0, extra.indexOf("(")).trim();
                    String value = extra.substring(1 + extra.indexOf("("), extra.lastIndexOf(")")).trim();
                    if (Constants.intVar.containsKey(key)) {
                        try {
                            int increment = Integer.parseInt(value);
                            Constants.intVar.put(key, Constants.intVar.get(key) + (pos*increment));
                        } catch (Exception e) {
                            Toast.makeText(act, "invalid increment of int var", Toast.LENGTH_LONG).show();
                        }
                    } else if (Constants.ch.hapMap.containsKey(key)) {
                        try {
                            int increment = Integer.parseInt(value);
                            Characters.Player pl = Constants.ch.hapMap.get(key);
                            pl.hap += (pos*increment);
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
        /*for (int i = 0; i < data.size(); i++) {
            String value = info.get(data.get(i));
            int pos = i + 1;
            String[] values = value.split(",");
            int j = 0;
            try {
                String var = values[j].trim();
                int amt = Integer.parseInt(values[j + 1].trim());
                j += 2;
                if (Constants.intVar.containsKey(var)) {
                    try {
                        int increment = Integer.parseInt(value);
                        Constants.intVar.put(var, Constants.intVar.get(var) + (amt * pos));
                    } catch (Exception e) {
                        Toast.makeText(act, "invalid increment of int var", Toast.LENGTH_LONG).show();
                    }
                } else if (Constants.ch.hapMap.containsKey(var)) {
                    try {
                        Characters.Player pl = Constants.ch.hapMap.get(var);
                        pl.hap += (amt * pos);
                    } catch (Exception e) {
                        Toast.makeText(act, "invalid increment of int var", Toast.LENGTH_LONG).show();
                    }
                }
            } catch (Exception e) {

            }
        }*/
    }
}

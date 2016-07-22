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
    private DragAdapter adapter;
    private RecyclerView dragDrop;

    public void setup(GeneralActivity act) {
        this.act = act;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        this.inflatedView = inflater.inflate(R.layout.view2, container, false);

        LinearLayout layout = (LinearLayout) inflatedView.findViewById(R.id.view2_layout);

        dragDrop = new RecyclerView(layout.getContext());
        layout.addView(dragDrop);
        dragDrop.setLayoutManager(new LinearLayoutManager(inflatedView.getContext()));
        final ArrayList<Screen.Msg> list = new ArrayList<Screen.Msg>();
        for (int i = 0; i < Constants.sc.parsedMsgs.size(); i++) {
            Screen.Msg but = Constants.sc.parsedMsgs.get(i);
            if (!Constants.condition(act, but.conditions)) {
                list.add(but);
            }
        }
        adapter = new DragAdapter(list, this, act);
        dragDrop.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(dragDrop);

        /*dragDrop.addOnItemTouchListener(
                new RecyclerItemClickListener(act, dragDrop ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        String description = list.get(position).desc;
                        if(!description.isEmpty()){
                            Toast.makeText(act, description, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever

                    }
                })
        );*/

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
            String var1 = data.get(i).var1;
            int increment = data.get(i).priRank.get(pos);
            if (Constants.intVar.containsKey(var1)) {
                try {
                    //int increment = Integer.parseInt(value);
                    Constants.intVar.put(var1, Constants.intVar.get(var1) + increment);
                } catch (Exception e) {
                    Toast.makeText(act, "invalid increment of int var", Toast.LENGTH_LONG).show();
                }
            } else if (Constants.ch.hapMap.containsKey(var1)) {
                try {
                    Characters.Player pl = Constants.ch.hapMap.get(var1);
                    pl.hap += increment;
                } catch (Exception e) {
                    Toast.makeText(act, "invalid increment of int var", Toast.LENGTH_LONG).show();
                }
            }
            /*else if (Constants.ch.varMap.containsKey(key)) {
                Characters.Player pl = Constants.ch.varMap.get(key);
                pl.msgs.add(value);
            }*/
            /*for (String extra : data.get(i).extra) {
                if (extra.contains("(")) {
                    String key = extra.substring(0, extra.indexOf("(")).trim();
                    String value = extra.substring(1 + extra.indexOf("("), extra.lastIndexOf(")")).trim();
                    if (Constants.intVar.containsKey(key)) {
                        try {
                            int increment = data.get(i).priRank.get(pos);
                            //int increment = Integer.parseInt(value);
                            Constants.intVar.put(key, Constants.intVar.get(key) + increment);
                        } catch (Exception e) {
                            Toast.makeText(act, "invalid increment of int var", Toast.LENGTH_LONG).show();
                        }
                    } else if (Constants.ch.hapMap.containsKey(key)) {
                        try {
                            int increment = data.get(i).priRank.get(pos);
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
            }*/
        }
    }
}

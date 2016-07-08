package vikram.com.swampfestation;

import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Collections;

import vikram.com.swampfestation.helper.ItemTouchHelperAdapter;
import vikram.com.swampfestation.helper.ItemTouchHelperViewHolder;
import vikram.com.swampfestation.helper.OnStartDragListener;

/**
 * Created by vikram on 7/2/16.
 */
public class DragAdapter extends RecyclerView.Adapter<DragAdapter.SelViewHolder> implements ItemTouchHelperAdapter {
    ArrayList<Screen.Msg> data;
    private final OnStartDragListener mDragStartListener;

    public DragAdapter(ArrayList<Screen.Msg> myDataset, OnStartDragListener dragStartListener)  {
        data = myDataset;
        mDragStartListener = dragStartListener;
    }

    @Override
    public SelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.drag_view, parent, false);
        SelViewHolder svh = new SelViewHolder(v);
        return svh;
    }

    @Override
    public void onBindViewHolder(final SelViewHolder svh, int i) {
        svh.title.setText(data.get(i).title);
        svh.title.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(svh);
                }
                return false;
            }
        });
    }

    @Override
    public void onItemDismiss(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(data, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class SelViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {
        // each data item is just a string in this case

        CardView cv;
        TextView title;

        public SelViewHolder(View v) {
            super(v);
            cv = (CardView) itemView.findViewById(R.id.cv_sel);
            title = (TextView) itemView.findViewById(R.id.title);
        }
        @Override
        public void onItemSelected() {
            //itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            //itemView.setBackgroundColor(0);
        }
    }
}

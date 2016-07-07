package vikram.com.swampfestation;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by vikram on 7/2/16.
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.SelViewHolder> {
    private ArrayList<Screen.Msg> data;

    public MessageAdapter(ArrayList<Screen.Msg> myDataset) {
        data = myDataset;
    }

    @Override
    public SelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_view, parent, false);
        SelViewHolder svh = new SelViewHolder(v);
        return svh;
    }

    @Override
    public void onBindViewHolder(SelViewHolder svh, int i) {
        Screen.Msg msg = data.get(i);
        try {
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(msg.url, svh.photo);
        } catch (Exception e) {
        }
        try {
            svh.desc.setText(msg.desc);
        } catch (Exception e) {
        }
        try {
            svh.title.setText(msg.title);
        } catch (Exception e) {
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class SelViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        CardView cv;
        TextView desc;
        TextView title;
        ImageView photo;

        public SelViewHolder(View v) {
            super(v);
            cv = (CardView) itemView.findViewById(R.id.cv_sel);
            desc = (TextView) itemView.findViewById(R.id.desc);
            title = (TextView) itemView.findViewById(R.id.title);
            photo = (ImageView) itemView.findViewById(R.id.img);
        }
    }
}

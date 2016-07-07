package vikram.com.swampfestation;


import android.support.v4.app.Fragment;

/**
 * Created by vikram on 7/7/16.
 */

public abstract class FragmentInterface extends Fragment {

    abstract void onEnd(Screen.IGButton but);
    abstract void setup(GeneralActivity act);

}
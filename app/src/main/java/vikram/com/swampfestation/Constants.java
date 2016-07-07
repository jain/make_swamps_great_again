package vikram.com.swampfestation;

import java.util.HashMap;

/**
 * Created by vikram on 7/2/16.
 */
public class Constants {
    public static Characters ch = new Characters();
    public static HashMap<String, String> stringVar = new HashMap<String, String>();
    public static HashMap<String, Integer> intVar = new HashMap<String, Integer>();
    public static HashMap<String, Screen> screens = new HashMap<String, Screen>();
    public static Screen sc = null;
    public static Characters.Player pl = null;
    public static void reset(){
        ch = new Characters();
        stringVar = new HashMap<String, String>();
        intVar = new HashMap<String, Integer>();
        screens = new HashMap<String, Screen>();
        sc = null;
        pl = null;
    }
}

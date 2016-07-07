package vikram.com.swampfestation;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by vikram on 7/2/16.
 */
public class Constants {
    public static Characters ch = new Characters();
    public static HashMap<String, Integer> intVar = new HashMap<String, Integer>();
    public static HashMap<String, Screen> screens = new HashMap<String, Screen>();
    public static Screen sc = null;
    public static Characters.Player pl = null;
    public static void reset(){
        ch = new Characters();
        intVar = new HashMap<String, Integer>();
        screens = new HashMap<String, Screen>();
        sc = null;
        pl = null;
    }
    public static boolean condition(AppCompatActivity act, ArrayList<String> conditions){
        boolean notAdd = true;
        int k = 0;
        if(conditions.size()==0){
            notAdd = false;
        }
        while (k<conditions.size()&&notAdd){
            String condition = conditions.get(k);
            k++;
            String[] parts = condition.split(",");
            try {
                boolean cond = true;
                for (int j = 0; j < parts.length && cond; j += 3) {
                    String key = parts[j].trim();
                    String comparison = parts[j + 1].trim();
                    int cutoff = Integer.parseInt(parts[j + 2].trim());
                    if (Constants.intVar.containsKey(key)) {
                        int value = Constants.intVar.get(key);
                        cond = comp(value, cutoff, comparison);
                    } else if (Constants.ch.hapMap.containsKey(key)) {
                        int value = Constants.ch.hapMap.get(key).hap;
                        cond = comp(value, cutoff, comparison);
                    }
                }
                notAdd = !cond;
            } catch (Exception e){
                Toast.makeText(act, "condition issue : " + condition, Toast.LENGTH_LONG).show();
            }
        }
        return notAdd;
    }
    private static boolean comp(int value, int cutoff, String comparison) {
        if(comparison.equals(">")){
            return (value > cutoff);
        }else if (comparison.equals(">=")){
            return (value >= cutoff);
        } else if (comparison.equals("=")){
            return (value == cutoff);
        } else if (comparison.equals("<")){
            return (value < cutoff);
        } else if (comparison.equals("<=")){
            return (value <= cutoff);
        }
        return true;
    }
}

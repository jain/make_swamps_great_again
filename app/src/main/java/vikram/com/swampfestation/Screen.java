package vikram.com.swampfestation;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by vikram on 7/2/16.
 */
public class Screen {
    String id;
    String title;
    String desc;
    String type;
    ArrayList<String> msgs;
    ArrayList<String> buttons;
    ArrayList<IGButton> parsedButtons;
    public Screen(String i, String t, String d, String type, ArrayList<String> msgs, ArrayList<String>buttons){
        id = i;
        title = t;
        desc = d;
        this.type = type;
        this.msgs = msgs;
        this.buttons = buttons;
        parseButtons();
    }

    private void parseButtons() {
        parsedButtons = new ArrayList<IGButton>();
        for (String but: buttons){
            parsedButtons.add(new IGButton(but));
        }
    }

    public String getDescription() {
        String[] descriptionArr = desc.split("\\|");
        for (String d : descriptionArr){
            if(d.startsWith("desc")){
                int start = desc.indexOf("(");
                int end = desc.lastIndexOf(")");
                return desc.substring(start+1, end);
            }
        }
        return "";
    }

    public class IGButton{
        String title = "";
        String next = "";
        ArrayList<String> extra = new ArrayList<String>();
        ArrayList<String> conditions = new ArrayList<>();
        public IGButton(String but){
            String[] msgs = but.split("[\\|]");
            for (int j = 0; j<msgs.length; j++){
                msgs[j] = msgs[j].trim();
                if (msgs[j].startsWith("title")){
                    try {
                        title = msgs[j].split("[\\(\\)]")[1];
                    } catch (Exception e){
                    }
                } else if (msgs[j].startsWith("link")){
                    try {
                        next = msgs[j].split("[\\(\\)]")[1];
                    } catch (Exception e){
                    }
                } else if (msgs[j].startsWith("cond")){
                    try {
                        String cond = msgs[j].split("[\\(\\)]")[1];
                        conditions.add(cond);
                    } catch (Exception e){
                    }
                } else {
                    extra.add(msgs[j]);
                }

            }
        }
    }
}

package vikram.com.swampfestation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

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
    ArrayList<Msg> parsedMsgs;
    ArrayList<IGButton> parsedButtons;
    public Screen(String i, String t, String d, String type, ArrayList<String> msgs, ArrayList<String>buttons){
        id = i;
        title = t;
        desc = d;
        this.type = type;
        this.msgs = msgs;
        this.buttons = buttons;
        parseButtons();
        parseMsgs();
    }

    private void parseMsgs() {
        parsedMsgs = new ArrayList<Msg>();
        for (String msg: msgs){
            parsedMsgs.add(new Msg(msg));
        }
    }

    private void parseButtons() {
        parsedButtons = new ArrayList<IGButton>();
        for (String but: buttons){
            parsedButtons.add(new IGButton(but));
        }
    }

    public String getDescription() {
        String[] descriptionArr = desc.split("\\|");
        String ret = "";
        for (String d : descriptionArr){
            d = d.trim();
            if(d.startsWith("desc")){
                int start = d.indexOf("(");
                int end = d.lastIndexOf(")");
                ret =  d.substring(start+1, end);
            } else if(d.startsWith("rand")){
                try {
                    int start = d.indexOf("(");
                    int end = d.lastIndexOf(")");
                    String contents = d.substring(start + 1, end);
                    String[] vals = contents.split(",");
                    int seed = Integer.parseInt(vals[0].trim());
                    String var = vals[1].trim();
                    Random rand = new Random();
                    Constants.intVar.put(var,rand.nextInt(seed));
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return ret;
    }
    public class Msg{
        String url = "";
        String desc = "";
        String title = "";
        ArrayList<String> conditions = new ArrayList<>();
        public Msg(String msge){
            String[] msgs = msge.split("[\\|]");
            for (int j = 0; j<msgs.length; j++){
                String msg = msgs[j].trim();
                if (msg.startsWith("title")){
                    try {
                        title = msg.split("[\\(\\)]")[1].trim();
                    } catch (Exception e){
                    }
                } else if (msg.startsWith("image")){
                    try {
                        url = msg.split("[\\(\\)]")[1].trim();
                    } catch (Exception e){
                    }
                } else if (msg.startsWith("cond")){
                    try {
                        String cond = msgs[j].split("[\\(\\)]")[1].trim();
                        conditions.add(cond);
                    } catch (Exception e){
                    }
                } else if (msg.startsWith("desc")){
                    desc = msg.split("[\\(\\)]")[1].trim();
                }

            }
        }
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
                        title = msgs[j].split("[\\(\\)]")[1].trim();
                    } catch (Exception e){
                    }
                } else if (msgs[j].startsWith("link")){
                    try {
                        next = msgs[j].split("[\\(\\)]")[1].trim();
                    } catch (Exception e){
                    }
                } else if (msgs[j].startsWith("cond")){
                    try {
                        String cond = msgs[j].split("[\\(\\)]")[1].trim();
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

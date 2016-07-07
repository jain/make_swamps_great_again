package vikram.com.swampfestation;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by vikram on 7/2/16.
 */
public class Characters {
    ArrayList<Player> players;
    HashMap<String, Player> hapMap;
    HashMap<String, Player> varMap;
    public Characters(){
        players = new ArrayList<>();
        hapMap = new HashMap<String, Player>();
        varMap = new HashMap<String, Player>();
    }
    public void addCharacter(String[][] data, int i){
        if(data[0][i]==null || data[1][i]==null){
            Log.d("sheets error", "missing var or name of char");
            return;
        }
        String name = "";
        String imgUrl = "";
        String desc = "";
        int hap = 0;
        int min_level = 0;
        if(data[2][i]!=null){
            name = data[2][i];
        }
        if(data[3][i]!=null){
            imgUrl = data[3][i];
        }
        if(data[4][i]!=null){
            desc = data[4][i];
        }
        if(data[5][i]!=null){
            try {
                hap = Integer.parseInt(data[5][i]);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        if(data[6][i]!=null){
            try {
                min_level = Integer.parseInt(data[6][i]);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        Player pl = new Player(name, imgUrl, desc, hap, min_level);
        players.add(pl);
        hapMap.put(data[0][i], pl);
        varMap.put(data[1][i], pl);
    }
    public class Player{
        String name;
        String imgUrl;
        String desc;
        int hap;
        int min_level;
        ArrayList<String> msgs;
        public Player(String name, String imgUrl, String desc, int hap, int min_level){
            this.name = name;
            this.imgUrl = imgUrl;
            this.desc = desc;
            this.hap = hap;
            this.min_level = min_level;
            msgs = new ArrayList<>();
        }
    }
}

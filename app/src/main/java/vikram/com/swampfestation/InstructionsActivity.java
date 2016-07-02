package vikram.com.swampfestation;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/*import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.CustomElementCollection;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.util.ServiceException;*/
//https://docs.google.com/spreadsheet/ccc?key=1G_zFPHjp4WI1qpWwTCL8mASi_Sro0PBIIkkCkl4Ea_s&output=csv
//https://spreadsheets.google.com/tq?key=1yyTcjWA6RAUwkI7sKOevWXAJfpITs__Zb0TwilihDCw
//https://spreadsheets.google.com/tq?key=1G_zFPHjp4WI1qpWwTCL8mASi_Sro0PBIIkkCkl4Ea_s


public class InstructionsActivity extends AppCompatActivity {
    private MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        //mp = new MediaPlayer();
    }
    @Override
    protected void onResume(){
        super.onResume();
        DownloadWebpageTask dwt = new DownloadWebpageTask();
        dwt.execute();
        //mp = MediaPlayer.create(this, R.raw.instructions);
        //mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        //mp.setLooping(true);
        //mp.start();
    }
    @Override
    protected void onPause(){
        super.onPause();
        //mp.stop();
    }
    public void start(View view){
        //Intent intent = new Intent(this, SelectActivity.class);
        //startActivity(intent);
    }

}

package vikram.com.swampfestation;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class InstructionsActivity extends AppCompatActivity {
    private MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(InstructionsActivity.this));
        //mp = new MediaPlayer();
    }
    @Override
    protected void onResume(){
        super.onResume();
        //mp = MediaPlayer.create(this, R.raw.instructions);
        //mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        //mp.setLooping(true);
        //mp.start();
        Constants.reset();
        DownloadWebpageTask dwt = new DownloadWebpageTask(this);
        dwt.execute();
    }
    @Override
    protected void onPause(){
        super.onPause();
        //mp.stop();
    }
    public void start(View view){
        if (Constants.screens.size()!=0){
            try {
                Constants.sc = Constants.screens.get("sc1");
                Intent intent = new Intent(this, GeneralActivity.class);
                startActivity(intent);
            } catch (Exception e){
                Toast.makeText(this, "init scene not set", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "there is no data", Toast.LENGTH_LONG).show();
        }
        //Intent intent = new Intent(this, SelectActivity.class);
        //startActivity(intent);
    }

    public void getNewData(View view){
        Constants.reset();
        DownloadWebpageTask dwt = new DownloadWebpageTask(this);
        dwt.execute();
    }

}

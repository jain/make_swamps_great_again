package vikram.com.swampfestation;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class InstructionsActivity extends AppCompatActivity {
    private MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        mp = new MediaPlayer();
    }
    @Override
    protected void onResume(){
        super.onResume();
        mp = MediaPlayer.create(this, R.raw.instructions);
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mp.setLooping(true);
        mp.start();
    }
    @Override
    protected void onPause(){
        super.onPause();
        mp.stop();
    }
    public void start(View view){
        Intent intent = new Intent(this, SelectActivity.class);
        startActivity(intent);
    }

}

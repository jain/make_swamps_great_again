package vikram.com.swampfestation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by vikram on 7/21/16.
 */
public class MyActivity extends AppCompatActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me);
        TextView desc = (TextView) findViewById(R.id.description);
        desc.setMovementMethod(new ScrollingMovementMethod());
        desc.setText("Hello, your name is Ted Brackett, you are an engineer at a major firm in a swampy area in Florida. You are 34 years old. You studied at Georgia Institute of Technology where you majored in Civil Engineering. You have finally seemed to earn a sense of authority within this company. You are the head of the structural engineering department meaning you are in charge of making major decisions in terms of projects given to your team. You are a father of two and your marriage is not necessarily the strongest because you are constantly working late hours and sometimes even bringing work home. Your family was originally from New York, and your wife is not the biggest fan of the location of where the firm is located. It is the morning and you are about to get into your car to head to work….");
        ImageView photo = (ImageView) findViewById(R.id.img);
        try {
            String url = "http://vignette4.wikia.nocookie.net/pokemon/images/e/e5/006Charizard_AG_anime.png/revision/latest?cb=20131213083244";
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(url, photo);
        } catch (Exception e){
            Toast.makeText(this, "invalid url for image", Toast.LENGTH_LONG).show();
        }
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

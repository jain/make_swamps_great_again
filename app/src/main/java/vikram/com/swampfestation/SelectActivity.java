package vikram.com.swampfestation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SelectActivity extends AppCompatActivity {
    private  MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        mp = new MediaPlayer();
    }
    @Override
    protected void onResume(){
        super.onResume();
        mp = MediaPlayer.create(this, R.raw.intro);
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mp.setLooping(true);
        mp.start();
    }
    @Override
    protected void onPause(){
        super.onPause();
        mp.stop();
    }
    public void engineer(View v)
    {
        alert("Engineer", "Works under the boss and is in charge of technical decisions").show();
    }
    public void boss(View v)
    {
        alert("Boss", "Undergraduate in Engineering, and has an MBA. " +
                "Has to balance worker happiness with cost and safety").show();
    }
    public void intern(View v)
    {
        alert("Intern", "Works under Engineer. Local boy and " +
                "knows of local environmental/animal issues").show();
    }
    public void gov(View v)
    {
        alert("Government Official", "Has to balance jobs/environment" +
                " vs environment/animal protection.").show();
    }
    public void protestor(View v)
    {
        alert("Public/Community Official", "Intern's relative has to ensure community and" +
                "remains well.").show();
    }
    public void investor(View v)
    {
        alert("Investor", "Cares about finanical returns from investing in company.").show();
    }
    public AlertDialog alert(final String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message)
                .setPositiveButton("Select", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                        Data.person = title;
                        Intent intent = new Intent(getBaseContext(), StoryActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}

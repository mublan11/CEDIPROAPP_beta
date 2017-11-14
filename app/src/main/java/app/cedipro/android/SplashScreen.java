package app.cedipro.android;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;

public class SplashScreen extends AppCompatActivity {
    VideoView videoSplash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        //getSupportActionBar().hide();

        videoSplash = (VideoView) findViewById(R.id.videoSplash);

        Uri video = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.splash_screen_video);
        videoSplash.setVideoURI(video);

        videoSplash.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (isFinishing()){
                    return;
                }
                startActivity(new Intent(SplashScreen.this, SplashScreenLogo.class));
                finish();
            }
        });

        videoSplash.start();
    }
}

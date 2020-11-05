package com.virsaradio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;

public class Live_VideoPlay extends AppCompatActivity implements YouTubePlayer.OnInitializedListener,View.OnClickListener {


    private static final int RECOVERY_REQUEST =1;
    public YouTubePlayerView youTubeView;
    YouTubePlayer youTubePlayer2;
    private boolean isFullScreen;
    private ImageView backarrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live__video_play);
        backarrow=(ImageView)findViewById(R.id.back_arrow);
        ClickListener();

        String id=getIntent().getExtras().getString("videoid");

        YouTubePlayerSupportFragment fragment = (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.youtube_fragment);
        fragment.initialize(YoutubeKey.YOUTUBE_API_KEY, this);

    }

    private void ClickListener() {
        backarrow.setOnClickListener(this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if(!b){
            this.youTubePlayer2 =youTubePlayer;

            String id=getIntent().getExtras().getString("videoid");

            youTubePlayer.loadVideo(id);
//            youTubePlayer.play();

            // Toast.makeText(getApplicationContext(),id,Toast.LENGTH_LONG).show();
            youTubePlayer.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
                @Override
                public void onFullscreen(boolean b) {
                    isFullScreen=b;
                    Log.e("isFullScreen","fullscreen condition-- "+isFullScreen);
//                      youTubePlayer2.play();
                }
            });

        }
    }



    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if(youTubeInitializationResult.isUserRecoverableError()){
            youTubeInitializationResult.getErrorDialog(this,RECOVERY_REQUEST).show();
        }else{
            String error=String.format("Error", youTubeInitializationResult.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(YoutubeKey.YOUTUBE_API_KEY, this);
        }
    }
    private YouTubePlayer.Provider getYouTubePlayerProvider()
    {
        return youTubeView;
    }

    @Override
    public void onClick(View view) {
        if(view==backarrow)
        {
            Intent intent=new Intent(Live_VideoPlay.this,MainActivity.class);
            startActivity(intent);
        }
    }
}

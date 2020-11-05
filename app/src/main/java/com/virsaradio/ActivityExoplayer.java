package com.virsaradio;

import android.content.Context;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class ActivityExoplayer extends AppCompatActivity implements View.OnClickListener,ConnectivityReceiver.ConnectivityReceiverListener{
    //SimpleExoPlayer exoPlayer;
//    private Dialog dialog_internet;
    String streamUrl;
    ImageView iv_play;
            //,iv_pause,iv_gif,iv_backarrow;
//    SeekBar seekBar;
//    AudioManager audioManager;
 RadioManager radioManager;
 SeekBar seekBar;
 AudioManager audioManager;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream);
        seekBar=(SeekBar)findViewById(R.id.seekbar);

//        Log.i("ActivityExo>>>>>>>",streamUrl);


        //Toast.makeText(getApplicationContext(),audioURL,Toast.LENGTH_SHORT).show();
    //    Init();

       // ClickListener();
//        checkConnection();
//        startExoplayer();

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        seekBar.setMax(audioManager
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        seekBar.setProgress(audioManager
                .getStreamVolume(AudioManager.STREAM_MUSIC));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                        progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        radioManager = RadioManager.with(this);
        try {
            streamUrl=getIntent().getExtras().getString("audio_url");
            radioManager.playOrPause(streamUrl);
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
            Log.i("errorrrrrrrrrrr",e.toString());
        }


//        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//        seekBar.setMax(audioManager
//                .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
//        seekBar.setProgress(audioManager
//                .getStreamVolume(AudioManager.STREAM_MUSIC));
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
//                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
//                        progress, 0);
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });
    }

//    private void ClickListener()
//    {
//        iv_play.setOnClickListener(this);
//        iv_pause.setOnClickListener(this);
//        iv_backarrow.setOnClickListener(this);
//    }
//
//    private void Init()
//    {
//        iv_play=(ImageView)findViewById(R.id.iv_play);
//        iv_pause=(ImageView)findViewById(R.id.iv_pause);
//        dialog_internet=new Dialog(this);
//       // dialog_internet.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog_internet=new Dialog(this,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
//        dialog_internet.setContentView(R.layout.dialog_internet);
//        seekBar=(SeekBar)findViewById(R.id.seekbar);
//        iv_gif=(ImageView)findViewById(R.id.iv_gif);
//        iv_backarrow=(ImageView)findViewById(R.id.iv_back_arrow);
//
//    }

    private void startExoplayer()
    {
        try
        {
          //  Toast.makeText(getApplicationContext(),"Started",Toast.LENGTH_SHORT).show();
//            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
//            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
//            exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
//            Uri videoURI = Uri.parse(audioURL);
//            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_audio");
//            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
//            MediaSource mediaSource = new ExtractorMediaSource(videoURI, dataSourceFactory, extractorsFactory, null, null);
//            exoPlayer.prepare(mediaSource);
//
//
//
//                exoPlayer.setPlayWhenReady(true);
//                iv_pause.setVisibility(View.VISIBLE);
//                iv_play.setVisibility(View.GONE);
//                iv_gif.setVisibility(View.VISIBLE);
//                Glide
//                        .with(this) // replace with 'this' if it's in activity
//                        .load("https://vignette.wikia.nocookie.net/animal-jam-clans-1/images/f/fa/Photo_banner_background.gif/revision/latest?cb=20170128095803%22).into(imageViewTarget")
//                        .asGif()
//                        .into(iv_gif);


        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();

        }
    }


    @Override
    public void onClick(View view)
    {
//        if(view==iv_play)
//        {
//            if (isConnected(getApplicationContext())) {
//                radioManager.playOrPause(audioURL);
//               // iv_play.setVisibility(View.GONE);
//               // iv_pause.setVisibility(View.VISIBLE);
//                //iv_gif.setVisibility(View.VISIBLE);
//            } else{
//                dialog_internet.show();
//               // Toast.makeText(getApplicationContext(), "No internet", Toast.LENGTH_SHORT).show();
//            }
//        // startExoplayer();
//
//        }
//        if(view==iv_pause)
//        {
//            if (isConnected(getApplicationContext())) {
//                //exoPlayer.stop();
//                iv_play.setVisibility(View.VISIBLE);
//                iv_pause.setVisibility(View.GONE);
//                iv_gif.setVisibility(View.GONE);
//            } else{
//                dialog_internet.show();
//                //Toast.makeText(getApplicationContext(), "No internet", Toast.LENGTH_SHORT).show();
//            }


        //}
//        if(view==iv_backarrow)
//        {
//            //exoPlayer.stop();
//            Intent intent=new Intent(ActivityExoplayer.this,MainActivity.class);
//            startActivity(intent);
//        }
    }

    private void methodBack() {
        //exoPlayer.stop();

    }

    public static boolean isConnected(Context context){
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected());
    }

    public static NetworkInfo getNetworkInfo(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }




    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showInternetStatus(isConnected);
    }

    private void showInternetStatus(boolean isConnected) {
//        if(isConnected)
//        {
//
//            dialog_internet.dismiss();
//            iv_pause.setVisibility(View.VISIBLE);
//            iv_gif.setVisibility(View.VISIBLE);
//            iv_play.setVisibility(View.GONE);
//            //Toast.makeText(getApplicationContext(),"Internet connected",Toast.LENGTH_SHORT).show();
//        }
//        else
//        {
//            dialog_internet.show();
//            iv_pause.setVisibility(View.GONE);
//            iv_gif.setVisibility(View.GONE);
//            iv_play.setVisibility(View.VISIBLE);
//
//            //Toast.makeText(getApplicationContext(),"Internet not connected",Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showInternetStatus(isConnected);
    }











    @Override
    public void onStart() {

        super.onStart();

        EventBus.getDefault().register(this);

    }

    @Override
    public void onStop() {

        EventBus.getDefault().unregister(this);
        super.onStop();

    }

    @Override
    protected void onDestroy() {

        radioManager.unbind();
        super.onDestroy();

    }

    @Override
    protected void onResume() {
        super.onResume();
        radioManager.bind();

    }

    @Override
    public void onBackPressed() {

        finish();

    }

    @Subscribe
    public void onEvent(String status) {

        switch (status) {

            case PlaybackStatus.LOADING:
                Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show();
                // loading

                break;

            case PlaybackStatus.ERROR:

                Toast.makeText(this, R.string.no_stream, Toast.LENGTH_SHORT).show();
                break;

        }

    }


}

package com.virsaradio;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import android.view.View;

import android.widget.ImageView;
import android.widget.SeekBar;

import com.bumptech.glide.Glide;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


import butterknife.ButterKnife;


/**
 * Created by khushdeep-android on 18/4/18.
 */

public class RadioClass extends AppCompatActivity implements View.OnClickListener,ConnectivityReceiver.ConnectivityReceiverListener{

    public static RadioManager radioManager;
    public String streamURL;
//    @BindView(R.id.btn_play)
//
//
//    ImageButton trigger;
    SeekBar seekBar;
    public AudioManager audioManager;
    public static ImageView iv_gif,iv_play,iv_pause;
   // public static ProgressDialog progressDialog;
    ImageView iv_back;
    private Dialog dialog_internet;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream);
        ButterKnife.bind(this);
        streamURL=getIntent().getExtras().getString("audio_url");

        radioManager = RadioManager.with(this);
        radioManager.bind();


       // EventBus.getDefault().register(this);

        dialog_internet=new Dialog(this,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog_internet.setContentView(R.layout.dialog_internet);
        iv_back=(ImageView)findViewById(R.id.iv_back_arrow);
        seekBar=(SeekBar)findViewById(R.id.seekbar);
        iv_gif=(ImageView)findViewById(R.id.iv_gif);
        iv_play=(ImageView)findViewById(R.id.iv_play2);
        iv_pause=(ImageView)findViewById(R.id.iv_pause2);
        iv_play.setOnClickListener(this);
        iv_pause.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        Glide
                .with(this) // replace with 'this' if it's in activity
                .load("https://vignette.wikia.nocookie.net/animal-jam-clans-1/images/f/fa/Photo_banner_background.gif/revision/latest?cb=20170128095803%22).into(imageViewTarget")
                .asGif()
                .into(iv_gif);
      //  progressDialog=new ProgressDialog(this);
       // progressDialog.setTitle("     Loading.....");
//        iv_pause=(ImageView)findViewById(R.id.iv_pause);
//        iv_play=(ImageView)findViewById(R.id.iv_play);


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
        checkConnection();
        methodStart();





    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showInternetStatus(isConnected);
    }

    private void showInternetStatus(boolean isConnected) {
        if(isConnected)
        {
            dialog_internet.dismiss();
            //Toast.makeText(getApplicationContext(),"Internet connected",Toast.LENGTH_SHORT).show();
        }
        else
        {
            iv_gif.setVisibility(View.GONE);
            iv_pause.setVisibility(View.GONE);
            iv_play.setVisibility(View.VISIBLE);
            dialog_internet.show();

            //Toast.makeText(getApplicationContext(),"Internet not connected",Toast.LENGTH_SHORT).show();
        }
    }

    private void methodStart() {
        try {
           // Toast.makeText(getApplicationContext(),streamURL,Toast.LENGTH_LONG).show();
            //radioManager.bind();
            radioManager.playOrPause(streamURL);
        }
        catch (Exception e)
        {
           // Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
        }
    }




    @Override
    public void onStart()
    {

        super.onStart();
         radioManager.bind();
      //  EventBus.getDefault().register(this);
         if(EventBus.getDefault().isRegistered(this))
         {

         }
         else
         {
             EventBus.getDefault().register(this);
         }
       //  methodStart();
//        streamURL=getIntent().getExtras().getString("audio_url");
//        Toast.makeText(getApplicationContext(),streamURL,Toast.LENGTH_LONG).show();
//        try
//        {
//            radioManager.playOrPause(streamURL);
//        }
//        catch (Exception e)
//        {
//            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
//            Log.i("errorrrrr",e.toString());
//        }


    }

    @Override
    protected void onRestart() {
        //radioManager.bind();
       // Toast.makeText(getApplicationContext(),"restart",Toast.LENGTH_LONG);

//        radioManager.bind();
//        EventBus.getDefault().register(this);
//        streamURL=getIntent().getExtras().getString("audio_url");
//        radioManager.playOrPause(streamURL);
        super.onRestart();
    }

    @Override
    public void onStop() {
        //radioManager.unbind();
       // EventBus.getDefault().unregister(this);
        super.onStop();

    }

    @Override
    protected void onDestroy() {

        radioManager.unbind();
        EventBus.getDefault().unregister(this);
        super.onDestroy();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Myapp.getInstance().setConnectivityListener(this);
        radioManager.bind();
        //radioManager.playOrPause(streamURL);

    }

    @Override
    public void onBackPressed() {
       // iv_play.setVisibility(View.VISIBLE);
        //iv_gif.setVisibility(View.GONE);
        //iv_pause.setVisibility(View.GONE);
      //  radioManager.unbind();
       //EventBus.getDefault().unregister(this);
       finish();

//        Intent in=new Intent(RadioClass.this,MainActivity.class);
//        startActivity(in);

    }

    @Subscribe
    public void onEvent(String status) {

        switch (status) {

            case PlaybackStatus.LOADING:
               // progressDialog.show();

                // loading

                break;

            case PlaybackStatus.ERROR:

              //  Toast.makeText(this, R.string.no_stream, Toast.LENGTH_SHORT).show();
               // progressDialog.dismiss();
                break;
            case PlaybackStatus.PLAYING:

               // progressDialog.dismiss();
                iv_gif.setVisibility(View.VISIBLE);
                iv_play.setVisibility(View.GONE);
                iv_pause.setVisibility(View.VISIBLE);
                //Toast.makeText(this,"playing", Toast.LENGTH_SHORT).show();
                break;
            case PlaybackStatus.PAUSED:

                 iv_gif.setVisibility(View.GONE);
                 iv_pause.setVisibility(View.GONE);
                 iv_play.setVisibility(View.VISIBLE);

                //Toast.makeText(this,"playing", Toast.LENGTH_SHORT).show();
                break;
//            case PlaybackStatus.STOPPED:
//                radioManager.unbind();
//                EventBus.getDefault().unregister(this);
//
//                iv_gif.setVisibility(View.GONE);
//                iv_pause.setVisibility(View.GONE);
//                iv_play.setVisibility(View.VISIBLE);
//                //Toast.makeText(this,"playing", Toast.LENGTH_SHORT).show();
//                break;
        }
//        trigger.setImageResource(status.equals(PlaybackStatus.PLAYING)
//                ? R.drawable.ic_pause_white
//                : R.drawable.ic_play_white);

    }

    @Override
    public void onClick(View view) {
        if(view==iv_play)
        {

            if (isConnected(getApplicationContext())) {
                radioManager.playOrPause(streamURL);

            } else{
                dialog_internet.show();

            }
           // radioManager.playOrPause(streamURL);
        }
        if(view==iv_pause)
        {

            if (isConnected(getApplicationContext())) {
                radioManager.playOrPause(streamURL);

            } else{

                dialog_internet.show();

            }
           // radioManager.playOrPause(streamURL);
        }
        if(view==iv_back)
        {

            Intent in=new Intent(RadioClass.this,MainActivity.class);
            startActivity(in);
        }
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showInternetStatus(isConnected);
    }
    public static boolean isConnected(Context context){
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected());
    }

    public static NetworkInfo getNetworkInfo(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }
}

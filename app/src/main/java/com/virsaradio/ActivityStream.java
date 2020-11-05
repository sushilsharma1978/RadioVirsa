package com.virsaradio;

import android.app.ProgressDialog;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;




import java.io.IOException;

public class ActivityStream extends AppCompatActivity implements View.OnClickListener{
    ImageView iv_radioplay;
    SeekBar seekBar;
    MediaPlayer mediaPlayer;
    boolean started = false;
    boolean prepared = false;
    ProgressDialog progressDialog;
    String url="http://s7.voscast.com:8772/;stream.mp3%20type:8772/;stream.mp3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream);
        Init();
        ClickListener();
        progressDialog=new ProgressDialog(this);
        progressDialog.show();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        new PlayTask().execute(url);


    }




    private void ClickListener() {
        iv_radioplay.setOnClickListener(this);
    }

    private void Init() {
       //iv_radioplay=(ImageView)findViewById(R.id.iv_play);
       seekBar=(SeekBar)findViewById(R.id.seekbar);
    }

    @Override
    public void onClick(View view) {
        if(view==iv_radioplay)
        {
radioStream();
        }
    }

    private void radioStream()
    {
        if (started) {
            progressDialog.dismiss();
            mediaPlayer.pause();
            started = false;
            iv_radioplay.setImageResource(R.drawable.play);
           // play.setText("Play");
        } else {
            progressDialog.dismiss();
            mediaPlayer.start();
            started = true;
            iv_radioplay.setImageResource(R.drawable.pause);
           // play.setText("Pause");
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
       /* if(started)
            mediaPlayer.pause();*/

    }

    @Override
    protected void onResume() {
        super.onResume();
        /*if(started)
            mediaPlayer.start();*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // mediaPlayer.release();
    }

    private class PlayTask extends AsyncTask<String, Void, Boolean>
    {

        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                mediaPlayer.setDataSource(strings[0]);
                mediaPlayer.prepare();
                prepared = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return prepared;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            progressDialog.dismiss();
            iv_radioplay.setImageResource(R.drawable.play);

        }
    }
}

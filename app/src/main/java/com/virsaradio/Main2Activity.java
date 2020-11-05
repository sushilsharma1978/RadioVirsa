package com.virsaradio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class Main2Activity extends AppCompatActivity {


    @BindView(R.id.playTrigger)
    ImageButton trigger;
    @BindView(R.id.listview)
    ListView listView;
    @BindView(R.id.name)
    TextView textView;
    @BindView(R.id.sub_player)
    View subPlayer;
    RadioManager radioManager;
    String streamURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);
       // streamURL=getIntent().getExtras().getString("audio_url");
       // Toast.makeText(getApplicationContext(),streamURL,Toast.LENGTH_SHORT).show();

        ButterKnife.bind(this);



        radioManager = RadioManager.with(this);

        listView.setAdapter(new ShoutcastListAdapter(this, ShoutcastHelper.retrieveShoutcasts(this)));
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
    public void onEvent(String status){

        switch (status){

            case PlaybackStatus.LOADING:

                // loading

                break;

            case PlaybackStatus.ERROR:

                Toast.makeText(this, R.string.no_stream, Toast.LENGTH_SHORT).show();
                break;

        }

        trigger.setImageResource(status.equals(PlaybackStatus.PLAYING)
                ? R.drawable.ic_pause_black
                : R.drawable.ic_play_arrow_black);

    }

    @OnClick(R.id.playTrigger)
    public void onClicked(){

     //   if(TextUtils.isEmpty(streamURL)) return;
        Toast.makeText(getApplicationContext(),"play",Toast.LENGTH_SHORT).show();
        radioManager.playOrPause(streamURL);

    }

    @OnItemClick(R.id.listview)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){

        Shoutcast shoutcast = (Shoutcast) parent.getItemAtPosition(position);
        if(shoutcast == null)
        {

            return;
        }

        textView.setText(shoutcast.getName());
        subPlayer.setVisibility(View.VISIBLE);
      //  streamURL = shoutcast.getUrl();
        //radioManager.playOrPause(streamURL);

    }
}

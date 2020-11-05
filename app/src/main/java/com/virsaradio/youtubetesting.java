package com.virsaradio;

//public class youtubetesting {
//}


//import com.google.android.youtube.player.YouTubeBaseActivity;
//import com.google.android.youtube.player.YouTubeInitializationResult;
//import com.google.android.youtube.player.YouTubePlayer;
//import com.google.android.youtube.player.YouTubePlayerView;
//import com.google.android.youtube.player.YouTubePlayer.Provider;
//
//import android.os.Bundle;
//import android.content.Intent;
//import android.view.Menu;
//import android.widget.Toast;
//
//public class youtubetesting extends YouTubeBaseActivity implements
//        YouTubePlayer.OnInitializedListener {
//
//    private YouTubePlayer YPlayer;
//    private static final String YoutubeDeveloperKey = "AIzaSyCCHuayCrwwcRAUZ__zTYyOP-ax5FD4R9E";
//    private static final int RECOVERY_DIALOG_REQUEST = 1;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.liveyoutube);
//
//        YouTubePlayerView youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
//        youTubeView.initialize(YoutubeDeveloperKey, this);
//    }
//
////    @Override
////    public boolean onCreateOptionsMenu(Menu menu) {
////        // Inflate the menu; this adds items to the action bar if it is present.
////        getMenuInflater().inflate(R.menu.you_tube_api, menu);
////        return true;
////    }
//
//    @Override
//    public void onInitializationFailure(YouTubePlayer.Provider provider,
//                                        YouTubeInitializationResult errorReason) {
//        if (errorReason.isUserRecoverableError()) {
//            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
//        } else {
//            String errorMessage = String.format(
//                    "There was an error initializing the YouTubePlayer",
//                    errorReason.toString());
//            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == RECOVERY_DIALOG_REQUEST) {
//            // Retry initialization if user performed a recovery action
//            getYouTubePlayerProvider().initialize(YoutubeDeveloperKey, this);
//        }
//    }
//
//    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
//        return (YouTubePlayerView) findViewById(R.id.youtube_view);
//    }
//
//    @Override
//    public void onInitializationSuccess(Provider provider,
//                                        YouTubePlayer player, boolean wasRestored) {
//        YPlayer = player;
//        /*
//         * Now that this variable YPlayer is global you can access it
//         * throughout the activity, and perform all the player actions like
//         * play, pause and seeking to a position by code.
//         */
//        if (!wasRestored) {
//            YPlayer.cueVideo("ICniUTaDoFM");
////            YPlayer.play();
//        }
//    }
//
//}


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.virsaradio.Interfaces.ApiInterface;
import com.virsaradio.Modelclass.LiveGetSet;
import com.virsaradio.Modelclass.PlaylistVideosGetset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;

public class youtubetesting extends AppCompatActivity{

    LiveGetSet liveGetSet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hitApi();
    }

    private void hitApi() {

        ApiInterface retrofitAPI;
        retrofitAPI = Api.getClient().create(ApiInterface.class);


        Call<JsonElement> call = retrofitAPI.getLiveyoutubeVideo();

        call.enqueue(new retrofit2.Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, retrofit2.Response<JsonElement> response) {

                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    JSONArray jsonArray=jsonObject.getJSONArray("items");

                    for(int i=0;i<1;i++)
                    {
                        JSONObject json_items = jsonArray.getJSONObject(i);
                        JSONObject id = json_items.getJSONObject("id");

                        liveGetSet = new LiveGetSet(id.getString("videoId"));
                        Log.e("VideoId=======", id.getString("videoId"));
//                        if(snippet.has("thumbnails"))
//                        {
//                            JSONObject thumbnails = snippet.getJSONObject("thumbnails");
//                            JSONObject medium = thumbnails.getJSONObject("medium");
//                        //    playlistVideosGetset = new PlaylistVideosGetset(snippet.getString("title"), medium.getString("url"),json_items.getString("id"));
//                        }
//                        else {
//
//                        }
                      //  arrayList.add(playlistVideosGetset);
                        Intent intent=new Intent(youtubetesting.this,Live_VideoPlay.class);
                        intent.putExtra("videoid",id.getString("videoId"));
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    }

                  //  setRecyclerview();

                  //  progress.dismiss();

                } catch (JSONException e) {

                    e.printStackTrace();
                  //  progress.dismiss();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                //Toast.makeText(getActivity(),"error",Toast.LENGTH_LONG).show();
               // progress.dismiss();
            }
        });
    }


}
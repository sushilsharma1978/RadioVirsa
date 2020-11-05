package com.virsaradio;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.virsaradio.Interfaces.ApiInterface;
import com.virsaradio.Modelclass.LiveGetSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ConnectivityReceiver.ConnectivityReceiverListener {
private LinearLayout iv_live;
    LiveGetSet liveGetSet;
private Button btn_highquality,btn_lowquality;
private Dialog dialog,dialog_internet,dialog_exit;
private LinearLayout tv_video;
Context context;
private static final String pageId="489044981161541";
    String low_audio_url="http://s7.voscast.com:8772/;stream.mp3%20type:8772/;stream.mp3";
    String high_audio_url="http://s8.voscast.com:7992/;stream.mp3%20type:7992/;stream.mp3";



private LinearLayout linearLayout_contact_us,linearLayout_website,linearLayout_facebook,linearLayout_youtube;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toast.makeText(getApplicationContext(),"create",Toast.LENGTH_LONG).show();

        Init();
        ClickListener();
        checkConnection();

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
            dialog_internet.show();
            //Toast.makeText(getApplicationContext(),"Internet not connected",Toast.LENGTH_SHORT).show();
        }
    }

    private void ClickListener()
    {
       iv_live.setOnClickListener(this);
       linearLayout_contact_us.setOnClickListener(this);
       linearLayout_website.setOnClickListener(this);
       linearLayout_facebook.setOnClickListener(this);
       btn_highquality.setOnClickListener(this);
       btn_lowquality.setOnClickListener(this);
       linearLayout_youtube.setOnClickListener(this);
       tv_video.setOnClickListener(this);

    }

    private void Init()
    {   linearLayout_contact_us=(LinearLayout)findViewById(R.id.linear_contact_us);
        linearLayout_website=(LinearLayout)findViewById(R.id.linear_website);
        linearLayout_facebook=(LinearLayout)findViewById(R.id.linear_facebook);
        linearLayout_youtube=(LinearLayout)findViewById(R.id.linear_youtube);
        iv_live=(LinearLayout)findViewById(R.id.live_radio);
        context=this;
        tv_video=(LinearLayout) findViewById(R.id.live_video);
        dialog=new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_audio_quality);
        //dialog_exit=new Dialog(MainActivity.this);
        //dialog_internet=new Dialog(MainActivity.this);

        //dialog_internet.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //dialog_exit.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog_internet=new Dialog(this,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog_internet.setContentView(R.layout.dialog_internet);
        dialog_exit=new Dialog(MainActivity.this);

        //dialog_exit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_exit.setContentView(R.layout.dialog_exit);
        btn_highquality=(Button)dialog.findViewById(R.id.btn_high_quality);
        btn_lowquality=(Button)dialog.findViewById(R.id.btn_low_quality);

    }

    @Override
    public void onClick(View view)
    {
       if(view==iv_live)
       {
        dialog.show();

       }
       if(view==linearLayout_contact_us)
       {
           Intent intent=new Intent(MainActivity.this,Contact.class);
           startActivity(intent);
       }
        if(view==linearLayout_website)
        {
            Uri uri = Uri.parse("http://radiovirsanz.com");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
        if(view==linearLayout_facebook)
        {
            try
            {
               Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/" + pageId));
                startActivity(intent);
            } catch (Exception e)
            {
                Intent intent =  new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/" + pageId));
                startActivity(intent);
            }
        }

        if(view==btn_highquality)
        {
            dialog.dismiss();
         // Toast.makeText(getApplicationContext(),"high",Toast.LENGTH_SHORT).show();
            if (isConnected(getApplicationContext())) {

                RadioClass.radioManager = RadioManager.with(this);

                RadioClass.radioManager.bind();
                Intent intent=new Intent(MainActivity.this,RadioClass.class);
                intent.putExtra("audio_url","http://s7.voscast.com:8772/;stream.mp3%20type:8772/;stream.mp3");
                startActivity(intent);

            } else{
                dialog_internet.show();
               // Toast.makeText(getApplicationContext(), "No internet", Toast.LENGTH_SHORT).show();
            }

        }
        if(view==btn_lowquality)
        {
            dialog.dismiss();
           // Toast.makeText(getApplicationContext(),"low",Toast.LENGTH_SHORT).show();
            if (isConnected(getApplicationContext())) {

                RadioClass.radioManager = RadioManager.with(this);

                 RadioClass.radioManager.bind();
                Intent intent=new Intent(MainActivity.this,RadioClass.class);
                intent.putExtra("audio_url","http://s8.voscast.com:7992/;stream.mp3%20type:7992/;stream.mp3");
                startActivity(intent);
            } else{
                dialog_internet.show();
               // Toast.makeText(getApplicationContext(), "No internet", Toast.LENGTH_SHORT).show();
            }
        }

        if(view==linearLayout_youtube)
        {
            if (isConnected(getApplicationContext())) {
//                Intent intent=new Intent(MainActivity.this,youtubetesting.class);
                Intent intent=new Intent(MainActivity.this,YoutubeActivity.class);
                startActivity(intent);
            } else{
                dialog_internet.show();
            }
        }

        if(view == tv_video){
            if (isConnected(getApplicationContext())) {
//               Intent intent=new Intent(MainActivity.this,youtubetesting.class);
////                Intent intent=new Intent(MainActivity.this,YoutubeActivity.class);
//                startActivity(intent);
                hitApi();
            } else{
                dialog_internet.show();

            }
        }
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
                        Intent intent=new Intent(MainActivity.this,Live_VideoPlay.class);
                        intent.putExtra("videoid",id.getString("videoId"));
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    }

                    //  setRecyclerview();

                    //  progress.dismiss();

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),"No Live Video available",Toast.LENGTH_LONG).show();
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        //dialog_exit.show();


          //RadioClass.radioManager.unbind();
       // EventBus.getDefault().unregister(this);

       // super.onBackPressed();
        //RadioClass.radioManager.unbind();
      //  EventBus.getDefault().unregister(this);
        showDialogExit();
       // finishAffinity();
       // System.exit(0);

    }
    private void showDialogExit() {

        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(MainActivity.this);
        View mView = layoutInflaterAndroid.inflate(R.layout.exitdialog, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilderUserInput.setTitle("Exit");

        alertDialogBuilderUserInput.setView(mView);
        //final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.et_Mob);
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialogBox, int id) {
                         finishAffinity();
                         System.exit(0);

                    }
                })

                .setNegativeButton("No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox,
                                                        int id) {
                                        dialogBox.cancel();
                                    }
                                });

        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();
    }

    @Override
    protected void onDestroy() {
       // RadioClass.radioManager.unbind();
       // EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void onStart() {
       // Toast.makeText(getApplicationContext(),"start",Toast.LENGTH_LONG).show();
        super.onStart();

    }

    @Override
    protected void onRestart() {
       // Toast.makeText(getApplicationContext(),"restart",Toast.LENGTH_LONG).show();
        super.onRestart();
        //RadioClass.radioManager = RadioManager.with(this);
         //EventBus.getDefault().register(this);
       // RadioClass.radioManager.bind();
    }

    public static boolean isConnected(Context context){
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected());
    }

    public static NetworkInfo getNetworkInfo(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Myapp.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showInternetStatus(isConnected);
    }

}

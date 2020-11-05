package com.virsaradio;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.virsaradio.Interfaces.ApiInterface;
import com.virsaradio.Modelclass.AllvideosGetSet;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoPlaylist extends AppCompatActivity implements View.OnClickListener{

    AllvideosGetSet allvideosGetSet;
    private RecyclerView rv;
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    LinearLayoutManager linearLayoutManager;
    private AllvideoAdapter2 adapter;
    private ProgressDialog progress;
    private ArrayList<AllvideosGetSet> arrayList = new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    String playListid;
    String str_token;
    private ImageView iv_arrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video_playlist);
        iv_arrow=(ImageView)findViewById(R.id.iv_back_arrow);
        iv_arrow.setOnClickListener(this);


        sharedPreferences =getSharedPreferences("pref", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();



        playListid=getIntent().getExtras().getString("playlist_id");
        Log.e("PlayListId",playListid);

        rv=(RecyclerView) findViewById(R.id.rv3);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.layout_swipe3);
        linearLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(linearLayoutManager);
        adapter= new AllvideoAdapter2(this,arrayList);
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // hitapi();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },3000);
            }
        });

        rv.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = linearLayoutManager.getItemCount();
                firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        //Toast.makeText(getContext(),"Load com",Toast.LENGTH_LONG).show();
                        loading = false;
                        previousTotal = totalItemCount;

                    }
                }

                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {

                     str_token=sharedPreferences.getString("pagetoken",null);
                    //Toast.makeText(getContext(),"Tokennn",Toast.LENGTH_SHORT).show();
                    if(str_token==null){
                        //  Toast.makeText(getContext(),"token null",Toast.LENGTH_SHORT).show();
                    }else {
                        if (isConnected(getApplicationContext())) {

                            getvideosnext(playListid,str_token);
                        }
                    }
                    loading = true;
                }
            }

        });

        if (isConnected(getApplicationContext())) {
            getVideos(playListid);
        } else{
            Toast.makeText(getApplicationContext(), "No internet", Toast.LENGTH_SHORT).show();
        }
    }

    private void getvideosnext(String playListid, final String str_token) {
       // Toast.makeText(getApplicationContext(),"method call",Toast.LENGTH_SHORT).show();
        ApiInterface retrofitAPI;

        retrofitAPI = Api.getClient().create(ApiInterface.class);
        Call<JsonElement> call = retrofitAPI.getYouTubePlaylistNextToken(playListid,str_token);

        progress = new ProgressDialog(this);
        progress.setMax(100);
        progress.setMessage("Loading Videos....");
        progress.setCancelable(false);
        //  progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.show();

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                try{

                    JSONObject jsonObject= new JSONObject(response.body().toString());
                    if(jsonObject.has("nextPageToken")) {
                        String str_pagetoken = jsonObject.getString("nextPageToken");
                       // Toast.makeText(getApplicationContext(), str_pagetoken, Toast.LENGTH_SHORT).show();
                        editor.putString("pagetoken", str_pagetoken);
                        editor.apply();
                    }
                    else {
                        String str_pagetoken = null;
                        // Toast.makeText(getApplicationContext(), str_pagetoken, Toast.LENGTH_SHORT).show();
                        editor.putString("pagetoken", str_pagetoken);
                        editor.apply();
                    //editor.clear();
                    }
                    JSONArray jsonArray=jsonObject.getJSONArray("items");

                    //   Log.e("Array length",jsonArray.length()+"");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                        JSONObject jsonObject4 = jsonObject1.getJSONObject("snippet");

                        if(jsonObject4.has("thumbnails")) {
                            JSONObject jsonObject2 = jsonObject4.getJSONObject("thumbnails");
                            JSONObject jsonObject3 = jsonObject2.getJSONObject("medium");
                            Log.e("Image url", jsonObject3.getString("url"));
                            JSONObject jsonObject5 = jsonObject4.getJSONObject("resourceId");
                            Log.e("VideoId", jsonObject5.getString("videoId"));
                            allvideosGetSet = new AllvideosGetSet(jsonObject4.getString("title"), jsonObject3.getString("url"), jsonObject5.getString("videoId"));
                        }
                        else
                        {
                            JSONObject jsonObject5 = jsonObject4.getJSONObject("resourceId");
                            Log.e("VideoId", jsonObject5.getString("videoId"));
                            allvideosGetSet = new AllvideosGetSet(jsonObject4.getString("title"),jsonObject4.getString("title"), jsonObject5.getString("videoId"));
                        }
                        arrayList.add(allvideosGetSet);
                    }
                    adapter.notifyDataSetChanged();

                }catch (Exception e){

                    e.printStackTrace();
                }
                progress.dismiss();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });

    }


    public void getVideos(String playListid){

        ApiInterface retrofitAPI;

        retrofitAPI = Api.getClient().create(ApiInterface.class);
        Call<JsonElement> call = retrofitAPI.getYouTubePlaylistNext(playListid);

        progress = new ProgressDialog(this);
        progress.setMax(100);
        progress.setMessage("Loading Videos....");
        progress.setCancelable(false);
        //  progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.show();

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                try{

                    JSONObject jsonObject= new JSONObject(response.body().toString());
                    if(jsonObject.has("nextPageToken")) {
                        String str_pagetoken = jsonObject.getString("nextPageToken");
                       // Toast.makeText(getApplicationContext(), str_pagetoken, Toast.LENGTH_SHORT).show();
                        editor.putString("pagetoken", str_pagetoken);
                        editor.apply();
                    }
                    else {
                        String str_pagetoken = null;
                        // Toast.makeText(getApplicationContext(), str_pagetoken, Toast.LENGTH_SHORT).show();
                        editor.putString("pagetoken", str_pagetoken);
                        editor.apply();
                        //editor.clear();
                    }
                    JSONArray jsonArray=jsonObject.getJSONArray("items");

                 //   Log.e("Array length",jsonArray.length()+"");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                        JSONObject jsonObject4 = jsonObject1.getJSONObject("snippet");

                        if(jsonObject4.has("thumbnails")) {
                            JSONObject jsonObject2 = jsonObject4.getJSONObject("thumbnails");
                            JSONObject jsonObject3 = jsonObject2.getJSONObject("medium");
                            Log.e("Image url", jsonObject3.getString("url"));
                            JSONObject jsonObject5 = jsonObject4.getJSONObject("resourceId");
                            Log.e("VideoId", jsonObject5.getString("videoId"));
                            allvideosGetSet = new AllvideosGetSet(jsonObject4.getString("title"), jsonObject3.getString("url"), jsonObject5.getString("videoId"));
                        }else
                        {
                            JSONObject jsonObject5 = jsonObject4.getJSONObject("resourceId");
                            Log.e("VideoId", jsonObject5.getString("videoId"));
                            allvideosGetSet = new AllvideosGetSet(jsonObject4.getString("title"),jsonObject4.getString("title"), jsonObject5.getString("videoId"));
                        }
                        arrayList.add(allvideosGetSet);
                    }
                    adapter.notifyDataSetChanged();

                }catch (Exception e){

                    e.printStackTrace();
                }
                progress.dismiss();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });

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
    public void onBackPressed() {
        //editor.clear();
        //super.onBackPressed();
        Intent in=new Intent(VideoPlaylist.this,YoutubeActivity.class);
        startActivity(in);
    }

    @Override
    public void onClick(View view) {
        if(view==iv_arrow)
        {
            Intent in=new Intent(VideoPlaylist.this,YoutubeActivity.class);
            startActivity(in);
        }

    }
}
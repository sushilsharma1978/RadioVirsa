package com.virsaradio.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.virsaradio.Api;
import com.virsaradio.Interfaces.ApiInterface;
import com.virsaradio.Modelclass.PlaylistVideosGetset;
import com.virsaradio.PlaylistAdapter;
import com.virsaradio.R;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;

/**
 * Created by khushdeep-android on 11/4/18.
 */

public class FragmentPlaylistVideos extends Fragment
{
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ProgressDialog progress;
    PlaylistVideosGetset playlistVideosGetset;
    PlaylistAdapter playlistAdapter;
    ArrayList<PlaylistVideosGetset> arrayList = new ArrayList<>();
    private FragmentPlaylistVideos.OnFragmentInteractionListener mListener;


    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_playlist_videos, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        Init();
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

        hitapi();
    }

    private void hitapi()
    {
        ApiInterface retrofitAPI;
        retrofitAPI = Api.getClient().create(ApiInterface.class);


        Call<JsonElement> call = retrofitAPI.getYouTubePlaylist();

        progress = new ProgressDialog(getContext());
        progress.setMax(100);
        progress.setMessage("Loading Videos");
        progress.setCancelable(false);
        progress.show();
        call.enqueue(new retrofit2.Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, retrofit2.Response<JsonElement> response) {

                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    JSONArray jsonArray=jsonObject.getJSONArray("items");

                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject json_items = jsonArray.getJSONObject(i);
                        JSONObject snippet = json_items.getJSONObject("snippet");
                        if(snippet.has("thumbnails"))
                        {
                            JSONObject thumbnails = snippet.getJSONObject("thumbnails");
                            JSONObject medium = thumbnails.getJSONObject("medium");
                            playlistVideosGetset = new PlaylistVideosGetset(snippet.getString("title"), medium.getString("url"),json_items.getString("id"));
                        }
                        else {

                        }
                  arrayList.add(playlistVideosGetset);


                    }

                    setRecyclerview();

                    progress.dismiss();

                } catch (JSONException e) {

                    e.printStackTrace();
                    progress.dismiss();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                //Toast.makeText(getActivity(),"error",Toast.LENGTH_LONG).show();
                progress.dismiss();
            }
        });
    }

    private void setRecyclerview() {
        playlistAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(playlistAdapter);
        playlistAdapter.notifyDataSetChanged();
    }

    private void Init() {

        swipeRefreshLayout=(SwipeRefreshLayout)getActivity().findViewById(R.id.layout_swipeplaylist);
        recyclerView=(RecyclerView)getActivity().findViewById(R.id.rv2);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        playlistAdapter= new PlaylistAdapter(getActivity(),arrayList);
        recyclerView.setAdapter(playlistAdapter);
        playlistAdapter.notifyDataSetChanged();


        //sharedPreferences =getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
       // editor=sharedPreferences.edit();
    }
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
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

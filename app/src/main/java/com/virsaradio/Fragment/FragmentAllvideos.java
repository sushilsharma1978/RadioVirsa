package com.virsaradio.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.google.gson.JsonElement;
import com.virsaradio.AllvideoAdapter;
import com.virsaradio.Api;
import com.virsaradio.Interfaces.ApiInterface;
import com.virsaradio.Modelclass.AllvideosGetSet;
import com.virsaradio.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;


/**
 * Created by khushdeep-android on 11/4/18.
 */

public class FragmentAllvideos extends Fragment
{

    public FragmentAllvideos()
    {

        setArguments(new Bundle());

    }
    private Parcelable recyclerViewState;

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    AllvideosGetSet allvideosGetSet;
    ProgressDialog progress;
    AllvideoAdapter allvideoAdapter;
    private ArrayList<AllvideosGetSet> arrayList = new ArrayList<>();
    private OnFragmentInteractionListener mListener;
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_all_videos, container, false);

    }

    @Override
    public void onPause() {

        super.onPause();

        getArguments().putInt("previousTotal",previousTotal);
        getArguments().putBoolean("loading",loading);
        getArguments().putInt("visibleThreshold",visibleThreshold);
        getArguments().putInt("firstVisibleItem",firstVisibleItem);
        getArguments().putInt("visibleItemCount",visibleItemCount);
        getArguments().putInt("totalItemCount",totalItemCount);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {

        super.onAttachFragment(childFragment);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore last state for checked position.
//            previousTotal = savedInstanceState.getInt("previousTotal", 0);
//            loading = savedInstanceState.getBoolean("loading");
//            visibleThreshold = savedInstanceState.getInt("visibleThreshold", 0);
//            firstVisibleItem = savedInstanceState.getInt("firstVisibleItem", 0);
//            visibleItemCount = savedInstanceState.getInt("visibleItemCount", 0);
//            totalItemCount = savedInstanceState.getInt("totalItemCount", 0);

        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        Init();
        recyclerView.setOnScrollListener( new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = linearLayoutManager.getItemCount();
                firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
                recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();

                if (loading) {
                    if (totalItemCount > previousTotal) {

                        loading = false;
                        previousTotal = totalItemCount;

                    }
                }

                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {

                    String str_token=sharedPreferences.getString("pagetoken",null);

                    if(str_token==null){

                    }else {
                        if (isConnected(getContext())) {

                            getvideosnext(str_token);
                        }
                    }
                    loading = true;
                }
            }

        });




        hitApi2();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        //hitApi2();

                    }
                },3000);
            }
        });


    }

    private void getvideosnext(String str_token)
    {
        ApiInterface retrofitAPI;
        retrofitAPI = Api.getClient().create(ApiInterface.class);


        Call<JsonElement> call = retrofitAPI.getYouTubeVideosNext(str_token);

        progress = new ProgressDialog(getContext());
        progress.setMax(100);
        progress.setMessage("Loading Videos");
        progress.setCancelable(false);
        progress.show();
        call.enqueue(new retrofit2.Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, retrofit2.Response<JsonElement> response) {
                // Toast.makeText(getActivity(),"success",Toast.LENGTH_LONG).show();
                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());

                    String str_pagetoken=jsonObject.getString("nextPageToken");

                    editor.putString("pagetoken",str_pagetoken);
                    editor.apply();

                    JSONArray jsonArray=jsonObject.getJSONArray("items");


                    for(int i=0;i<jsonArray.length();i++)
                    {


                        JSONObject json_items = jsonArray.getJSONObject(i);

                        //   Log.e("Video Id", jsonObject5.getString("videoId"));
                        JSONObject snippet = json_items.getJSONObject("snippet");

                        if(snippet.has("thumbnails"))
                        {
                            JSONObject thumbnails = snippet.getJSONObject("thumbnails");
                            JSONObject medium = thumbnails.getJSONObject("medium");
                            JSONObject json_id = json_items.getJSONObject("id");

                            //Log.e("Image url", jsonObject3.getString("url"));


                            allvideosGetSet = new AllvideosGetSet(snippet.getString("title"), medium.getString("url"),json_id.getString("videoId"));
                        }
                        else {

                        }
                        arrayList.add(allvideosGetSet);

                    }

                    setRecyclerview();
                    recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);

                    progress.dismiss();

                } catch (JSONException e) {

                    e.printStackTrace();
                    progress.dismiss();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toast.makeText(getActivity(),"error",Toast.LENGTH_LONG).show();
                progress.dismiss();
            }
        });
    }

    private void setRecyclerview()
    {
        //allvideoAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(allvideoAdapter);
    }

    private void hitApi2()
    {
        ApiInterface retrofitAPI;
        retrofitAPI = Api.getClient().create(ApiInterface.class);

        Call<JsonElement> call = retrofitAPI.getuserdetails();

        progress = new ProgressDialog(getContext());
        progress.setMax(100);
        progress.setMessage("Loading Videos");
        progress.setCancelable(false);
        progress.show();
        call.enqueue(new retrofit2.Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, retrofit2.Response<JsonElement> response) {
               // Toast.makeText(getActivity(),"success",Toast.LENGTH_LONG).show();
                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    String str_pagetoken=jsonObject.getString("nextPageToken");
                    editor.putString("pagetoken",str_pagetoken);
                    editor.apply();
                    // Toast.makeText(getContext(),str_token,Toast.LENGTH_LONG).show();
                    JSONArray jsonArray=jsonObject.getJSONArray("items");

                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject json_items = jsonArray.getJSONObject(i);

                     //   Log.e("Video Id", jsonObject5.getString("videoId"));
                        JSONObject snippet = json_items.getJSONObject("snippet");

                        if(snippet.has("thumbnails"))
                        {
                            JSONObject thumbnails = snippet.getJSONObject("thumbnails");
                            JSONObject medium = thumbnails.getJSONObject("medium");
                            JSONObject json_id = json_items.getJSONObject("id");
                            //Log.e("Image url", jsonObject3.getString("url"));
                            allvideosGetSet = new AllvideosGetSet(snippet.getString("title"), medium.getString("url"),json_id.getString("videoId"));
                        }

                        arrayList.add(allvideosGetSet);

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



    private void Init() {
    swipeRefreshLayout=(SwipeRefreshLayout)getActivity().findViewById(R.id.layout_swipe);
    recyclerView=(RecyclerView)getActivity().findViewById(R.id.rv);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        allvideoAdapter= new AllvideoAdapter(getActivity(),arrayList);
        recyclerView.setAdapter(allvideoAdapter);
        sharedPreferences =getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        //allvideoAdapter.notifyDataSetChanged();



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

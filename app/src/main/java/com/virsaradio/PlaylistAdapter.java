package com.virsaradio;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.virsaradio.Modelclass.PlaylistVideosGetset;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by khushdeep-android on 12/4/18.
 */

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.MyViewHolder>
{
    ArrayList<PlaylistVideosGetset>arrayList=new
            ArrayList<>();
    FragmentActivity context;

    public PlaylistAdapter(FragmentActivity activity, ArrayList<PlaylistVideosGetset> arrayList)
    {
        this.context=activity;
        this.arrayList=arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_playlist_videos, parent, false);
        // set the view's size, margins, paddings and layout parameters
        PlaylistAdapter.MyViewHolder vh = new PlaylistAdapter.MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final PlaylistVideosGetset playlistVideosGetset = arrayList.get(position);

        holder.tv_title.setText(playlistVideosGetset.getTitle2());
        Picasso.with(context)
                .load(playlistVideosGetset.getImage_url2())
                .into(holder.iv_video);

        holder.rv_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(context,playlistVideosGetset.getVideo_id2(),Toast.LENGTH_SHORT).show();
                String id=playlistVideosGetset.getVideo_id2();
                Intent intent=new Intent(context,VideoPlaylist.class);
                intent.putExtra("playlist_id",id);
                Log.i("playlisttttttt",id);
                context.startActivity(intent);
                //
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView iv_video;
        TextView tv_title;
        LinearLayout rv_view;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv_video=(ImageView)itemView.findViewById(R.id.iv_video2);
            tv_title=(TextView)itemView.findViewById(R.id.tv_title2);
            rv_view=(LinearLayout)itemView.findViewById(R.id.rv_view2);
        }
    }
}

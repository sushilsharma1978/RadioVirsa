package com.virsaradio;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.virsaradio.Modelclass.AllvideosGetSet;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by khushdeep-android on 17/4/18.
 */

public class AllvideoAdapter2 extends RecyclerView.Adapter<AllvideoAdapter2.MyViewHolder>
{

    ArrayList<AllvideosGetSet> arrayList=new ArrayList<>();
    Context context;
    public AllvideoAdapter2(Activity activity, ArrayList<AllvideosGetSet> arrayList) {
        this.context=activity;
        this.arrayList=arrayList;

    }

    @Override
    public AllvideoAdapter2.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_all_videos, parent, false);

        AllvideoAdapter2.MyViewHolder vh = new AllvideoAdapter2.MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(AllvideoAdapter2.MyViewHolder holder, int position) {
        final AllvideosGetSet allvideosGetSet = arrayList.get(position);
        holder.tv_title.setText(allvideosGetSet.getTitle());
        Picasso.with(context)
                .load(allvideosGetSet.getImage())
                .into(holder.iv_video);

        holder.rv_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id=allvideosGetSet.getVideo_id();

                Intent intent=new Intent(context,YoutubePlayervideos.class);
                intent.putExtra("videoid",id);
                context.startActivity(intent);
            }
        });


    }






    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_video;
        TextView tv_title;
        LinearLayout rv_view;
        public MyViewHolder(View itemView) {
            super(itemView);
            iv_video=(ImageView)itemView.findViewById(R.id.iv_video);
            tv_title=(TextView)itemView.findViewById(R.id.tv_title);
            rv_view=(LinearLayout)itemView.findViewById(R.id.rv_view);
        }
    }
}

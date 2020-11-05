package com.virsaradio;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
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
 * Created by khushdeep-android on 11/4/18.
 */

public class AllvideoAdapter extends RecyclerView.Adapter<AllvideoAdapter.MyViewHolder>
{

    ArrayList<AllvideosGetSet>arrayList=new ArrayList<>();
    FragmentActivity context;
    public AllvideoAdapter(FragmentActivity activity, ArrayList<AllvideosGetSet> arrayList) {
        this.context=activity;
        this.arrayList=arrayList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_all_videos, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,  int position) {
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

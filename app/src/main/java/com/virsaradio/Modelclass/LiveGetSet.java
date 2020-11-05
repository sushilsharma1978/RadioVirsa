package com.virsaradio.Modelclass;

public class LiveGetSet {
    String videoId;



    public String getVideo_id()
    {
        return videoId;
    }

    public void setVideo_id(String video_id)
    {
        this.videoId = video_id;
    }

    public LiveGetSet(String videoId) {
        this.videoId = videoId;
    }

}

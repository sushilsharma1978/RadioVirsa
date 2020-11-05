package com.virsaradio.Modelclass;

/**
 * Created by khushdeep-android on 12/4/18.
 */

public class PlaylistVideosGetset
{
    String title2;
    String image_url2;
    String video_id2;

    public String getVideo_id2() {
        return video_id2;
    }

    public void setVideo_id2(String video_id2) {
        this.video_id2 = video_id2;
    }

    public String getTitle2() {
        return title2;
    }

    public void setTitle2(String title) {
        this.title2 = title;
    }

    public String getImage_url2() {
        return image_url2;
    }

    public void setImage_url2(String image_url) {
        this.image_url2 = image_url;
    }


    public PlaylistVideosGetset(String title, String img_url,String id)
    {

        this.title2 = title;
        this.image_url2=img_url;
        this.video_id2=id;


    }
}

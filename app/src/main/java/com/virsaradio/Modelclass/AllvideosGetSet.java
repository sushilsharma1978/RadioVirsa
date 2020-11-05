package com.virsaradio.Modelclass;

/**
 * Created by khushdeep-android on 11/4/18.
 */

public class AllvideosGetSet
{
    String title;
    String image_url;
    String video_id;

    public String getVideo_id()
    {
        return video_id;
    }

    public void setVideo_id(String video_id)
    {
        this.video_id = video_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image_url;
    }

    public void setImage(String image) {
        this.image_url = image;
    }
    public AllvideosGetSet(String title, String img_url,String video_id)
    {

        this.title = title;
        this.image_url=img_url;
        this.video_id=video_id;

    }
}

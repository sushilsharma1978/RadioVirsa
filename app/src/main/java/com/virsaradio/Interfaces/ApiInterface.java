package com.virsaradio.Interfaces;

import com.google.gson.JsonElement;

import retrofit2.http.GET;
import retrofit2.Call;
import retrofit2.http.Query;

/**
 * Created by khushdeep-android on 11/4/18.
 */

public interface ApiInterface
{
//    AIzaSyD63ufiralo4kbpHKNSrL0Y2mOtLuN84_w

    @GET("search?key=AIzaSyDy3hmHnvyw4KicUQB7NkRMiAzos-YHZZg&channelId=UCDv5rzPo0NPMTyWbRedp8Dg&part=snippet,id&order=date&maxResults=20")
    Call<JsonElement> getuserdetails();
//    UCDv5rzPo0NPMTyWbRedp8Dg
    @GET("search?key=AIzaSyDy3hmHnvyw4KicUQB7NkRMiAzos-YHZZg&channelId=UCDv5rzPo0NPMTyWbRedp8Dg&part=snippet,id&order=date&maxResults=20")
    Call<JsonElement> getYouTubeVideosNext(@Query("pageToken") String pageToken);
//    AIzaSyCCHuayCrwwcRAUZ__zTYyOP-ax5FD4R9E
//    UC_8MgB59_2_7kOkas6DvEnA
    @GET("playlists?key=AIzaSyDy3hmHnvyw4KicUQB7NkRMiAzos-YHZZg&channelId=UCDv5rzPo0NPMTyWbRedp8Dg&part=snippet,id,contentDetails&maxResults=20")
    Call<JsonElement> getYouTubePlaylist();

    @GET("playlistItems?key=AIzaSyDy3hmHnvyw4KicUQB7NkRMiAzos-YHZZg&channelId=UCDv5rzPo0NPMTyWbRedp8Dg&part=snippet,id&maxResults=20")
    Call<JsonElement> getYouTubePlaylistNext(@Query("playlistId") String playlistId);

    @GET("playlistItems?key=AIzaSyDy3hmHnvyw4KicUQB7NkRMiAzos-YHZZg&channelId=UCDv5rzPo0NPMTyWbRedp8Dg&part=snippet,id&maxResults=20")
    Call<JsonElement> getYouTubePlaylistNextToken(@Query("playlistId") String playlistId,@Query("pageToken") String pageToken);

    @GET("search?key=AIzaSyDy3hmHnvyw4KicUQB7NkRMiAzos-YHZZg&channelId=UCDv5rzPo0NPMTyWbRedp8Dg&part=snippet,id&order=date&maxResults=20&eventType=live&type=video")
    Call<JsonElement> getLiveyoutubeVideo();

}

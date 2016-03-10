package primr.apps.eurakacachet.ryme.ryme.utils.player.dataInterface;


import android.support.annotation.NonNull;

import com.devbrackets.android.playlistcore.manager.BasePlaylistManager;
import com.devbrackets.android.playlistcore.manager.IPlaylistItem;

import primr.apps.eurakacachet.ryme.ryme.data.model.AudioAd;
import primr.apps.eurakacachet.ryme.ryme.data.model.Track;

public class PCOnlineAudioItemInterface implements IPlaylistItem {

    private String uuid;
    private String cover;
    private String mediaUrl;
    private String title;
    private String artist;
    boolean isAudio;

    public PCOnlineAudioItemInterface(Track track){
        title = track.title();
        artist = track.artist_name();
        cover = track.cover();
        mediaUrl = track.path();
        uuid = track.uuid();
        isAudio = true;
    }

    public PCOnlineAudioItemInterface(@NonNull AudioAd ad, @NonNull String pTitle,
                                    @NonNull String pArtist, @NonNull String pCover){
        title = pTitle;
        artist = pArtist;
        cover = pCover;
        mediaUrl = ad.path();
        uuid = ad.uuid();
        isAudio = true;
    }

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public long getPlaylistId() {
        return 0;
    }

    @Override
    public int getMediaType() {
        return isAudio ? BasePlaylistManager.AUDIO : BasePlaylistManager.VIDEO;
    }

    @Override
    public String getMediaUrl() {
//        return mediaUrl;
        return "http://mp3loaf.com/music/down/32139660/1161193/ZmU1MDMrRnl5ellJMU1kZm1wNXBVdE0rRW1MTHFIUmhFM2Q0dWluWjJjcy82emZOQmc=/11.+Lady+In+A+Glass+Dress+%28Interlude%29.mp3";
    }

    @Override
    public String getDownloadedMediaUri() {
        return null;
    }

    @Override
    public String getThumbnailUrl() {
        return cover;
    }

    @Override
    public String getArtworkUrl() {
        return cover;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getAlbum() {
        return "Ryme";
    }

    @Override
    public String getArtist() {
        return artist;
    }

    public String getUuid() {
        return uuid;
    }
}

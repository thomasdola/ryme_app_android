package primr.apps.eurakacachet.ryme.ryme.utils.player.dataInterface;


import com.devbrackets.android.exomedia.manager.EMPlaylistManager;

import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.SavedTrack;

public class OfflineAudioItemInterface implements EMPlaylistManager.PlaylistItem {

    private String cover;
    private String mediaUrl;
    private String title;
    private String artist;
    private String uuid;
    boolean isAudio;

    public OfflineAudioItemInterface(SavedTrack track){
        title = track.title();
        artist = track.artist();
        cover = track.cover();
        mediaUrl = track.path();
        uuid = track.uuid();
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
    public EMPlaylistManager.MediaType getMediaType() {
        return isAudio ? EMPlaylistManager.MediaType.AUDIO : EMPlaylistManager.MediaType.VIDEO;
    }

    @Override
    public String getMediaUrl() {
        return mediaUrl;
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
        return null;
    }

    @Override
    public String getArtist() {
        return artist;
    }

    public String getUuid(){
        return uuid;
    }
}

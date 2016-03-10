package primr.apps.eurakacachet.ryme.ryme.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class Track implements Parcelable {

    @NonNull
    String title;

    @NonNull
    String artist_name;

    @NonNull
    long released_date;

    @NonNull
    long downloads;

    @NonNull
    long likes;

    @NonNull
    long streams;

    @NonNull
    long comments;

    @NonNull
    boolean downloadable;

    @NonNull
    String artistId;

    @NonNull
    String uuid;

    @NonNull
    String cover;

    @NonNull
    String path;

    @NonNull
    String trackExt;

    @NonNull
    long duration;

    @NonNull
    String coverExt;

    @NonNull
    boolean downloaded;

    @NonNull
    boolean liked;

    @NonNull
    boolean amTheOwner;

    @Nullable
    AudioAd firstAd;

    @Nullable
    AudioAd secondAd;

    @NonNull
    public static Track newTrack(@NonNull String title, @NonNull String artist_name,
                                 @NonNull long released_date, @NonNull long downloads,
                                 @NonNull long likes, @NonNull long streams, @NonNull long comments,
                                 @NonNull boolean downloadable, @NonNull String artistId,
                                 @NonNull String uuid, @NonNull String cover, @NonNull String path,
                                 @NonNull String trackExt, @NonNull String coverExt,
                                 @NonNull boolean downloaded, @NonNull boolean amTheOwner, @NonNull boolean liked,
                                 @NonNull long duration, @Nullable AudioAd firstAd,
                                 @Nullable AudioAd secondAd){
        Track track = new Track();
        track.title = title;
        track.artist_name = artist_name;
        track.released_date = released_date;
        track.downloads = downloads;
        track.likes = likes;
        track.streams = streams;
        track.comments = comments;
        track.downloadable = downloadable;
        track.artistId = artistId;
        track.uuid = uuid;
        track.cover = cover;
        track.path = path;
        track.trackExt = trackExt;
        track.duration = duration;
        track.coverExt = coverExt;
        track.downloaded = downloaded;
        track.liked = liked;
        track.amTheOwner = amTheOwner;
        track.firstAd = firstAd;
        track.secondAd = secondAd;
        return track;
    }

    @NonNull
    public String artist_name() {
        return artist_name;
    }

    @NonNull
    public long released_date() {
        return released_date;
    }

    @NonNull
    public long downloads() {
        return downloads;
    }

    @NonNull
    public long likes() {
        return likes;
    }

    @NonNull
    public long streams() {
        return streams;
    }

    @NonNull
    public long comments() {
        return comments;
    }

    @NonNull
    public long duration() {return duration; }

    @NonNull
    public boolean liked() {
        return liked;
    }

    @NonNull
    public boolean downloadable() {
        return downloadable;
    }

    @NonNull
    public boolean downloaded() {
        return downloaded;
    }

    @NonNull
    public boolean amTheOwner() {
        return amTheOwner;
    }

    @NonNull
    public String artistId() {
        return artistId;
    }

    @NonNull
    public String uuid() {
        return uuid;
    }

    @Nullable
    public String cover() {
        return cover;
    }

    @NonNull
    public String title(){
        return title;
    }

    @NonNull
    public String path(){
        return path;
    }

    @Nullable
    public AudioAd firstAd(){
        return firstAd;
    }

    @Nullable
    public AudioAd secondAd(){
        return secondAd;
    }

    @NonNull
    public String trackExt(){
        return trackExt;
    }

    @NonNull
    public String coverExt(){
        return coverExt;
    }

    public Track() {
    }

    @Override
    public String toString() {
        return "Track{" +
                "title='" + title + '\'' +
                ", artist_name='" + artist_name + '\'' +
                ", released_date=" + released_date +
                ", downloads=" + downloads +
                ", likes=" + likes +
                ", streams=" + streams +
                ", comments=" + comments +
                ", downloadable=" + downloadable +
                ", artistId='" + artistId + '\'' +
                ", uuid='" + uuid + '\'' +
                ", cover='" + cover + '\'' +
                ", path='" + path + '\'' +
                ", trackExt='" + trackExt + '\'' +
                ", duration=" + duration +
                ", coverExt='" + coverExt + '\'' +
                ", downloaded=" + downloaded +
                ", liked=" + liked +
                ", amTheOwner=" + amTheOwner +
                ", firstAd=" + firstAd +
                ", secondAd=" + secondAd +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.artist_name);
        dest.writeLong(this.released_date);
        dest.writeLong(this.downloads);
        dest.writeLong(this.likes);
        dest.writeLong(this.streams);
        dest.writeLong(this.comments);
        dest.writeByte(downloadable ? (byte) 1 : (byte) 0);
        dest.writeString(this.artistId);
        dest.writeString(this.uuid);
        dest.writeString(this.cover);
        dest.writeString(this.path);
        dest.writeString(this.trackExt);
        dest.writeLong(this.duration);
        dest.writeString(this.coverExt);
        dest.writeByte(downloaded ? (byte) 1 : (byte) 0);
        dest.writeByte(liked ? (byte) 1 : (byte) 0);
        dest.writeByte(amTheOwner ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.firstAd, 0);
        dest.writeParcelable(this.secondAd, 0);
    }

    protected Track(Parcel in) {
        this.title = in.readString();
        this.artist_name = in.readString();
        this.released_date = in.readLong();
        this.downloads = in.readLong();
        this.likes = in.readLong();
        this.streams = in.readLong();
        this.comments = in.readLong();
        this.downloadable = in.readByte() != 0;
        this.artistId = in.readString();
        this.uuid = in.readString();
        this.cover = in.readString();
        this.path = in.readString();
        this.trackExt = in.readString();
        this.duration = in.readLong();
        this.coverExt = in.readString();
        this.downloaded = in.readByte() != 0;
        this.liked = in.readByte() != 0;
        this.amTheOwner = in.readByte() != 0;
        this.firstAd = in.readParcelable(AudioAd.class.getClassLoader());
        this.secondAd = in.readParcelable(AudioAd.class.getClassLoader());
    }

    public static final Creator<Track> CREATOR = new Creator<Track>() {
        public Track createFromParcel(Parcel source) {
            return new Track(source);
        }

        public Track[] newArray(int size) {
            return new Track[size];
        }
    };
}

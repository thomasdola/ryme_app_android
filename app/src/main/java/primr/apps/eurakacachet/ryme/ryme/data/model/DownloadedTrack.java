package primr.apps.eurakacachet.ryme.ryme.data.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

public class DownloadedTrack implements Parcelable {

    public UUID uuid;
    public String title;
    public String artist;
    public String path;
    public long duration;
    public String cover;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DownloadedTrack track = (DownloadedTrack) o;

        if (duration != track.duration) return false;
        if (uuid != null ? !uuid.equals(track.uuid) : track.uuid != null) return false;
        if (title != null ? !title.equals(track.title) : track.title != null) return false;
        if (artist != null ? !artist.equals(track.artist) : track.artist != null) return false;
        if (path != null ? !path.equals(track.path) : track.path != null) return false;
        return !(cover != null ? !cover.equals(track.cover) : track.cover != null);

    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (artist != null ? artist.hashCode() : 0);
        result = 31 * result + (path != null ? path.hashCode() : 0);
        result = 31 * result + (int) (duration ^ (duration >>> 32));
        result = 31 * result + (cover != null ? cover.hashCode() : 0);
        return result;
    }

    public DownloadedTrack() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.uuid);
        dest.writeString(this.title);
        dest.writeString(this.artist);
        dest.writeString(this.path);
        dest.writeLong(this.duration);
        dest.writeString(this.cover);
    }

    protected DownloadedTrack(Parcel in) {
        this.uuid = (UUID) in.readSerializable();
        this.title = in.readString();
        this.artist = in.readString();
        this.path = in.readString();
        this.duration = in.readLong();
        this.cover = in.readString();
    }

    public static final Creator<DownloadedTrack> CREATOR = new Creator<DownloadedTrack>() {
        public DownloadedTrack createFromParcel(Parcel source) {
            return new DownloadedTrack(source);
        }

        public DownloadedTrack[] newArray(int size) {
            return new DownloadedTrack[size];
        }
    };
}

package primr.apps.eurakacachet.ryme.ryme.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.UUID;

public class Track implements Parcelable {

    public String title;
    public Date released_date;
    public long downloads;
    public long likes;
    public long streams;
    public long comments;
    public boolean downloadable;
    public UUID artistId;
    public UUID uuid;
    public String cover;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Track track = (Track) o;

        if (downloads != track.downloads) return false;
        if (likes != track.likes) return false;
        if (streams != track.streams) return false;
        if (comments != track.comments) return false;
        if (downloadable != track.downloadable) return false;
        if (title != null ? !title.equals(track.title) : track.title != null) return false;
        if (released_date != null ? !released_date.equals(track.released_date) : track.released_date != null)
            return false;
        if (artistId != null ? !artistId.equals(track.artistId) : track.artistId != null)
            return false;
        if (uuid != null ? !uuid.equals(track.uuid) : track.uuid != null) return false;
        return !(cover != null ? !cover.equals(track.cover) : track.cover != null);

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (released_date != null ? released_date.hashCode() : 0);
        result = 31 * result + (int) (downloads ^ (downloads >>> 32));
        result = 31 * result + (int) (likes ^ (likes >>> 32));
        result = 31 * result + (int) (streams ^ (streams >>> 32));
        result = 31 * result + (int) (comments ^ (comments >>> 32));
        result = 31 * result + (downloadable ? 1 : 0);
        result = 31 * result + (artistId != null ? artistId.hashCode() : 0);
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        result = 31 * result + (cover != null ? cover.hashCode() : 0);
        return result;
    }

    public Track() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeLong(released_date != null ? released_date.getTime() : -1);
        dest.writeLong(this.downloads);
        dest.writeLong(this.likes);
        dest.writeLong(this.streams);
        dest.writeLong(this.comments);
        dest.writeByte(downloadable ? (byte) 1 : (byte) 0);
        dest.writeSerializable(this.artistId);
        dest.writeSerializable(this.uuid);
        dest.writeString(this.cover);
    }

    protected Track(Parcel in) {
        this.title = in.readString();
        long tmpReleased_date = in.readLong();
        this.released_date = tmpReleased_date == -1 ? null : new Date(tmpReleased_date);
        this.downloads = in.readLong();
        this.likes = in.readLong();
        this.streams = in.readLong();
        this.comments = in.readLong();
        this.downloadable = in.readByte() != 0;
        this.artistId = (UUID) in.readSerializable();
        this.uuid = (UUID) in.readSerializable();
        this.cover = in.readString();
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

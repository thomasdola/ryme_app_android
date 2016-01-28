package primr.apps.eurakacachet.ryme.ryme.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Track implements Parcelable {

    public String title;
    public Date released_date;
    public long downloads;
    public long likes;
    public long streams;
    public boolean downloadable;
    public List<Comment> comments;
    public Artist artist;
    public UUID uuid;

    public Track() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Track track = (Track) o;

        if (downloads != track.downloads) return false;
        if (likes != track.likes) return false;
        if (streams != track.streams) return false;
        if (downloadable != track.downloadable) return false;
        if (title != null ? !title.equals(track.title) : track.title != null) return false;
        if (released_date != null ? !released_date.equals(track.released_date) : track.released_date != null)
            return false;
        if (comments != null ? !comments.equals(track.comments) : track.comments != null)
            return false;
        if (artist != null ? !artist.equals(track.artist) : track.artist != null) return false;
        return !(uuid != null ? !uuid.equals(track.uuid) : track.uuid != null);

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (released_date != null ? released_date.hashCode() : 0);
        result = 31 * result + (int) (downloads ^ (downloads >>> 32));
        result = 31 * result + (int) (likes ^ (likes >>> 32));
        result = 31 * result + (int) (streams ^ (streams >>> 32));
        result = 31 * result + (downloadable ? 1 : 0);
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
        result = 31 * result + (artist != null ? artist.hashCode() : 0);
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        return result;
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
        dest.writeByte(downloadable ? (byte) 1 : (byte) 0);
        dest.writeTypedList(comments);
        dest.writeParcelable(this.artist, 0);
        dest.writeSerializable(this.uuid);
    }

    protected Track(Parcel in) {
        this.title = in.readString();
        long tmpReleased_date = in.readLong();
        this.released_date = tmpReleased_date == -1 ? null : new Date(tmpReleased_date);
        this.downloads = in.readLong();
        this.likes = in.readLong();
        this.streams = in.readLong();
        this.downloadable = in.readByte() != 0;
        this.comments = in.createTypedArrayList(Comment.CREATOR);
        this.artist = in.readParcelable(Artist.class.getClassLoader());
        this.uuid = (UUID) in.readSerializable();
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

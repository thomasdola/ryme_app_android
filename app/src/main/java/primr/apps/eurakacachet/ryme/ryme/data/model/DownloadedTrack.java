package primr.apps.eurakacachet.ryme.ryme.data.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

public class DownloadedTrack implements Parcelable {

    public long id;
    public UUID uuid;
    public String title;
    public String path;
    public String cover;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DownloadedTrack that = (DownloadedTrack) o;

        if (id != that.id) return false;
        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (path != null ? !path.equals(that.path) : that.path != null) return false;
        return !(cover != null ? !cover.equals(that.cover) : that.cover != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (path != null ? path.hashCode() : 0);
        result = 31 * result + (cover != null ? cover.hashCode() : 0);
        return result;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeSerializable(this.uuid);
        dest.writeString(this.title);
        dest.writeString(this.path);
        dest.writeString(this.cover);
    }

    public DownloadedTrack() {
    }

    protected DownloadedTrack(Parcel in) {
        this.id = in.readLong();
        this.uuid = (UUID) in.readSerializable();
        this.title = in.readString();
        this.path = in.readString();
        this.cover = in.readString();
    }

    public static final Parcelable.Creator<DownloadedTrack> CREATOR = new Parcelable.Creator<DownloadedTrack>() {
        public DownloadedTrack createFromParcel(Parcel source) {
            return new DownloadedTrack(source);
        }

        public DownloadedTrack[] newArray(int size) {
            return new DownloadedTrack[size];
        }
    };
}

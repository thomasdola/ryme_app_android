package primr.apps.eurakacachet.ryme.ryme.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

public class Artist implements Parcelable {

    public String stage_name;
    public UUID uuid;
    public long followers;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Artist artist = (Artist) o;

        if (followers != artist.followers) return false;
        if (stage_name != null ? !stage_name.equals(artist.stage_name) : artist.stage_name != null)
            return false;
        return !(uuid != null ? !uuid.equals(artist.uuid) : artist.uuid != null);

    }

    @Override
    public int hashCode() {
        int result = stage_name != null ? stage_name.hashCode() : 0;
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        result = 31 * result + (int) (followers ^ (followers >>> 32));
        return result;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.stage_name);
        dest.writeSerializable(this.uuid);
        dest.writeLong(this.followers);
    }

    public Artist() {
    }

    protected Artist(Parcel in) {
        this.stage_name = in.readString();
        this.uuid = (UUID) in.readSerializable();
        this.followers = in.readLong();
    }

    public static final Parcelable.Creator<Artist> CREATOR = new Parcelable.Creator<Artist>() {
        public Artist createFromParcel(Parcel source) {
            return new Artist(source);
        }

        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };
}

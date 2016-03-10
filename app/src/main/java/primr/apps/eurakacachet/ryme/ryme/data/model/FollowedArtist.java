package primr.apps.eurakacachet.ryme.ryme.data.model;


import android.os.Parcel;
import android.os.Parcelable;

public class FollowedArtist implements Parcelable {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FollowedArtist artist = (FollowedArtist) o;

        return uuid.equals(artist.uuid);

    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    public String uuid;

    public FollowedArtist() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uuid);
    }

    protected FollowedArtist(Parcel in) {
        this.uuid = in.readString();
    }

    public static final Creator<FollowedArtist> CREATOR = new Creator<FollowedArtist>() {
        public FollowedArtist createFromParcel(Parcel source) {
            return new FollowedArtist(source);
        }

        public FollowedArtist[] newArray(int size) {
            return new FollowedArtist[size];
        }
    };
}

package primr.apps.eurakacachet.ryme.ryme.data.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

public class FollowingArtist implements Parcelable {

    public UUID id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FollowingArtist that = (FollowingArtist) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public FollowingArtist() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.id);
    }

    protected FollowingArtist(Parcel in) {
        this.id = (UUID) in.readSerializable();
    }

    public static final Creator<FollowingArtist> CREATOR = new Creator<FollowingArtist>() {
        public FollowingArtist createFromParcel(Parcel source) {
            return new FollowingArtist(source);
        }

        public FollowingArtist[] newArray(int size) {
            return new FollowingArtist[size];
        }
    };
}

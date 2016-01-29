package primr.apps.eurakacachet.ryme.ryme.data.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

public class FollowedArtist implements Parcelable {

    public UUID id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FollowedArtist that = (FollowedArtist) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public FollowedArtist() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.id);
    }

    protected FollowedArtist(Parcel in) {
        this.id = (UUID) in.readSerializable();
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

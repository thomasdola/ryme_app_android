package primr.apps.eurakacachet.ryme.ryme.data.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

public class FollowedArtist implements Parcelable {

    public UUID uuid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FollowedArtist that = (FollowedArtist) o;

        return !(uuid != null ? !uuid.equals(that.uuid) : that.uuid != null);

    }

    @Override
    public int hashCode() {
        return uuid != null ? uuid.hashCode() : 0;
    }

    public FollowedArtist() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.uuid);
    }

    protected FollowedArtist(Parcel in) {
        this.uuid = (UUID) in.readSerializable();
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

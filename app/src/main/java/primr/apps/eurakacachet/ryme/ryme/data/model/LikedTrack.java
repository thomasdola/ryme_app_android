package primr.apps.eurakacachet.ryme.ryme.data.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

public class LikedTrack implements Parcelable {

    public UUID uuid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LikedTrack that = (LikedTrack) o;

        return !(uuid != null ? !uuid.equals(that.uuid) : that.uuid != null);

    }

    @Override
    public int hashCode() {
        return uuid != null ? uuid.hashCode() : 0;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.uuid);
    }

    public LikedTrack() {
    }

    protected LikedTrack(Parcel in) {
        this.uuid = (UUID) in.readSerializable();
    }

    public static final Parcelable.Creator<LikedTrack> CREATOR = new Parcelable.Creator<LikedTrack>() {
        public LikedTrack createFromParcel(Parcel source) {
            return new LikedTrack(source);
        }

        public LikedTrack[] newArray(int size) {
            return new LikedTrack[size];
        }
    };
}

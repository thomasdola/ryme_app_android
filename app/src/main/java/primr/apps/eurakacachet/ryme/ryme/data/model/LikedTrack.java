package primr.apps.eurakacachet.ryme.ryme.data.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

public class LikedTrack implements Parcelable {

    public UUID id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LikedTrack that = (LikedTrack) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.id);
    }

    public LikedTrack() {
    }

    protected LikedTrack(Parcel in) {
        this.id = (UUID) in.readSerializable();
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

package primr.apps.eurakacachet.com.rhyme.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

public class Track implements Parcelable {

    private final UUID mTrackId;
    private String mTrackTitle;

    public String getTrackTitle() {
        return mTrackTitle;
    }
//
//    public void setTrackTitle(String mTrackTitle) {
//        this.mTrackTitle = mTrackTitle;
//    }
//
//    public UUID getTrackId() {
//        return mTrackId;
//    }

    public Track(String trackTitle) {
        mTrackId = UUID.randomUUID();
        mTrackTitle = trackTitle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.mTrackId);
        dest.writeString(this.mTrackTitle);
    }

    protected Track(Parcel in) {
        this.mTrackId = (UUID) in.readSerializable();
        this.mTrackTitle = in.readString();
    }

    public static final Parcelable.Creator<Track> CREATOR = new Parcelable.Creator<Track>() {
        public Track createFromParcel(Parcel source) {
            return new Track(source);
        }

        public Track[] newArray(int size) {
            return new Track[size];
        }
    };
}

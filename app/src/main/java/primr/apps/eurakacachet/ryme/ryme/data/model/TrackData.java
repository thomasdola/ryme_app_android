package primr.apps.eurakacachet.ryme.ryme.data.model;


import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

public class TrackData implements Parcelable {

    @Nullable
    Track data;

    public static TrackData newData(@Nullable Track data){
        TrackData trackData = new TrackData();
        trackData.data = data;
        return trackData;
    }

    @Nullable
    public Track getData() {
        return data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.data, 0);
    }

    public TrackData() {
    }

    protected TrackData(Parcel in) {
        this.data = in.readParcelable(Track.class.getClassLoader());
    }

    public static final Parcelable.Creator<TrackData> CREATOR = new Parcelable.Creator<TrackData>() {
        public TrackData createFromParcel(Parcel source) {
            return new TrackData(source);
        }

        public TrackData[] newArray(int size) {
            return new TrackData[size];
        }
    };
}

package primr.apps.eurakacachet.ryme.ryme.data.model;


import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

public class ArtistData implements Parcelable {

    @Nullable Artist data;

    public static ArtistData newData(@Nullable Artist data){
        ArtistData artistData = new ArtistData();
        artistData.data = data;
        return artistData;
    }

    @Nullable
    public Artist data() {
        return data;
    }

    @Override
    public String toString() {
        return "ArtistData{" +
                "data=" + data +
                '}';
    }


    public ArtistData() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.data, 0);
    }

    protected ArtistData(Parcel in) {
        this.data = in.readParcelable(Artist.class.getClassLoader());
    }

    public static final Creator<ArtistData> CREATOR = new Creator<ArtistData>() {
        public ArtistData createFromParcel(Parcel source) {
            return new ArtistData(source);
        }

        public ArtistData[] newArray(int size) {
            return new ArtistData[size];
        }
    };
}

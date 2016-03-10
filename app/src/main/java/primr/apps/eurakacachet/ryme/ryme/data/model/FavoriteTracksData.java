package primr.apps.eurakacachet.ryme.ryme.data.model;


import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Arrays;

public class FavoriteTracksData implements Parcelable {

    @NonNull
    public String status;
    @NonNull
    public String message;
    @NonNull
    public int code;
    @Nullable
    public Track[] data;

    @NonNull
    public String getStatus() {
        return status;
    }

    @NonNull
    public String getMessage() {
        return message;
    }

    @NonNull
    public int getCode() {
        return code;
    }

    @Nullable
    public Track[] getData() {
        return data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.status);
        dest.writeString(this.message);
        dest.writeInt(this.code);
        dest.writeParcelableArray(this.data, 0);
    }

    public FavoriteTracksData() {
    }

    protected FavoriteTracksData(Parcel in) {
        this.status = in.readString();
        this.message = in.readString();
        this.code = in.readInt();
        this.data = (Track[]) in.readParcelableArray(Track.class.getClassLoader());
    }

    public static final Parcelable.Creator<FavoriteTracksData> CREATOR = new Parcelable.Creator<FavoriteTracksData>() {
        public FavoriteTracksData createFromParcel(Parcel source) {
            return new FavoriteTracksData(source);
        }

        public FavoriteTracksData[] newArray(int size) {
            return new FavoriteTracksData[size];
        }
    };

    @Override
    public String toString() {
        return "FavoriteTracksData{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", code=" + code +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}

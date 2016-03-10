package primr.apps.eurakacachet.ryme.ryme.data.model;


import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Arrays;

public class FavoriteArtistsData implements Parcelable {

    @NonNull
    public String status;
    @NonNull
    public String message;
    @NonNull
    public int code;
    @Nullable
    public Artist[] data;

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
    public Artist[] getData() {
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

    public FavoriteArtistsData() {
    }

    protected FavoriteArtistsData(Parcel in) {
        this.status = in.readString();
        this.message = in.readString();
        this.code = in.readInt();
        this.data = (Artist[]) in.readParcelableArray(Artist.class.getClassLoader());
    }

    public static final Parcelable.Creator<FavoriteArtistsData> CREATOR = new Parcelable.Creator<FavoriteArtistsData>() {
        public FavoriteArtistsData createFromParcel(Parcel source) {
            return new FavoriteArtistsData(source);
        }

        public FavoriteArtistsData[] newArray(int size) {
            return new FavoriteArtistsData[size];
        }
    };

    @Override
    public String toString() {
        return "FavoriteArtistsData{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", code=" + code +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}

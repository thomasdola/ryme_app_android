package primr.apps.eurakacachet.ryme.ryme.data.model;


import android.os.Parcel;
import android.os.Parcelable;

public class ApiData implements Parcelable {

    UserProfile data;

    public UserProfile data() {
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

    public ApiData() {
    }

    protected ApiData(Parcel in) {
        this.data = in.readParcelable(UserProfile.class.getClassLoader());
    }

    public static final Parcelable.Creator<ApiData> CREATOR = new Parcelable.Creator<ApiData>() {
        public ApiData createFromParcel(Parcel source) {
            return new ApiData(source);
        }

        public ApiData[] newArray(int size) {
            return new ApiData[size];
        }
    };

    @Override
    public String toString() {
        return "ApiData{" +
                "data=" + data +
                '}';
    }
}

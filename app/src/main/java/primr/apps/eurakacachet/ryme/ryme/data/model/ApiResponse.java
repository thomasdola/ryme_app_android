package primr.apps.eurakacachet.ryme.ryme.data.model;


import android.os.Parcel;
import android.os.Parcelable;

public class ApiResponse implements Parcelable {

    public String status;
    public int code;
    public String message;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApiResponse that = (ApiResponse) o;

        if (code != that.code) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        return !(message != null ? !message.equals(that.message) : that.message != null);

    }

    @Override
    public int hashCode() {
        int result = status != null ? status.hashCode() : 0;
        result = 31 * result + code;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.status);
        dest.writeInt(this.code);
        dest.writeString(this.message);
    }

    public ApiResponse() {
    }

    protected ApiResponse(Parcel in) {
        this.status = in.readString();
        this.code = in.readInt();
        this.message = in.readString();
    }

    public static final Parcelable.Creator<ApiResponse> CREATOR = new Parcelable.Creator<ApiResponse>() {
        public ApiResponse createFromParcel(Parcel source) {
            return new ApiResponse(source);
        }

        public ApiResponse[] newArray(int size) {
            return new ApiResponse[size];
        }
    };
}

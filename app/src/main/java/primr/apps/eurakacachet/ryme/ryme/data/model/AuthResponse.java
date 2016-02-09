package primr.apps.eurakacachet.ryme.ryme.data.model;


import android.os.Parcel;
import android.os.Parcelable;

public class AuthResponse implements Parcelable {

    public String status;
    public String message;
    public int code;
    public UserProfile data;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthResponse that = (AuthResponse) o;

        if (code != that.code) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        return !(data != null ? !data.equals(that.data) : that.data != null);

    }

    @Override
    public int hashCode() {
        int result = status != null ? status.hashCode() : 0;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + code;
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
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
        dest.writeParcelable(this.data, 0);
    }

    public AuthResponse() {
    }

    protected AuthResponse(Parcel in) {
        this.status = in.readString();
        this.message = in.readString();
        this.code = in.readInt();
        this.data = in.readParcelable(UserProfile.class.getClassLoader());
    }

    public static final Parcelable.Creator<AuthResponse> CREATOR = new Parcelable.Creator<AuthResponse>() {
        public AuthResponse createFromParcel(Parcel source) {
            return new AuthResponse(source);
        }

        public AuthResponse[] newArray(int size) {
            return new AuthResponse[size];
        }
    };
}

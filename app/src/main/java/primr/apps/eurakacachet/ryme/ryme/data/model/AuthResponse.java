package primr.apps.eurakacachet.ryme.ryme.data.model;


import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

public class AuthResponse implements Parcelable {

    public String status;
    public String message;
    public int code;
    @Nullable
    public ApiData user;
    @Nullable
    public String token;

    public String status() {
        return status;
    }

    public String message() {
        return message;
    }

    public int code() {
        return code;
    }

    @Nullable
    public ApiData data() {
        return user;
    }

    @Nullable
    public String token() {
        return token;
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
        dest.writeParcelable(this.user, 0);
        dest.writeString(this.token);
    }

    public AuthResponse() {
    }

    protected AuthResponse(Parcel in) {
        this.status = in.readString();
        this.message = in.readString();
        this.code = in.readInt();
        this.user = in.readParcelable(ApiData.class.getClassLoader());
        this.token = in.readString();
    }

    public static final Parcelable.Creator<AuthResponse> CREATOR = new Parcelable.Creator<AuthResponse>() {
        public AuthResponse createFromParcel(Parcel source) {
            return new AuthResponse(source);
        }

        public AuthResponse[] newArray(int size) {
            return new AuthResponse[size];
        }
    };

    @Override
    public String toString() {
        return "AuthResponse{" +
                "message='" + message + '\'' +
                ", code=" + code +
                ", user=" + user +
                ", token='" + token + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

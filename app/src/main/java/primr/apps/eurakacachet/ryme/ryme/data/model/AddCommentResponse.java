package primr.apps.eurakacachet.ryme.ryme.data.model;


import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class AddCommentResponse implements Parcelable {

    @NonNull
    public String status;
    @NonNull
    public String message;
    @NonNull
    public int code;
    @Nullable
    public Comment data;

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
    public Comment getData() {
        return data;
    }

    @Override
    public String toString() {
        return "AddCommentResponse{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", code=" + code +
                ", data=" + data +
                '}';
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
        dest.writeParcelable(this.data, flags);
    }

    public AddCommentResponse() {
    }

    protected AddCommentResponse(Parcel in) {
        this.status = in.readString();
        this.message = in.readString();
        this.code = in.readInt();
        this.data = in.readParcelable(Comment.class.getClassLoader());
    }

    public static final Parcelable.Creator<AddCommentResponse> CREATOR = new Parcelable.Creator<AddCommentResponse>() {
        public AddCommentResponse createFromParcel(Parcel source) {
            return new AddCommentResponse(source);
        }

        public AddCommentResponse[] newArray(int size) {
            return new AddCommentResponse[size];
        }
    };
}

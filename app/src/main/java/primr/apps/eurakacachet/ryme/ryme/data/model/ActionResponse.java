package primr.apps.eurakacachet.ryme.ryme.data.model;


import android.os.Parcel;
import android.os.Parcelable;

public class ActionResponse implements Parcelable {

    public String status;
    public String message;
    public int code;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActionResponse that = (ActionResponse) o;

        if (code != that.code) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        return !(message != null ? !message.equals(that.message) : that.message != null);

    }

    @Override
    public int hashCode() {
        int result = status != null ? status.hashCode() : 0;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + code;
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
    }

    public ActionResponse() {
    }

    protected ActionResponse(Parcel in) {
        this.status = in.readString();
        this.message = in.readString();
        this.code = in.readInt();
    }

    public static final Parcelable.Creator<ActionResponse> CREATOR = new Parcelable.Creator<ActionResponse>() {
        public ActionResponse createFromParcel(Parcel source) {
            return new ActionResponse(source);
        }

        public ActionResponse[] newArray(int size) {
            return new ActionResponse[size];
        }
    };
}

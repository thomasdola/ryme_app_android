package primr.apps.eurakacachet.ryme.ryme.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by GURU on 1/27/2016.
 */
public class UserProfile implements Parcelable {

    public String username;
    public String id;
    public String stage_name;
    public String phone_number;
    public String avatar;
    public String stage_picture;

    public UserProfile() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.username);
        dest.writeSerializable(this.id);
        dest.writeString(this.stage_name);
        dest.writeString(this.phone_number);
        dest.writeString(this.avatar);
        dest.writeString(this.stage_picture);
    }

    protected UserProfile(Parcel in) {
        this.username = in.readString();
        this.id = in.readString();
        this.stage_name = in.readString();
        this.phone_number = in.readString();
        this.avatar = in.readString();
        this.stage_picture = in.readString();
    }

    public static final Creator<UserProfile> CREATOR = new Creator<UserProfile>() {
        public UserProfile createFromParcel(Parcel source) {
            return new UserProfile(source);
        }

        public UserProfile[] newArray(int size) {
            return new UserProfile[size];
        }
    };
}

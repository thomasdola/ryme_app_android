package primr.apps.eurakacachet.ryme.ryme.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;


public class UserProfile implements Parcelable {

    public String uuid;
    public String username;
    public String phone_number;
    @Nullable
    public String avatar;
    public boolean is_artist;
    public boolean is_request_on;
    @Nullable
    public String stage_name;
    @Nullable
    public String background_picture;

    public String uuid() {
        return uuid;
    }

    public String username() {
        return username;
    }

    public String phone_number() {
        return phone_number;
    }

    @Nullable
    public String avatar() {
        return avatar;
    }

    public boolean is_artist() {
        return is_artist;
    }

    @Nullable
    public String stage_name() {
        return stage_name;
    }

    @Nullable
    public String background_picture() {
        return background_picture;
    }

    public boolean is_request_on() {
        return is_request_on;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "uuid='" + uuid + '\'' +
                ", username='" + username + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", avatar='" + avatar + '\'' +
                ", is_artist=" + is_artist +
                ", is_request_on=" + is_request_on +
                ", stage_name='" + stage_name + '\'' +
                ", background_picture='" + background_picture + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uuid);
        dest.writeString(this.username);
        dest.writeString(this.phone_number);
        dest.writeString(this.avatar);
        dest.writeByte(is_artist ? (byte) 1 : (byte) 0);
        dest.writeString(this.stage_name);
        dest.writeString(this.background_picture);
    }

    public UserProfile() {
    }

    protected UserProfile(Parcel in) {
        this.uuid = in.readString();
        this.username = in.readString();
        this.phone_number = in.readString();
        this.avatar = in.readString();
        this.is_artist = in.readByte() != 0;
        this.stage_name = in.readString();
        this.background_picture = in.readString();
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

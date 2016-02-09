package primr.apps.eurakacachet.ryme.ryme.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;


public class UserProfile implements Parcelable {

    public UUID uuid;
    public String username;
    public String phone_number;
    public String avatar;
    public boolean is_artist;
    public String stage_name;
    public String background_picture;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserProfile that = (UserProfile) o;

        if (is_artist != that.is_artist) return false;
        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null)
            return false;
        if (phone_number != null ? !phone_number.equals(that.phone_number) : that.phone_number != null)
            return false;
        if (avatar != null ? !avatar.equals(that.avatar) : that.avatar != null) return false;
        if (stage_name != null ? !stage_name.equals(that.stage_name) : that.stage_name != null)
            return false;
        return !(background_picture != null ? !background_picture.equals(that.background_picture) : that.background_picture != null);

    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (phone_number != null ? phone_number.hashCode() : 0);
        result = 31 * result + (avatar != null ? avatar.hashCode() : 0);
        result = 31 * result + (is_artist ? 1 : 0);
        result = 31 * result + (stage_name != null ? stage_name.hashCode() : 0);
        result = 31 * result + (background_picture != null ? background_picture.hashCode() : 0);
        return result;
    }

    public UserProfile() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.uuid);
        dest.writeString(this.username);
        dest.writeString(this.phone_number);
        dest.writeString(this.avatar);
        dest.writeByte(is_artist ? (byte) 1 : (byte) 0);
        dest.writeString(this.stage_name);
        dest.writeString(this.background_picture);
    }

    protected UserProfile(Parcel in) {
        this.uuid = (UUID) in.readSerializable();
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

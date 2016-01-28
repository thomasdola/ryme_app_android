package primr.apps.eurakacachet.ryme.ryme.data.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

public class FollowingCategory implements Parcelable {

    public UUID id;
    public String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FollowingCategory that = (FollowingCategory) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return !(name != null ? !name.equals(that.name) : that.name != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    public FollowingCategory() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.id);
        dest.writeString(this.name);
    }

    protected FollowingCategory(Parcel in) {
        this.id = (UUID) in.readSerializable();
        this.name = in.readString();
    }

    public static final Creator<FollowingCategory> CREATOR = new Creator<FollowingCategory>() {
        public FollowingCategory createFromParcel(Parcel source) {
            return new FollowingCategory(source);
        }

        public FollowingCategory[] newArray(int size) {
            return new FollowingCategory[size];
        }
    };
}

package primr.apps.eurakacachet.ryme.ryme.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;


public class Searchable implements Parcelable {

    public UUID id;
    public String picture;
    public String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Searchable searchable = (Searchable) o;

        if (id != null ? !id.equals(searchable.id) : searchable.id != null) return false;
        if (picture != null ? !picture.equals(searchable.picture) : searchable.picture != null)
            return false;
        return !(name != null ? !name.equals(searchable.name) : searchable.name != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (picture != null ? picture.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.id);
        dest.writeString(this.picture);
        dest.writeString(this.name);
    }

    public Searchable() {
    }

    protected Searchable(Parcel in) {
        this.id = (UUID) in.readSerializable();
        this.picture = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<Searchable> CREATOR = new Parcelable.Creator<Searchable>() {
        public Searchable createFromParcel(Parcel source) {
            return new Searchable(source);
        }

        public Searchable[] newArray(int size) {
            return new Searchable[size];
        }
    };
}

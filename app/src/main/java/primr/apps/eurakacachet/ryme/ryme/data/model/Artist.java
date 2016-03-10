package primr.apps.eurakacachet.ryme.ryme.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class Artist implements Parcelable {

    @NonNull
    String stage_name;
    @NonNull
    String uuid;
    @NonNull
    long followers;
    @NonNull
    String profilePic;
    @NonNull
    boolean followed;
    @NonNull
    boolean amTheOne;
    @NonNull
    String backPic;


    public static Artist newArtist(@NonNull String uuid, @NonNull String stage_name,
                                   @NonNull long followers, @NonNull String profilePic,
                                   @NonNull String backPic, @NonNull boolean followed,
                                   @NonNull boolean amTheOne) {
        Artist artist = new Artist();
        artist.uuid = uuid;
        artist.stage_name = stage_name;
        artist.followers = followers;
        artist.profilePic = profilePic;
        artist.followed = followed;
        artist.amTheOne = amTheOne;
        artist.backPic = backPic;
        return artist;
    }

    @NonNull
    public String stage_name() {
        return stage_name;
    }

    @NonNull
    public String uuid() {
        return uuid;
    }

    @NonNull
    public long followers() {
        return followers;
    }

    @NonNull
    public String profilPic(){
        return profilePic;
    }

    @NonNull
    public String backPic(){
        return backPic;
    }

    @NonNull
    public boolean followed() {
        return followed;
    }

    @NonNull
    public boolean amTheOne() {
        return amTheOne;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Artist artist = (Artist) o;

        if (followers != artist.followers) return false;
        if (followed != artist.followed) return false;
        if (amTheOne != artist.amTheOne) return false;
        if (!stage_name.equals(artist.stage_name)) return false;
        if (!uuid.equals(artist.uuid)) return false;
        if (!profilePic.equals(artist.profilePic)) return false;
        return backPic.equals(artist.backPic);

    }

    @Override
    public int hashCode() {
        int result = stage_name.hashCode();
        result = 31 * result + uuid.hashCode();
        result = 31 * result + (int) (followers ^ (followers >>> 32));
        result = 31 * result + profilePic.hashCode();
        result = 31 * result + (followed ? 1 : 0);
        result = 31 * result + (amTheOne ? 1 : 0);
        result = 31 * result + backPic.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "stage_name='" + stage_name + '\'' +
                ", uuid='" + uuid + '\'' +
                ", followers=" + followers +
                ", profilePic='" + profilePic + '\'' +
                ", followed=" + followed +
                ", amTheOne=" + amTheOne +
                ", backPic='" + backPic + '\'' +
                '}';
    }

    public Artist() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.stage_name);
        dest.writeString(this.uuid);
        dest.writeLong(this.followers);
        dest.writeString(this.profilePic);
        dest.writeByte(followed ? (byte) 1 : (byte) 0);
        dest.writeByte(amTheOne ? (byte) 1 : (byte) 0);
        dest.writeString(this.backPic);
    }

    protected Artist(Parcel in) {
        this.stage_name = in.readString();
        this.uuid = in.readString();
        this.followers = in.readLong();
        this.profilePic = in.readString();
        this.followed = in.readByte() != 0;
        this.amTheOne = in.readByte() != 0;
        this.backPic = in.readString();
    }

    public static final Creator<Artist> CREATOR = new Creator<Artist>() {
        public Artist createFromParcel(Parcel source) {
            return new Artist(source);
        }

        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };
}

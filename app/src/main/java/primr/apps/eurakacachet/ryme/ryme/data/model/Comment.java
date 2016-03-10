package primr.apps.eurakacachet.ryme.ryme.data.model;


import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class Comment implements Parcelable {

    @NonNull String username;
    @NonNull String message;
    @NonNull long time;
    @Nullable String userAvatar;

    public static Comment newComment(@NonNull String username, @NonNull long time,
                                     @NonNull String message, @NonNull String userAvatar){
        Comment comment = new Comment();
        comment.username = username;
        comment.time = time;
        comment.message = message;
        comment.userAvatar = userAvatar;
        return comment;
    }

    @NonNull
    public String username() {
        return username;
    }

    @NonNull
    public String message() {
        return message;
    }

    @NonNull
    public long time() {
        return time;
    }

    @Nullable
    public String userAvatar() {
        return userAvatar;
    }


    public Comment() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        if (time != comment.time) return false;
        if (!username.equals(comment.username)) return false;
        if (!message.equals(comment.message)) return false;
        return !(userAvatar != null ? !userAvatar.equals(comment.userAvatar) : comment.userAvatar != null);

    }

    @Override
    public int hashCode() {
        int result = username.hashCode();
        result = 31 * result + message.hashCode();
        result = 31 * result + (int) (time ^ (time >>> 32));
        result = 31 * result + (userAvatar != null ? userAvatar.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "username='" + username + '\'' +
                ", message='" + message + '\'' +
                ", time=" + time +
                ", userAvatar='" + userAvatar + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.username);
        dest.writeString(this.message);
        dest.writeLong(this.time);
        dest.writeString(this.userAvatar);
    }

    protected Comment(Parcel in) {
        this.username = in.readString();
        this.message = in.readString();
        this.time = in.readLong();
        this.userAvatar = in.readString();
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        public Comment createFromParcel(Parcel source) {
            return new Comment(source);
        }

        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}

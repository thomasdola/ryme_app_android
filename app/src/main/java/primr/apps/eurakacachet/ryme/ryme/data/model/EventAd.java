package primr.apps.eurakacachet.ryme.ryme.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.UUID;


public class EventAd implements Parcelable {

    public String title;
    public Date date;
    public Date time;
    public String description;
    public float fare;
    public long views;
    public UUID uuid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventAd eventAd = (EventAd) o;

        if (Float.compare(eventAd.fare, fare) != 0) return false;
        if (views != eventAd.views) return false;
        if (title != null ? !title.equals(eventAd.title) : eventAd.title != null) return false;
        if (date != null ? !date.equals(eventAd.date) : eventAd.date != null) return false;
        if (time != null ? !time.equals(eventAd.time) : eventAd.time != null) return false;
        if (description != null ? !description.equals(eventAd.description) : eventAd.description != null)
            return false;
        return !(uuid != null ? !uuid.equals(eventAd.uuid) : eventAd.uuid != null);

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (fare != +0.0f ? Float.floatToIntBits(fare) : 0);
        result = 31 * result + (int) (views ^ (views >>> 32));
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        return result;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeLong(date != null ? date.getTime() : -1);
        dest.writeLong(time != null ? time.getTime() : -1);
        dest.writeString(this.description);
        dest.writeFloat(this.fare);
        dest.writeLong(this.views);
        dest.writeSerializable(this.uuid);
    }

    public EventAd() {
    }

    protected EventAd(Parcel in) {
        this.title = in.readString();
        long tmpDate = in.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
        long tmpTime = in.readLong();
        this.time = tmpTime == -1 ? null : new Date(tmpTime);
        this.description = in.readString();
        this.fare = in.readFloat();
        this.views = in.readLong();
        this.uuid = (UUID) in.readSerializable();
    }

    public static final Parcelable.Creator<EventAd> CREATOR = new Parcelable.Creator<EventAd>() {
        public EventAd createFromParcel(Parcel source) {
            return new EventAd(source);
        }

        public EventAd[] newArray(int size) {
            return new EventAd[size];
        }
    };
}

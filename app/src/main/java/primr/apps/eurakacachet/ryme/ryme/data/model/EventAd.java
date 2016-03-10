package primr.apps.eurakacachet.ryme.ryme.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.ArrayList;
import java.util.List;

import primr.apps.eurakacachet.ryme.ryme.ui.view.eventAd.EventAdDetail;


public class EventAd implements Parcelable, ParentListItem {

    @NonNull
    public String title;
    @NonNull
    public int date;
    @NonNull
    public int time;
    @NonNull
    public String description;
    @NonNull
    public float fare;
    @NonNull
    public long views;
    @NonNull
    public String uuid;
    @NonNull
    public String venue;
    @NonNull
    public String cover;
    private List<EventAdDetail> mChildItemList;

    public static EventAd newAd(@NonNull String title, @NonNull int date, @NonNull int time,
                                @NonNull String description, @NonNull String venue,
                                @NonNull float fare, @NonNull long views, @NonNull String cover){
        EventAd ad = new EventAd();
        ad.title = title;
        ad.date = date;
        ad.time = time;
        ad.fare = fare;
        ad.description = description;
        ad.venue = venue;
        ad.views = views;
        ad.cover = cover;
        return ad;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    @NonNull
    public int getDate() {
        return date;
    }

    @NonNull
    public int getTime() {
        return time;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    @NonNull
    public float getFare() {
        return fare;
    }

    @NonNull
    public long getViews() {
        return views;
    }

    @NonNull
    public String getUuid() {
        return uuid;
    }

    @NonNull
    public String getVenue() {
        return venue;
    }

    @NonNull
    public String getCover() {
        return cover;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventAd ad = (EventAd) o;

        if (date != ad.date) return false;
        if (time != ad.time) return false;
        if (Float.compare(ad.fare, fare) != 0) return false;
        if (views != ad.views) return false;
        if (!title.equals(ad.title)) return false;
        if (!description.equals(ad.description)) return false;
        if (!uuid.equals(ad.uuid)) return false;
        if (!venue.equals(ad.venue)) return false;
        return cover.equals(ad.cover);

    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + date;
        result = 31 * result + time;
        result = 31 * result + description.hashCode();
        result = 31 * result + (fare != +0.0f ? Float.floatToIntBits(fare) : 0);
        result = 31 * result + (int) (views ^ (views >>> 32));
        result = 31 * result + uuid.hashCode();
        result = 31 * result + venue.hashCode();
        result = 31 * result + cover.hashCode();
        return result;
    }

    public EventAd() {
    }

    @Override
    public List<?> getChildItemList() {
        return mChildItemList;
    }

    public void setChildItemList(List<EventAdDetail> details){
        mChildItemList = details;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeInt(this.date);
        dest.writeInt(this.time);
        dest.writeString(this.description);
        dest.writeFloat(this.fare);
        dest.writeLong(this.views);
        dest.writeString(this.uuid);
        dest.writeString(this.venue);
        dest.writeString(this.cover);
        dest.writeList(this.mChildItemList);
    }

    protected EventAd(Parcel in) {
        this.title = in.readString();
        this.date = in.readInt();
        this.time = in.readInt();
        this.description = in.readString();
        this.fare = in.readFloat();
        this.views = in.readLong();
        this.uuid = in.readString();
        this.venue = in.readString();
        this.cover = in.readString();
        this.mChildItemList = new ArrayList<EventAdDetail>();
        in.readList(this.mChildItemList, List.class.getClassLoader());
    }

    public static final Creator<EventAd> CREATOR = new Creator<EventAd>() {
        public EventAd createFromParcel(Parcel source) {
            return new EventAd(source);
        }

        public EventAd[] newArray(int size) {
            return new EventAd[size];
        }
    };
}

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
    public String date;
    @NonNull
    public String time;
    @NonNull
    public String description;
    @NonNull
    public long views;
    @NonNull
    public String uuid;
    @NonNull
    public String venue;
    @NonNull
    public String cover;
    private List<EventAdDetail> mChildItemList;

    public static EventAd newAd(@NonNull String title, @NonNull String date, @NonNull String time,
                                @NonNull String description, @NonNull String venue,
                                @NonNull long views, @NonNull String cover){
        EventAd ad = new EventAd();
        ad.title = title;
        ad.date = date;
        ad.time = time;
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
    public String getDate() {
        return date;
    }

    @NonNull
    public String getTime() {
        return time;
    }

    @NonNull
    public String getDescription() {
        return description;
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
    public String toString() {
        return "EventAd{" +
                "title='" + title + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", description='" + description + '\'' +
                ", views=" + views +
                ", uuid='" + uuid + '\'' +
                ", venue='" + venue + '\'' +
                ", cover='" + cover + '\'' +
                '}';
    }

    public EventAd() {
    }

    @Override
    public List getChildItemList() {
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
        dest.writeString(this.date);
        dest.writeString(this.time);
        dest.writeString(this.description);
        dest.writeLong(this.views);
        dest.writeString(this.uuid);
        dest.writeString(this.venue);
        dest.writeString(this.cover);
        dest.writeList(this.mChildItemList);
    }

    protected EventAd(Parcel in) {
        this.title = in.readString();
        this.date = in.readString();
        this.time = in.readString();
        this.description = in.readString();
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

package primr.apps.eurakacachet.ryme.ryme.data.model;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Event implements ParentObject {

    private String mEventName;
    private static List<Event> mEventList;
    private String mEventDateTime;
    private String mEventDescription;
    private String mEventFare;
    private String mEventViews;


    public static List<Event> getEventList() {
        mEventList = new ArrayList<>();
        mEventList.add(new Event("Ghana Meet Niga", "20", "Shatta Walle, StoneBowy " +
                "National Theater Achimota Mall", "2k"));
        return mEventList;
    }

    public Event(String eventName, String  eventFare, String eventDescription, String eventViews){
        mEventFare = eventFare;
        mEventName = eventName;
        mEventDescription = eventDescription;
        mEventViews = eventViews;
        mEventDateTime = new Date().toString();
    }

    public String getEventName() {
        return mEventName;
    }

    public String getEventDateTime() {
        return mEventDateTime;
    }

    public String getEventDescription() {
        return mEventDescription;
    }

    public String getEventViews() {
        return mEventViews;
    }

    public String getEventFare() {
        return mEventFare;
    }

    private List<Object> mChildrenList;

    @Override
    public List<Object> getChildObjectList() {
        return mChildrenList;
    }

    @Override
    public void setChildObjectList(List<Object> list) {
        mChildrenList = list;
    }
}

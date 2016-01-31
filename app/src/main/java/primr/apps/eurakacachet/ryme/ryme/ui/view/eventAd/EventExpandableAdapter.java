package primr.apps.eurakacachet.ryme.ryme.ui.view.eventAd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;

import primr.apps.eurakacachet.ryme.ryme.data.model.EventAd;
import primr.apps.eurakacachet.ryme.ryme.R;


public class EventExpandableAdapter extends ExpandableRecyclerAdapter<EventParentViewHolder, EventChildViewHolder> {

    private LayoutInflater mViewInflater = LayoutInflater.from(mContext);

    public EventExpandableAdapter(Context context, List<ParentObject> parentItemList) {
        super(context, parentItemList);
    }

    @Override
    public EventParentViewHolder onCreateParentViewHolder(ViewGroup viewGroup) {
        View view = mViewInflater.inflate(R.layout.event_main_card_view, viewGroup, false);
        return new EventParentViewHolder(view);
    }

    @Override
    public EventChildViewHolder onCreateChildViewHolder(ViewGroup viewGroup) {
        View view = mViewInflater.inflate(R.layout.event_detail_card_view, viewGroup, false);
        return new EventChildViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(EventParentViewHolder eventParentViewHolder, int i,
                                       Object o) {
        EventAd event = (EventAd) o;
        eventParentViewHolder.mEventFareView.setText((int) event.fare);
        eventParentViewHolder.mEventViewsView.setText("20");
    }

    @Override
    public void onBindChildViewHolder(EventChildViewHolder eventChildViewHolder, int i, Object o) {
        EventChild eventChild = (EventChild) o;
        eventChildViewHolder.mEventDescription.setText(eventChild.mEventDescription);
    }
}

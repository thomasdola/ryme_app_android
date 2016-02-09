package primr.apps.eurakacachet.ryme.ryme.ui.view.eventAd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.List;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.model.EventAd;


public class EventExpandableAdapter extends ExpandableRecyclerAdapter<EventParentViewHolder, EventChildViewHolder> {

    private LayoutInflater mViewInflater;

    public EventExpandableAdapter(Context context, List<? extends ParentListItem> parentItemList) {
        super(parentItemList);
        mViewInflater  = LayoutInflater.from(context);
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
    public void onBindParentViewHolder(EventParentViewHolder parentViewHolder, int position,
                                       ParentListItem parentListItem) {
        EventAd event = (EventAd) parentListItem;
        parentViewHolder.bindEventHead(event);
    }

    @Override
    public void onBindChildViewHolder(EventChildViewHolder adDetailViewHolder, int position,
                                      Object childListItem) {
        EventAdDetail eventChild = (EventAdDetail) childListItem;
        adDetailViewHolder.mEventDescription.setText(eventChild.mEventDescription);
    }
}

package primr.apps.eurakacachet.ryme.ryme.ui.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.ArrayList;
import java.util.List;

import primr.apps.eurakacachet.ryme.ryme.data.model.EventAd;
import primr.apps.eurakacachet.ryme.ryme.R;


public class EventsFragment extends Fragment {


    private RecyclerView mEventRecyclerView;
    private EventExpandableAdapter mEventExpnadableAdapter;
    private ArrayList<ParentObject> mEventList;

    public EventsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_events, container, false);

        mEventRecyclerView = (RecyclerView) rootView.findViewById(R.id.event_recycler_view);
        mEventRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mEventExpnadableAdapter = new EventExpandableAdapter(getActivity(), generateEvents());
        mEventExpnadableAdapter.setCustomParentAnimationViewId(R.id.event_expand_button);
        mEventExpnadableAdapter.setParentClickableViewAnimationDefaultDuration();
        mEventExpnadableAdapter.setParentAndIconExpandOnClick(true);
        mEventRecyclerView.setAdapter(mEventExpnadableAdapter);
        return rootView;
    }

    private ArrayList<ParentObject> generateEvents() {
        List<EventAd> events = EventAd.getEventList();
        mEventList = new ArrayList<>();
        for(EventAd event: events){
            ArrayList<Object> eventChildList = new ArrayList<>();
            eventChildList.add(new EventChild(event.getEventDescription()));
            event.setChildObjectList(eventChildList);
            mEventList.add(event);
        }
        return mEventList;
    }


}

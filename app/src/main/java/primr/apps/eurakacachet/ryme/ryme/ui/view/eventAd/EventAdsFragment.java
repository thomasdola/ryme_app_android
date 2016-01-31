package primr.apps.eurakacachet.ryme.ryme.ui.view.eventAd;


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

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.model.EventAd;


public class EventAdsFragment extends Fragment {

    @Inject EventAdsPresenter mEventAdsPresenter;

    public EventAdsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_events, container, false);

        RecyclerView eventRecyclerView = (RecyclerView) rootView.findViewById(R.id.event_recycler_view);
        eventRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        EventExpandableAdapter eventExpandableAdapter = new EventExpandableAdapter(getActivity(), generateEvents());
        eventExpandableAdapter.setCustomParentAnimationViewId(R.id.event_expand_button);
        eventExpandableAdapter.setParentClickableViewAnimationDefaultDuration();
        eventExpandableAdapter.setParentAndIconExpandOnClick(true);
        eventRecyclerView.setAdapter(eventExpandableAdapter);
        return rootView;
    }

    private List<ParentObject> generateEvents() {
        List<EventAd> eventList = new ArrayList<>();//get From DataManager Class
        List<ParentObject> parentObjects = new ArrayList<>();
        for(EventAd event: eventList){
            List<Object> eventChildList = new ArrayList<>();
            eventChildList.add(new EventChild(event.description));
            event.setChildObjectList(eventChildList);
            parentObjects.add(event);
        }
        return parentObjects;
    }


}

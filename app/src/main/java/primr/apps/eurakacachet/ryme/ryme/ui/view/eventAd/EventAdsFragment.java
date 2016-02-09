package primr.apps.eurakacachet.ryme.ryme.ui.view.eventAd;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.model.EventAd;


public class EventAdsFragment extends Fragment{

    @Inject EventAdsPresenter mEventAdsPresenter;

    List<EventAd> mAds;

    EventExpandableAdapter mAdapter;

    public EventAdsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if( savedInstanceState != null ){
            mAdapter.onRestoreInstanceState(savedInstanceState);
        }

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_events, container, false);

        RecyclerView eventRecyclerView = (RecyclerView) rootView.findViewById(R.id.event_recycler_view);
        EventExpandableAdapter mAdapter = new EventExpandableAdapter(getActivity(), prepareAds());
        eventRecyclerView.setAdapter(mAdapter);
        eventRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return rootView;
    }

    private List<ParentListItem> prepareAds() {
        List<EventAd> adList = loadAds();
        List<ParentListItem> parentObjects = new ArrayList<>();
        for(EventAd event: adList){
            List<EventAdDetail> eventChildList = new ArrayList<>();
            eventChildList.add(new EventAdDetail(event.description));
            event.setChildItemList(eventChildList);
            parentObjects.add(event);
        }
        return parentObjects;
    }

    private List<EventAd> loadAds() {
        return new ArrayList<>();
    }

}

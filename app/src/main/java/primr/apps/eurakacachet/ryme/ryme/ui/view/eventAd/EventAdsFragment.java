package primr.apps.eurakacachet.ryme.ryme.ui.view.eventAd;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.model.EventAd;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BaseActivity;


public class EventAdsFragment extends Fragment implements EventAdsMvpView{

    @Inject EventAdsPresenter mEventAdsPresenter;

    List<EventAd> mAds;
    private EventExpandableAdapter mAdapter;
    private RecyclerView mEventRecyclerView;
    private RelativeLayout mEventsEmptyState;

    public EventAdsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_events, container, false);

        mEventRecyclerView = (RecyclerView) rootView.findViewById(R.id.event_recycler_view);
        mEventsEmptyState = (RelativeLayout) rootView.findViewById(R.id.event_list_empty_state);
        return rootView;
    }

    private void initListeners(){
        mAdapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener() {
            @Override
            public void onListItemExpanded(int position) {
                EventAd ad = mAds.get(position);
                Log.d("event", "event -> " + ad.toString() + " is opened");
            }

            @Override
            public void onListItemCollapsed(int position) {
                EventAd ad = mAds.get(position);
                mEventAdsPresenter.logView(ad);
                Log.d("event", "event -> " + ad.toString() + " is opened");
            }
        });
    }

    private List<ParentListItem> prepareAds(List<EventAd> ads) {
        List<ParentListItem> parentObjects = new ArrayList<>();
        if(ads != null){
            for(EventAd event: ads){
                List<EventAdDetail> eventChildList = new ArrayList<>();
                eventChildList.add(new EventAdDetail(event.getDescription()));
                event.setChildItemList(eventChildList);
                parentObjects.add(event);
            }
        }
        return parentObjects;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((BaseActivity)getActivity()).getActivityComponent().inject(this);
        mEventAdsPresenter.attachView(this);
        mEventAdsPresenter.loadEventAds();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void setEventAds(List<EventAd> ads) {
        Log.d("events", "setEventAds called with -> " + ads.toString());
        mAds = ads;
        mAdapter = new EventExpandableAdapter(getActivity(), prepareAds(ads));
        initListeners();
        mEventRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mEventRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showEmpty() {
        mEventRecyclerView.setVisibility(View.INVISIBLE);
        mEventsEmptyState.setVisibility(View.VISIBLE);
        Log.d("events", "showing empty state");
    }
}

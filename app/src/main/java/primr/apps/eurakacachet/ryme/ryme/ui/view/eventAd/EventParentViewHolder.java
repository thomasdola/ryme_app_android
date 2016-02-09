package primr.apps.eurakacachet.ryme.ryme.ui.view.eventAd;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.model.EventAd;


public class EventParentViewHolder extends ParentViewHolder implements EventAdViewHolderMvpView{

    @Inject EventAdViewHolderPresenter mEventAdViewHolderPresenter;

    ImageView mEventImageView;
    public TextView mEventDateTimeView;
    public TextView mEventFareView;
    public TextView mEventViewsView;
    public ImageView mEventExpandViewButton;
    private EventAd mEventAd;

    public EventParentViewHolder(View itemView) {
        super(itemView);
        mEventImageView = (ImageView) itemView.findViewById(R.id.event_photo_image_view);
        mEventViewsView = (TextView) itemView.findViewById(R.id.event_views_text_view);
        mEventDateTimeView = (TextView) itemView.findViewById(R.id.event_date_time_text_view);
        mEventFareView = (TextView) itemView.findViewById(R.id.event_price_text_view);
        mEventExpandViewButton = (ImageView) itemView.findViewById(R.id.event_expand_button);
        mEventExpandViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpanded()) {
                    collapseView();
                } else {
                    expandView();
                    mEventAdViewHolderPresenter.view(mEventAd);
                }
            }
        });
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void updateViews(long views) {
        mEventViewsView.setText((int) views);
    }

    public void bindEventHead(EventAd event) {
        mEventAd = event;
        mEventFareView.setText((int) event.fare);
        mEventViewsView.setText((int) event.views);
    }

    @Override
    public boolean shouldItemViewClickToggleExpansion() {
        return false;
    }

}

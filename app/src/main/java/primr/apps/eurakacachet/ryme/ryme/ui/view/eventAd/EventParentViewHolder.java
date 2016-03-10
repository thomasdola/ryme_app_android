package primr.apps.eurakacachet.ryme.ryme.ui.view.eventAd;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.squareup.picasso.Picasso;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.model.EventAd;


public class EventParentViewHolder extends ParentViewHolder{

    Picasso mPicasso;
    ImageView mEventImageView;
    public TextView mEventDateTimeView;
    public TextView mEventFareView;
    public TextView mEventViewsView;
    public ImageView mEventExpandViewButton;
    private EventAd mEventAd;

    public EventParentViewHolder(View itemView, Context context) {
        super(itemView);
        mPicasso = Picasso.with(context);
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
                }
            }
        });
    }

    public void bindEventHead(EventAd event) {
        mEventAd = event;
        mPicasso.load(event.getCover())
                .placeholder(R.drawable.wallpaper)
                .error(R.drawable.wallpaper)
                .into(mEventImageView);
        mEventFareView.setText((int) event.getFare());
        mEventViewsView.setText((int) event.getViews());
    }

    @Override
    public boolean shouldItemViewClickToggleExpansion() {
        return false;
    }

}

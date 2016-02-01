package primr.apps.eurakacachet.ryme.ryme.ui.view.eventAd;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;

import primr.apps.eurakacachet.ryme.ryme.R;


public class EventParentViewHolder extends ParentViewHolder {

    ImageView mEventImageView;
    public TextView mEventDateTimeView;
    public TextView mEventFareView;
    public TextView mEventViewsView;
    public ImageView mEventExpandViewButton;

    public EventParentViewHolder(View itemView) {
        super(itemView);
        mEventImageView = (ImageView) itemView.findViewById(R.id.event_photo_image_view);
        mEventExpandViewButton = (ImageView) itemView.findViewById(R.id.event_expand_button);
        mEventViewsView = (TextView) itemView.findViewById(R.id.event_views_text_view);
        mEventDateTimeView = (TextView) itemView.findViewById(R.id.event_date_time_text_view);
        mEventFareView = (TextView) itemView.findViewById(R.id.event_price_text_view);
    }
}

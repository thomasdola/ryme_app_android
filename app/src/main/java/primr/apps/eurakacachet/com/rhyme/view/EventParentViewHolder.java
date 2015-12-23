package primr.apps.eurakacachet.com.rhyme.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;

import primr.apps.eurakacachet.com.rhyme.R;


public class EventParentViewHolder extends ParentViewHolder {

    public ImageView mEventImageView;
    public TextView mEventTitleView;
    public TextView mEventDateTimeView;
    public TextView mEventFareView;
    public TextView mEventViewsView;
    public ImageView mEventExpandViewButton;

    public EventParentViewHolder(View itemView) {
        super(itemView);

        mEventImageView = (ImageView) itemView.findViewById(R.id.event_photo_image_view);
        mEventDateTimeView = (TextView) itemView.findViewById(R.id.event_date_time_text_view);
        mEventFareView = (TextView) itemView.findViewById(R.id.event_price_text_view);
        mEventViewsView = (TextView) itemView.findViewById(R.id.event_views_text_view);
        mEventExpandViewButton = (ImageView) itemView.findViewById(R.id.event_expand_button);
    }
}

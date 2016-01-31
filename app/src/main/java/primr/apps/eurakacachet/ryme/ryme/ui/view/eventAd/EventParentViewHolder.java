package primr.apps.eurakacachet.ryme.ryme.ui.view.eventAd;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;

import butterknife.Bind;
import butterknife.ButterKnife;
import primr.apps.eurakacachet.ryme.ryme.R;


public class EventParentViewHolder extends ParentViewHolder {

    @Bind(R.id.event_photo_image_view)
    ImageView mEventImageView;

    @Bind(R.id.event_date_time_text_view)
    public TextView mEventDateTimeView;

    @Bind(R.id.event_price_text_view)
    public TextView mEventFareView;

    @Bind(R.id.event_views_text_view)
    public TextView mEventViewsView;

    @Bind(R.id.event_expand_button)
    public ImageView mEventExpandViewButton;

    public EventParentViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}

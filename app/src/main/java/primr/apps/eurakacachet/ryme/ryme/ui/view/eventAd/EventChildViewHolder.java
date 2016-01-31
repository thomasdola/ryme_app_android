package primr.apps.eurakacachet.ryme.ryme.ui.view.eventAd;


import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;

import butterknife.Bind;
import butterknife.ButterKnife;
import primr.apps.eurakacachet.ryme.ryme.R;

public class EventChildViewHolder extends ChildViewHolder {

    @Bind(R.id.event_description)
    TextView mEventDescription;

    public EventChildViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }
}

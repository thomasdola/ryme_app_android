package primr.apps.eurakacachet.ryme.ryme.ui.view;


import android.view.View;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;

import primr.apps.eurakacachet.ryme.ryme.R;

public class EventChildViewHolder extends ChildViewHolder {

    public TextView mEventDescription;

    public EventChildViewHolder(View itemView) {
        super(itemView);

        mEventDescription = (TextView) itemView.findViewById(R.id.event_description);

    }
}

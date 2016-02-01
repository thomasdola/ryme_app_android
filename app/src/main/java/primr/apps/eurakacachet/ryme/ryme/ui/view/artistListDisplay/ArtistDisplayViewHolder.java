package primr.apps.eurakacachet.ryme.ryme.ui.view.artistListDisplay;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import primr.apps.eurakacachet.ryme.ryme.R;

/**
 * Created by GURU on 2/1/2016.
 */
public class ArtistDisplayViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView mArtistNameTextView;

    public ArtistDisplayViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        mArtistNameTextView = (TextView) itemView.findViewById(R.id.artist_name_text_view);
    }

    @Override
    public void onClick(View v) {

    }
}

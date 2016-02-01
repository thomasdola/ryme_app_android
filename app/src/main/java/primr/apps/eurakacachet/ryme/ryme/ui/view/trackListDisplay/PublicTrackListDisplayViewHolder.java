package primr.apps.eurakacachet.ryme.ryme.ui.view.trackListDisplay;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.model.Track;
import primr.apps.eurakacachet.ryme.ryme.ui.view.trackDisplay.PublicTrackDisplayActivity;


public class PublicTrackListDisplayViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private PublicTrackListDisplayFragment mPublicTrackListDisplayFragment;

    TextView mTrackTitleTextView;

    private Track mTrack;

    public PublicTrackListDisplayViewHolder(PublicTrackListDisplayFragment
                                                    publicTrackListDisplayFragment,
                                            View itemView, Track track) {
        super(itemView);
        mTrack = track;
        mTrackTitleTextView = (TextView) itemView.findViewById(R.id.track_title_text_view);
        mPublicTrackListDisplayFragment = publicTrackListDisplayFragment;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = PublicTrackDisplayActivity.newIntent(mPublicTrackListDisplayFragment.getActivity(), mTrack);
        String transitionName = mPublicTrackListDisplayFragment.getString(R.string.track_transition_string);
        View startView = mPublicTrackListDisplayFragment.getActivity().findViewById(R.id.track_cv);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                .makeSceneTransitionAnimation(mPublicTrackListDisplayFragment.getActivity(), startView, transitionName);
        ActivityCompat.startActivity(mPublicTrackListDisplayFragment.getActivity(), intent, optionsCompat.toBundle());
    }
}

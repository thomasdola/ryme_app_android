package primr.apps.eurakacachet.ryme.ryme.ui.view.artist.trackDisplay;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.model.Track;
import primr.apps.eurakacachet.ryme.ryme.ui.view.trackDisplay.PublicTrackDisplayActivity;


public class ArtistTrackDisplayViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    protected ArtistTrackListDisplayFragment mArtistTrackListDisplayFragment;
    protected TextView mTrackTitleTextView;
    protected TextView mTrackStreamTextView;
    protected TextView mTrackDownloadTextView;
    protected TextView mTrackFavoriteTextView;
    protected TextView mTrackReleasedDateTextView;
    protected Track mTrack;

    public ArtistTrackDisplayViewHolder(ArtistTrackListDisplayFragment artistTrackListDisplayFragment, View itemView) {
        super(itemView);
        mArtistTrackListDisplayFragment = artistTrackListDisplayFragment;
        itemView.setOnClickListener(this);

        mTrackTitleTextView = (TextView) itemView.findViewById(R.id.artist_track_title_text_view);
        mTrackStreamTextView = (TextView) itemView.findViewById(R.id.artist_track_stream_text);
        mTrackDownloadTextView = (TextView) itemView.findViewById(R.id.artist_track_download_text);
        mTrackFavoriteTextView = (TextView) itemView.findViewById(R.id.artist_track_favorite_text);
        mTrackReleasedDateTextView = (TextView) itemView.findViewById(R.id.artist_track_released_date);
    }

    @Override
    public void onClick(View v) {

        Intent intent = PublicTrackDisplayActivity.newIntent(mArtistTrackListDisplayFragment.getActivity(), mTrack);

        String transitionName = mArtistTrackListDisplayFragment.getString(R.string.track_transition_string);
        View startView = mArtistTrackListDisplayFragment.getActivity().findViewById(R.id.artist_track_cv);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                .makeSceneTransitionAnimation(mArtistTrackListDisplayFragment.getActivity(), startView, transitionName);
        ActivityCompat.startActivity(mArtistTrackListDisplayFragment.getActivity(), intent, optionsCompat.toBundle());
    }
}

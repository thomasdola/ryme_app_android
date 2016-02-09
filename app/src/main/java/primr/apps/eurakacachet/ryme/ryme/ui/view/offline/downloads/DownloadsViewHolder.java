package primr.apps.eurakacachet.ryme.ryme.ui.view.offline.downloads;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.model.DownloadedTrack;
import primr.apps.eurakacachet.ryme.ryme.ui.view.offline.trackDisplay.TrackDisplayActivity;


public class DownloadsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView mTrackTitle;
    TextView mTrackArtist;
    TextView mTrackDuration;
    ImageView mTrackCover;
    private DownloadedTrack mTrack;
    DownloadsFragment mDownloadsFragment;

    public DownloadsViewHolder(DownloadsFragment downloadsFragment, View itemView) {
        super(itemView);
        mDownloadsFragment = downloadsFragment;
        mTrackCover = (ImageView) itemView.findViewById(R.id.download_track_cover);
        mTrackArtist = (TextView) itemView.findViewById(R.id.download_track_artist_text_view);
        mTrackTitle = (TextView) itemView.findViewById(R.id.download_track_title_text_view);
        mTrackDuration = (TextView) itemView.findViewById(R.id.download_track_duration_text_view);
    }

    public void bindTrack(DownloadedTrack track){
        mTrack = track;
//        mTrackCover.setImageResource();
        mTrackArtist.setText(track.artist.toUpperCase());
        mTrackDuration.setText((int) track.duration);
        mTrackTitle.setText(track.title.toUpperCase());
    }

    @Override
    public void onClick(View v) {
        Intent intent = TrackDisplayActivity.newIntent(mDownloadsFragment.getActivity(), mTrack);
        String transitionName = mDownloadsFragment.getString(R.string.track_transition_string);
        View startView = mDownloadsFragment.getActivity().findViewById(R.id.download_track_card);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                .makeSceneTransitionAnimation(mDownloadsFragment.getActivity(), startView, transitionName);
        ActivityCompat.startActivity(mDownloadsFragment.getActivity(), intent, optionsCompat.toBundle());
    }
}

package primr.apps.eurakacachet.ryme.ryme.ui.view.offline.downloads;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.SavedTrack;
import primr.apps.eurakacachet.ryme.ryme.ui.view.offline.trackDisplay.TrackDisplayActivity;


public class DownloadsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView mTrackTitle;
    TextView mTrackArtist;
    TextView mTrackDuration;
    ImageView mTrackCover;
    private SavedTrack mTrack;
    DownloadsFragment mDownloadsFragment;
    Picasso mPicasso;
    private int mTrackPosition;

    public DownloadsViewHolder(DownloadsFragment downloadsFragment, View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        mPicasso = Picasso.with(downloadsFragment.getContext());
        mDownloadsFragment = downloadsFragment;
        mTrackCover = (ImageView) itemView.findViewById(R.id.download_track_cover);
        mTrackArtist = (TextView) itemView.findViewById(R.id.download_track_artist_text_view);
        mTrackTitle = (TextView) itemView.findViewById(R.id.download_track_title_text_view);
        mTrackDuration = (TextView) itemView.findViewById(R.id.download_track_duration_text_view);
    }

    public void bindTrack(SavedTrack track, int position){
        mTrack = track;
        mTrackPosition = position;
        File coverPath = new File(track.cover());
        mPicasso.load(coverPath).into(mTrackCover);
        mTrackArtist.setText(track.artist().toUpperCase());
        if(track.duration() != null){
            mTrackDuration.setText(Long.toString(track.duration()));
        }
        mTrackTitle.setText(track.title().toUpperCase());
    }

    @Override
    public void onClick(View v) {
        Intent intent = TrackDisplayActivity.newIntent(mDownloadsFragment.getActivity(), mTrack, mTrackPosition);
        String transitionName = mDownloadsFragment.getString(R.string.track_transition_string);
        View startView = mDownloadsFragment.getActivity().findViewById(R.id.download_track_card);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                .makeSceneTransitionAnimation(mDownloadsFragment.getActivity(), startView, transitionName);
        ActivityCompat.startActivity(mDownloadsFragment.getActivity(), intent, optionsCompat.toBundle());
    }
}

package primr.apps.eurakacachet.ryme.ryme.ui.view.trackListDisplay;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.model.Track;
import primr.apps.eurakacachet.ryme.ryme.ui.view.trackDisplay.PublicTrackDisplayActivity;


public class PublicTrackDisplayViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener, PublicTrackDisplayViewHolderMvpView {

    @Inject PublicTrackDisplayViewHolderPresenter mPresenter;

    private PublicTrackListDisplayFragment mPublicTrackListDisplayFragment;

    ImageView mTrackCover;
    TextView mTrackArtistAndTitleView;
    TextView mTrackReleaseDateView;
    TextView mTrackStreamsView;
    TextView mTrackLikesView;
    TextView mTrackDownloads;

    private Track mTrack;

    public PublicTrackDisplayViewHolder(PublicTrackListDisplayFragment
                                                fragment, View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        mPublicTrackListDisplayFragment = fragment;
        mTrackCover = (ImageView) itemView.findViewById(R.id.track_cover_avatar);
        mTrackArtistAndTitleView = (TextView) itemView.findViewById(R.id.track_title_text_view);
        mTrackReleaseDateView = (TextView) itemView.findViewById(R.id.track_release_date_text_view);
        mTrackStreamsView = (TextView) itemView.findViewById(R.id.info_track_stream_text);
        mTrackLikesView = (TextView) itemView.findViewById(R.id.info_track_favorite_text);
        mTrackDownloads = (TextView) itemView.findViewById(R.id.info_track_download_text);
    }

    @Override
    public void onClick(View v) {
        mPresenter.view(mTrack);
        Intent intent = PublicTrackDisplayActivity.newIntent(mPublicTrackListDisplayFragment.getActivity(), mTrack);
        String transitionName = mPublicTrackListDisplayFragment.getString(R.string.track_transition_string);
        View startView = mPublicTrackListDisplayFragment.getActivity().findViewById(R.id.track_cv);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                .makeSceneTransitionAnimation(mPublicTrackListDisplayFragment.getActivity(), startView, transitionName);
        ActivityCompat.startActivity(mPublicTrackListDisplayFragment.getActivity(), intent, optionsCompat.toBundle());
    }

    public void bindTrack(Track track){
        mTrack = track;
        //bind avatar with picasso library
//        mTrackCover.setImageResource();
        mTrackArtistAndTitleView.setText(getArtistAndTrackTitle(track));
        mTrackReleaseDateView.setText(formatDate(track.released_date));
        mTrackStreamsView.setText((int) track.streams);
        mTrackLikesView.setText((int) track.likes);
        mTrackDownloads.setText((int) track.downloads);
    }

    private String formatDate(Date date) {
        return null;
    }

    private String getArtistAndTrackTitle(Track track) {
        return track.artist_name.toUpperCase() + " - " + track.title.toUpperCase();
    }
}

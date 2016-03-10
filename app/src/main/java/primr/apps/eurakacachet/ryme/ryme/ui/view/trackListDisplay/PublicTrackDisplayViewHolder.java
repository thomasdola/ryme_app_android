package primr.apps.eurakacachet.ryme.ryme.ui.view.trackListDisplay;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.model.Track;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BaseActivity;
import primr.apps.eurakacachet.ryme.ryme.ui.view.trackDisplay.PublicTrackDisplayActivity;


public class PublicTrackDisplayViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener, PublicTrackDisplayViewHolderMvpView {

    @Inject PublicTrackDisplayViewHolderPresenter mPresenter;

    private PublicTrackListDisplayFragment mPublicTrackListDisplayFragment;
    Picasso mPicasso;

    ImageView mTrackCover;
    TextView mTrackArtistAndTitleView;
    RelativeTimeTextView mTrackReleaseDateView;
    TextView mTrackStreamsView;
    TextView mTrackLikesView;
    TextView mTrackDownloads;

    private Track mTrack;

    public PublicTrackDisplayViewHolder(PublicTrackListDisplayFragment
                                                fragment, View itemView) {
        super(itemView);
        ((BaseActivity)fragment.getActivity()).getActivityComponent().inject(this);
        mPresenter.attachView(this);
        mPicasso = Picasso.with(fragment.getActivity());
        itemView.setOnClickListener(this);
        mPublicTrackListDisplayFragment = fragment;
        mTrackCover = (ImageView) itemView.findViewById(R.id.track_cover_avatar);
        mTrackArtistAndTitleView = (TextView) itemView.findViewById(R.id.track_title_text_view);
        mTrackReleaseDateView = (RelativeTimeTextView) itemView.findViewById(R.id.track_release_date_text_view);
        mTrackStreamsView = (TextView) itemView.findViewById(R.id.info_track_stream_text);
        mTrackLikesView = (TextView) itemView.findViewById(R.id.info_track_favorite_text);
        mTrackDownloads = (TextView) itemView.findViewById(R.id.info_track_download_text);
    }

//    @Override
//    public void onClick(View v) {
//        mPresenter.view(mTrack);
//        Intent intent = PublicTrackDisplayActivity.newIntent(mPublicTrackListDisplayFragment.getActivity(), mTrack);
//        String transitionName = mPublicTrackListDisplayFragment.getString(R.string.track_transition_string);
//        View startView = mPublicTrackListDisplayFragment.getActivity().findViewById(R.id.track_cv);
//        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
//                .makeSceneTransitionAnimation(mPublicTrackListDisplayFragment.getActivity(), startView, transitionName);
//        ActivityCompat.startActivity(mPublicTrackListDisplayFragment.getActivity(), intent, optionsCompat.toBundle());
//    }

    public void bindTrack(Track track){
        mTrack = track;
        if( track.cover() != null ){
            mPicasso.load(track.cover())
                    .placeholder(R.drawable.wallpaper)
                    .error(R.drawable.wallpaper)
                    .into(mTrackCover);
            Log.d("adapter", track.cover() + " " + track.path());
        }else {
            mPicasso.load(R.drawable.wallpaper).into(mTrackCover);
        }
        mTrackArtistAndTitleView.setText(getArtistAndTrackTitle(track));
        mTrackReleaseDateView.setReferenceTime(track.released_date());
        mTrackStreamsView.setText(Long.toString(track.streams()));
        mTrackLikesView.setText(Long.toString(track.likes()));
        mTrackDownloads.setText(Long.toString(track.downloads()));
    }

    private String getArtistAndTrackTitle(Track track) {
        return track.artist_name() + " - " + track.title();
    }

    @Override
    public void onClick(View v) {
        mPresenter.view(mTrack);
        Intent intent = PublicTrackDisplayActivity.newIntent(mPublicTrackListDisplayFragment.getActivity(), mTrack);
        mPublicTrackListDisplayFragment.getActivity().startActivity(intent);
        mPublicTrackListDisplayFragment.getActivity()
                .overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }
}

package primr.apps.eurakacachet.ryme.ryme.ui.view.artist.trackDisplay;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.model.Track;
import primr.apps.eurakacachet.ryme.ryme.ui.base.BaseActivity;
import primr.apps.eurakacachet.ryme.ryme.ui.view.search.SearchableActivity;
import primr.apps.eurakacachet.ryme.ryme.ui.view.trackDisplay.PublicTrackDisplayActivity;


public class ArtistTrackDisplayViewHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener, ArtistTrackListViewHolderMvpView{

    @Inject ArtistTrackListViewHolderPresenter mPresenter;
    private  SearchableActivity mActivity;
    protected ArtistTrackListDisplayFragment mArtistTrackListDisplayFragment;
    protected TextView mTrackTitleTextView;
    protected TextView mTrackStreamTextView;
    protected TextView mTrackDownloadTextView;
    protected TextView mTrackFavoriteTextView;
    protected RelativeTimeTextView mTrackReleasedDateTextView;
    protected ImageView mTrackCoverImageView;
    protected SwitchCompat mDownloadableTrack;
    protected ImageView mPopMenu;
    protected Track mTrack;
    Picasso mPicasso;
    private boolean mIsArtistViewing;
    private PopupMenu mPopupMenu;


    public ArtistTrackDisplayViewHolder(ArtistTrackListDisplayFragment artistTrackListDisplayFragment, View itemView) {
        super(itemView);
        ((BaseActivity)artistTrackListDisplayFragment.getActivity()).getActivityComponent()
                .inject(this);
        mPresenter.attachView(this);
        mArtistTrackListDisplayFragment = artistTrackListDisplayFragment;
        mPicasso = Picasso.with(artistTrackListDisplayFragment.getContext());
        initViews(itemView);
    }

    public ArtistTrackDisplayViewHolder(SearchableActivity activity, View itemView){
        super(itemView);
        activity.getActivityComponent().inject(this);
        mPresenter.attachView(this);
        mActivity = activity;
        mPicasso = Picasso.with(activity);
        initViews(itemView);
    }

    public void initViews(View itemView) {
        mTrackTitleTextView = (TextView) itemView.findViewById(R.id.artist_track_title_text_view);
        mTrackCoverImageView = (ImageView) itemView.findViewById(R.id.track_cover_image_view);
        mTrackStreamTextView = (TextView) itemView.findViewById(R.id.artist_track_stream_text);
        mTrackDownloadTextView = (TextView) itemView.findViewById(R.id.artist_track_download_text);
        mTrackFavoriteTextView = (TextView) itemView.findViewById(R.id.artist_track_favorite_text);
        mTrackReleasedDateTextView = (RelativeTimeTextView) itemView.findViewById(R.id.artist_track_released_date);
        mDownloadableTrack = (SwitchCompat) itemView.findViewById(R.id.downloadable);
        mPopMenu = (ImageView) itemView.findViewById(R.id.popup_menu_track_card);

        itemView.setOnClickListener(this);
        setSwitchListener();
        setPopupMenuListener();
    }

    private void setPopupMenuListener() {
        mPopMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPopupMenu != null){
                    mPopupMenu.show();
                }
            }
        });
    }

    private void setSwitchListener() {
        mDownloadableTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.isTrackDownloadable(mTrack.uuid(), mDownloadableTrack.isChecked());
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        if(mArtistTrackListDisplayFragment != null){
            intent = PublicTrackDisplayActivity.newIntent(mArtistTrackListDisplayFragment.getActivity(), mTrack.uuid());
        }else if(mActivity != null){
            intent = PublicTrackDisplayActivity.newIntent(mActivity, mTrack.uuid());
        }

        String transitionName = mArtistTrackListDisplayFragment.getString(R.string.track_transition_string);
        View startView = mArtistTrackListDisplayFragment.getActivity().findViewById(R.id.artist_track_cv);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                .makeSceneTransitionAnimation(mArtistTrackListDisplayFragment.getActivity(), startView, transitionName);
        ActivityCompat.startActivity(mArtistTrackListDisplayFragment.getActivity(), intent, optionsCompat.toBundle());
    }

    public void bindTrack(Track track, boolean isArtistViewing) {
        mTrack = track;
        mIsArtistViewing = isArtistViewing;
        mPicasso.load(track.cover())
                .placeholder(R.drawable.wallpaper)
                .error(R.drawable.wallpaper)
                .into(mTrackCoverImageView);
        mTrackTitleTextView.setText(track.title());
        mTrackReleasedDateTextView.setReferenceTime(track.released_date());
        mTrackStreamTextView.setText(Long.toString(track.streams()));
        mTrackFavoriteTextView.setText(Long.toString(track.likes()));
        mTrackDownloadTextView.setText(Long.toString(track.downloads()));
        if(track.amTheOwner()){
            mDownloadableTrack.setVisibility(View.VISIBLE);
            mPopMenu.setVisibility(View.VISIBLE);
            mDownloadableTrack.setChecked(track.downloadable());
            mPopupMenu = new PopupMenu(mArtistTrackListDisplayFragment.getContext(), mPopMenu);
            mPopupMenu.inflate(R.menu.track_actions_menu);
            mPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menu_artist_share_track:
                            shareTrack();
                            return true;
                        default:
                            return true;
                    }
                }
            });
        }
    }

    private void shareTrack() {

    }

    public void bindTrack(Track track){
        Log.d("track" , track.toString());
        mTrack = track;
        mPicasso.load(track.cover())
                .placeholder(R.drawable.wallpaper)
                .error(R.drawable.wallpaper)
                .into(mTrackCoverImageView);
        mTrackTitleTextView.setText(track.title());
        mTrackReleasedDateTextView.setReferenceTime(track.released_date());
        mTrackStreamTextView.setText(Long.toString(track.streams()));
        mTrackFavoriteTextView.setText(Long.toString(track.likes()));
        mTrackDownloadTextView.setText(Long.toString(track.downloads()));
    }

    @Override
    public void disableSwitch() {
        mDownloadableTrack.setEnabled(false);
    }

    @Override
    public void enableSwitch() {
        mDownloadableTrack.setEnabled(true);
    }

    @Override
    public void showError() {
        Toast.makeText(mArtistTrackListDisplayFragment.getActivity(),
                "Network error. please try again later", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void checkSwitch(boolean isDownloadable) {
        mDownloadableTrack.setChecked(isDownloadable);
    }
}

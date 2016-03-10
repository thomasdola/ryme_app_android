package primr.apps.eurakacachet.ryme.ryme.ui.view.artistListDisplay;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.model.Artist;
import primr.apps.eurakacachet.ryme.ryme.ui.view.artist.profile.ArtistProfileActivity;


public class ArtistDisplayViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    protected TextView mArtistNameTextView;
    protected ImageView mArtistPhoto;
    private Artist mArtist;
    protected ArtistListDisplayFragment mArtistListDisplayFragment;
    Picasso mPicasso;

    public ArtistDisplayViewHolder(ArtistListDisplayFragment fragment, View itemView) {
        super(itemView);
        mPicasso = Picasso.with(fragment.getActivity());
        mArtistListDisplayFragment = fragment;
        itemView.setOnClickListener(this);
        mArtistPhoto = (ImageView) itemView.findViewById(R.id.artist_avatar);
        mArtistNameTextView = (TextView) itemView.findViewById(R.id.artist_name_text_view);
    }

    @Override
    public void onClick(View v) {
        Intent intent = ArtistProfileActivity.newIntent(mArtistListDisplayFragment.getActivity(),
                mArtist.uuid());
        mArtistListDisplayFragment.getActivity()
                .overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
        mArtistListDisplayFragment.getActivity().startActivity(intent);
    }

    public void bindArtist(Artist artist) {
        mArtist = artist;
        mPicasso.load(artist.profilPic())
                .placeholder(R.drawable.wallpaper)
                .error(R.drawable.wallpaper)
                .into(mArtistPhoto);
        mArtistNameTextView.setText(artist.stage_name());
    }
}

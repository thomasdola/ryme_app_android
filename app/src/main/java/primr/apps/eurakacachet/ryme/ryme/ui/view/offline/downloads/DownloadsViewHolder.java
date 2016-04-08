package primr.apps.eurakacachet.ryme.ryme.ui.view.offline.downloads;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.devbrackets.android.exomedia.util.TimeFormatUtil;
import com.squareup.picasso.Picasso;

import java.io.File;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.SavedTrack;


public class DownloadsViewHolder extends RecyclerView.ViewHolder{

    TextView mTrackTitle;
    TextView mTrackArtist;
    TextView mTrackDuration;
    ImageView mTrackCover;
    private SavedTrack mTrack;
    Context mCo;
    Picasso mPicasso;
    private int mTrackPosition;

    public DownloadsViewHolder(Context co, View itemView) {
        super(itemView);
        mPicasso = Picasso.with(co);
        mCo = co;
        mTrackCover = (ImageView) itemView.findViewById(R.id.track_cover_avatar);
        mTrackArtist = (TextView) itemView.findViewById(R.id.artist_name_view);
        mTrackTitle = (TextView) itemView.findViewById(R.id.track_title_text_view);
        mTrackDuration = (TextView) itemView.findViewById(R.id.track_duration_text_view);
    }

    public void bindTrack(SavedTrack track, int position){
        mTrack = track;
        mTrackPosition = position;
        File coverPath = new File(track.cover());
        mPicasso.load(coverPath).into(mTrackCover);
        mTrackArtist.setText(track.artist());
        mTrackDuration.setText(TimeFormatUtil.formatMs(track.duration()));
        mTrackTitle.setText(track.title().toUpperCase());
    }
}

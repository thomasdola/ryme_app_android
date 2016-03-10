package primr.apps.eurakacachet.ryme.ryme.ui.view.search;


import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.grobas.view.PolygonImageView;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.model.ArtistRequest;
import primr.apps.eurakacachet.ryme.ryme.ui.view.artistRequest.ArtistRequestViewFragment;

public class ArtistRequestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private static final String VOUCH_FOR_ARTIST = "vouch_for_artist";
    private final SearchableActivity mActivity;
    Picasso mPicasso;
    private String mViewerId;
    public TextView mUsername;
    public TextView mYes;
    public TextView mNo;
    public ImageView userAvatarView;
    private ArtistRequest mRequest;

    public ArtistRequestViewHolder(View itemView, SearchableActivity activity, String viewerId) {
        super(itemView);
        itemView.setOnClickListener(this);
        mActivity = activity;
        mViewerId = viewerId;
        mPicasso = Picasso.with(activity);
        mUsername = (TextView) itemView.findViewById(R.id.user_name_text_view);
        mYes = (TextView) itemView.findViewById(R.id.total_yes);
        mNo = (TextView) itemView.findViewById(R.id.total_no);
        userAvatarView = (PolygonImageView) itemView.findViewById(R.id.user_photo_image_view);
    }

    public void bindResult(ArtistRequest request){
        mRequest = request;
        mPicasso.load(request.getAvatar())
                .placeholder(R.drawable.wallpaper)
                .error(R.drawable.wallpaper)
                .into(userAvatarView);
        mUsername.setText(request.getStage_name());
        mYes.setText(Long.toString(request.getYes()));
        mNo.setText(Long.toString(request.getNo()));
    }

    @Override
    public void onClick(View v) {
        if(mRequest.isDeja_vu()){
            Log.d("search", "already vouched for " + mRequest.getStage_name());
        }
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        ArtistRequestViewFragment fragment = ArtistRequestViewFragment.newInstance(mRequest, mViewerId);
        fragment.show(fragmentManager, VOUCH_FOR_ARTIST);
    }
}

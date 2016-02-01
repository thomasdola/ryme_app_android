package primr.apps.eurakacachet.ryme.ryme.ui.view.artistListDisplay;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import primr.apps.eurakacachet.ryme.ryme.R;
import primr.apps.eurakacachet.ryme.ryme.data.model.Artist;


public class ArtistListDisplayAdapter extends RecyclerView.Adapter<ArtistDisplayViewHolder> {

    private ArtistListDisplayFragment mArtistListDisplayFragment;
    private List<Artist> mArtistList;

    public ArtistListDisplayAdapter(ArtistListDisplayFragment artistListDisplayFragment, List<Artist> artistArrayList) {
        mArtistListDisplayFragment = artistListDisplayFragment;
        mArtistList = artistArrayList;
    }

    @Override
    public ArtistDisplayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mArtistListDisplayFragment.getActivity());
        View view = layoutInflater.inflate(R.layout.artist_card_view, parent, false);

        return new ArtistDisplayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArtistDisplayViewHolder holder, int position) {
//            holder.mArtistNameTextView.setText(mArtistList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if( mArtistList != null ){
            return mArtistList.size();
        }
        return 0;
    }
}

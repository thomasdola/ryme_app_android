package primr.apps.eurakacachet.ryme.ryme.ui.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import primr.apps.eurakacachet.ryme.ryme.data.model.Artist;
import primr.apps.eurakacachet.ryme.ryme.utils.helpers.layout.MarginDecoration;
import primr.apps.eurakacachet.ryme.ryme.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArtistListDisplayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArtistListDisplayFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ARTIST_LIST = "artist_list";

    // TODO: Rename and change types of parameters
    private ArrayList<Artist> mArtistList;
    private RecyclerView mArtistListDisplayRecyclerView;
    private ArtistListDisplayAdapter mArtistListDisplayAdapter;


    public static ArtistListDisplayFragment newInstance(ArrayList<Artist> artistArrayList) {
        ArtistListDisplayFragment fragment = new ArtistListDisplayFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_ARTIST_LIST, artistArrayList);
        fragment.setArguments(args);
        return fragment;
    }

    public ArtistListDisplayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mArtistList = getArguments().getParcelableArrayList(ARG_ARTIST_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_artist_list_display, container, false);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        mArtistListDisplayRecyclerView = (RecyclerView) rootView.findViewById(R.id.artist_list_display_recycler_view);
        mArtistListDisplayRecyclerView.addItemDecoration(new MarginDecoration(getActivity()));
        mArtistListDisplayRecyclerView.setHasFixedSize(true);
        mArtistListDisplayRecyclerView.setLayoutManager(layoutManager);

        mArtistListDisplayAdapter = new ArtistListDisplayAdapter(mArtistList);
        mArtistListDisplayRecyclerView.setAdapter(mArtistListDisplayAdapter);
        return rootView;
    }

    private class ArtistDisplayViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mArtistNameTextView;

        public ArtistDisplayViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mArtistNameTextView = (TextView) itemView.findViewById(R.id.artist_name_text_view);
        }

        @Override
        public void onClick(View v) {

        }
    }


    private class ArtistListDisplayAdapter extends RecyclerView.Adapter<ArtistDisplayViewHolder>{

        private ArrayList<Artist> mArtistList;

        public ArtistListDisplayAdapter(ArrayList<Artist> artistArrayList){
            mArtistList = artistArrayList;
        }

        @Override
        public ArtistDisplayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.artist_card_view, parent, false);

            return new ArtistDisplayViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ArtistDisplayViewHolder holder, int position) {
//            holder.mArtistNameTextView.setText(mArtistList.get(position).getName());
        }

        @Override
        public int getItemCount() {
            return mArtistList.size();
        }
    }

}

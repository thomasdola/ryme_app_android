package primr.apps.eurakacachet.ryme.ryme.ui.view.offline.downloads;


import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

public class TrackTouchHelper  extends ItemTouchHelper.SimpleCallback{

    private DownloadsAdapter mAdapter;

    public TrackTouchHelper(DownloadsAdapter downloadsAdapter) {
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        mAdapter = downloadsAdapter;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target) {
        mAdapter.swap(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mAdapter.remove(viewHolder.getAdapterPosition());
    }
}

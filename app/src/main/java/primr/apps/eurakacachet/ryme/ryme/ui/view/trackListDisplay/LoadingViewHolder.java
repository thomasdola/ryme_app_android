package primr.apps.eurakacachet.ryme.ryme.ui.view.trackListDisplay;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import primr.apps.eurakacachet.ryme.ryme.R;


public class LoadingViewHolder extends RecyclerView.ViewHolder {

    public ProgressBar mProgressBar;

    public LoadingViewHolder(View itemView) {
        super(itemView);
        mProgressBar = (ProgressBar) itemView.findViewById(R.id.native_progressbar);
    }
}

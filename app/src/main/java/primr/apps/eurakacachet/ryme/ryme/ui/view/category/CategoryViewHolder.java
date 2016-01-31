package primr.apps.eurakacachet.ryme.ryme.ui.view.category;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import primr.apps.eurakacachet.ryme.ryme.R;


public class CategoryViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.category_name_text_view)
    TextView mCategoryName;

    @Bind(R.id.follow_category_button)
    private Button mFollowButton;

    public CategoryViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

}

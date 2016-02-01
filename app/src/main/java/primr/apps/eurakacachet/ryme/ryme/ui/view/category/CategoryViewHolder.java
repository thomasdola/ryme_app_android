package primr.apps.eurakacachet.ryme.ryme.ui.view.category;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import primr.apps.eurakacachet.ryme.ryme.R;


public class CategoryViewHolder extends RecyclerView.ViewHolder {


    TextView mCategoryName;
    Button mFollowButton;

    public CategoryViewHolder(View itemView) {
        super(itemView);
        mCategoryName = (TextView) itemView.findViewById(R.id.category_name_text_view);
        mFollowButton = (Button) itemView.findViewById(R.id.follow_category_button);
    }

}

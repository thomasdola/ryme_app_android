package primr.apps.eurakacachet.ryme.ryme.ui.view.category.inApp;

import java.util.List;

import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.Category;
import primr.apps.eurakacachet.ryme.ryme.ui.base.MvpView;


public interface StoredCategoriesMvpView extends MvpView {

    void setAdapter(List<Category> categories);


}

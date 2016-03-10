package primr.apps.eurakacachet.ryme.ryme.ui.view.category;

import java.util.List;

import primr.apps.eurakacachet.ryme.ryme.data.local.storio.models.Category;
import primr.apps.eurakacachet.ryme.ryme.ui.base.MvpView;


public interface CategoriesFragmentMvpView extends MvpView {

    void showLoading();

    void hideLoading();

    void setCategoriesAdapter(List<Category> categories);

    void showError();

    void launchMainActivity();

}

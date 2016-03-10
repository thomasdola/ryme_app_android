package primr.apps.eurakacachet.ryme.ryme.ui.view.search;

import java.util.List;

import primr.apps.eurakacachet.ryme.ryme.ui.base.MvpView;


public interface SearchableMvpView extends MvpView {

    void showLoading();

    void hideLoading();

    void setResults(List<Object> results);

    void setOops(boolean isError);

    void setUserId(String user_id);
}

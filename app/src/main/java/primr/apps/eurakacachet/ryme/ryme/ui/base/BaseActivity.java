package primr.apps.eurakacachet.ryme.ryme.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import primr.apps.eurakacachet.ryme.ryme.RymeApplication;
import primr.apps.eurakacachet.ryme.ryme.injection.component.ActivityComponent;
import primr.apps.eurakacachet.ryme.ryme.injection.component.DaggerActivityComponent;
import primr.apps.eurakacachet.ryme.ryme.injection.module.ActivityModule;


public class BaseActivity extends AppCompatActivity {

    private ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public ActivityComponent getActivityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(RymeApplication.get(this).getComponent())
                    .build();
        }
        return mActivityComponent;
    }

}

package primr.apps.eurakacachet.ryme.ryme.injection.component;


import dagger.Component;
import primr.apps.eurakacachet.ryme.ryme.injection.PerActivity;
import primr.apps.eurakacachet.ryme.ryme.injection.module.ActivityModule;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
//    void inject();
}

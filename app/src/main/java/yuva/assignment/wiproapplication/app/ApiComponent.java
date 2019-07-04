package yuva.assignment.wiproapplication.app;

import javax.inject.Singleton;

import dagger.Component;
import yuva.assignment.wiproapplication.diprovider.ApiModule;
import yuva.assignment.wiproapplication.diprovider.AppModule;
import yuva.assignment.wiproapplication.viewmodel.FactsViewModel;

@Singleton
@Component(modules = {AppModule.class, ApiModule.class})
public interface ApiComponent {
    void inject(FactsViewModel factsViewModel);

}

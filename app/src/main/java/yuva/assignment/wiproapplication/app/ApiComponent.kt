package yuva.assignment.wiproapplication.app

import javax.inject.Singleton

import dagger.Component
import yuva.assignment.wiproapplication.diprovider.ApiModule
import yuva.assignment.wiproapplication.diprovider.AppModule
import yuva.assignment.wiproapplication.diprovider.ViewModelModule
import yuva.assignment.wiproapplication.fragment.FactsFragment

@Singleton
@Component(modules = [AppModule::class, ApiModule::class, ViewModelModule::class])
interface ApiComponent {
    fun inject(fragment: FactsFragment)
}

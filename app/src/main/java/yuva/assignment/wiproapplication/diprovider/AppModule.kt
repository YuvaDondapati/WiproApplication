package yuva.assignment.wiproapplication.diprovider

import android.app.Application
import javax.inject.Singleton
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val mApplication: Application) {

    @Provides
    @Singleton
    fun provideApplication() = mApplication
}

package yuva.assignment.wiproapplication.app;

import android.app.Application;

import yuva.assignment.wiproapplication.diprovider.ApiModule;
import yuva.assignment.wiproapplication.diprovider.AppModule;

public class MyApplication extends Application {

    private static ApiComponent mApiComponent;
    private static MyApplication mInstance;

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mApiComponent = DaggerApiComponent.builder()
                .appModule(new AppModule(this))
                .apiModule(new ApiModule(Constant.BASE_URL))
                .build();

    }

    public static ApiComponent getNetComponent() {
        return mApiComponent;
    }
}
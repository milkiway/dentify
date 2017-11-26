package rxfirebase;

import android.support.annotation.NonNull;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import rx.Observable;

/**
 * Created by Nick Moskalenko on 24/05/2016.
 */
public class RxFirebaseConfig {

    @NonNull
    public static Observable<Void> fetch(@NonNull final FirebaseRemoteConfig config) {
        return Observable.create((Observable.OnSubscribe<Void>) subscriber -> RxHandler.assignOnTask(subscriber, config.fetch()));
    }

    @NonNull
    public static Observable<Void> fetch(@NonNull final FirebaseRemoteConfig config, final long cacheExpirationSeconds) {
        return Observable.create((Observable.OnSubscribe<Void>) subscriber -> RxHandler.assignOnTask(subscriber, config.fetch(cacheExpirationSeconds)));
    }

}

package rxfirebase;

import android.support.annotation.NonNull;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import rx.Observable;
import rx.functions.Func1;
import rx.subscriptions.Subscriptions;

/**
 * Created by Nick Moskalenko on 15/05/2016.
 */
public class RxFirebaseDatabase {

    @NonNull
    public static Observable<DataSnapshot> observeValueEvent(final Query query) {
        return Observable.create(subscriber -> {
            final ValueEventListener valueEventListener = query.addValueEventListener(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (!subscriber.isUnsubscribed()) {
                                subscriber.onNext(dataSnapshot);
                            }
                        }

                        @Override
                        public void onCancelled(final DatabaseError error) {
                            if (!subscriber.isUnsubscribed()) {
//                                Answers.getInstance().logCustom(new FirebaseDatabaseErrorEvent(error));
//                                subscriber.onError(new RxFirebaseDataException(error));
                            }
                        }
                    });

            subscriber.add(Subscriptions.create(() -> query.removeEventListener(valueEventListener)));
        });
    }

    @NonNull
    public static Observable<DataSnapshot> observeSingleValueEvent(@NonNull final Query query) {
        return Observable.create(subscriber -> query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(dataSnapshot);
                    subscriber.onCompleted();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                if (!subscriber.isUnsubscribed()) {
//                    subscriber.onError(new RxFirebaseDataException(error));
//                    Answers.getInstance().logCustom(new FirebaseDatabaseErrorEvent(error));
                }
            }
        }));
    }

    @NonNull
    public static Observable<RxFirebaseChildEvent<DataSnapshot>> observeChildEvent(
            @NonNull final Query query) {
        return Observable.create(subscriber -> {
            final ChildEventListener childEventListener = query.addChildEventListener(
                    new ChildEventListener() {

                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                            if (!subscriber.isUnsubscribed()) {
                                subscriber.onNext(
                                        new RxFirebaseChildEvent<>(dataSnapshot.getKey(), dataSnapshot, previousChildName,
                                                RxFirebaseChildEvent.EventType.ADDED));
                            }
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                            if (!subscriber.isUnsubscribed()) {
                                subscriber.onNext(
                                        new RxFirebaseChildEvent<>(dataSnapshot.getKey(), dataSnapshot, previousChildName,
                                                RxFirebaseChildEvent.EventType.CHANGED));
                            }
                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {
                            if (!subscriber.isUnsubscribed()) {
                                subscriber.onNext(new RxFirebaseChildEvent<>(dataSnapshot.getKey(), dataSnapshot,
                                        RxFirebaseChildEvent.EventType.REMOVED));
                            }
                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                            if (!subscriber.isUnsubscribed()) {
                                subscriber.onNext(
                                        new RxFirebaseChildEvent<>(dataSnapshot.getKey(), dataSnapshot, previousChildName,
                                                RxFirebaseChildEvent.EventType.MOVED));
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            if (!subscriber.isUnsubscribed()) {
//                                        Answers.getInstance().logCustom(new FirebaseDatabaseErrorEvent(error));
//                                subscriber.onError(new RxFirebaseDataException(error));
                            }
                        }
                    });

            subscriber.add(Subscriptions.create(() -> query.removeEventListener(childEventListener)));
        });
    }

    @NonNull
    public static <T> Observable<T> observeValueEvent(@NonNull final Query query,
                                                      @NonNull final Class<T> clazz) {
        return observeValueEvent(query, DataSnapshotMapper.of(clazz));
    }

    @NonNull
    public static <T> Observable<T> observeSingleValueEvent(@NonNull final Query query,
                                                            @NonNull final Class<T> clazz) {
        return observeSingleValueEvent(query, DataSnapshotMapper.of(clazz));
    }

    @NonNull
    public static <T> Observable<RxFirebaseChildEvent<T>> observeChildEvent(
            @NonNull final Query query, @NonNull final Class<T> clazz) {
        return observeChildEvent(query, DataSnapshotMapper.ofChildEvent(clazz));
    }

    @NonNull
    public static <T> Observable<T> observeValueEvent(@NonNull final Query query,
                                                      @NonNull final Func1<? super DataSnapshot, ? extends T> mapper) {
        return observeValueEvent(query).map(mapper);
    }

    @NonNull
    public static <T> Observable<T> observeSingleValueEvent(@NonNull final Query query,
                                                            @NonNull final Func1<? super DataSnapshot, ? extends T> mapper) {
        return observeSingleValueEvent(query).map(mapper);
    }

    @NonNull
    public static <T> Observable<RxFirebaseChildEvent<T>> observeChildEvent(
            @NonNull final Query query, @NonNull final Func1<? super RxFirebaseChildEvent<DataSnapshot>, ? extends RxFirebaseChildEvent<T>> mapper) {
        return observeChildEvent(query).map(mapper);
    }
}
